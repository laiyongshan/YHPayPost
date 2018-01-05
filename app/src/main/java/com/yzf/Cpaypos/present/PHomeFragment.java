package com.yzf.Cpaypos.present;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.HomeSource;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetH5Results;
import com.yzf.Cpaypos.model.servicesmodels.GetPreviewPlanResult;
import com.yzf.Cpaypos.model.servicesmodels.GetPushMsgResults;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.AgenWebViewActivity;
import com.yzf.Cpaypos.ui.activitys.BankCardActivity;
import com.yzf.Cpaypos.ui.activitys.UploadDataActivity;
import com.yzf.Cpaypos.ui.activitys.UploadPhotosActivity;
import com.yzf.Cpaypos.ui.fragments.HomeFragment;


import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.log.Logger;
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
 * ClassName：PHomeFragment
 * Description: 首页逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:56
 * Modified By：
 * Fixtime：2017/5/18 11:56
 * FixDescription：
 */
public class PHomeFragment extends XPresent<HomeFragment> {
    String mUrl = null;

    /**
     * 刷新用户状态
     *
     * @param merchId
     */
    public void refresh(final String merchId) {
        Api.getAPPService().refresh(merchId)
                .compose(XApi.<LoginResult>getApiTransformer())
                .compose(XApi.<LoginResult>getScheduler())
                .compose(getV().<LoginResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<LoginResult>() {
                    @Override
                    public void onNext(LoginResult loginResult) {
                        getV().refreshed();
                        if (loginResult.getRespCode().equals("00")) {
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
        Api.getAPPService().getBannerList(topBranchNo)
                .compose(XApi.<GetBannerListResult>getApiTransformer())
                .compose(XApi.<GetBannerListResult>getScheduler())
                .compose(getV().<GetBannerListResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetBannerListResult>() {
                    @Override
                    public void onNext(GetBannerListResult getBannerListResult) {
                        if (getBannerListResult.getRespCode().equals("00")) {
                            getV().setBanner(getBannerListResult);
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
    public void getPreviewPlan(String merchId) {
        Api.getAPPService().getPreviewPlan(merchId, "")
                .compose(XApi.<GetPreviewPlanResult>getApiTransformer())
                .compose(XApi.<GetPreviewPlanResult>getScheduler())
                .compose(getV().<GetPreviewPlanResult>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetPreviewPlanResult>() {
                    @Override
                    public void onNext(GetPreviewPlanResult getPreviewPlanResult) {
                        if (getPreviewPlanResult.getRespCode().equals("00")) {
                            getV().setPreviewPlan(getPreviewPlanResult, true);
                        } else {
                            getV().setPreviewPlan(getPreviewPlanResult, false);
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 获取推送消息
     */
    public void getPushMsg(String topBranchNo) {
        Api.getAPPService().getPushMsg(topBranchNo)
                .compose(XApi.<GetPushMsgResults>getApiTransformer())
                .compose(XApi.<GetPushMsgResults>getScheduler())
                .compose(getV().<GetPushMsgResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetPushMsgResults>() {
                    @Override
                    public void onNext(GetPushMsgResults getPushMsgResults) {
                        if (getPushMsgResults.getRespCode().equals("00")) {
                            getV().setMarqueeView(getPushMsgResults.getData().getMsgList());
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
    public void addRecord(String merchId, String topBranchNo, String orderId, String clientIdentify,
                          String systemModel, String systemPhone, String lng, String lat, String platformCode, String userId, String phone, String soureCode) {
        Api.getAPPService().addRecord(merchId, topBranchNo, orderId, clientIdentify, systemModel, systemPhone, lng, lat, platformCode, userId, phone, soureCode)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        Logger.i(baseResults.getRespMsg());
                        getV().dismissLoading();
                        AgenWebViewActivity.launch(getV().getActivity(), mUrl, "我要借款");
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 获取贷款H5
     */
    public void getLoansH5(String merchId, String topBranchNo, String orderId, String clientIdentify,
                           String systemModel, String systemPhone, String lng, String lat, String platformCode, String userId, String phone, String soureCode) {
        Api.getAPPService().getLoansH5(merchId, topBranchNo, orderId, clientIdentify, systemModel, systemPhone, lng, lat, platformCode, userId, phone, soureCode)
                .compose(XApi.<GetH5Results>getApiTransformer())
                .compose(XApi.<GetH5Results>getScheduler())
                .compose(getV().<GetH5Results>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetH5Results>() {
                    @Override
                    public void onNext(GetH5Results getH5Results) {
                        getV().dismissLoading();
                        if (getH5Results.getRespCode().equals("00")) {
                            AgenWebViewActivity.launch(getV().getActivity(), getH5Results.getData().getUrl(), "我要借款");
                        } else {
                            getV().showToast(getH5Results.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 获取信用卡H5
     */
    public void getCreditH5(String merchId, String topBranchNo, String orderId, String clientIdentify,
                            String systemModel, String systemPhone, String lng, String lat, String platformCode, String userId, String phone, String soureCode) {
        Api.getAPPService().getCreditH5(merchId, topBranchNo, orderId, clientIdentify, systemModel, systemPhone, lng, lat, platformCode, userId, phone, soureCode)
                .compose(XApi.<GetH5Results>getApiTransformer())
                .compose(XApi.<GetH5Results>getScheduler())
                .compose(getV().<GetH5Results>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetH5Results>() {
                    @Override
                    public void onNext(GetH5Results getH5Results) {
                        getV().dismissLoading();
                        if (getH5Results.getRespCode().equals("00")) {
                            AgenWebViewActivity.launch(getV().getActivity(), getH5Results.getData().getUrl(), "我要借款");
                        } else {
                            getV().showToast(getH5Results.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 根据点击位置进行具体的操作
     *
     * @param homeSource
     */
    @SuppressLint("MissingPermission")
    public void toPay(HomeSource homeSource) {
        String state = AppUser.getInstance().getStatus();
        switch (homeSource.getId()) {
            case 0://信用卡管理
                if (isVerifyPass(state)) {
                    IBus.IEvent iEvent = new IEvent();
                    iEvent.setId("to_tab");
                    iEvent.setObject(1);
                    BusProvider.getBus().post(iEvent);
                }
                break;
            case 1://信用卡取款
                if (isVerifyPass(state)) {
                    getV().JumpActivity(BankCardActivity.class, AppConfig.KJZFH5);
                }
                break;
            case 2://我要借款
                if (isVerifyPass(state)) {
                    getLoansUrlParms();
                }
                break;
            case 3://信用卡申请
                if (isVerifyPass(state)) {
                    getCreditUrlParms();
                }
                break;
            case 4://便民服务
                if (isVerifyPass(state)) {
                    getV().showToast("暂未开放");
                }
                break;
            case 5://话费充值
                if (isVerifyPass(state)) {
                    getV().showToast("暂未开放");


                }
                break;
            case 6://水电缴费
                if (isVerifyPass(state)) {
                    getV().showToast("暂未开放");
                }
                break;
            case 7://更多
                if (isVerifyPass(state)) {
                    getV().showToast("暂未开放");
                }
                break;
            default:
                break;
        }

    }

    public void toApplyLoans(HomeSource homeSource) {
        String state = AppUser.getInstance().getStatus();
        if (isVerifyPass(state)) {
            getLoansUrlParms();
        }
    }

    public void getLoansUrlParms() {
        String clientIdentify = null;
        String systemModel = null;
        String systemPhone = null;
        String lng = null;
        String lat = null;
        String platformCode = null;
        String userId = null;
        String phone = null;
        String soureCode = null;
        String merchId = AppUser.getInstance().getUserId();
        String orderId = AppTools.craeateOrderId();
        String topBranchNo = AppConfig.TOPBRANCHNO;

        clientIdentify = getIMEI(getV().getContext());
        if (AppTools.isEmpty(clientIdentify)) {
            clientIdentify = getIMSI(getV().getContext());
        }
        systemModel = getSystemModel();
        systemPhone = "android";
        lng = SharedPref.getInstance(getV().getContext()).getString("lng", "");
        lat = SharedPref.getInstance(getV().getContext()).getString("lat", "");
        if (AppTools.isEmpty(lng))//如果第一次获取不到经纬度信息，赋值一个默认经纬度信息
        {
            lng = "118.884349";
        }
        if (AppTools.isEmpty(lat)) {
            lat = "32.003884";
        }
        userId = AppUser.getInstance().getUserId();
        phone = userId;
        soureCode = "AnYF" + AppConfig.TOPBRANCHNO + Kits.Date.getYmd().replace("-", "");
        getV().showLoading();
        getLoansH5(merchId, topBranchNo, orderId, clientIdentify, systemModel, systemPhone, lng, lat, platformCode, userId, phone, soureCode);

//        addRecord(merchId, topBranchNo, orderId, clientIdentify, systemModel, systemPhone, lng, lat, platformCode, userId, phone, soureCode);
    }

    public void getCreditUrlParms() {
        String clientIdentify = null;
        String systemModel = null;
        String systemPhone = null;
        String lng = null;
        String lat = null;
        String platformCode = null;
        String userId = null;
        String phone = null;
        String soureCode = null;
        String merchId = AppUser.getInstance().getUserId();
        String orderId = AppTools.craeateOrderId();
        String topBranchNo = AppConfig.TOPBRANCHNO;

        clientIdentify = getIMEI(getV().getContext());
        if (AppTools.isEmpty(clientIdentify)) {
            clientIdentify = getIMSI(getV().getContext());
        }
        systemModel = getSystemModel();
        systemPhone = "android";

        userId = AppUser.getInstance().getUserId();
        phone = userId;
        soureCode = "AnYF" + AppConfig.TOPBRANCHNO + Kits.Date.getYmd().replace("-", "");
        getV().showLoading();
        getCreditH5(merchId, topBranchNo, orderId, clientIdentify, systemModel, systemPhone, lng, lat, platformCode, userId, phone, soureCode);

//        addRecord(merchId, topBranchNo, orderId, clientIdentify, systemModel, systemPhone, lng, lat, platformCode, userId, phone, soureCode);
    }


    /**
     * 根据state判断用户状态
     *
     * @param state 用户状态
     * @return 已实名认证后返回true
     */
    private boolean isVerifyPass(String state) {
        if (state.equals("00")) {//通过实名认证
            return true;
        }
        if (state.equals("10")) {//
            getV().showErrorDialog("收款账户已冻结");
            return false;
        }
        if (state.equals("30")) {//
            return true;
           /* getV().showToast("商户信息正审核，请稍候...");
            return false;*/
        } else if (state.equals("03")) {
            getV().showNoticeDialog("尚未实名验证\n是否实名验证", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadDataActivity.class);
                    }
                }
            });
            return false;
        } else if (state.equals("02")) {//{"message":"商户未上传照片","status":100,"state":"3","storeId":null}

            getV().showNoticeDialog("尚未上传认证照片\n是否上传", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadPhotosActivity.class);
                    }
                }

            });
            return false;
        } else if (state.equals("01")) {//资料未通过
            getV().showNoticeDialog("尚未完善资料" + "\n是否完善资料", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadDataActivity.class);
                    }
                }
            });
            return false;
        } else {//资料未通过
            getV().showNoticeDialog("尚未实名验证\n是否实名验证", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        getV().JumpActivity(UploadDataActivity.class);
                    }
                }
            });
            return false;
        }
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取手机IMSI
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            @SuppressLint("MissingPermission") String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }


}
