package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.PlanCardDetailAdapter;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.servicesmodels.AddRepaymentResults;
import com.yzf.Cpaypos.model.servicesmodels.GetPlanCardResults;
import com.yzf.Cpaypos.present.PPlanCardDetail;
import com.yzf.Cpaypos.widget.MultipleStatusView;
import com.yzf.Cpaypos.widget.StateButton;
import com.yzf.Cpaypos.widget.StateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.XRecyclerView;

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
 * ClassName：PlanCardDetailActivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/6 11:47
 * Modified By：
 * Fixtime：2017/9/6 11:47
 * FixDescription：
 */
public class PlanCardDetailActivity extends XActivity<PPlanCardDetail> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.plancarddetail_recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.plancarddetail_multiplestatusview)
    MultipleStatusView multiplestatusview;
    @BindView(R.id.plancarddetail_confirm_bt)
    StateButton plancarddetailConfirmBt;
    private GetPlanCardResults.DataBean dataBean = new GetPlanCardResults.DataBean();
    PlanCardDetailAdapter adapter;


    @Override
    public void initData(Bundle savedInstanceState) {
        dataBean = (GetPlanCardResults.DataBean) getIntent().getSerializableExtra("dataBean");
        initView();
        initAdapter();
        if(savedInstanceState==null)
        {
            multiplestatusview.showLoading();
        }
        getP().GetPlanCardDetail(dataBean.getOrderId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_card_detail;
    }

    @Override
    public PPlanCardDetail newP() {
        return new PPlanCardDetail();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        if (dataBean.getStatus().equals("00")) {
            plancarddetailConfirmBt.setVisibility(View.VISIBLE);
        }
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplestatusview.showLoading();
                getP().GetPlanCardDetail(dataBean.getOrderId());
            }
        });
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new PlanCardDetailAdapter(context);
        }
        recyclerview.verticalLayoutManager(this);
        recyclerview.addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
    }

    public void setAdapter(GetPlanCardResults.DataBean dataBean) {
        multiplestatusview.showContent();
        if (dataBean == null) {
            multiplestatusview.showEmpty("暂无数据");
            return;
        }
        adapter.setData(dataBean.getDetail());
        if (adapter.getItemCount() < 1) {
            multiplestatusview.showEmpty("暂无数据");
            return;
        }
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
        title.setText("规划明细");
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

    /**
     * activity跳转
     */
    public void JumpActivity(Class<?> activity, boolean isfinish, AddRepaymentResults.DataBean dataBean) {
        getvDelegate().dismissLoading();
        Router.newIntent(context)
                .to(activity)
                .putSerializable("dataBean", dataBean)
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

    public void showEmptyView(String msg) {
        multiplestatusview.showEmpty(msg);
    }

    public void showErrorView(String msg) {
        multiplestatusview.showError(msg);
    }

    public void showErrorView(NetError error) {
        multiplestatusview.showError(getvDelegate().getErrorType(error));
    }

    public void finishActivity(String msg) {
        showToast(msg);
        finish();
    }

    @OnClick(R.id.plancarddetail_confirm_bt)
    public void onViewClicked() {
        AddRepaymentResults.DataBean bean=new AddRepaymentResults.DataBean();
        bean.setDepositAmount(AppTools.formatFenAmt(dataBean.getDepositAmount()));
        bean.setFeeAmount(AppTools.formatFenAmt(dataBean.getFeeAmount()));
        bean.setTotalAmount(AppTools.formatFenAmt(dataBean.getRepaymentAmount()));
        bean.setCardId(dataBean.getCardId());
        bean.setOrderId(dataBean.getOrderId());
        bean.setStatus(dataBean.getStatus());
        bean.setCount(dataBean.getCount());
        bean.setTimes(dataBean.getTimes());
        bean.setDetailDays(dataBean.getDetailDays());

        JumpActivity(PayBondActivity.class, true, bean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
