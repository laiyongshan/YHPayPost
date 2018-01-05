package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.AccountAdapter;
import com.yzf.Cpaypos.adapter.MerchTransAdapter;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.servicesmodels.GetAccountInfoResult;
import com.yzf.Cpaypos.model.servicesmodels.GetAccountResults;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
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
 * ClassName：AccountActivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/12/8 11:22
 * Modified By：
 * Fixtime：2017/12/8 11:22
 * FixDescription：
 */
public class AccountActivity extends XActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account_recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.account_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.account_multiplestatusview)
    MultipleStatusView multiplestatusview;
    AccountAdapter adapter;
    GetAccountInfoResult.DataBean dataBean;

    @Override
    public void initData(Bundle savedInstanceState) {
        dataBean = (GetAccountInfoResult.DataBean) getIntent().getSerializableExtra("dataBean");
        initView();
        initAdapter();
        if (savedInstanceState == null) {
            multiplestatusview.showLoading();
        }
        setAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account;
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

    public void initAdapter() {
        if (adapter == null) {
            adapter = new AccountAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GetAccountResults, AccountAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GetAccountResults item, int tag, AccountAdapter.ViewHolder holder) {
                    super.onItemClick(position, item, tag, holder);
                    switch (tag) {
                        case AccountAdapter.TAG_VIEW:
                            if(item.getType().equals("0"))
                            {
                                Router.newIntent(context)
                                    .to(WithDrawActivity.class)
                                    .putSerializable("dataBean", item)
                                    .launch();
                            }else {
                                showToast("暂未开放");
                            }

                            break;
                    }
                }
            });
        }
        swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.theme));
        recyclerview.verticalLayoutManager(this);
//        recyclerview.addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
        swiperefreshlayout.setEnabled(false);
    }

    private void setAdapter()
    {
        multiplestatusview.showContent();
        List<GetAccountResults> list=new ArrayList<>();
        GetAccountResults getAccountResults=new GetAccountResults();
        if(dataBean!=null)
        {
            getAccountResults.setAmt(dataBean.getSumAmt());
            getAccountResults.setAvlAmt(dataBean.getAvlbBal());
            getAccountResults.setFznAmt(dataBean.getFznAmt());
            getAccountResults.setType("0");
            list.add(getAccountResults);
        }
        getAccountResults=new GetAccountResults();
        getAccountResults.setAmt("0.00");
        getAccountResults.setAvlAmt("0.00");
        getAccountResults.setFznAmt("0.00");
        getAccountResults.setType("1");
        list.add(getAccountResults);

        getAccountResults=new GetAccountResults();
        getAccountResults.setAmt("0.00");
        getAccountResults.setAvlAmt("0.00");
        getAccountResults.setFznAmt("0.00");
        getAccountResults.setType("2");
        list.add(getAccountResults);

        adapter.setData(list);
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
        title.setText("账户信息");
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

}
