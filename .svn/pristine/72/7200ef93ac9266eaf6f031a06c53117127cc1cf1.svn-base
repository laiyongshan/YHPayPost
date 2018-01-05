package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.InputMoneyActivity;
import com.yzf.Cpaypos.ui.activitys.PayBondActivity;

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

public class PPayBond extends XPresent<PayBondActivity> {
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
                        }else if (baseResults.getRespCode().equals("0002")) {
                            getV().showToast(baseResults.getRespMsg());
//                            getV().JumpActivity(InputMoneyActivity.class,false);
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

}
