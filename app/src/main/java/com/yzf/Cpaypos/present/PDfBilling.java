package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.fragments.DfBillingFragment;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃ + +
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃
 * 　　　┃　　　┃ + + + +
 * 　　　┃　　　┃
 * 　　　┃　　　┃ +  神兽镇楼
 * 　　　┃　　　┃    代码无bug
 * 　　　┃　　　┃　　+
 * 　　　┃　 　　┗━━━┓ + +
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * ClassName：PDfBilling
 * Description: 提现账单逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/20 9:18
 * Modified By：
 * Fixtime：2017/3/20 9:18
 * FixDescription：
 */
public class PDfBilling extends XPresent<DfBillingFragment> {


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
                                getV().setAdapter(getOrderListResult, page);
                            } else {
                                getV().showErrorView(getOrderListResult.getRespMsg());
                            }

                        }

                        @Override
                        protected void onFail(NetError error) {
                            getV().showErrorView(error);
                        }

                    });
        }
    }

}
