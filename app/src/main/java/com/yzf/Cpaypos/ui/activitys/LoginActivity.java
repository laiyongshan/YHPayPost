package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;
import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.App;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.utils.AesUtil;
import com.yzf.Cpaypos.present.PLogin;
import com.yzf.Cpaypos.widget.StateButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;

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
 * ClassName：LoginActivity
 * Description:用户登录界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/17 16:55
 * Modified By：
 * Fixtime：2017/3/17 16:55
 * FixDescription：
 */
public class LoginActivity extends XActivity<PLogin> implements OnItemClickListener {
    /* @BindView(R.id.title)
     TextView title;*/
    @BindView(R.id.login_merchid_et)
    XEditText loginMerchidEt;
    @BindView(R.id.login_psw_et)
    XEditText loginPswEt;
    @BindView(R.id.login_remember_cb)
    CheckBox loginRememberCb;
    @BindView(R.id.login_bt)
    StateButton loginBt;
    @BindView(R.id.login_find_pwd_tv)
    TextView loginFindPwdTv;
    @BindView(R.id.login_regiest_tv)
    TextView loginRegiestTv;
    /* @BindView(R.id.toolbar)
     Toolbar toolbar;*/
    private List<GetBannerListResult.DataBean> bannerList;

    @Override
    public void initData(Bundle savedInstanceState) {
//        getP().getBannerList();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        String merchId = SharedPref.getInstance(context).getString("merchId", "");//充本地缓存中获取账号密码
        String password = SharedPref.getInstance(context).getString("password", "");
        boolean autoLogin = SharedPref.getInstance(context).getBoolean("autoLogin", true);
        loginRememberCb.setChecked(autoLogin);
        if (autoLogin) {
            if (!AppTools.isEmpty(merchId)) {
                password = AesUtil.decrypt(password);
                loginMerchidEt.setText(merchId);
                loginPswEt.setText(password);
            } else {
                loginMerchidEt.setText("");
                loginPswEt.setText("");
            }
        } else {
            loginMerchidEt.setText(merchId);
            loginPswEt.setText("");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initToolbar() {
 /*       setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("登录");*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//隐藏标题栏，弄成全屏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public PLogin newP() {
        return new PLogin();
    }


    @OnClick({R.id.login_bt, R.id.login_find_pwd_tv, R.id.login_regiest_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                getvDelegate().showLoading();
                getP().login(loginMerchidEt.getNonSeparatorText(), loginPswEt.getNonSeparatorText(), AppConfig.TOPBRANCHNO);
                break;
            case R.id.login_find_pwd_tv:
                JumpActivity(FindPasswordActivity.class, false);
                break;
            case R.id.login_regiest_tv:
                JumpActivity(RegistActivity.class, false);
                break;
        }
    }

    /**
     * activity跳转
     */
    public void JumpActivity(Class<?> activity, boolean isfinish) {
        if (loginRememberCb.isChecked()) {
            SharedPref.getInstance(App.getContext()).putBoolean("autoLogin", true);

        } else {
            SharedPref.getInstance(App.getContext()).putBoolean("autoLogin", false);

        }
        Router.newIntent(context)
                .to(activity)
                .launch();
        if (isfinish) {
            Router.pop(context);
        }
        getvDelegate().dismissLoading();
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

    public void showError(NetError error) {
        getvDelegate().showError(error);
    }

    @Override
    public void onItemClick(int position) {

    }

}
