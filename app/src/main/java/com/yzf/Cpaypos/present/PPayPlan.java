package com.yzf.Cpaypos.present;

import android.text.TextUtils;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.Des3;
import com.yzf.Cpaypos.model.CgiQuickPay;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetCodeResults;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.BindCardActivity;
import com.yzf.Cpaypos.ui.activitys.MainActivity;
import com.yzf.Cpaypos.ui.activitys.PayPlanActivity;
import com.yzf.Cpaypos.ui.activitys.PaySuccessActivity;
import com.yzf.Cpaypos.ui.activitys.TransDetailActivity;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PPayPlan
 * Description：支付保证金逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 14:31
 * Modified By：
 * Fixtime：2017/4/1 14:31
 * FixDescription：
 */

public class PPayPlan extends XPresent<PayPlanActivity> {
    /**
     * 获取验证码
     *
     * @param phoneNo
     */
    public void getCode(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            getV().showToast("请输入手机号");
            return;
        }
        if (phoneNo.startsWith("170") || phoneNo.startsWith("171")) {
            getV().showToast("暂不支持170、171号段的手机号码");
            return;
        }
        // 判断是否是合法的手机号码
        if (!AppTools.isMobile(phoneNo)) {
            getV().showToast("手机号码格式不正确");
            return;
        }
        Api.getAPPService().getCode(phoneNo, "1")
                .compose(XApi.<GetCodeResults>getApiTransformer())
                .compose(XApi.<GetCodeResults>getScheduler())
                .compose(getV().<GetCodeResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetCodeResults>() {
                    @Override
                    public void onNext(GetCodeResults getCodeResults) {
                        getV().showToast(getCodeResults.getRespMsg());
                        getV().startTimer();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 支付保证金
     *
     * @param merchId
     * @param cardId
     * @param orderId
     */
    public void PayPlanCard(String merchId, String cardId, String orderId,String confirmType,String code) {
        if (AppTools.isEmpty(merchId)) {
            getV().showToast("商户号为空");
            return;
        }
        if (AppTools.isEmpty(cardId)) {
            getV().showToast("卡号为空");
            return;
        }
        if (AppTools.isEmpty(orderId)) {
            getV().showToast("规划单号为空");
            return;
        }if (AppTools.isEmpty(confirmType)) {
            getV().showToast("确认类型为空");
            return;
        }if (AppTools.isEmpty(code)) {
            getV().showToast("验证码为空");
            return;
        }

        Api.getAPPService().PayPlanCard(merchId, cardId, orderId,confirmType,code)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            IBus.IEvent iEvent = new IEvent();
                            iEvent.setId("refresh_plan");//交易成功后发消息去更新金额
                            BusProvider.getBus().post(iEvent);
                            getV().JumpActivity(PaySuccessActivity.class,true);
                        }else if (baseResults.getRespCode().equals("0002")) {
                            getV().showToast(baseResults.getRespMsg());
                        }else {
                            getV().showToast(baseResults.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * cgi快捷交易
     *
     * @param cgiQuickPay
     */
    public void cgiQuickPay(CgiQuickPay cgiQuickPay) {
        String ORDER_AMT = AppTools.formatAmt(cgiQuickPay.getOrderAmt());
        final String ORDER_ID = AppTools.craeateOrderId();
        AppUser.getInstance().setOrderId(ORDER_ID);
        String ORDER_TIME = Kits.Date.getyyyyMMddHHmmss();
        final String USER_ID = AppUser.getInstance().getUserId();
        String SIGN_TYPE = "03";
        String BUS_CODE = null;
//        BUS_CODE = "1031";
        BUS_CODE = "7801";
        String USER_TYPE = "02";
        String CCT = "CNY";
        String CARD_NO = cgiQuickPay.getCardNo();
        String CVN2 = cgiQuickPay.getCVN2();
        String EXPDATE = cgiQuickPay.getExpDate();
        String PHONE_NO = cgiQuickPay.getPhoneNo();
        String ID_NO = cgiQuickPay.getIdNo();
        String NAME = cgiQuickPay.getName();
        String SMS_CODE=cgiQuickPay.getCode();
       /* String sign = orderId + orderAmt + userId + busCode;
        sign = AppTools.appEncrypt(sign);*/

        Api.getAPPService().cgiQuickPay(ORDER_ID, ORDER_AMT, ORDER_TIME, USER_TYPE, USER_ID, SIGN_TYPE, BUS_CODE, CCT, CARD_NO, CVN2, EXPDATE, PHONE_NO, ID_NO, NAME,SMS_CODE)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
//                        getV().showToast(baseResults.getRespMsg());//交易成功后发消息去更新金额
                        if (baseResults.getRespCode().equals("00")) {
                            GetOrderList(1, 10, "", "", USER_ID, "", "", ORDER_ID, "");
                        } else {
                            getV().showToast(baseResults.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 获取订单信息
     *
     * @param serviceId
     */
    public void GetOrderList(final int page, int pageNum, String beginTime, String endTime, String merchId, String serviceId, String result, String orderId, String notServiceId) {
        {
            Api.getAPPService().getOrderList(page, pageNum, beginTime, endTime, merchId, serviceId, result, orderId, notServiceId)
                    .compose(XApi.<GetOrderListResult>getApiTransformer())
                    .compose(XApi.<GetOrderListResult>getScheduler())
                    .compose(getV().<GetOrderListResult>bindToLifecycle())
                    .subscribe(new ApiSubcriber<GetOrderListResult>() {
                        @Override
                        public void onNext(GetOrderListResult getOrderListResult) {
                            if (getOrderListResult.getRespCode().equals("00")) {
                                GetOrderListResult.DataBean dataBean = getOrderListResult.getData().get(0);
                                if (!dataBean.getResultCode().equals("00")) {
                                    if (dataBean.getResultCode().equals("01")) {
                                        IBus.IEvent iEvent = new IEvent();
                                        iEvent.setId("refresh_money");
                                        BusProvider.getBus().post(iEvent);
                                        getV().JumpActivity(TransDetailActivity.class, true, dataBean);
                                    } else {
                                        getV().showToast(dataBean.getResult());
                                    }

                                }
                            }
                        }

                        @Override
                        protected void onFail(NetError error) {
                            getV().showError(error);
                        }

                    });
        }
    }
}
