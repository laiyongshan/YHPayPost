package com.yzf.Cpaypos.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.ActivityManager;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.AddRepaymentResults;
import com.yzf.Cpaypos.present.PPayBond;
import com.yzf.Cpaypos.widget.StateButton;

import butterknife.BindView;
import butterknife.OnClick;
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
 * ClassName：PayBondActivity
 * Description: 支付保证金页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/4 11:51
 * Modified By：
 * Fixtime：2017/9/4 11:51
 * FixDescription：
 */
public class PaySuccessActivity extends XActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.success_payamt_tv)
    TextView successPayamtTv;
    @BindView(R.id.success_total_tv)
    TextView successTotalTv;
    @BindView(R.id.success_fee_tv)
    TextView successFeeTv;
    @BindView(R.id.success_money_tv)
    TextView successMoneyTv;
    @BindView(R.id.success_confirm_bt)
    Button successConfirmBt;

    AddRepaymentResults.DataBean dataBean;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        dataBean = (AddRepaymentResults.DataBean) getIntent().getSerializableExtra("dataBean");
        if (dataBean != null) {
            successTotalTv.setText(dataBean.getTotalAmount());
            successFeeTv.setText(dataBean.getFeeAmount());
            successMoneyTv.setText(dataBean.getDepositAmount());
            double total;
            total = Double.parseDouble(dataBean.getFeeAmount()) + Double.parseDouble(dataBean.getDepositAmount());
            successPayamtTv.setText("￥"+AppTools.formatAmt(String.valueOf(total)));
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
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
        title.setText("支付保留金");
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


    /**
     * activity跳转
     */
    public void JumpActivity(Class<?> activity, boolean isfinish) {
        getvDelegate().dismissLoading();
        Router.newIntent(context)
                .to(activity)
                .launch();
        if (isfinish) {
            Router.pop(context);
        }
    }

    public void finishActivity(String msg) {
        showToast(msg);
        finish();
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
     * 显示当按钮对话框
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

    @OnClick(R.id.success_confirm_bt)
    public void onViewClicked() {
       /* Router.newIntent(context)
                .to(MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .launch();
        Router.pop(context);*/
        ActivityManager.getInstance().finishAllActivityExceptOne(MainActivity.class);
    }

}
