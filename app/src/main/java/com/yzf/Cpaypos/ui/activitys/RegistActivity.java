package com.yzf.Cpaypos.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.present.PRegist;
import com.yzf.Cpaypos.widget.StateButton;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
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
 * ClassName：RegistActivity
 * Description: 注册页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:49
 * Modified By：
 * Fixtime：2017/5/18 11:49
 * FixDescription：
 */
public class RegistActivity extends XActivity<PRegist> {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.regist_phone_et)
    XEditText registPhoneEt;
    @BindView(R.id.regist_code_bt)
    StateButton registCodeBt;
    @BindView(R.id.regist_code_et)
    XEditText registCodeEt;
    @BindView(R.id.regist_name_et)
    XEditText registNameEt;
    @BindView(R.id.regist_pwd_et)
    XEditText registPwdEt;
    @BindView(R.id.regist_protocol_tv)
    TextView registProtocolTv;
    @BindView(R.id.regist_bt)
    StateButton registBt;
    @BindView(R.id.regist_login_tv)
    TextView registLoginTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.regist_breach_et)
    XEditText registBreachEt;
    @BindView(R.id.regist_scan_iv)
    ImageView registScanIv;

    private int REQUEST_CODE = 100;
    private String planCode;
    private String branchNo;
    boolean scanFlag;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    public PRegist newP() {
        return new PRegist();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        registPhoneEt.setPattern((new int[]{3, 5, 5}));
        registPhoneEt.setSeparator(" ");
        registBreachEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                scanFlag = false;
            }
        });
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("注册");
    }

    /**
     * 标题栏监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.regist_code_bt, R.id.regist_protocol_tv, R.id.regist_bt, R.id.regist_login_tv, R.id.regist_scan_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist_code_bt:
                getvDelegate().showLoading();
                getP().getCode(registPhoneEt.getNonSeparatorText());
                break;
            case R.id.regist_protocol_tv:
                String centent = AppTools.readAssetsTextReturnStr(context, "protocol");//从TXT文件中读取协议
                new MaterialDialog.Builder(this)
                        .title(R.string.protocol_titie)
                        .content(centent)
                        .positiveText("同意")
                        .cancelable(false)
                        .show();
                break;
            case R.id.regist_bt:
                getvDelegate().showLoading();
                if (scanFlag) {
                    getP().regist(registPhoneEt.getNonSeparatorText(), registPwdEt.getNonSeparatorText(), registCodeEt.getNonSeparatorText(), branchNo, "01", planCode, AppConfig.TOPBRANCHNO);
                } else {
                    getP().regist(registPhoneEt.getNonSeparatorText(), registPwdEt.getNonSeparatorText(), registCodeEt.getNonSeparatorText(), null, "01", registBreachEt.getNonSeparatorText(), AppConfig.TOPBRANCHNO);
                }
                break;
            case R.id.regist_login_tv:
                finish();
                break;
            case R.id.regist_scan_iv:
                toCaptureActivity();
                break;
        }
    }

    /**
     * 调用摄像头
     */
    private void toCaptureActivity() {
        getRxPermissions()
                .request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            //TODO 许可
                            Router.newIntent(context)
                                    .to(CaptureActivity.class)
                                    .requestCode(REQUEST_CODE)
                                    .launch();

                        } else {
                            //TODO 未许可
                            showToast("权限未获取");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (!AppTools.isEmpty(result)) {
                        Map<String, List<String>> params = getQueryParams(result);
                        if (params.get("planCode") != null) {
                            planCode = params.get("planCode").get(0);
                        }
                        if (params.get("branchId") != null) {
                            branchNo = params.get("branchId").get(0);
                        }
                    }
                    if (AppTools.isEmpty(planCode) && AppTools.isEmpty(branchNo)) {
                        showToast("无效二维码");
                    } else if (!AppTools.isEmpty(planCode) && !AppTools.isEmpty(branchNo)) {
                        registBreachEt.setText(planCode);
                        scanFlag = true;
                    } else if (!AppTools.isEmpty(planCode) && AppTools.isEmpty(branchNo)) {
                        registBreachEt.setText(planCode);
                        scanFlag = true;
                    } else if (AppTools.isEmpty(planCode) && !AppTools.isEmpty(branchNo)) {
                        registBreachEt.setText(branchNo);
                        scanFlag = true;
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    showToast("解析二维码失败");
                }
            }
        }
    }

    public static Map<String, List<String>> getQueryParams(String url) {
        try {
            Map<String, List<String>> params = new HashMap<String, List<String>>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }

                    List<String> values = params.get(key);
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(key, values);
                    }
                    values.add(value);
                }
            }

            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
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

    /**
     * 显示单按钮对话框
     *
     * @param msg
     */
    public void showErrorDialog(String msg) {
        getvDelegate().showErrorDialog(msg);
    }

    /**
     * 显示错误信息
     *
     * @param error
     */
    public void showError(NetError error) {
        getvDelegate().showError(error);
    }

    public void startTimer() {
        getvDelegate().dismissLoading();
        registCodeBt.setEnabled(false);
        mTime = new Timer();
        mTime.schedule(new TimerTask() {
            int time = 60000;

            @Override
            public void run() {
                time -= 1000;
                mHandler.sendMessage(mHandler.obtainMessage(1, time));
            }
        }, 1000, 1000);
    }

    /**
     * 定时器
     */
    private Timer mTime;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int m = (Integer) msg.obj;
            registCodeBt.setText(m / 1000 + "s重新获取");
            if (m / 1000 == 0) {
                registCodeBt.setEnabled(true);
                mTime.cancel();
                registCodeBt.setText("获取验证码");
            }
        }

    };

}
