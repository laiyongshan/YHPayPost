package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.SubMerchAdapter;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.servicesmodels.GetSubmerchResult;
import com.yzf.Cpaypos.present.PSubMerch;
import com.yzf.Cpaypos.widget.StateView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
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
 * ClassName：SubMerchActivity
 * Description: 子商户界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/18 16:23
 * Modified By：
 * Fixtime：2017/4/18 16:23
 * FixDescription：
 */
public class SubMerchActivity extends XActivity<PSubMerch> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.submerch_contentLayout)
    XRecyclerContentLayout contentLayout;
    StateView errorView;
    private SubMerchAdapter adapter;

    private int page_num = 10;// 每页10行
    protected static final int MAX_PAGE = 30;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        contentLayout.showLoading();
        getP().getSubMerch(AppUser.getInstance().getUserId(), 1, page_num);
//        Integer.parseInt("asdf");

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_merch;
    }

    @Override
    public PSubMerch newP() {
        return new PSubMerch();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        initAdapter();
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
        title.setText("我的商户");
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

    public void initAdapter() {
        if (adapter == null) {
            adapter = new SubMerchAdapter(context);
        }
        contentLayout.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.theme));
        contentLayout.getRecyclerView().verticalLayoutManager(this);
        contentLayout.getRecyclerView().addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        contentLayout.getRecyclerView().setAdapter(adapter);
        contentLayout.getRecyclerView().setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                Logger.i("onRefresh");
//                contentLayout.getRecyclerView().refreshData();
                getP().getSubMerch(AppUser.getInstance().getUserId(), 1, page_num);
            }

            @Override
            public void onLoadMore(int page) {
                getP().getSubMerch(AppUser.getInstance().getUserId(), page, page_num);
            }
        });
        if (errorView == null) {
            errorView = new StateView(context);
        }
        contentLayout.errorView(errorView);
        contentLayout.getRecyclerView().useDefLoadMoreView();
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
        contentLayout.emptyView(View.inflate(context, R.layout.view_empty, null));
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

    public void showErrorView(String msg, int id) {
        errorView.setMsg(msg);
        errorView.setImg(id);
        contentLayout.showError();
    }

    public void showErrorView(NetError error, int id) {
        errorView.setMsg(getvDelegate().getErrorType(error));
        errorView.setImg(id);
        contentLayout.showError();
    }


    public void setAdapter(GetSubmerchResult getSubmerchResult, int page) {
        if (page > 1) {
            adapter.addData(getSubmerchResult.getData());
        } else {
            adapter.setData(getSubmerchResult.getData());
        }
        if (adapter.getItemCount() < 1) {
            contentLayout.showEmpty();
            return;
        }
        if (getSubmerchResult.getData().size() < page_num) {
            contentLayout.getRecyclerView().setPage(page, page);//当条数少于默认条数时，调整最大页数
            contentLayout.getRecyclerView().removeAllFootView();
        } else {
            contentLayout.getRecyclerView().setPage(page, MAX_PAGE);//必须设置setPage，否则上拉加载更多会无效
        }
    }

}
