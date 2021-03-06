package com.yzf.Cpaypos.present;

import android.os.Handler;

import com.google.gson.Gson;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.CgiQuickPay;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.model.servicesmodels.TradeResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.AgenWebViewActivity;
import com.yzf.Cpaypos.ui.activitys.InputMoneyActivity;
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
 * ClaseName：PInputMoney
 * Description：我要收款逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/20 10:34
 * Modified By：
 * Fixtime：2017/3/20 10:34
 * FixDescription：
 */

public class PInputMoney extends XPresent<InputMoneyActivity> {
    private Handler handlerdelay = new Handler();

    /**
     * 正扫
     *
     * @param serviceId
     */
    public void positiveScan(String serviceId, String orderAmt) {
        if (AppTools.isEmpty(orderAmt)) {
            getV().showToast("请输入金额");
            return;
        }
        orderAmt = AppTools.formatAmt(orderAmt);
        String orderId = AppTools.craeateOrderId();
        AppUser.getInstance().setOrderId(orderId);
        String userId = AppUser.getInstance().getUserId();
        String busCode = null;
        if (serviceId.equals(AppConfig.WXZS)) {//微信支付(正扫)
            busCode = AppConfig.serviceType.get(AppConfig.WXZS);
        } else if (serviceId.equals(AppConfig.ZFBZS)) {//支付宝支付(正扫)
            busCode = AppConfig.serviceType.get(AppConfig.ZFBZS);
        }
        String authCode = "";
        String pan = "";
        String goodsName = "";
        String goodsDesc = "";
        String sign = orderId + orderAmt + userId + busCode;
        sign = AppTools.appEncrypt(sign);

        final String finalOrderAmt = orderAmt;
        Api.getAPPService().trade(orderId, orderAmt, userId, sign, busCode, authCode, pan, goodsName, goodsDesc)
                .compose(XApi.<TradeResult>getApiTransformer())
                .compose(XApi.<TradeResult>getScheduler())
                .compose(getV().<TradeResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<TradeResult>() {
                    @Override
                    public void onNext(TradeResult tradeResult) {
                        if (tradeResult.getRespCode().equals("00")) {
                            String sign = tradeResult.getData().getOrderId() + tradeResult.getData().getOrderAmt() + AppUser.getInstance().getUserId() + tradeResult.getData().getBusCode()
                                    + tradeResult.getData().getRespCode() + tradeResult.getData().getRespDesc();
                            sign = AppTools.appEncrypt(sign);
                            if (tradeResult.getData().getSign().equals(sign)) {
                                getV().showQrCodeActivity(tradeResult.getData().getCodeUrl(), finalOrderAmt);
                            } else {
                                getV().showToast("返回数据验签失败");
                            }
                        } else {
                            getV().showToast(tradeResult.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 反扫
     *
     * @param serviceId
     */
    public void reverseScan(String serviceId, String orderAmt, String authCode) {
        if (AppTools.isEmpty(orderAmt)) {
            getV().showToast("请输入金额");
            return;
        }
        orderAmt = AppTools.formatAmt(orderAmt);
        final String orderId = AppTools.craeateOrderId();
        AppUser.getInstance().setOrderId(orderId);
        final String userId = AppUser.getInstance().getUserId();
        String busCode = null;
        if (serviceId.equals(AppConfig.WXFS)) {//微信支付(反扫)
            busCode = AppConfig.serviceType.get(AppConfig.WXFS);
        } else if (serviceId.equals(AppConfig.ZFBFS)) {//支付宝支付(反扫)
            busCode = AppConfig.serviceType.get(AppConfig.ZFBFS);
        }
        String pan = "";
        String goodsName = "";
        String goodsDesc = "";
        String sign = orderId + orderAmt + userId + busCode;
        sign = AppTools.appEncrypt(sign);

        Api.getAPPService().trade(orderId, orderAmt, userId, sign, busCode, authCode, pan, goodsName, goodsDesc)
                .compose(XApi.<TradeResult>getApiTransformer())
                .compose(XApi.<TradeResult>getScheduler())
                .compose(getV().<TradeResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<TradeResult>() {
                    @Override
                    public void onNext(TradeResult tradeResult) {
//                        GetOrderListResult.DataBean dataBean = new GetOrderListResult.DataBean();
                        if (tradeResult.getData() != null) {
                           /* IBus.IEvent iEvent = new IEvent();
                            iEvent.setId("refresh_money");//交易成功后发消息去更新金额
                            BusProvider.getBus().post(iEvent);*/
                           /* dataBean.setOrderId(tradeResult.getData().getOrderId());
                            dataBean.setMerchId(tradeResult.getData().getUserId());
                            if (tradeResult.getData().getBusCode().equals("3108")) {
                                dataBean.setServiceId("13");
                                dataBean.setServiceName("支付宝扫一扫支付");
                            } else if (tradeResult.getData().getBusCode().equals("3206")) {
                                dataBean.setServiceId("12");
                                dataBean.setServiceName("微信扫一扫支付");
                            }
                            dataBean.setResult(tradeResult.getData().getRespDesc());
                            dataBean.setAmt(tradeResult.getData().getOrderAmt());
                            dataBean.setTransTime(tradeResult.getData().getOrderTime());
                            dataBean.setFee("*");
                            getV().JumpActivity(TransDetailActivity.class, true, dataBean);//组成tradeResult并跳转到订单详情页*/
                            GetOrderList(1, 10, "", "", userId, "", "", orderId, "");
                        } else {
                            getV().showErrorDialog(tradeResult.getRespMsg());
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
                                        getV().showErrorDialog(dataBean.getResult());
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

    /**
     * 提现
     *
     * @param serviceId
     */
    public void withDraw(String serviceId, String orderAmt) {
        orderAmt = AppTools.formatAmt(orderAmt);
        String orderId = AppTools.craeateOrderId();
        AppUser.getInstance().setOrderId(orderId);
        String userId = AppUser.getInstance().getUserId();
        String busCode = null;
        if (serviceId.equals(AppConfig.TIXIAN)) {
            busCode = AppConfig.serviceType.get(AppConfig.TIXIAN);
        }
        String authCode = "";
        String pan = "";
        String goodsName = "";
        String goodsDesc = "";
        String sign = orderId + orderAmt + userId + busCode;
        sign = AppTools.appEncrypt(sign);

        Api.getAPPService().trade(orderId, orderAmt, userId, sign, busCode, authCode, pan, goodsName, goodsDesc)
                .compose(XApi.<TradeResult>getApiTransformer())
                .compose(XApi.<TradeResult>getScheduler())
                .compose(getV().<TradeResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<TradeResult>() {
                    @Override
                    public void onNext(TradeResult tradeResult) {
                        IBus.IEvent iEvent = new IEvent();
                        iEvent.setId("refresh_money");
                        BusProvider.getBus().post(iEvent);
                        getV().showToast(tradeResult.getRespMsg());//交易成功后发消息去更新金额
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
        BUS_CODE = "1031";
        String USER_TYPE = "02";
        String CCT = "CNY";
        String CARD_NO = cgiQuickPay.getCardNo();
        String CVN2 = cgiQuickPay.getCVN2();
        String EXPDATE = cgiQuickPay.getExpDate();
        String PHONE_NO = cgiQuickPay.getPhoneNo();
        String ID_NO = cgiQuickPay.getIdNo();
        String NAME = cgiQuickPay.getName();
       /* String sign = orderId + orderAmt + userId + busCode;
        sign = AppTools.appEncrypt(sign);*/

        Api.getAPPService().cgiQuickPay(ORDER_ID, ORDER_AMT, ORDER_TIME, USER_TYPE, USER_ID, SIGN_TYPE, BUS_CODE, CCT, CARD_NO, CVN2, EXPDATE, PHONE_NO, ID_NO, NAME,"")
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
//                        getV().showToast(baseResults.getRespMsg());//交易成功后发消息去更新金额
                        if (baseResults.getRespCode().equals("00")) {
                            getV().showToast(baseResults.getRespMsg());
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
     * 路由跳转
     *
     * @param serviceId
     * @param orderAmt
     */
    public void route(String serviceId, String orderAmt) {
        if (AppTools.isEmpty(orderAmt)) {
            getV().showToast("请输入金额");
            return;
        }
        AppUser.getInstance().setAMT(orderAmt);
        if (serviceId.equals(AppConfig.TIXIAN)) {//提现
            withDraw(serviceId, orderAmt);
        }
        if (serviceId.equals(AppConfig.KJZFH5) || serviceId.equals(AppConfig.SSKKXG)) {//银联快捷
            getV().JumpActivity(AgenWebViewActivity.class, false, serviceId);
        }
    }

    /**
     * 获取白名单卡信息
     *
     * @param merchId
     */
    public void getWhiteCardList(String merchId) {
        Api.getAPPService().getWhiteCardList(merchId)
                .compose(XApi.<GetWhiteCardListResult>getApiTransformer())
                .compose(XApi.<GetWhiteCardListResult>getScheduler())
                .compose(getV().<GetWhiteCardListResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetWhiteCardListResult>() {
                    @Override
                    public void onNext(GetWhiteCardListResult getWhiteCardListResult) {
                        Gson gson = new Gson();
                        AppUser.getInstance().setCardWhiteInfo(gson.toJson(getWhiteCardListResult.getData()));
                        getV().showCardDialog(getWhiteCardListResult.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }


}
