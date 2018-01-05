package com.yzf.Cpaypos.ui.activitys;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.XAdapter;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.CgiQuickPay;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchInfoResult;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;
import com.yzf.Cpaypos.present.PCreditWithDraw;
import com.yzf.Cpaypos.widget.StateButton;
import com.yzf.Cpaypos.widget.WeChatPswKeyboard.VirtualKeyboardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.log.Logger;
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
 * ClassName：CreditWithDrawActivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/11/14 16:41
 * Modified By：
 * Fixtime：2017/11/14 16:41
 * FixDescription：
 */
public class CreditWithDrawActivity extends XActivity<PCreditWithDraw> {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.creditwithdraw_amt_et)
    XEditText creditwithdrawAmtEt;
    @BindView(R.id.creditwithdraw_bank_tv)
    TextView creditwithdrawBankTv;
    @BindView(R.id.creditwithdraw_replace_tv)
    TextView creditwithdrawReplaceTv;
    @BindView(R.id.creditwithdraw_confirm_bt)
    StateButton creditwithdrawConfirmBt;
    @BindView(R.id.virtualKeyboardView)
    VirtualKeyboardView virtualKeyboardView;
    @BindView(R.id.creditwithdraw_bank_iv)
    ImageView creditwithdrawBankIv;
    @BindView(R.id.creditwithdraw_settle_tv)
    TextView creditwithdrawSettleTv;
    @BindView(R.id.creditwithdraw_type_spinner)
    AppCompatSpinner creditwithdrawTypeSpinner;

    private GetWhiteCardListResult.DataBean dataBean = new GetWhiteCardListResult.DataBean();

    private Animation enterAnim;
    private Animation exitAnim;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
    final double limitamt = 500.00;

    private List<LoginResult.DataBean.ServiceListBean> typelist;
    private String selectType;

    @Override
    public void initData(Bundle savedInstanceState) {
        dataBean = (GetWhiteCardListResult.DataBean) getIntent().getSerializableExtra("dataBean");
        if (dataBean != null) {
            Gson gson = new Gson();
            AppUser.getInstance().setCardInfo(gson.toJson(dataBean));//将卡信息存到单例中
        }
        valueList = virtualKeyboardView.getValueList();
        initAnim();
        initView();
        GetMerchInfoResult.DataBean bean = AppUser.getInstance().getSettleBean();
        if (bean != null) {
            setSettleInfo(bean);
        } else {
            getP().getMerchInfo(AppUser.getInstance().getUserId());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_credit_with_draw;
    }

    @Override
    public PCreditWithDraw newP() {
        return new PCreditWithDraw();
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        if (dataBean != null) {
            String acctNo = dataBean.getAcctNo();
            if (acctNo != null && acctNo.length() > 4) {
                creditwithdrawBankTv.setText(dataBean.getCardDesc() + "  (" + acctNo.substring(acctNo.length() - 4, acctNo.length()) + ")");
            }

        }


        /*if (Build.VERSION.SDK_INT <= 10) {
            creditwithdrawAmtEt.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(creditwithdrawAmtEt, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);

        creditwithdrawAmtEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);
                virtualKeyboardView.startAnimation(enterAnim);
                virtualKeyboardView.setVisibility(View.VISIBLE);
            }
        });*/

        initTypeDatas();
        XAdapter scaledapter = new XAdapter(context, typelist);
        creditwithdrawTypeSpinner.setAdapter(scaledapter);
        creditwithdrawTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = typelist.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 初始化还款类型
     */
    private void initTypeDatas() {
        typelist = new ArrayList<>();
        LoginResult.DataBean.ServiceListBean bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("00");
        bean.setName("普通取款");
        typelist.add(bean);
        bean = new LoginResult.DataBean.ServiceListBean();
        bean.setId("01");
        bean.setName("vip取款");
        typelist.add(bean);

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
        title.setText("信用卡取款");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_limits;
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
                AgenWebViewActivity.launch(context, AppConfig.BASE_URL + "home/getBankLimit.do", "支持银行");
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.creditwithdraw_replace_tv, R.id.creditwithdraw_confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.creditwithdraw_replace_tv:
                finish();
                break;
            case R.id.creditwithdraw_confirm_bt:
                String amt = creditwithdrawAmtEt.getNonSeparatorText();
                if (AppTools.isEmpty(amt)) {
                    showToast("请输入金额");
                    return;
                }
                if (Double.parseDouble(amt) < limitamt) {
                    showToast("最小取款金额为:" + limitamt);
                    return;
                } else if (AppTools.getContinuousCount(amt) > 2) {
                    showToast("订单金额不能连续超过3位相同");
                    return;
                } else {
                    AppUser.getInstance().setAMT(amt);
                    if (selectType.equals("00")) {
                        getvDelegate().showLoading();
                        getP().gethtml(dataBean.getAcctNo());
                    }else if(selectType.equals("01"))
                    {
                        Router.newIntent(context)
                                .to(PayPlanActivity.class)
                                .putString("payType","payH5")
                                .launch();
                    }
                }
                break;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = creditwithdrawAmtEt.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                creditwithdrawAmtEt.setText(amount);

                Editable ea = creditwithdrawAmtEt.getText();
                creditwithdrawAmtEt.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount = creditwithdrawAmtEt.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        creditwithdrawAmtEt.setText(amount);

                        Editable ea = creditwithdrawAmtEt.getText();
                        creditwithdrawAmtEt.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = creditwithdrawAmtEt.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        creditwithdrawAmtEt.setText(amount);

                        Editable ea = creditwithdrawAmtEt.getText();
                        creditwithdrawAmtEt.setSelection(ea.length());
                    }
                }
            }
        }
    };

    public void toWebView(String result) {
        Logger.i("回调结果：" + result);
        getvDelegate().dismissLoading();
        AgenWebViewActivity.launch(context, result, "支持银行");

    }

    public void setSettleInfo(GetMerchInfoResult.DataBean dataBean) {
        String acctNo = dataBean.getAcctNo();
        if (acctNo != null && acctNo.length() > 4) {
            creditwithdrawSettleTv.setText(dataBean.getBankName() + "  (" + acctNo.substring(acctNo.length() - 4, acctNo.length()) + ")");
        }
    }
}
