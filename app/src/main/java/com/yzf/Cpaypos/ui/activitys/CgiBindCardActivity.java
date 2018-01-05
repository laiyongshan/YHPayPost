package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.present.PCgiBindCard;
import com.yzf.Cpaypos.widget.StateButton;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
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
 * ClassName：BindCardActivity
 * Description: 绑卡页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 14:29
 * Modified By：
 * Fixtime：2017/4/1 14:29
 * FixDescription：
 */
public class CgiBindCardActivity extends XActivity<PCgiBindCard> {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cgibindcard_name_tv)
    XEditText cgibindcardNameTv;
    @BindView(R.id.cgibindcard_idno_tv)
    XEditText cgibindcardIdnoTv;
    @BindView(R.id.cgibindcard_cardno_tv)
    XEditText cgibindcardCardnoTv;
    @BindView(R.id.cgibindcard_cvn2_tv)
    XEditText cgibindcardCvn2Tv;
    @BindView(R.id.cgibindcard_expdate_tv)
    XEditText cgibindcardExpdateTv;
    @BindView(R.id.cgibindcard_bankname_tv)
    XEditText cgibindcardBanknameTv;
    @BindView(R.id.cgibindcard_statementday_tv)
    XEditText cgibindcardStatementdayTv;
    @BindView(R.id.cgibindcard_repaymentday_tv)
    XEditText cgibindcardRepaymentdayTv;
    @BindView(R.id.cgibindcard_phoneno_tv)
    XEditText cgibindcardPhonenoTv;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.cgibindcard_confirm_bt)
    StateButton cgibindcardConfirmBt;
    @BindView(R.id.cgibindcard_code_tv)
    XEditText cgibindcardCodeTv;
    @BindView(R.id.cgibindcard_code_ll)
    LinearLayout cgibindcardCodeLl;
    String ORIG_ORDER_ID;
    @BindView(R.id.cgibindcard_getcode_bt)
    StateButton cgibindcardGetcodeBt;


    @Override
    public void initData(Bundle savedInstanceState) {
        useEventBus(true);
        initView();
        cgibindcardPhonenoTv.setPattern((new int[]{3, 5, 5}));
        cgibindcardPhonenoTv.setSeparator(" ");
        cgibindcardCardnoTv.setPattern((new int[]{4, 5, 5, 5, 5}));
        cgibindcardCardnoTv.setSeparator(" ");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cgi_bind_card;
    }

    @Override
    public PCgiBindCard newP() {
        return new PCgiBindCard();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
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
        title.setText("添加信用卡");
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

    public void finishActivity(String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void bindEvent() {
        BusProvider.getBus().toObservable(IEvent.class)
                .subscribe(new Action1<IEvent>() {
                    @Override
                    public void call(IEvent iEvent) {
                        //TODO 事件处理
                        if (iEvent.getId().equals("show_code")) {
                            ORIG_ORDER_ID = (String) iEvent.getObject();
                        }
                    }
                });
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


    @OnClick({R.id.cgibindcard_getcode_bt, R.id.cgibindcard_confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cgibindcard_getcode_bt:
                getvDelegate().showLoading();
                GetWhiteCardListResult.DataBean dataBean = new GetWhiteCardListResult.DataBean();
                dataBean.setAcctName(cgibindcardNameTv.getNonSeparatorText());
                dataBean.setIdCard(cgibindcardIdnoTv.getNonSeparatorText());
                dataBean.setAcctNo(cgibindcardCardnoTv.getNonSeparatorText());
                dataBean.setAdd1(cgibindcardCvn2Tv.getNonSeparatorText());
                dataBean.setAdd2(cgibindcardExpdateTv.getNonSeparatorText());
                dataBean.setPhoneNo(cgibindcardPhonenoTv.getNonSeparatorText());
                dataBean.setAdd3(cgibindcardStatementdayTv.getNonSeparatorText());
                dataBean.setAdd4(cgibindcardRepaymentdayTv.getNonSeparatorText());
                getP().BindCard(dataBean);
                break;
            case R.id.cgibindcard_confirm_bt:
                getvDelegate().showLoading();
                getP().BindCardConfirm("10.00", cgibindcardCardnoTv.getNonSeparatorText(), cgibindcardCodeTv.getNonSeparatorText(), ORIG_ORDER_ID);
                break;
        }
    }

    public void startTimer() {
        getvDelegate().dismissLoading();
        cgibindcardGetcodeBt.setEnabled(false);
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
            cgibindcardGetcodeBt.setText(m / 1000 + "s重新获取");
            if (m / 1000 == 0) {
                cgibindcardGetcodeBt.setEnabled(true);
                mTime.cancel();
                cgibindcardGetcodeBt.setText("验证码");
            }
        }

    };
}
