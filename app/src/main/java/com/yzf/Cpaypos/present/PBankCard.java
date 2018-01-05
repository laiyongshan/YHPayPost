package com.yzf.Cpaypos.present;

import com.google.gson.Gson;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.BankCardActivity;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

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

public class PBankCard extends XPresent<BankCardActivity> {
    public void getWhiteCardList(String merchId) {
        Api.getAPPService().getWhiteCardList(merchId)
                .compose(XApi.<GetWhiteCardListResult>getApiTransformer())
                .compose(XApi.<GetWhiteCardListResult>getScheduler())
                .compose(getV().<GetWhiteCardListResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetWhiteCardListResult>() {
                    @Override
                    public void onNext(GetWhiteCardListResult getWhiteCardListResult) {
                        if (getWhiteCardListResult.getRespCode().equals("00")) {
                            if (getWhiteCardListResult.getData() != null && getWhiteCardListResult.getData().size() > 0) {
                                Gson gson = new Gson();
                                AppUser.getInstance().setCardWhiteInfo(gson.toJson(getWhiteCardListResult.getData()));
                                getV().setAdapter(getWhiteCardListResult);
                            } else {
                                getV().showEmptyView("还没有绑定过银行卡");
                            }
                        } else {
                            getV().showErrorView(getWhiteCardListResult.getRespMsg());
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showErrorView(error);
                    }

                });
    }

    public void deleteBankCard(String userId, String acctNo) {
        Api.getAPPService().deleteBankCard(userId, acctNo)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        IBus.IEvent iEvent = new IEvent();
                        iEvent.setId("refresh_card");//交易成功后发消息去更新金额
                        BusProvider.getBus().post(iEvent);
                        getV().refresh(baseResults.getRespMsg());
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

}
