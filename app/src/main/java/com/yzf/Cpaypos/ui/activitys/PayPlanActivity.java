package com.yzf.Cpaypos.ui.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.ActivityManager;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.CgiQuickPay;
import com.yzf.Cpaypos.model.servicesmodels.AddRepaymentResults;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.present.PPayPlan;
import com.yzf.Cpaypos.widget.StateButton;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
 * ClassName：PayPlanActivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/12/6 15:46
 * Modified By：
 * Fixtime：2017/12/6 15:46
 * FixDescription：
 */
public class PayPlanActivity extends XActivity<PPayPlan> {
    @BindView(R.id.payplan_bank_iv)
    ImageView payplanBankIv;
    @BindView(R.id.payplan_bankname_tv)
    TextView payplanBanknameTv;
    @BindView(R.id.payplan_bankNo_tv)
    TextView payplanBankNoTv;
    @BindView(R.id.payplan_banktype_tv)
    TextView payplanBanktypeTv;
    @BindView(R.id.payplan_amt_et)
    XEditText payplanAmtEt;
    @BindView(R.id.payplan_phone_et)
    XEditText payplanPhoneEt;
    @BindView(R.id.payplan_code_et)
    XEditText payplanCodeEt;
    @BindView(R.id.payplan_code_bt)
    StateButton payplanCodeBt;
    @BindView(R.id.pay_confirm_bt)
    StateButton payConfirmBt;
    @BindView(R.id.payplan_name_tv)
    TextView payplanNameTv;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AddRepaymentResults.DataBean dataBean = new AddRepaymentResults.DataBean();
    private GetWhiteCardListResult.DataBean cardBean = new GetWhiteCardListResult.DataBean();
    private String payType;

    @Override
    public void initData(Bundle savedInstanceState) {
        dataBean = (AddRepaymentResults.DataBean) getIntent().getSerializableExtra("dataBean");
        payType = getIntent().getStringExtra("payType");
        cardBean = AppUser.getInstance().getCardBean();
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_plan;
    }

    @Override
    public PPayPlan newP() {
        return new PPayPlan();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        int id = getImgId("b" + cardBean.getCardInst());
        if (id > 0) {
            RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), id2Bitmap(id));
            //设置为圆形
            circleDrawable.setCircular(true);
            payplanBankIv.setImageDrawable(circleDrawable);
        }
        payplanBanknameTv.setText(cardBean.getCardDesc());
        payplanBanktypeTv.setText(setCardType(cardBean.getCardType()));
        payplanBankNoTv.setText("尾号" + setAcctno(cardBean.getAcctNo()));
        payplanPhoneEt.setText(setPhoneNo(cardBean.getPhoneNo()));
        if (payType == null || payType != null && payType.equals("payPlan")) {
            double total;
            total = Double.parseDouble(dataBean.getFeeAmount()) + Double.parseDouble(dataBean.getDepositAmount());
            payplanAmtEt.setText("￥" + AppTools.formatAmt(String.valueOf(total)));
            payplanNameTv.setText("保留金");
        } else if (payType != null && payType.equals("payH5")) {
            payplanAmtEt.setText("￥" + AppTools.formatAmt(AppUser.getInstance().getAMT()));
            payplanNameTv.setText("交易金额");
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

    private String setAcctno(String acctNo) {
        if (!AppTools.isEmpty(acctNo)) {
            acctNo = acctNo.substring(acctNo.length() - 4, acctNo.length());
        }
        return acctNo;
    }

    private String setPhoneNo(String phoneNo) {
        if (!AppTools.isEmpty(phoneNo)) {
            phoneNo = phoneNo.substring(0, 4) + "****" + phoneNo.substring(phoneNo.length() - 4, phoneNo.length());
        }
        return phoneNo;
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("银联支付");
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
                .putSerializable("dataBean", dataBean)
                .launch();
        if (isfinish) {
            Router.pop(context);
        }
    }

    /**
     * activity跳转
     */
    public void JumpActivity(Class<?> activity, boolean isfinish, GetOrderListResult.DataBean dataBean) {
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



    @OnClick({R.id.payplan_code_bt, R.id.pay_confirm_bt, R.id.iv_back, R.id.iv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.payplan_code_bt:
                getvDelegate().showLoading();
                getP().getCode(cardBean.getPhoneNo());
                break;
            case R.id.pay_confirm_bt:
                if (payType == null || payType != null && payType.equals("payPlan")) {
                    getvDelegate().showLoading();
                    getP().PayPlanCard(AppUser.getInstance().getUserId(), dataBean.getCardId(), dataBean.getOrderId(), "confirm", payplanCodeEt.getNonSeparatorText());
                } else if (payType != null && payType.equals("payH5")) {
                    if (AppTools.isEmpty(payplanCodeEt.getNonSeparatorText())) {
                        showToast("请输入验证码");
                        return;
                    }
                    payplanAmtEt.setText("￥" + AppTools.formatAmt(AppUser.getInstance().getAMT()));
                    CgiQuickPay cgiQuickPay = new CgiQuickPay();
                    cgiQuickPay.setOrderAmt(AppUser.getInstance().getAMT());
                    cgiQuickPay.setCVN2(cardBean.getAdd1());
                    cgiQuickPay.setExpDate(cardBean.getAdd2());
                    cgiQuickPay.setPhoneNo(cardBean.getPhoneNo());
                    cgiQuickPay.setIdNo(cardBean.getIdCard());
                    cgiQuickPay.setName(cardBean.getAcctName());
                    cgiQuickPay.setCardNo(cardBean.getAcctNo());
                    cgiQuickPay.setCode(payplanCodeEt.getNonSeparatorText());

                    getvDelegate().showLoading();
                    getP().cgiQuickPay(cgiQuickPay);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_finish:
                ActivityManager.getInstance().finishAllActivityExceptOne(MainActivity.class);
                break;
        }
    }

    public void startTimer() {
        getvDelegate().dismissLoading();
        payplanCodeBt.setEnabled(false);
        mTime = new Timer();
        mTime.schedule(new TimerTask() {
            int time = 60000;

            @Override
            public void run() {
                time -= 1000;
                mHandler.sendMessage(mHandler.obtainMessage(1, time));
            }
        }, 1000, 1000);
    }

    /**
     * 定时器
     */
    private Timer mTime;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int m = (Integer) msg.obj;
            payplanCodeBt.setText(m / 1000 + "s重新获取");
            if (m / 1000 == 0) {
                payplanCodeBt.setEnabled(true);
                mTime.cancel();
                payplanCodeBt.setText("获取验证码");
            }
        }

    };
}
