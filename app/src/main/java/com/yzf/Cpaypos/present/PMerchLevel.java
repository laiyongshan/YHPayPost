package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.model.servicesmodels.GetMerchLevelResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.MerchLevelActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PMerchLevel
 * Description：商户等级逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 11:50
 * Modified By：
 * Fixtime：2017/4/1 11:50
 * FixDescription：
 */

public class PMerchLevel extends XPresent<MerchLevelActivity> {
    /**
     * 获取商户等级信息
     *
     * @param merchId
     */
    public void getMerchLevel(String merchId) {
        Api.getAPPService().getMerchLevel(merchId)
                .compose(XApi.<GetMerchLevelResults>getApiTransformer())
                .compose(XApi.<GetMerchLevelResults>getScheduler())
                .compose(getV().<GetMerchLevelResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetMerchLevelResults>() {
                    @Override
                    public void onNext(GetMerchLevelResults getMerchLevelResults) {
                        if (getMerchLevelResults.getRespCode().equals("00")) {
                            getV().setData(getMerchLevelResults);
                        } else {
                            getV().showErrorView(getMerchLevelResults.getRespMsg(), getImgId("execption"));
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showErrorView(error, getImgId("execption"));
                    }

                });
    }

    private int getImgId(String imgName) {
        int id = -1;
        try {
            id = getV().getResources().getIdentifier(imgName, "mipmap", getV().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


}
