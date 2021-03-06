package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.model.servicesmodels.GetProfitListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.fragments.ProfitBillingFragment;

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
 * ClassName：PProfitBilling
 * Description: 收益账单逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/20 9:18
 * Modified By：
 * Fixtime：2017/3/20 9:18
 * FixDescription：
 */
public class PProfitBilling extends XPresent<ProfitBillingFragment> {

    public void getProfitList(String merchId, final int page, int pageNum, String beginTime, String endTime) {
        Api.getAPPService().getProfitList(merchId, page, pageNum, beginTime, endTime)
                .compose(XApi.<GetProfitListResult>getApiTransformer())
                .compose(XApi.<GetProfitListResult>getScheduler())
                .compose(getV().<GetProfitListResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetProfitListResult>() {
                    @Override
                    public void onNext(GetProfitListResult getProfitListResult) {
                        if (getProfitListResult.getRespCode().equals("00")) {
                            getV().setAdapter(getProfitListResult, page);
                        } else {
                            getV().showErrorView(getProfitListResult.getRespMsg());
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showErrorView(error);
                    }

                });
    }

}
