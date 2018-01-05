package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.SearchBankResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.ModifiedSettleCardctivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PBankCard
 * Description：银行卡管理逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 11:50
 * Modified By：
 * Fixtime：2017/4/1 11:50
 * FixDescription：
 */

public class PModifiedSettleCard extends XPresent<ModifiedSettleCardctivity> {
    /**
     * 获取支行
     *
     * @param bankName
     * @param page
     * @param pagenum
     * @param openBankNo
     */
    public void searchSubBank(String bankName, int page, int pagenum, String openBankNo) {
        // 判断
        if (AppTools.isEmpty(bankName)) {
            getV().showToast("请输入关键字");
            return;
        }
        Api.getAPPService().searchSubBank(bankName, page, pagenum, openBankNo)
                .compose(XApi.<SearchBankResult>getApiTransformer())
                .compose(XApi.<SearchBankResult>getScheduler())
                .compose(getV().<SearchBankResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<SearchBankResult>() {
                    @Override
                    public void onNext(SearchBankResult searchBankResult) {
                        if (searchBankResult.getRespCode().equals("00")) {
                            if (searchBankResult.getData().size() > 0) {
                                getV().showSubBankDialog(searchBankResult.getData());
                            } else {
                                getV().showToast(searchBankResult.getRespMsg());
                            }
                        } else {
                            getV().showToast(searchBankResult.getRespMsg());
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
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

    public void modifiedSettleCard(String merchId, String acctNo, String phoneNo) {
        if (AppTools.isEmpty(merchId)) {
            getV().showToast("商户号为空");
            return;
        }
        if (AppTools.isEmpty(acctNo)) {
            getV().showToast("卡号为空");
            return;
        }
        if (AppTools.isEmpty(phoneNo)) {
            getV().showToast("手机号为空");
            return;
        }
        Api.getAPPService().modifySettleCard(merchId, acctNo, phoneNo)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            getV().finish(baseResults.getRespMsg());
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
