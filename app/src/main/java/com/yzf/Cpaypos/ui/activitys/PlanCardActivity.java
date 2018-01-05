package com.yzf.Cpaypos.ui.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.CgiBankCardAdapter;
import com.yzf.Cpaypos.adapter.PlanCardAdapter;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.ChooseItem;
import com.yzf.Cpaypos.model.servicesmodels.GetPlanCardResults;
import com.yzf.Cpaypos.model.servicesmodels.GetPreviewPlanResult;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.present.PPlanCard;
import com.yzf.Cpaypos.widget.BottomDialog;
import com.yzf.Cpaypos.widget.MultipleStatusView;
import com.yzf.Cpaypos.widget.StateButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerView;
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
 * ClassName：PlanCardActivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/5 10:21
 * Modified By：
 * Fixtime：2017/9/5 10:21
 * FixDescription：
 */

public class PlanCardActivity extends XActivity<PPlanCard>{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bank_iv)
    ImageView bankIv;
    @BindView(R.id.card_bankname_tv)
    TextView cardBanknameTv;
    @BindView(R.id.card_banktype_tv)
    TextView cardBanktypeTv;
    @BindView(R.id.card_acctno_tv)
    TextView cardAcctnoTv;
    @BindView(R.id.card_payday_tv)
    TextView cardPaydayTv;
    @BindView(R.id.card_repayday_tv)
    TextView cardRepaydayTv;
    @BindView(R.id.card_limit_tv)
    TextView cardLimitTv;
    @BindView(R.id.bank_bg)
    LinearLayout bankBg;
    @BindView(R.id.plancard_recyclerview)
    XRecyclerView recyclerview;
    @BindView(R.id.card_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.card_multiplestatusview)
    MultipleStatusView multiplestatusview;
    @BindView(R.id.plancard_add_bt)
    StateButton plancardAddBt;
    private GetWhiteCardListResult.DataBean dataBean = new GetWhiteCardListResult.DataBean();
    private int delposition = 0;

    private int page_num = 10;// 每页10行
    protected static final int MAX_PAGE = 30;
    private String begin_time;
    private String end_time;
    private String status;
    private PlanCardAdapter adapter;
    private View headView;
    private TextView amtTv;
    private TextView countTv;
    private TextView feeTv;
    private TextView progressTv;
    private TextView bankNameTv;
    private TextView bankNoTv;
    private ImageView headBankIv;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initData(Bundle savedInstanceState) {
        useEventBus(true);
        dataBean = (GetWhiteCardListResult.DataBean) getIntent().getSerializableExtra("dataBean");
        if (dataBean!=null) {
            Gson gson = new Gson();
            AppUser.getInstance().setCardInfo(gson.toJson(dataBean));//将卡信息存到单例中
        }
        initView();
        initAdapter();
        if (savedInstanceState == null) {
            multiplestatusview.showLoading();
        }
        getP().GetPlanCard(1, AppUser.getInstance().getUserId(), dataBean.getAcctNo(), page_num, begin_time, end_time, status);
        getP().getPreviewPlan(AppUser.getInstance().getUserId(),dataBean.getAcctNo());
    }

    @Override
    public void onResume() {
        super.onResume();
//        contentLayout.showLoading();
//        getP().GetPlanCard(1,AppUser.getInstance().getUserId(), dataBean.getAcctNo(),page_num,begin_time,end_time,status);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_card;
    }

    @Override
    public PPlanCard newP() {
        return new PPlanCard();
    }

    /**
     * 初始化界面
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        initToolbar();
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplestatusview.showLoading();
                getP().GetPlanCard(1, AppUser.getInstance().getUserId(), dataBean.getAcctNo(), page_num, begin_time, end_time, status);
            }
        });

        cardAcctnoTv.setText(setAcctno(dataBean.getAcctNo()));
        cardBanknameTv.setText(dataBean.getCardDesc());
        cardBanktypeTv.setText(setCardType(dataBean.getCardType()));
        cardPaydayTv.setText("账单日：" + dataBean.getAdd3() + "日");
        cardRepaydayTv.setText("还款日：" + dataBean.getAdd4() + "日");
        cardLimitTv.setText("额度：" + dataBean.getCardLimit() + "元");
        if (dataBean.getCardDesc().contains("工商") || dataBean.getCardDesc().contains("中国银行")
                || dataBean.getCardDesc().contains("招商")|| dataBean.getCardDesc().contains("中信")) {
            bankBg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_red));
        } else if (dataBean.getCardDesc().contains("农业") || dataBean.getCardDesc().contains("邮政")) {
            bankBg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_green));
        } else if (dataBean.getCardDesc().contains("交通") || dataBean.getCardDesc().contains("浦")|| dataBean.getCardDesc().contains("民生")
                || dataBean.getCardDesc().contains("兴业")|| dataBean.getCardDesc().contains("建设")) {
            bankBg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_blue));
        } else if (dataBean.getCardDesc().contains("平安") || dataBean.getCardDesc().contains("光大") || dataBean.getCardDesc().contains("农村")) {
            bankBg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_yellow));
        } else {
            bankBg.setBackground(ContextCompat.getDrawable(context, R.drawable.bankcard_red));
        }

        int id = getImgId("b" + dataBean.getCardInst());
        if (id > 0) {
            RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), id2Bitmap(id));
            //设置为圆形
            circleDrawable.setCircular(true);
            bankIv.setImageDrawable(circleDrawable);
        }
    }

    private String setAcctno(String acctNo) {
        if (!AppTools.isEmpty(acctNo)) {
            acctNo = "****     ****     ****     " + acctNo.substring(acctNo.length() - 4, acctNo.length());
        }
        return acctNo;
    }

    private String setCardType(String cartType) {
        String type = "储蓄卡";
        if (!AppTools.isEmpty(cartType)) {
            if (cartType.equals("00")) {
                type = "信用卡";
            } else if (cartType.equals("01")) {
                type = "储蓄卡";
            } else if (cartType.equals("02")) {
                type = "准贷记卡";
            }
        }
        return type;
    }

    public void setPreviewPlan(GetPreviewPlanResult getPreviewPlanResult, boolean flag) {
        if (flag) {
            GetPreviewPlanResult.DataBean dataBean = getPreviewPlanResult.getData();
            bankNameTv.setText(dataBean.getCardDesc());
            String card_id = dataBean.getCard_id();
            if (card_id != null && card_id.length() > 4) {
                card_id = card_id.substring(card_id.length() - 4, card_id.length());
            }
            bankNoTv.setText(card_id);
            amtTv.setText(dataBean.getRepayment_amount());
            countTv.setText(dataBean.getTimes());
            progressTv.setText(dataBean.getSchedule());
            feeTv.setText(dataBean.getFee_amt());
        } else {
            bankNameTv.setText("暂无规划");
        }

    }

    private Bitmap id2Bitmap(int id) {
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    private int getImgId(String imgName) {
        int id = -1;
        try {
            id = context.getResources().getIdentifier(imgName, "mipmap", context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void bindEvent() {
        BusProvider.getBus().toObservable(IEvent.class)
                .subscribe(new Action1<IEvent>() {
                    @Override
                    public void call(IEvent iEvent) {
                        //TODO 事件处理
                        if (iEvent.getId().equals("refresh_plan")) {
                            getP().GetPlanCard(1, AppUser.getInstance().getUserId(), dataBean.getAcctNo(), page_num, begin_time, end_time, status);
                            getP().getPreviewPlan(AppUser.getInstance().getUserId(),dataBean.getAcctNo());
                        }
                    }
                });
    }

    /**
     * 删除规划后刷新规划列表
     *
     * @param msg
     * @param confirmType
     */
    public void refresh(String msg, String confirmType) {
        getvDelegate().toastShort(msg);
        if (confirmType!=null&&confirmType.equals("delete")) {
            adapter.removeElement(delposition);
        } else {
            getP().GetPlanCard(1, AppUser.getInstance().getUserId(), dataBean.getAcctNo(), page_num, begin_time, end_time, status);
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
        title.setText(dataBean.getCardDesc());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new PlanCardAdapter(context);
        }

        adapter.setOnMyItemClickListener(new PlanCardAdapter.OnMyItemClickListener() {
            @Override
            public void myClick(View v, int pos, GetPlanCardResults.DataBean item) {
                if (item.getStatus().equals("00")) {
                    Router.newIntent(context)
                            .to(PlanCardDetailActivity.class)
                            .putSerializable("dataBean", item)
                            .launch();
                } else {
                    Router.newIntent(context)
                            .to(PlanCardDetailsActivity.class)
                            .putSerializable("dataBean", item)
                            .launch();
                }
            }

            @Override
            public void mLongClick(View v, int pos, GetPlanCardResults.DataBean item) {
                final int tempposition = pos;
                final GetPlanCardResults.DataBean tempitem = item;
                delposition = tempposition;
                if (item.getStatus()!=null&&item.getStatus().equals("00")) {
                    showNoticeDialog("是否删除该规划", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which == DialogAction.POSITIVE) {
                                getvDelegate().showLoading();
                                delposition = tempposition;
                                getP().ConfirmPlanCard(AppUser.getInstance().getUserId(), tempitem.getCardId(), tempitem.getOrderId(), "delete");
                            }
                        }
                    });
                }else if (item.getStatus()!=null&&item.getStatus().equals("01")||item.getStatus().equals("04")||item.getStatus().equals("05")){
                    showNoticeDialog("是否终止该规划", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which == DialogAction.POSITIVE) {
                                getvDelegate().showLoading();
                                getP().ConfirmPlanCard(AppUser.getInstance().getUserId(), tempitem.getCardId(), tempitem.getOrderId(), "end");
                            }
                        }
                    });
                }
            }
        } );


        swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.theme));
        recyclerview.verticalLayoutManager(this);
        recyclerview.addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
        recyclerview.setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                Logger.i("onRefresh");
                getP().GetPlanCard(1, AppUser.getInstance().getUserId(), dataBean.getAcctNo(), page_num, begin_time, end_time, status);
            }

            @Override
            public void onLoadMore(int page) {
                getP().GetPlanCard(page, AppUser.getInstance().getUserId(), dataBean.getAcctNo(), page_num, begin_time, end_time, status);
            }
        });
        recyclerview.useDefLoadMoreView();

        headView = LayoutInflater.from(this).inflate(R.layout.head_plancard, recyclerview, false);
        amtTv = (TextView) headView.findViewById(R.id.head_amt_tv);
        feeTv = (TextView) headView.findViewById(R.id.head_fee_tv);
        progressTv = (TextView) headView.findViewById(R.id.head_progress_tv);
        countTv = (TextView) headView.findViewById(R.id.head_count_tv);
        bankNameTv = (TextView) headView.findViewById(R.id.head_bankname_tv);
        bankNoTv = (TextView) headView.findViewById(R.id.head_bankno_tv);
        headBankIv = (ImageView) headView.findViewById(R.id.head_bank_iv);
        recyclerview.addHeaderView(headView);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getP().GetPlanCard(1, AppUser.getInstance().getUserId(), dataBean.getAcctNo(), page_num, begin_time, end_time, status);
                getP().getPreviewPlan(AppUser.getInstance().getUserId(),dataBean.getAcctNo());
            }
        });
    }


    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_creditcard;
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
                JumpActivity(ModifiedCreditCardctivity.class, true);
                break;
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
     * 显示双按钮对话框
     *
     * @param msg
     * @param callback
     */
    public void showNoticeDialog(String msg, MaterialDialog.SingleButtonCallback callback) {
        getvDelegate().showNoticeDialog(msg, callback);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void setAdapter(GetPlanCardResults getPlanCardResults, int page) {
        swiperefreshlayout.setRefreshing(false);
        multiplestatusview.showContent();
        List<GetPlanCardResults.DataBean> list = new ArrayList<>();
        list = getPlanCardResults.getData();
        if (page > 1) {
            adapter.addData(list);
        } else {
            adapter.setData(list);
        }
        if (adapter.getItemCount() < 1) {
            multiplestatusview.showEmpty("暂无数据");
            return;
        }
        if (list.size() < page_num) {
            recyclerview.setPage(page, page);//当条数少于默认条数时，调整最大页数
            recyclerview.removeAllFootView();
        } else {
            recyclerview.setPage(page, MAX_PAGE);//必须设置setPage，否则上拉加载更多会无效
        }
    }

    @OnClick({R.id.bank_iv, R.id.plancard_add_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bank_iv:
                break;
            case R.id.plancard_add_bt:
                Router.newIntent(context)
                        .to(RepaymentActivity.class)
                        .putSerializable("dataBean", dataBean)
                        .launch();
                break;
        }
    }
}
