package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.utils.Des3;
import com.yzf.Cpaypos.kit.utils.IdCardUtil;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.SearchBankResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.UploadDataActivity;
import com.yzf.Cpaypos.ui.activitys.UploadPhotosActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PUploadData
 * Description：上传资料逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/21 17:00
 * Modified By：
 * Fixtime：2017/3/21 17:00
 * FixDescription：
 */

public class PUploadData extends XPresent<UploadDataActivity> {
    /**
     * 获取大行名称
     *
     * @param bankName
     */
    public void searchBank(String bankName) {
        // 判断
        if (AppTools.isEmpty(bankName)) {
            getV().showToast("请输入开户银行");
            return;
        }
        Api.getAPPService().searchBank(bankName, 1, 20)
                .compose(XApi.<SearchBankResult>getApiTransformer())
                .compose(XApi.<SearchBankResult>getScheduler())
                .compose(getV().<SearchBankResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<SearchBankResult>() {
                    @Override
                    public void onNext(SearchBankResult searchBankResult) {
                        if (searchBankResult.getRespCode().equals("00")) {
                            if (searchBankResult.getData().size() > 0) {
                                getV().showBankDialog(searchBankResult.getData());
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

    /**
     * 上传资料
     *
     * @param merchId
     * @param idCard
     * @param bankName
     * @param acctNo
     * @param phoneNo
     * @param acctName
     * @param bankNo
     */
    public void upLoadData(String merchId, String idCard, String bankName, String acctNo,
                           String phoneNo, String acctName, String bankNo) {
        IdCardUtil idCardUtil = new IdCardUtil();
        // 判断
        if (AppTools.isEmpty(idCard)) {
            getV().showToast("请输入身份证号");
            return;
        }
        if (!idCardUtil.verify(idCard)) {
            getV().showToast(idCardUtil.getCodeError());
            return;
        }
        if (AppTools.isEmpty(bankName)) {
            getV().showToast("请输入支行名称");
            return;
        }
        if (AppTools.isEmpty(acctName)) {
            getV().showToast("请输入银行卡开户名");
            return;
        }

        if (AppTools.isEmpty(acctNo)) {
            getV().showToast("请输入结算卡号");
            return;
        }
        if (AppTools.isEmpty(phoneNo)) {
            getV().showToast("请输入手机号");
            return;
        }
        if (!AppTools.isMobile(phoneNo)) {
            getV().showToast("手机号格式不正确");
            return;
        }
        if (AppTools.isEmpty(bankNo)) {
            getV().showToast("联行号为空，请搜索选择到具体的支行以获取联行号");
            return;
        }

        String sign = idCard + acctNo + phoneNo;
        sign = AppTools.appEncrypt(sign);
        try {
            idCard = Des3.encode(idCard);
            acctNo = Des3.encode(acctNo);
            phoneNo = Des3.encode(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Api.getAPPService().upLoadData(merchId, idCard, bankName, acctNo, phoneNo, acctName, bankNo, sign)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            getV().JumpActivity(UploadPhotosActivity.class, true);
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

    /**
     * 上传资料
     *
     * @param merchId
     * @param idCard
     * @param acctNo
     * @param phoneNo
     * @param acctName
     */
    public void upLoadData(String merchId, String idCard,  String acctNo,
                           String phoneNo, String acctName) {
        IdCardUtil idCardUtil = new IdCardUtil();
        // 判断
        if (AppTools.isEmpty(idCard)) {
            getV().showToast("请输入身份证号");
            return;
        }
        if (!idCardUtil.verify(idCard)) {
            getV().showToast(idCardUtil.getCodeError());
            return;
        }
        if (AppTools.isEmpty(acctName)) {
            getV().showToast("请输入银行卡开户名");
            return;
        }

        if (AppTools.isEmpty(acctNo)) {
            getV().showToast("请输入结算卡号");
            return;
        }
        if (AppTools.isEmpty(phoneNo)) {
            getV().showToast("请输入手机号");
            return;
        }
        if (!AppTools.isMobile(phoneNo)) {
            getV().showToast("手机号格式不正确");
            return;
        }

        String sign = idCard + acctNo + phoneNo;
        sign = AppTools.appEncrypt(sign);
        try {
            idCard = Des3.encode(idCard);
            acctNo = Des3.encode(acctNo);
            phoneNo = Des3.encode(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Api.getAPPService().upLoadData(merchId, idCard, acctNo, phoneNo, acctName, sign)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            getV().JumpActivity(UploadPhotosActivity.class, true);
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
