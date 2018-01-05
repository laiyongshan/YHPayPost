package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.AddRepaymentResults;
import com.yzf.Cpaypos.present.PPayBond;
import com.yzf.Cpaypos.widget.StateButton;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class PayBondActivity extends XActivity<PPayBond> {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bond_payamt_tv)
    TextView bondPayamtTv;
    @BindView(R.id.bond_total_tv)
    TextView bondTotalTv;
    @BindView(R.id.bond_fee_tv)
    TextView bondFeeTv;
    @BindView(R.id.bond_money_tv)
    TextView bondMoneyTv;
    @BindView(R.id.bond_confirm_bt)
    StateButton bondConfirmBt;
    @BindView(R.id.bond_count_tv)
    TextView bondCountTv;
    @BindView(R.id.bond_times_tv)
    TextView bondTimesTv;
    @BindView(R.id.bond_detaildays_tv)
    TextView bondDetaildaysTv;
    @BindView(R.id.bond_credit_ll)
    LinearLayout bondCreditLl;
    @BindView(R.id.bond_limit_ll)
    LinearLayout bondLimitLl;

    AddRepaymentResults.DataBean dataBean;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        dataBean = (AddRepaymentResults.DataBean) getIntent().getSerializableExtra("dataBean");
        if (dataBean != null) {
            bondTotalTv.setText(dataBean.getTotalAmount());
            bondFeeTv.setText(dataBean.getFeeAmount());
            bondMoneyTv.setText(dataBean.getDepositAmount());
            double total;
            total = Double.parseDouble(dataBean.getFeeAmount()) + Double.parseDouble(dataBean.getDepositAmount());
            bondPayamtTv.setText("￥" + AppTools.formatAmt(String.valueOf(total)));
            bondCountTv.setText(dataBean.getCount());
            bondTimesTv.setText(dataBean.getTimes());
            bondDetaildaysTv.setText(dataBean.getDetailDays());
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_bond;
    }

    @Override
    public PPayBond newP() {
        return new PPayBond();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bond_confirm_bt, R.id.bond_credit_ll, R.id.bond_limit_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bond_confirm_bt:
                Router.newIntent(context)
                        .to(PayPlanActivity.class)
                        .putSerializable("dataBean", dataBean)
                        .putString("payType","payPlan")
                        .launch();
                break;
            case R.id.bond_credit_ll:
                showToast("暂未开放");
                break;
            case R.id.bond_limit_ll:
                showToast("暂未开放");
                break;
        }
    }
}
