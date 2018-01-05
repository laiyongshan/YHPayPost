package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mcxtzhang.nestlistview.NestFullListView;
import com.mcxtzhang.nestlistview.NestFullListViewAdapter;
import com.mcxtzhang.nestlistview.NestFullViewHolder;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetPlanCardResults;
import com.yzf.Cpaypos.present.PPlanCardDetails;
import com.yzf.Cpaypos.widget.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
 * ClassName：PlanCardDetailActivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/6 11:47
 * Modified By：
 * Fixtime：2017/9/6 11:47
 * FixDescription：
 */
public class PlanCardDetailsActivity extends XActivity<PPlanCardDetails> {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fullListView)
    NestFullListView fullListView;
    @BindView(R.id.plancarddetails_multiplestatusview)
    MultipleStatusView multiplestatusview;
    private GetPlanCardResults.DataBean dataBean = new GetPlanCardResults.DataBean();


    @Override
    public void initData(Bundle savedInstanceState) {
        dataBean = (GetPlanCardResults.DataBean) getIntent().getSerializableExtra("dataBean");
        initView();
        if (savedInstanceState == null) {
            multiplestatusview.showLoading();
        }
        getP().GetPlanCardDetail(dataBean.getOrderId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan_card_details;
    }

    @Override
    public PPlanCardDetails newP() {
        return new PPlanCardDetails();
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
                getP().GetPlanCardDetail(dataBean.getOrderId());
            }
        });

    }

    public void setAdapter(GetPlanCardResults.DataBean dataBean) {
        multiplestatusview.showContent();
        fullListView.setAdapter(new NestFullListViewAdapter<GetPlanCardResults.DataBean.DetailListBean>(R.layout.adapter_plancard_detail, dataBean.getDetailList()) {
            @Override
            public void onBind(int pos, GetPlanCardResults.DataBean.DetailListBean detailListBean, NestFullViewHolder holder) {
//                Logger.i("嵌套第一层ScrollView onBind() called with: pos = [" + pos + "], detailListBean = [" + detailListBean + "], v = [" + holder + "]");
                holder.setText(R.id.plancard_types_adapter, formatType(detailListBean.getRepayType(), detailListBean.getRepayStatus()));
                holder.setText(R.id.plancard_amt_adapter, "￥" + detailListBean.getRepayAmt());
                holder.setText(R.id.plancard_time_adapter, detailListBean.getRepayTime());

                List<GetPlanCardResults.DataBean.DetailListBean.ListBean> listBeans = new ArrayList<>();
                listBeans = detailListBean.getList();
                /*if (listBeans.size() > 1) {
                    listBeans = listBeans.subList(1, listBeans.size());
                }*/
                ((NestFullListView) holder.getView(R.id.fullListView2)).setAdapter(new NestFullListViewAdapter<GetPlanCardResults.DataBean.DetailListBean.ListBean>(R.layout.adapter_plan, listBeans) {
                    @Override
                    public void onBind(int pos, GetPlanCardResults.DataBean.DetailListBean.ListBean listBean, NestFullViewHolder holder) {
//                        Logger.i( "嵌套第二层onBind() called with: pos = [" + pos + "], listBean = [" + listBean + "], v = [" + holder + "]");
                        holder.setText(R.id.plan_type_adapter, formatType(listBean.getTrans_type(), listBean.getStatus()));
                        holder.setText(R.id.plan_amt_adapter, "￥" + listBean.getTrans_amt());
                        holder.setText(R.id.plan_time_adapter, listBean.getRepay_date());
                    }
                });
            }
        });

    }


    private String formatType(String s, String status) {
        if (AppTools.isEmpty(status)) {
            if (!AppTools.isEmpty(s) && s.equals("71") || !AppTools.isEmpty(s) && s.equals("73")) {
                return "还款";
            }
            if (!AppTools.isEmpty(s) && s.equals("72") || !AppTools.isEmpty(s) && s.equals("70")) {
                return "消费";
            }
        } else {
            if (status.equals("0") || status.equals("1")) {
                if (!AppTools.isEmpty(s) && s.equals("71") || !AppTools.isEmpty(s) && s.equals("73")) {
                    if (s.equals("73")) {
                        return "未还款(保证金退还)";
                    }
                    return "未还款";
                }
                if (!AppTools.isEmpty(s) && s.equals("72") || !AppTools.isEmpty(s) && s.equals("70")) {
                    return "未消费";
                }
            } else if (status.equals("2")) {
                if (!AppTools.isEmpty(s) && s.equals("71") || !AppTools.isEmpty(s) && s.equals("73")) {
                    if (s.equals("73")) {
                        return "已还款(保证金退还)";
                    }
                    return "已还款";
                }
                if (!AppTools.isEmpty(s) && s.equals("72") || !AppTools.isEmpty(s) && s.equals("70")) {
                    return "已消费";
                }
            } else if (status.equals("3")) {
                if (!AppTools.isEmpty(s) && s.equals("71") || !AppTools.isEmpty(s) && s.equals("73")) {
                    if (s.equals("73")) {
                        return "还款失败(保证金退还)";
                    }
                    return "还款失败";
                }
                if (!AppTools.isEmpty(s) && s.equals("72") || !AppTools.isEmpty(s) && s.equals("70")) {
                    return "消费失败";
                }
            }
        }
        return s;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
