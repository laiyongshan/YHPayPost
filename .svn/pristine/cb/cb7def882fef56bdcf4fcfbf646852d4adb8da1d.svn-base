package com.yzf.Cpaypos.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.MerchTransAdapter;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.present.PDfBilling;
import com.yzf.Cpaypos.ui.activitys.TransDetailActivity;
import com.yzf.Cpaypos.widget.MultipleStatusView;
import com.yzf.Cpaypos.widget.StateView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
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
 * ClassName：DfBillingFragment
 * Description: 提现账单界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:54
 * Modified By：
 * Fixtime：2017/5/18 11:54
 * FixDescription：
 */
public class DfBillingFragment extends XFragment<PDfBilling> {

    @BindView(R.id.orderbilling_recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.orderbilling_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.orderbilling_multiplestatusview)
    MultipleStatusView multiplestatusview;
    private MerchTransAdapter adapter;
    private int page_num = 10;// 每页10行
    protected static final int MAX_PAGE = 30;
    private String begin_time;
    private String end_time;
    private String merch_id;
    private String service_id = "01";
    private String result = "01";
    private String notServiceId = "";

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        if (savedInstanceState == null) {
            multiplestatusview.showLoading();
        }
        merch_id = AppUser.getInstance().getUserId();
        getP().GetOrderList(1, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
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
                getP().GetOrderList(1, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
            }
        });
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new MerchTransAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GetOrderListResult.DataBean, MerchTransAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GetOrderListResult.DataBean item, int tag, MerchTransAdapter.ViewHolder holder) {
                    super.onItemClick(position, item, tag, holder);
                    switch (tag) {
                        case MerchTransAdapter.TAG_VIEW:
                            Router.newIntent(context)
                                    .to(TransDetailActivity.class)
                                    .putSerializable("dataBean", item)
                                    .launch();
                            break;
                    }
                }
            });
        }
        swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.theme));
        recyclerview.verticalLayoutManager(context);
        recyclerview.addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
        recyclerview.setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                Logger.i("onRefresh");
                getP().GetOrderList(1, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
            }

            @Override
            public void onLoadMore(int page) {
                getP().GetOrderList(page, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
            }
        });
        recyclerview.useDefLoadMoreView();
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getP().GetOrderList(1, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_orderbilling;
    }

    @Override
    public PDfBilling newP() {
        return new PDfBilling();
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

    public void setAdapter(GetOrderListResult getOrderListResult, int page) {
        swiperefreshlayout.setRefreshing(false);
        multiplestatusview.showContent();
        if (page > 1) {
            adapter.addData(getOrderListResult.getData());
        } else {
            adapter.setData(getOrderListResult.getData());
        }
        if (adapter.getItemCount() < 1) {
            multiplestatusview.showEmpty("暂无数据");
            return;
        }
        if (getOrderListResult.getData().size() < page_num) {
            recyclerview.setPage(page, page);//当条数少于默认条数时，调整最大页数
            recyclerview.removeAllFootView();
        } else {
            recyclerview.setPage(page, MAX_PAGE);//必须设置setPage，否则上拉加载更多会无效
        }

    }

}
