package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetPlanCardResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.PlanCardDetailsActivity;

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

public class PPlanCardDetails extends XPresent<PlanCardDetailsActivity> {
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
                                if (getPlanCardResults.getData().get(0).getDetailList()!=null&&getPlanCardResults.getData().get(0).getDetailList().size()>0)
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
