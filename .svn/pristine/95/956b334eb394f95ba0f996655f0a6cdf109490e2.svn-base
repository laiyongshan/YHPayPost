package com.yzf.Cpaypos.ui.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.ActivityManager;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppKit;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.present.PPersenFragment;
import com.yzf.Cpaypos.ui.activitys.AboutActivity;
import com.yzf.Cpaypos.ui.activitys.BillingActivity;
import com.yzf.Cpaypos.ui.activitys.ChangePwdActivity;
import com.yzf.Cpaypos.ui.activitys.LoginActivity;
import com.yzf.Cpaypos.ui.activitys.MainActivity;
import com.yzf.Cpaypos.ui.activitys.MerchInfoActivity;
import com.yzf.Cpaypos.ui.activitys.ModifiedSettleCardctivity;
import com.yzf.Cpaypos.ui.activitys.PhoneActivity;
import com.yzf.Cpaypos.ui.activitys.QrCodeActivity;
import com.yzf.Cpaypos.ui.activitys.UploadDataActivity;
import com.yzf.Cpaypos.ui.activitys.UploadPhotosActivity;
import com.yzf.Cpaypos.ui.activitys.WebActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import rx.functions.Action1;

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
 * ClassName：PersonlFragment
 * Description: 账户中心界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 12:00
 * Modified By：
 * Fixtime：2017/5/18 12:00
 * FixDescription：
 */

public class PersonlFragment extends XFragment<PPersenFragment> {
    @BindView(R.id.fmperson_head_iv)
    ImageView fmpersonHeadIv;
    @BindView(R.id.fmperson_name_tv)
    TextView fmpersonNameTv;
    @BindView(R.id.fmperson_status_tv)
    TextView fmpersonStatusTv;
    @BindView(R.id.fmperson_name_rl)
    RelativeLayout fmpersonNameRl;
    @BindView(R.id.fission_levl_tv)
    TextView fissionLevlTv;
    @BindView(R.id.fmperson_billing_rl)
    LinearLayout fmpersonBillingRl;
    @BindView(R.id.fmperson_bankcard_rl)
    LinearLayout fmpersonBankcardRl;
    @BindView(R.id.fmperson_verify_ll)
    LinearLayout fmpersonVerifyLl;
    @BindView(R.id.fmperson_psw_ll)
    LinearLayout fmpersonPswLl;
    @BindView(R.id.fmperson_contract_ll)
    LinearLayout fmpersonContractLl;
    @BindView(R.id.fmperson_about_ll)
    LinearLayout fmpersonAboutLl;
    @BindView(R.id.fmperson_update_ll)
    LinearLayout fmpersonUpdateLl;
    @BindView(R.id.fmperson_help_ll)
    LinearLayout fmpersonHelpLl;
    @BindView(R.id.fmperson_share_ll)
    LinearLayout fmpersonShareLl;
    @BindView(R.id.fmperson_exit_ll)
    LinearLayout fmpersonExitLl;
    @BindView(R.id.fmperson_planCard_ll)
    LinearLayout fmpersonPlanCardLl;
    @BindView(R.id.fmperson_settlecard_ll)
    LinearLayout fmpersonSettlecardLl;

    private String state;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        state = AppUser.getInstance().getStatus();
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    public PPersenFragment newP() {
        return new PPersenFragment();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        String name = AppUser.getInstance().getMerchName();
        if (AppTools.isEmpty(name)) {
            name = AppUser.getInstance().getUserId();
        }
        fmpersonNameTv.setText(name);
        fmpersonStatusTv.setText(getP().setStatus(state));
        checkState();
       /* ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("");*/
    }

    /**
     * 根据用户状态显示资料或者实名认证
     */
    private void checkState() {
        if (AppUser.getInstance().getPlanCardStatus().equals("1")) {
            fmpersonPlanCardLl.setVisibility(View.GONE);
        } else {
            fmpersonPlanCardLl.setVisibility(View.VISIBLE);
        }
        if (!AppTools.isEmpty(state)) {
            if (state.equals("00")) {//通过实名认证
                fmpersonVerifyLl.setVisibility(View.GONE);
                return;
            }
            if (state.equals("01") || state.equals("02") || state.equals("03")) {
                fmpersonVerifyLl.setVisibility(View.VISIBLE);
                return;
            } else {
                fmpersonVerifyLl.setVisibility(View.GONE);
                return;
            }
        } else {
            fmpersonVerifyLl.setVisibility(View.VISIBLE);
            return;
        }
    }


    /**
     * activity跳转
     */
    public void JumpActivity(Class<?> activity, boolean isfinish) {
        Router.newIntent(context)
                .to(activity)
                .launch();
        if (isfinish) {
            Router.pop(context);
        }
    }


    /**
     * 显示Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        getvDelegate().toastShort(msg);
    }

    public void JumpActivity(Class<?> activity) {
        Router.newIntent(context)
                .to(activity)
                .launch();
    }

    /**
     * 显示单按钮对话框
     *
     * @param msg
     */
    public void showErrorDialog(String msg) {
        getvDelegate().showErrorDialog(msg);
    }

    /**
     * 显示双按钮对话框
     *
     * @param msg
     * @param callback
     */
    public void showNoticeDialog(String msg, MaterialDialog.SingleButtonCallback callback) {
        getvDelegate().showNoticeDialog(msg, callback);
    }

    /**
     * 显示错误信息
     *
     * @param error
     */
    public void showError(NetError error) {
        getvDelegate().showError(error);
    }


    /**
     * 根据state判断用户状态
     *
     * @param state 用户状态
     * @return 已实名认证后返回true
     */
    private boolean isVerifyPass(String state) {
        if (state.equals("03")) {
            JumpActivity(UploadDataActivity.class);
            return false;
        } else if (state.equals("02")) {//{"message":"商户未上传照片","status":100,"state":"3","storeId":null}
            JumpActivity(UploadPhotosActivity.class);
            return false;
        } else if (state.equals("01")) {//资料未通过
            JumpActivity(UploadDataActivity.class);
            return false;
        } else {//资料未通过
            JumpActivity(UploadDataActivity.class);
            return false;
        }
    }

    @OnClick({R.id.fmperson_planCard_ll, R.id.fmperson_name_rl, R.id.fmperson_billing_rl, R.id.fmperson_bankcard_rl, R.id.fmperson_verify_ll, R.id.fmperson_psw_ll, R.id.fmperson_contract_ll, R.id.fmperson_about_ll, R.id.fmperson_update_ll, R.id.fmperson_help_ll, R.id.fmperson_exit_ll, R.id.fmperson_share_ll,R.id.fmperson_settlecard_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fmperson_name_rl:
                if (getP().isVerifyPass(state)) {
                    JumpActivity(MerchInfoActivity.class);
                }
                break;
            case R.id.fmperson_billing_rl:
                if (getP().isVerifyPass(state)) {
//                    JumpActivity(BillingActivity.class);
                    IBus.IEvent iEvent = new IEvent();
                    iEvent.setId("to_tab");
                    iEvent.setObject(2);
                    BusProvider.getBus().post(iEvent);
                }
                break;
            case R.id.fmperson_bankcard_rl:
                if (getP().isVerifyPass(state)) {
                    IBus.IEvent iEvent = new IEvent();
                    iEvent.setId("to_tab");
                    iEvent.setObject(1);
                    BusProvider.getBus().post(iEvent);
//                JumpActivity(BankCardActivity.class);
                }
                break;
            case R.id.fmperson_verify_ll:
                isVerifyPass(AppUser.getInstance().getStatus());
                break;
            case R.id.fmperson_psw_ll:
                JumpActivity(ChangePwdActivity.class);
                break;
            case R.id.fmperson_contract_ll:
                JumpActivity(PhoneActivity.class);
                break;
            case R.id.fmperson_about_ll:
                JumpActivity(AboutActivity.class);
                break;
            case R.id.fmperson_update_ll:
                checkUpdate(false);
                break;
            case R.id.fmperson_help_ll:
                WebActivity.launch(context, AppConfig.BASE_URL + "home/getHelpCenter.do", "帮助中心");
                break;
            case R.id.fmperson_exit_ll:
                showNoticeDialog("是否注销？", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            ActivityManager.getInstance().finishAllActivityExceptOne(MainActivity.class);
                            JumpActivity(LoginActivity.class, true);
                        }
                    }
                });
                break;
            case R.id.fmperson_share_ll:
                QrCodeActivity.launch(context, "", "", "扫码下载", AppConfig.PGY_URL);
                break;
            case R.id.fmperson_planCard_ll:
                new MaterialDialog.Builder(context)
                        .title("开通卡规划")
                        .titleColor(getResources().getColor(R.color.text_6))
                        .inputRangeRes(1, 36, R.color.text_tip)
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input("请输入邀请码", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                getvDelegate().showLoading();
                                getP().openPlanCard(input.toString());
                            }
                        }).show();
                break;
            case R.id.fmperson_settlecard_ll:
                if (getP().isVerifyPass(state)) {
                    JumpActivity(ModifiedSettleCardctivity.class, false);
                }
                break;
        }
    }

    /**
     * 检查更新
     *
     * @param forceUpdate 强制更新标志
     */
    private void checkUpdate(final boolean forceUpdate) {
        getvDelegate().showLoading("检查更新中....");
        PgyUpdateManager.register(context, AppKit.getpackageNames(context) + ".fileprovider",
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        getvDelegate().dismissLoading();
                        // 将新版本信息封装到AppBean中
                        //{"code":0,"message":"","data":{"lastBuild":"9","downloadURL":"http:\/\/qiniu-app-cdn.pgyer.com\/2bd97e24ecdea088cd1d0e170c034124.apk?e=1476694444&attname=Mall_pgyer_v1.2_20161014.apk&token=6fYeQ7_TVB5L0QSzosNFfw2HU8eJhAirMF5VxV9G:4laj0OzXJZUXTiQc9MThSRHBqlg=","versionCode":"12","versionName":"1.2","appUrl":"http:\/\/www.pgyer.com\/xnJu","build":"9","releaseNote":"\u66f4\u65b0\u5230\u7248\u672c: 1.2(build9)"}
//                        Logger.i("updateresult="+result.toString());
                        getRxPermissions()
                                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(new Action1<Boolean>() {
                                    @Override
                                    public void call(Boolean granted) {
                                        if (granted) {
                                            //TODO 许可
                                            if (!context.isFinishing()) {
                                                final AppBean appBean = getAppBeanFromString(result);
                                                if (!forceUpdate) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                    builder.setTitle(R.string.update_version);
                                                    builder.setCancelable(false);
                                                    builder.setMessage(appBean.getReleaseNote());
                                                    builder.setNegativeButton(R.string.update_later, null);
                                                    builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            startDownloadTask(context, appBean.getDownloadURL());
                                                        }
                                                    });
                                                    builder.show();
                                                    PgyUpdateManager.unregister();
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                    builder.setTitle(R.string.update_version);
                                                    builder.setCancelable(false);
                                                    builder.setMessage(appBean.getReleaseNote());
                                                    builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            startDownloadTask(context, appBean.getDownloadURL());
                                                        }
                                                    });
                                                    builder.show();
                                                    PgyUpdateManager.unregister();
                                                }
                                            } else {
                                                PgyUpdateManager.unregister();
                                            }
                                        } else {
                                            //TODO 未许可
                                            getvDelegate().toastShort("权限未获取");
                                        }
                                    }
                                });

                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        showToast(getString(R.string.is_the_latest_version));
                        PgyUpdateManager.unregister();
                    }
                });
    }

    public void hidePlanLL() {
        AppUser.getInstance().setPlanCardStatus("1");
        fmpersonPlanCardLl.setVisibility(View.GONE);
    }
}
