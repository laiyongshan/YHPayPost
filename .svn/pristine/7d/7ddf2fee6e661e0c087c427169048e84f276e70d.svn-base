package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.utils.Des3;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.BindCardActivity;

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

public class PBindCard extends XPresent<BindCardActivity> {
    /**
     * 绑定银行卡
     *
     * @param merchId
     * @param acctNo
     * @param phoneNo
     */
    public void BindCard(String merchId, String acctNo, String phoneNo) {
        if (AppTools.isEmpty(acctNo)) {
            getV().showToast("请输入您的银行卡号");
            return;
        }
        if (AppTools.isEmpty(phoneNo)) {
            getV().showToast("请输入银行预留手机号");
            return;
        }
        String sign = acctNo + phoneNo;
        sign = AppTools.appEncrypt(sign);
        try {
            acctNo = Des3.encode(acctNo);//des3加密
            phoneNo = Des3.encode(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Api.getAPPService().BindCard(merchId, acctNo, phoneNo, sign)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            getV().finishActivity(baseResults.getRespMsg());//绑卡成功后返回前一个页面
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
