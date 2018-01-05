package com.yzf.Cpaypos.present;

import com.google.gson.Gson;
import com.yzf.Cpaypos.App;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.utils.Des3;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.BindCardActivity;
import com.yzf.Cpaypos.ui.activitys.SplashActivity;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PSplash
 * Description：欢迎页逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 14:31
 * Modified By：
 * Fixtime：2017/4/1 14:31
 * FixDescription：
 */

public class PSplash extends XPresent<SplashActivity> {
    /**
     * 获取欢迎页图片
     */
    public void getWelcomeImg(String topBranchNo) {
        Api.getAPPService().getLoginBannerList(topBranchNo)
                .compose(XApi.<GetBannerListResult>getApiTransformer())
                .compose(XApi.<GetBannerListResult>getScheduler())
                .compose(getV().<GetBannerListResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetBannerListResult>() {
                    @Override
                    public void onNext(GetBannerListResult getBannerListResult) {
                        if (getBannerListResult.getRespCode().equals("00")) {
                            if (getBannerListResult.getData().size() > 0) {
                                Gson gson = new Gson();
                                SharedPref.getInstance(App.getContext()).putString("welcomeImg", gson.toJson(getBannerListResult.getData()));//将账号密码保存到本地
                            } else {
                                SharedPref.getInstance(App.getContext()).putString("welcomeImg", "");//将账号密码保存到本地
                            }
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }

                });
    }
}
