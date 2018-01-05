package com.yzf.Cpaypos.present;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.UploadDataActivity;
import com.yzf.Cpaypos.ui.activitys.UploadPhotosActivity;
import com.yzf.Cpaypos.ui.fragments.CardFragment;

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

public class PCardFragment extends XPresent<CardFragment> {
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
                                AppUser.getInstance().setCardWhiteInfo("");
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
                        getV().showErrorView(error);
                    }

                });
    }

    private int getImgId(String imgName) {
        int id = -1;
        try {
            id = getV().getResources().getIdentifier(imgName, "mipmap", getV().getActivity().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 根据state判断用户状态
     *
     * @param state 用户状态
     * @return 已实名认证后返回true
     */
    public boolean isVerifyPass(String state) {
        if (state.equals("00")) {//通过实名认证
            return true;
        }
        if (state.equals("10")) {//
            getV().showErrorDialog("收款账户已冻结");
            return false;
        }
        if (state.equals("30")) {//
            return true;
            /*getV().showToast("商户信息正审核，请稍候...");
            return false;*/
        } else if (state.equals("03")) {
            getV().showNoticeDialog("尚未实名验证\n是否实名验证", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadDataActivity.class);
                    }
                }
            });
            return false;
        } else if (state.equals("02")) {//{"message":"商户未上传照片","status":100,"state":"3","storeId":null}

            getV().showNoticeDialog("尚未上传认证照片\n是否上传", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadPhotosActivity.class);
                    }
                }

            });
            return false;
        } else if (state.equals("01")) {//资料未通过
            getV().showNoticeDialog("尚未完善资料" + "\n是否完善资料", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadDataActivity.class);
                    }
                }
            });
            return false;
        } else {//资料未通过
            getV().showNoticeDialog("尚未实名验证\n是否实名验证", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadDataActivity.class);
                    }
                }
            });
            return false;
        }
    }


}
