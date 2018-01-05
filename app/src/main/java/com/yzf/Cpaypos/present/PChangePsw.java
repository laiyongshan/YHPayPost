package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.App;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.utils.AesUtil;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.ChangePwdActivity;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.Codec;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PChangePsw
 * Description：修改密码逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 15:20
 * Modified By：
 * Fixtime：2017/4/1 15:20
 * FixDescription：
 */

public class PChangePsw extends XPresent<ChangePwdActivity> {
    /**
     * 修改密码
     *
     * @param merchId
     * @param oldPwd
     * @param newPwd
     * @param newPwds
     */
    public void changePassword(String merchId, String oldPwd, String newPwd, String newPwds) {
        if (AppTools.isEmpty(oldPwd)) {
            getV().showToast("请输入当前密码");
            return;
        }
        if (AppTools.isEmpty(newPwd)) {
            getV().showToast("请输入新密码");
            return;
        }
        if (AppTools.isEmpty(newPwds)) {
            getV().showToast("请再次输入新密码");
            return;
        }
        if (!newPwd.equals(newPwds)) {
            getV().showToast("前后两次密码不一致");
            return;
        }
        String savePwd = newPwd;
        oldPwd = Codec.MD5.getMD5(oldPwd, 1);
        newPwd = Codec.MD5.getMD5(newPwd, 1);
        savePwd = AesUtil.encrypt(savePwd);
        final String finalSavePwd = savePwd;
        Api.getAPPService().changePassword(merchId, oldPwd, newPwd)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            SharedPref.getInstance(App.getContext()).putString("password", finalSavePwd);
                            getV().finishActivity(baseResults.getRespMsg());//修改密码成功后返回上一个页面
                        } else {
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
