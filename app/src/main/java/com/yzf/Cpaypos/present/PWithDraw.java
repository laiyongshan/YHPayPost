package com.yzf.Cpaypos.present;

import com.google.gson.Gson;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppKit;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.Des3;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchInfoResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.WithDrawActivity;

import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PWithDraw
 * Description：提现逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/8 14:52
 * Modified By：
 * Fixtime：2017/5/8 14:52
 * FixDescription：
 */

public class PWithDraw extends XPresent<WithDrawActivity> {
    /**
     * 提现
     *
     * @param serviceId
     */
    public void withDraw(String serviceId, String orderAmt) {
        if (AppTools.isEmpty(orderAmt)) {
            getV().showToast("请输入金额");
            return;
        }
        if (orderAmt.contains("-")) {//全部提现传进来是负值
            getV().showToast("您的余额不足以提现");
            return;
        }
        orderAmt = AppTools.formatAmt(orderAmt);
        String busCode = null;
        if (serviceId.equals(AppConfig.TIXIAN)) {//一般提现
            busCode = AppConfig.serviceType.get(AppConfig.TIXIAN);
            if (AppKit.subString(AppUser.getInstance().getAVL_AMT(), AppKit.addString(orderAmt, AppUser.getInstance().getFee())) < 0) {
                Double tempstr = AppKit.subString(AppUser.getInstance().getAVL_AMT(), AppUser.getInstance().getFee());
                if (tempstr < 0) {
                    getV().showToast("您的余额不足以提现");
                } else {
                    getV().showToast("最多只能提现" + tempstr + "元");
                }
                return;
            }
        }
        String pan = "";
        if (serviceId.equals(AppConfig.SYTIXIAN)) {//收益提现
            busCode = AppConfig.serviceType.get(AppConfig.SYTIXIAN);
            pan = "3";
            if (AppKit.subString(AppUser.getInstance().getSY_AMT(), AppKit.addString(orderAmt, AppUser.getInstance().getFee())) < 0) {
                Double tempstr = AppKit.subString(AppUser.getInstance().getSY_AMT(), AppUser.getInstance().getFee());
                if (tempstr < 0) {
                    getV().showToast("您的余额不足以提现");
                } else {
                    getV().showToast("最多只能提现" + tempstr + "元");
                }
                return;
            }
        }
        String ORDER_ID = AppTools.craeateOrderId();
        AppUser.getInstance().setOrderId(ORDER_ID);
        String ORDER_TIME = Kits.Date.getyyyyMMddHHmmss();
        String USER_ID = AppUser.getInstance().getUserId();
        String SIGN_TYPE = "03";
        String BUS_CODE = null;
        BUS_CODE = "1006";
        String USER_TYPE = "02";

        Api.getAPPService().withDraw(ORDER_ID, orderAmt, ORDER_TIME, USER_TYPE, USER_ID, SIGN_TYPE, BUS_CODE)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        getV().showToast(baseResults.getRespMsg());//交易成功后发消息去更新金额
                        if(baseResults.getRespCode().equals("00"))
                        {
                            getV().finishActivity(baseResults.getRespMsg());
                        }else {
                            getV().showToast("交易失败："+baseResults.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });

    }

    /**
     * 获取商户信息
     *
     * @param merchid
     */
    public void getMerchInfo(String merchid) {

        Api.getAPPService().getMerchInfo(merchid)
                .compose(XApi.<GetMerchInfoResult>getApiTransformer())
                .compose(XApi.<GetMerchInfoResult>getScheduler())
                .compose(getV().<GetMerchInfoResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetMerchInfoResult>() {
                    @Override
                    public void onNext(GetMerchInfoResult getMerchInfoResult) {
                        if (getMerchInfoResult.getRespCode().equals("00")) {
                            try {
                                String status = getMerchInfoResult.getData().getMerchStatus();
                                getMerchInfoResult.getData().setMerchStatus(setStatus(status));
                                String idcard = getMerchInfoResult.getData().getIdCard();
                                idcard = Des3.decode(idcard);
                                getMerchInfoResult.getData().setIdCard(idcard);
                                String phone = getMerchInfoResult.getData().getPhoneNo();
                                phone = Des3.decode(phone);
                                getMerchInfoResult.getData().setPhoneNo(phone);
                                String acctno = getMerchInfoResult.getData().getAcctNo();
                                acctno = Des3.decode(acctno);
                                getMerchInfoResult.getData().setAcctNo(acctno);
                                Gson gson = new Gson();
                                AppUser.getInstance().setSettleInfo(gson.toJson(getMerchInfoResult.getData()));//将结算信息存到单例中
                                getV().setSettleInfo(getMerchInfoResult.getData());
                            } catch (Exception e) {
                                e.printStackTrace();
                                getV().showErrorDialog("验签失败，请到首页刷新或重新登录");
                            }
                        } else {
                            getV().showErrorDialog(getMerchInfoResult.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }
    public String setStatus(String status) {
        if (!AppTools.isEmpty(status)) {
            if (status.equals("00")) {
                status = "已实名认证";
            } else if (status.equals("01")) {
                status = "认证信息不全";
            } else if (status.equals("02")) {
                status = "认证图片不全";
            } else if (status.equals("03")) {
                status = "未认证";
            } else if (status.equals("10")) {
                status = "已停用";
            } else if (status.equals("30")) {
                status = "审核中";
            }
        }
        return status;
    }
}
