package com.yzf.Cpaypos.present;

import com.google.gson.Gson;
import com.yzf.Cpaypos.App;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.AesUtil;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.LoginActivity;
import com.yzf.Cpaypos.ui.activitys.MainActivity;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.Codec;
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
 * ClassName：PLogin
 * Description: 用户登录逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/20 9:18
 * Modified By：
 * Fixtime：2017/3/20 9:18
 * FixDescription：
 */
public class PLogin extends XPresent<LoginActivity> {


    /**
     * 登陆
     *
     * @param merchId
     * @param password
     */
    public void login(final String merchId, String password, String topBranchNo) {
        // 判断
        if (AppTools.isEmpty(merchId)) {
            getV().showToast("请输入用户名");
            return;
        }
        if (!AppTools.isMobile(merchId)) {
            getV().showToast("手机号格式不正确");
            return;
        }
        if (AppTools.isEmpty(password)) {
            getV().showToast("请输入密码");
            return;
        }
        String savePwd = password;
        password = Codec.MD5.getMD5(password, 1);
        savePwd = AesUtil.encrypt(savePwd);
        final String finalPassword = savePwd;
        Api.getAPPService().login(merchId, password, topBranchNo)
                .compose(XApi.<LoginResult>getApiTransformer())
                .compose(XApi.<LoginResult>getScheduler())
                .compose(getV().<LoginResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<LoginResult>() {
                    @Override
                    public void onNext(LoginResult loginResult) {
                        if (loginResult.getRespCode().equals("00")) {//给AppUser单例赋值
                            AppUser.getInstance().setUserId(merchId);
                            AppUser.getInstance().setKey(loginResult.getData().getKey());
                            AppUser.getInstance().setStatus(loginResult.getData().getMerchStatus());
                            AppUser.getInstance().setBeginTime(loginResult.getData().getBeginTime());
                            AppUser.getInstance().setEndTime(loginResult.getData().getEndTime());
                            AppUser.getInstance().setMaxAmt(loginResult.getData().getMaxAmt());
                            AppUser.getInstance().setMinAmt(loginResult.getData().getMinAmt());
                            AppUser.getInstance().setBranchNo(loginResult.getData().getBranchNo());
                            AppUser.getInstance().setMerchName(loginResult.getData().getMerchName());
                            AppUser.getInstance().setPlanCardStatus(loginResult.getData().getPlanCardStatus());
                            Gson gson = new Gson();
                            AppUser.getInstance().setServiceList(gson.toJson(loginResult.getData().getServiceList()));
                            SharedPref.getInstance(App.getContext()).putString("merchId", merchId);//将账号密码保存到本地
                            SharedPref.getInstance(App.getContext()).putString("password", finalPassword);
                            getV().JumpActivity(MainActivity.class, true);
                        } else {
                            getV().showToast(loginResult.getRespMsg());
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 获取banner
     */
    public void getBannerList(String topBranchNo) {
        Api.getAPPService().getLoginBannerList(topBranchNo)
                .compose(XApi.<GetBannerListResult>getApiTransformer())
                .compose(XApi.<GetBannerListResult>getScheduler())
                .compose(getV().<GetBannerListResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetBannerListResult>() {
                    @Override
                    public void onNext(GetBannerListResult getBannerListResult) {
                        if (getBannerListResult.getRespCode().equals("00")) {
//                            getV().setBanner(getBannerListResult);
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

}
