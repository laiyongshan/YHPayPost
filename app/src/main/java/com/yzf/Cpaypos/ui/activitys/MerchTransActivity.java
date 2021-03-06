package com.yzf.Cpaypos.ui.activitys;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.MerchTransAdapter;
import com.yzf.Cpaypos.kit.ActivityManager;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.present.PMerchTrans;
import com.yzf.Cpaypos.widget.DialogPopup;
import com.yzf.Cpaypos.widget.MultipleStatusView;
import com.yzf.Cpaypos.widget.StateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XActivity;
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
 * ClassName：MerchTransActivity
 * Description: 商户交易信息界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/24 14:10
 * Modified By：
 * Fixtime：2017/3/24 14:10
 * FixDescription：
 */
public class MerchTransActivity extends XActivity<PMerchTrans> implements DialogPopup.IPopupCallBack {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.merchtrans_recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.merchtrans_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.merchtrans_multiplestatusview)
    MultipleStatusView multiplestatusview;
    private MerchTransAdapter adapter;

    private int page_num = 10;// 每页10行
    protected static final int MAX_PAGE = 30;
    // 时间
//    private List<GetOrderListResult.DataBean> mData = new ArrayList<>();
    private String begin_time;
    private String end_time;
    private String merch_id;
    private String service_id = "";
    private String result = "";
    private String notServiceId = "";
    private DialogPopup popup;

    @Override
    public void initData(Bundle savedInstanceState) {
        merch_id = AppUser.getInstance().getUserId();
        initView();
        initAdapter();
        /*end_time = Kits.Date.getYmd() + " 23:59:59";
        begin_time = Kits.Date.backDate(-7) + " 00:00:00";*/
        if (savedInstanceState == null) {
            multiplestatusview.showLoading();
        }
        getP().GetOrderList(1, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
    }

    @Override
    public void onResume() {
        super.onResume();
        DialogPopup.setmPopupCallBack(this);
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
        recyclerview.verticalLayoutManager(this);
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
        return R.layout.activity_merch_trans;
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_trans;
    }

    @Override
    public PMerchTrans newP() {
        return new PMerchTrans();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplestatusview.showLoading();
                getP().GetOrderList(1, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
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
        title.setText("交易");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 标题栏监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                break;
            case R.id.menu_text:
                FragmentManager fragmentManager = getFragmentManager();//弹出条件查询对话框
                popup = new DialogPopup(context, fragmentManager);
                popup.showPopupWindow();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    /**
     * 条件查询
     *
     * @param startTime
     * @param endTime
     * @param type
     * @param results
     */
    @Override
    public void query(String startTime, String endTime, String type, String results) {
        if (popup != null) {
            popup.dismiss();
        }
        begin_time = startTime + " 00:00:00";
        end_time = endTime + " 23:59:59";
        service_id = type;
        result = results;
        multiplestatusview.showLoading();
        getP().GetOrderList(1, page_num, begin_time, end_time, merch_id, service_id, result, "", notServiceId);
    }

    @Override
    public void onBackPressed() {
        if (popup!=null&&popup.isShowing()) {
            popup.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
