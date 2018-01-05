package com.yzf.Cpaypos.present;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.GetProfitResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.UploadDataActivity;
import com.yzf.Cpaypos.ui.activitys.UploadPhotosActivity;
import com.yzf.Cpaypos.ui.activitys.WithDrawActivity;
import com.yzf.Cpaypos.ui.fragments.FissionFragment;

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
 * ClassName：PFissionFragment
 * Description: 微创业逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/24 11:59
 * Modified By：
 * Fixtime：2017/3/24 11:59
 * FixDescription：
 */
public class PFissionFragment extends XPresent<FissionFragment> {


    /**
     * 获取收益
     *
     * @param merchId
     */
    public void getProfit(final String merchId) {
        Api.getAPPService().getProfit(merchId)
                .compose(XApi.<GetProfitResult>getApiTransformer())
                .compose(XApi.<GetProfitResult>getScheduler())
                .compose(getV().<GetProfitResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetProfitResult>() {
                    @Override
                    public void onNext(GetProfitResult getProfitResult) {
                        if (getProfitResult.getRespCode().equals("00")) {
                            AppUser.getInstance().setSY_AMT(getProfitResult.getData().getProfitAmt());
                            getV().setProfitInfo(getProfitResult);
                        } else {
                            getV().showToast(getProfitResult.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }
                });
    }

    /**
     * 根据state判断用户状态
     *
     * @param state 用户状态
     * @return 已实名认证后返回true
     */
    private boolean isVerifyPass(String state) {
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
                        getV().JumpActivity(UploadPhotosActivity.class);
                    }
                }
            });
            return false;
        } else if (state.equals("02")) {//{"message":"商户未上传照片","status":100,"state":"3","storeId":null}

            getV().showNoticeDialog("尚未上传认证照片\n是否上传", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadDataActivity.class);
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

    public void toWithDraw(String status) {
        if (isVerifyPass(status)) {//收益提现
            getV().JumpActivity(WithDrawActivity.class, AppConfig.SYTIXIAN);
        }
    }
}
