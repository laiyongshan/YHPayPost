package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.RepaymentAdapter;
import com.yzf.Cpaypos.adapter.XAdapter;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.servicesmodels.AddRepaymentResults;
import com.yzf.Cpaypos.model.servicesmodels.GetRepaymentResults;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;
import com.yzf.Cpaypos.present.PRepayment;
import com.yzf.Cpaypos.widget.DateDialogPopup;
import com.yzf.Cpaypos.widget.StateButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import ren.qinc.numberbutton.NumberButton;

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
 * ClassName：RepaymentActivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/2 17:08
 * Modified By：
 * Fixtime：2017/9/2 17:08
 * FixDescription：
 */
public class RepaymentActivity extends XActivity<PRepayment> implements DateDialogPopup.IPopupCallBack {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.repayment_type_spinner)
    AppCompatSpinner repaymentTypeSpinner;
    @BindView(R.id.repayment_scale_spinner)
    AppCompatSpinner repaymentScaleSpinner;
    @BindView(R.id.repayment_date_tv)
    XEditText repaymentDateTv;
    @BindView(R.id.repayment_date_rl)
    RelativeLayout repaymentDateRl;
    @BindView(R.id.repayment_type_rl)
    RelativeLayout repaymentTypeRl;
    @BindView(R.id.repayment_scale_rl)
    RelativeLayout repaymentScaleRl;
    @BindView(R.id.repayment_balance_et)
    XEditText repaymentBalanceEt;
    @BindView(R.id.repayment_balance_rl)
    RelativeLayout repaymentBalanceRl;
    @BindView(R.id.repayment_amount_et)
    XEditText repaymentAmountEt;
    @BindView(R.id.repayment_amount_rl)
    RelativeLayout repaymentAmountRl;
    @BindView(R.id.repayment_count_bt)
    NumberButton repaymentCountBt;
    @BindView(R.id.repayment_count_rl)
    RelativeLayout repaymentCountRl;
    @BindView(R.id.repayment_times_bt)
    NumberButton repaymentTimesBt;
    @BindView(R.id.repayment_times_rl)
    RelativeLayout repaymentTimesRl;
    @BindView(R.id.repayment_confirm_bt)
    StateButton repaymentConfirmBt;
    @BindView(R.id.repayment_recyclerView)
    RecyclerView recyclerView;
    private RepaymentAdapter adapter;
    private String detail;
    private GetWhiteCardListResult.DataBean dataBean = new GetWhiteCardListResult.DataBean();
    private StringBuffer sb;
    String vul[];
    List<String> mDatas = new ArrayList<>();
    DateDialogPopup popup;
    private List<LoginResult.DataBean.ServiceListBean> typelist;
    private List<LoginResult.DataBean.ServiceListBean> scalelist;

    private int dateSize;
    private String selectType;
    private String scaleType;

    @Override
    public void initData(Bundle savedInstanceState) {
        dataBean = (GetWhiteCardListResult.DataBean) getIntent().getSerializableExtra("dataBean");
        initView();
        initAdapter();
        sb = new StringBuffer();
        sb.delete(0, sb.length());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_repayment;
    }

    @Override
    public PRepayment newP() {
        return new PRepayment();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        repaymentCountBt.setCurrentNumber(1).setBuyMax(1).setOnWarnListener(new NumberButton.OnWarnListener() {
            @Override
            public void onWarningForInventory(int inventory) {
            }

            @Override
            public void onWarningForBuyMax(int buyMax) {

                if (buyMax == 1) {
                    showToast("请选择还款日期");
                } else {
                    showToast("还款笔数目前不能超过" + buyMax + "笔，最高每日3笔");
                }
            }
        });
        repaymentTimesBt.setCurrentNumber(1).setBuyMax(6).setOnWarnListener(new NumberButton.OnWarnListener() {
            @Override
            public void onWarningForInventory(int inventory) {
            }

            @Override
            public void onWarningForBuyMax(int buyMax) {
                showToast("还款月数不能超过" + buyMax + "笔");
            }
        });

        initTypeDatas();
        repaymentTypeSpinner.setPrompt("            请选择还款类型");
        XAdapter typedapter = new XAdapter(context, typelist);
        repaymentTypeSpinner.setAdapter(typedapter);
        repaymentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = typelist.get(position).getId();
                if (selectType.equals("00")) {
                    detail = null;
                    adapter.clearData();
                    repaymentScaleRl.setVisibility(View.GONE);
                    repaymentBalanceRl.setVisibility(View.GONE);
                    repaymentDateRl.setVisibility(View.VISIBLE);
                    repaymentAmountRl.setVisibility(View.VISIBLE);
                    repaymentCountRl.setVisibility(View.VISIBLE);
                    repaymentTimesRl.setVisibility(View.VISIBLE);
                } else if (selectType.equals("01")) {
                    detail = null;
                    adapter.clearData();
                    repaymentScaleRl.setVisibility(View.VISIBLE);
                    repaymentBalanceRl.setVisibility(View.GONE);
                    repaymentDateRl.setVisibility(View.GONE);
                    repaymentAmountRl.setVisibility(View.VISIBLE);
                    repaymentCountRl.setVisibility(View.GONE);
                    repaymentTimesRl.setVisibility(View.GONE);
                } else if (selectType.equals("02")) {
                    detail = null;
                    adapter.clearData();
                    repaymentScaleRl.setVisibility(View.GONE);
                    repaymentBalanceRl.setVisibility(View.VISIBLE);
                    repaymentDateRl.setVisibility(View.GONE);
                    repaymentAmountRl.setVisibility(View.VISIBLE);
                    repaymentCountRl.setVisibility(View.GONE);
                    repaymentTimesRl.setVisibility(View.GONE);
                } else {
                    {
                        detail = null;
                        adapter.clearData();
                        repaymentScaleRl.setVisibility(View.GONE);
                        repaymentBalanceRl.setVisibility(View.GONE);
                        repaymentDateRl.setVisibility(View.VISIBLE);
                        repaymentAmountRl.setVisibility(View.VISIBLE);
                        repaymentCountRl.setVisibility(View.VISIBLE);
                        repaymentTimesRl.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initScaleDatas();
        repaymentScaleSpinner.setPrompt("            请选择还款比例");
        XAdapter scaledapter = new XAdapter(context, scalelist);
        repaymentScaleSpinner.setAdapter(scaledapter);
        repaymentScaleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scaleType = scalelist.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DateDialogPopup.setmPopupCallBack(this);
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
        title.setText("新增还款计划");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new RepaymentAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GetRepaymentResults.DataBean.RowsBean, RepaymentAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GetRepaymentResults.DataBean.RowsBean item, int tag, RepaymentAdapter.ViewHolder holder) {
                    super.onItemClick(position, item, tag, holder);
                    switch (tag) {
                    }
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_confirm;
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
            case R.id.menu_confirm:
                if (AppTools.isEmpty(detail)) {
                    showToast("请生成规划后再提交");
                } else {
                    int count = repaymentCountBt.getNumber();
                    if (count <= dateSize * 2) {
                        showNoticeDialog("确定提交还款计划吗？", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (which == DialogAction.POSITIVE) {
                                    getvDelegate().showLoading();
                                    getP().CreatePlanCard(repaymentAmountEt.getNonSeparatorText(),
                                            String.valueOf(repaymentCountBt.getNumber()), AppUser.getInstance().getUserId(),
                                            dataBean.getAcctNo(), detail, dateSize);
                                }
                            }
                        });
                    } else {
                        getvDelegate().showLoading();
                        getP().CreatePlanCard(repaymentAmountEt.getNonSeparatorText(),
                                String.valueOf(repaymentCountBt.getNumber()), AppUser.getInstance().getUserId(),
                                dataBean.getAcctNo(), detail, dateSize);
                    }
                }
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
        adapter.clearData();
        detail = null;
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

    /**
     * 显示错误信息
     *
     * @param error
     */
    public void showError(NetError error) {
        getvDelegate().showError(error);
    }


    public void setAdapter(GetRepaymentResults getRepaymentResults) {
        getvDelegate().dismissLoading();
        detail = getRepaymentResults.getData();
        adapter.setData(getRepaymentResults.getDataBean().getRows());

    }


    @OnClick({R.id.repayment_date_tv, R.id.repayment_date_rl, R.id.repayment_confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.repayment_date_rl:
                int size = 15;
                try {
                    String repayday = Kits.Date.getYmd().replace("-", "").substring(0, 6) + dataBean.getAdd4();
                    if (getP().getBetweenDays(repayday) < 0)//当日日期超过还款日
                    {
                        repayday = getP().getNextMonth(repayday);
                        size = (int) getP().getBetweenDays(repayday);
                        repayday = repayday + "," + Kits.Date.backDate(repayday, -1) + "," + Kits.Date.backDate(repayday, -2);
                        for (int i = 0; i < size; i++) {
                            String time = Kits.Date.backDate(i).replace("-", "");
                            if (dataBean != null && repayday.indexOf(time) != -1) {

                            } else {
                                mDatas.add(time);
                            }
                        }
                    } else if (getP().getBetweenDays(repayday) <= 2 && getP().getBetweenDays(repayday) >= 0)//当日日期小于还款日2日
                    {
                        repayday = getP().getNextMonth(repayday);
                        size = (int) getP().getBetweenDays(repayday);
                        repayday = repayday + "," + Kits.Date.backDate(repayday, -1) + "," + Kits.Date.backDate(repayday, -2);
                        for (int i = 3; i < size; i++) {
                            String time = Kits.Date.backDate(i).replace("-", "");
                            if (dataBean != null && repayday.indexOf(time) != -1) {

                            } else {
                                mDatas.add(time);
                            }
                        }
                    } else {//当日日期小于还款日超过2日
                        size = (int) getP().getBetweenDays(repayday);
                        repayday = repayday + "," + Kits.Date.backDate(repayday, -1) + "," + Kits.Date.backDate(repayday, -2);
                        for (int i = 0; i < size; i++) {
                            String time = Kits.Date.backDate(i).replace("-", "");
                            if (dataBean != null && repayday.indexOf(time) != -1) {

                            } else {
                                mDatas.add(time);
                            }
                        }
                    }
                } catch (Exception e) {
                    Logger.e(e.toString());
                    for (int i = 0; i < size; i++) {
                        String time = Kits.Date.backDate(i).replace("-", "");
                        mDatas.add(time);
                    }
                    e.printStackTrace();
                }
                popup = new DateDialogPopup(context, mDatas);
                popup.showPopupWindow();

                break;
            case R.id.repayment_date_tv:
                int size1 = 15;
                try {
                    String repayday = Kits.Date.getYmd().replace("-", "").substring(0, 6) + dataBean.getAdd4();
                    if (getP().getBetweenDays(repayday) < 0)//当日日期超过还款日
                    {
                        repayday = getP().getNextMonth(repayday);
                        size1 = (int) getP().getBetweenDays(repayday);
                        repayday = repayday + "," + Kits.Date.backDate(repayday, -1) + "," + Kits.Date.backDate(repayday, -2);
                        for (int i = 0; i < size1; i++) {
                            String time = Kits.Date.backDate(i).replace("-", "");
                            if (dataBean != null && repayday.indexOf(time) != -1) {

                            } else {
                                mDatas.add(time);
                            }
                        }
                    } else if (getP().getBetweenDays(repayday) <= 2 && getP().getBetweenDays(repayday) >= 0)//当日日期小于还款日2日
                    {
//                        int num= (int) getP().getBetweenDays(repayday);
                        String day = repayday;
                        repayday = getP().getNextMonth(repayday);
                        size1 = (int) getP().getBetweenDays(repayday);
                        repayday = repayday + "," + Kits.Date.backDate(repayday, -1) + "," + Kits.Date.backDate(repayday, -2);
                        for (int i = 1; i < size1; i++) {
                            String time = Kits.Date.backDate(day, i).replace("-", "");
                            if (dataBean != null && repayday.indexOf(time) != -1) {

                            } else {
                                mDatas.add(time);
                            }
                        }
                    } else {//当日日期小于还款日超过2日
                        size = (int) getP().getBetweenDays(repayday);
                        repayday = repayday + "," + Kits.Date.backDate(repayday, -1) + "," + Kits.Date.backDate(repayday, -2);
                        for (int i = 0; i < size; i++) {
                            String time = Kits.Date.backDate(i).replace("-", "");
                            if (dataBean != null && repayday.indexOf(time) != -1) {

                            } else {
                                mDatas.add(time);
                            }
                        }
                    }

                } catch (Exception e) {
                    for (int i = 0; i < size1; i++) {
                        String time = Kits.Date.backDate(i).replace("-", "");
                        mDatas.add(time);
                    }
                    e.printStackTrace();
                }
                popup = new DateDialogPopup(context, mDatas);
                popup.showPopupWindow();
                break;
            case R.id.repayment_confirm_bt:
                getvDelegate().showLoading();
                getP().RandomPlanCard(repaymentAmountEt.getNonSeparatorText(), String.valueOf(repaymentCountBt.getNumber()),
                        repaymentDateTv.getNonSeparatorText(), String.valueOf(repaymentTimesBt.getNumber()), dateSize, selectType, scaleType, repaymentBalanceEt.getNonSeparatorText());
                /*if (selectType.equals("00")) {
                    getvDelegate().showLoading();
                    getP().RandomPlanCard(repaymentAmountEt.getNonSeparatorText(), String.valueOf(repaymentCountBt.getNumber()),
                            repaymentDateTv.getNonSeparatorText(), String.valueOf(repaymentTimesBt.getNumber()), dateSize,selectType,scaleType,repaymentBalanceEt.getNonSeparatorText());
                }
                if (selectType.equals("01")) {
                    getvDelegate().showLoading();
                    getP().RandomPlanCard(repaymentAmountEt.getNonSeparatorText(), String.valueOf(repaymentCountBt.getNumber()),
                            repaymentDateTv.getNonSeparatorText(), String.valueOf(repaymentTimesBt.getNumber()), dateSize,selectType,scaleType,repaymentBalanceEt.getNonSeparatorText());
                }
                if (selectType.equals("02")) {
                    getvDelegate().showLoading();
                    getP().RandomPlanCard(repaymentAmountEt.getNonSeparatorText(), String.valueOf(repaymentCountBt.getNumber()),
                            repaymentDateTv.getNonSeparatorText(), String.valueOf(repaymentTimesBt.getNumber()), dateSize,selectType,scaleType,repaymentBalanceEt.getNonSeparatorText());
                }*/
                break;
        }
    }

    /**
     * 初始化还款类型
     */
    private void initTypeDatas() {
        typelist = new ArrayList<>();
        LoginResult.DataBean.ServiceListBean bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("00");
        bean.setName("自定义还款");
        typelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("01");
        bean.setName("按比例还款");
        typelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("02");
        bean.setName("按余额还款");
        typelist.add(bean);

    }

    /**
     * 初始化还款类型
     */
    private void initScaleDatas() {
        scalelist = new ArrayList<>();
        LoginResult.DataBean.ServiceListBean bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("0");
        bean.setName("5%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("1");
        bean.setName("10%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("2");
        bean.setName("15%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("3");
        bean.setName("20%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("4");
        bean.setName("25%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("5");
        bean.setName("30%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("6");
        bean.setName("35%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("7");
        bean.setName("40%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("8");
        bean.setName("45%");
        scalelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("9");
        bean.setName("50%");
        scalelist.add(bean);

    }

    /**
     * 自定义数字键盘转换
     *
     * @param i
     */
    private void appendStr(String i) {
        String bt = i;
        String str = i;
        if (sb.length() <= 0) {
            sb.append(str);
        } else {
            sb.append("|" + str);
        }
        repaymentDateTv.setText(sb);
    }

    @Override
    public void sent(List<String> list) {
        if (list.size() > 0) {
            dateSize = list.size();
            repaymentCountBt.setBuyMax(dateSize * 3);
            if (repaymentCountBt.getNumber() > dateSize * 3) {
                repaymentCountBt.setCurrentNumber(dateSize * 3);
            }
        }
        sb.delete(0, sb.length());
        for (String s : list) {
            appendStr(s);
        }

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
