package com.yzf.Cpaypos.present;

import android.text.TextUtils;

import com.yzf.Cpaypos.App;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.AesUtil;
import com.yzf.Cpaypos.model.servicesmodels.GetCodeResults;
import com.yzf.Cpaypos.model.servicesmodels.RegiestResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.MainActivity;
import com.yzf.Cpaypos.ui.activitys.RegistActivity;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.Codec;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PRegist
 * Description：注册逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/20 10:34
 * Modified By：
 * Fixtime：2017/3/20 10:34
 * FixDescription：
 */

public class PRegist extends XPresent<RegistActivity> {
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
     * 注册
     *
     * @param merchId
     * @param password
     * @param code
     * @param branchNo
     * @param userType
     * @param planCode
     */
    public void regist(final String merchId, String password, String code, String branchNo, String userType, String planCode,String topBranchNo) {
        if (TextUtils.isEmpty(merchId)) {
            getV().showToast("请输入手机号");
            return;
        }
        if (merchId.startsWith("170") || merchId.startsWith("171")) {
            getV().showToast("暂不支持170、171号段的手机号码");
            return;
        }
        // 判断是否是合法的手机号码
        if (!AppTools.isMobile(merchId)) {
            getV().showToast("手机号码格式不正确");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            getV().showToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(planCode)&&TextUtils.isEmpty(branchNo)) {
            getV().showToast("请输入代理商编号或邀请码");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            getV().showToast("请输入当前密码");
            return;
        }
        if (password.length() < 6) {
            getV().showToast("6~20位登录密码");
            return;
        }
        String savePwd = password;
        password = Codec.MD5.getMD5(password, 1);
        savePwd = AesUtil.encrypt(savePwd);
        final String finalPassword = savePwd;
        Api.getAPPService().regist(merchId, password, code, branchNo, userType, planCode,topBranchNo)
                .compose(XApi.<RegiestResult>getApiTransformer())
                .compose(XApi.<RegiestResult>getScheduler())
                .compose(getV().<RegiestResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<RegiestResult>() {
                    @Override
                    public void onNext(RegiestResult regiestResult) {
                        getV().showToast(regiestResult.getRespMsg());
                        if (regiestResult.getRespCode().equals("00")) {
                            AppUser.getInstance().setUserId(merchId);
                            AppUser.getInstance().setMerchName(merchId);
                            AppUser.getInstance().setStatus("03");
                            AppUser.getInstance().setKey(regiestResult.getData().getMerchKey());
                            SharedPref.getInstance(App.getContext()).putString("merchId", merchId);
                            SharedPref.getInstance(App.getContext()).putString("password", finalPassword);
                            getV().JumpActivity(MainActivity.class, true);
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }
                });
    }
}
