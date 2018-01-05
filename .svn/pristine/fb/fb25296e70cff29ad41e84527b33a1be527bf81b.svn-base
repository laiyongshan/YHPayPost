package com.yzf.Cpaypos.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.ProfitAdapter;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.servicesmodels.GetProfitListResult;
import com.yzf.Cpaypos.present.PProfitBilling;
import com.yzf.Cpaypos.widget.MultipleStatusView;
import com.yzf.Cpaypos.widget.StateView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XFragment;
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
 * ClassName：ProfitBillingFragment
 * Description: 收益账单界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 12:00
 * Modified By：
 * Fixtime：2017/5/18 12:00
 * FixDescription：
 */

public class ProfitBillingFragment extends XFragment<PProfitBilling> {
    @BindView(R.id.orderbilling_recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.orderbilling_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.orderbilling_multiplestatusview)
    MultipleStatusView multiplestatusview;
    private ProfitAdapter adapter;
    private int page_num = 10;// 每页10行
    protected static final int MAX_PAGE = 30;
    private String begin_time;
    private String end_time;
    private String merch_id;
    private String service_id = "";
    private String result = "01";

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        if (savedInstanceState == null) {
            multiplestatusview.showLoading();
        }
        merch_id = AppUser.getInstance().getUserId();
        getP().getProfitList(merch_id, 1, page_num, begin_time, end_time);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initAdapter();
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplestatusview.showLoading();
                getP().getProfitList(merch_id, 1, page_num, begin_time, end_time);
            }
        });
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new ProfitAdapter(context);
        }
        swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.theme));
        recyclerview.verticalLayoutManager(context);
        recyclerview.addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
        recyclerview.setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                Logger.i("onRefresh");
//                recycleview.refreshData();
                getP().getProfitList(merch_id, 1, page_num, begin_time, end_time);
            }

            @Override
            public void onLoadMore(int page) {
                getP().getProfitList(merch_id, page, page_num, begin_time, end_time);
            }
        });
        recyclerview.useDefLoadMoreView();
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getP().getProfitList(merch_id, 1, page_num, begin_time, end_time);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_orderbilling;
    }

    @Override
    public PProfitBilling newP() {
        return new PProfitBilling();
    }

    public void JumpActivity(Class<?> activity) {
        Router.newIntent(context)
                .to(activity)
                .launch();
    }

    public void JumpActivity(Class<?> activity, String serviceId) {
        Router.newIntent(context)
                .to(activity)
                .putString("serviceId", serviceId)
                .launch();
    }

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
     * 显示双按钮对话框
     *
     * @param msg
     * @param callback
     */
    public void showNoticeDialog(String msg, MaterialDialog.SingleButtonCallback callback) {
        getvDelegate().showNoticeDialog(msg, callback);
    }
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

    public void setAdapter(GetProfitListResult getProfitListResult, int page) {
        swiperefreshlayout.setRefreshing(false);
        multiplestatusview.showContent();
        if (page > 1) {
            adapter.addData(getProfitListResult.getData());
        } else {
            adapter.setData(getProfitListResult.getData());
        }
        if (adapter.getItemCount() < 1) {
            multiplestatusview.showEmpty();
            return;
        }
        if (getProfitListResult.getData().size() < page_num) {
            recyclerview.setPage(page, page);//当条数少于默认条数时，调整最大页数
            recyclerview.removeAllFootView();
        } else {
            recyclerview.setPage(page, MAX_PAGE);//必须设置setPage，否则上拉加载更多会无效
        }

    }

}
