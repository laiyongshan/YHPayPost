package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetPlanCardResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.PlanCardDetailActivity;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PBindCard
 * Description：绑卡逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 14:31
 * Modified By：
 * Fixtime：2017/4/1 14:31
 * FixDescription：
 */

public class PPlanCardDetail extends XPresent<PlanCardDetailActivity> {
    /**
     * 养卡规划确认
     *
     * @param merchId
     * @param cardId
     * @param orderId
     */
    public void ConfirmPlanCard(String merchId, String cardId, String orderId,String confirmType ) {
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
        }

        Api.getAPPService().ConfirmPlanCard(merchId, cardId, orderId,confirmType)
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
                            getV().finishActivity(baseResults.getRespMsg());
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
     * 获取详细的养卡规划
     *
     * @param orderId
     */
    public void GetPlanCardDetail(String orderId) {
        if (AppTools.isEmpty(orderId)) {
            getV().showToast("订单号为空");
            return;
        }

        Api.getAPPService().GetPlanCardDetail(orderId)
                .compose(XApi.<GetPlanCardResults>getApiTransformer())
                .compose(XApi.<GetPlanCardResults>getScheduler())
                .compose(getV().<GetPlanCardResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetPlanCardResults>() {
                    @Override
                    public void onNext(GetPlanCardResults getPlanCardResults) {
                        if (getPlanCardResults.getRespCode().equals("00")) {
                            if (getPlanCardResults.getData()!=null&&getPlanCardResults.getData().size()>0) {
                                if (getPlanCardResults.getData().get(0).getDetail()!=null&&getPlanCardResults.getData().get(0).getDetail().size()>0)
                                {
                                    getV().setAdapter(getPlanCardResults.getData().get(0));
                                }else {
                                    getV().showEmptyView("暂无数据");
                                }

                            }else {
                                getV().showEmptyView("暂无数据");
                            }
                        }else {
                            getV().showToast(getPlanCardResults.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

}
