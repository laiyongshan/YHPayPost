package com.yzf.Cpaypos.present;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.Des3;
import com.yzf.Cpaypos.model.CgiQuickPay;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchInfoResult;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.BankCardActivity;
import com.yzf.Cpaypos.ui.activitys.CreditWithDrawActivity;
import com.yzf.Cpaypos.ui.activitys.TransDetailActivity;

import org.json.JSONException;

import java.net.UnknownHostException;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ClaseName：PBankCard
 * Description：银行卡管理逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 11:50
 * Modified By：
 * Fixtime：2017/4/1 11:50
 * FixDescription：
 */

public class PCreditWithDraw extends XPresent<CreditWithDrawActivity> {

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

    public void gethtml(String card_no)
    {
        String ORDER_AMT = AppUser.getInstance().getAMT();
        if (!AppTools.isEmpty(ORDER_AMT)) {
            ORDER_AMT = AppTools.formatAmt(ORDER_AMT);
        }
        String ORDER_ID = AppTools.craeateOrderId();
        AppUser.getInstance().setOrderId(ORDER_ID);
        String ORDER_TIME = Kits.Date.getyyyyMMddHHmmss();
        String USER_ID = AppUser.getInstance().getUserId();
        String SIGN_TYPE = "03";
//        String BUS_CODE = "3001";
        String BUS_CODE = "7601";
        String USER_TYPE = "02";
        String CCT = "CNY";
        String CARD_NO = card_no;
        String PAY_TYPE = "02";

        Api.getAPPService().h5QuickPay(ORDER_ID, ORDER_AMT, ORDER_TIME, USER_TYPE, USER_ID, SIGN_TYPE, BUS_CODE, CCT, CARD_NO, PAY_TYPE)//返回的是string，不是json
                .compose(getV().<String>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        NetError error = null;
                        if (!(e instanceof NetError)) {
                            if (e instanceof UnknownHostException) {
                                error = new NetError(e, NetError.NoConnectError);
                            } else if (e instanceof JSONException
                                    || e instanceof JsonParseException
                                    || e instanceof JsonSyntaxException) {
                                error = new NetError(e, NetError.ParseError);
                            } else {
                                error = new NetError(e, NetError.OtherError);
                            }
                        } else {
                            error = (NetError) e;
                        }
                        getV().showError(error);
                    }

                    @Override
                    public void onNext(String result) {
                        getV().toWebView(result);
                    }
                });
    }




}
