package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.model.servicesmodels.GetSubmerchResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.SubMerchActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PSubMerch
 * Description：子商户逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 11:50
 * Modified By：
 * Fixtime：2017/4/1 11:50
 * FixDescription：
 */

public class PSubMerch extends XPresent<SubMerchActivity> {
    /**
     * 获取子商户
     *
     * @param merchId
     * @param page
     * @param pageNum
     */
    public void getSubMerch(String merchId, final int page, int pageNum) {
        Api.getAPPService().getSubMerch(merchId, page, pageNum)
                .compose(XApi.<GetSubmerchResult>getApiTransformer())
                .compose(XApi.<GetSubmerchResult>getScheduler())
                .compose(getV().<GetSubmerchResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetSubmerchResult>() {
                    @Override
                    public void onNext(GetSubmerchResult getSubmerchResult) {
                        if (getSubmerchResult.getRespCode().equals("00")) {
                            getV().setAdapter(getSubmerchResult, page);
                        } else {
                            getV().showErrorView(getSubmerchResult.getRespMsg(), getImgId("execption"));
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
