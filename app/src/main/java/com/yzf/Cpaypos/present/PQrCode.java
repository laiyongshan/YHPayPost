package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.QrCodeActivity;
import com.yzf.Cpaypos.ui.activitys.TransDetailActivity;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PQrCode
 * Description：二维码逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/20 10:34
 * Modified By：
 * Fixtime：2017/3/20 10:34
 * FixDescription：
 */

public class PQrCode extends XPresent<QrCodeActivity> {

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
                            } else {
                                getV().showErrorDialog(getOrderListResult.getRespMsg());
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
