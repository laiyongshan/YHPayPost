package com.yzf.Cpaypos.present;

import android.text.TextUtils;

import com.yzf.Cpaypos.App;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.utils.AesUtil;
import com.yzf.Cpaypos.model.servicesmodels.FindPwdResults;
import com.yzf.Cpaypos.model.servicesmodels.GetCodeResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.FindPasswordActivity;
import com.yzf.Cpaypos.ui.activitys.LoginActivity;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.Codec;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PFindPassword
 * Description：找回密码逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/21 14:33
 * Modified By：
 * Fixtime：2017/3/21 14:33
 * FixDescription：
 */

public class PFindPassword extends XPresent<FindPasswordActivity> {
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
        Api.getAPPService().getCode(phoneNo, "2")
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
     * 找回密码
     *
     * @param merchId
     * @param password
     * @param code
     */
    public void findpassword(final String merchId, String password, String code) {
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
        if (TextUtils.isEmpty(password)) {
            getV().showToast("请输入密码");
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
        Api.getAPPService().findpassword(merchId, password, code)
                .compose(XApi.<FindPwdResults>getApiTransformer())
                .compose(XApi.<FindPwdResults>getScheduler())
                .compose(getV().<FindPwdResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<FindPwdResults>() {
                    @Override
                    public void onNext(FindPwdResults findPwdResults) {
                        getV().showToast(findPwdResults.getRespMsg());
                        if (findPwdResults.getRespCode().equals("00")) {//找回密码成功后，更新本地账号密码，跳转到登陆页面
                            SharedPref.getInstance(App.getContext()).putString("merchId", merchId);
                            SharedPref.getInstance(App.getContext()).putString("password", finalPassword);
                            getV().JumpActivity(LoginActivity.class, true);
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }
}
