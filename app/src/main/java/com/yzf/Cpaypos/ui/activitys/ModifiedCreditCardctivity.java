package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.present.PModifiedCreditCard;
import com.yzf.Cpaypos.widget.StateButton;
import com.xw.repo.xedittext.XEditText;

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
 * ClassName：ModifiedSettleCardctivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/11/7 11:03
 * Modified By：
 * Fixtime：2017/11/7 11:03
 * FixDescription：
 */
public class ModifiedCreditCardctivity extends XActivity<PModifiedCreditCard> {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.modified_creditcardno_tv)
    XEditText modifiedCreditcardnoTv;
    @BindView(R.id.modified_creditcardpayday_tv)
    XEditText modifiedCreditcardpaydayTv;
    @BindView(R.id.modified_creditcardrepayday_tv)
    XEditText modifiedCreditcardrepaydayTv;
    @BindView(R.id.modified_creditcardlimit_tv)
    XEditText modifiedCreditcardlimitTv;
    @BindView(R.id.modified_creditconfirm_bt)
    StateButton modifiedCreditconfirmBt;

    private GetWhiteCardListResult.DataBean dataBean = new GetWhiteCardListResult.DataBean();

    @Override
    public void initData(Bundle savedInstanceState) {
        dataBean = (GetWhiteCardListResult.DataBean) getIntent().getSerializableExtra("dataBean");
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modified_credit_cardctivity;
    }

    @Override
    public PModifiedCreditCard newP() {
        return new PModifiedCreditCard();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        if(dataBean!=null)
        {
            modifiedCreditcardnoTv.setText(dataBean.getAcctNo());
            modifiedCreditcardpaydayTv.setText(dataBean.getAdd3());
            modifiedCreditcardrepaydayTv.setText(dataBean.getAdd4());
            modifiedCreditcardlimitTv.setText(dataBean.getCardLimit());
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
        title.setText("信用卡修改");
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
     * 显示Toast
     *
     * @param msg
     */
    public void finish(String msg) {
        getvDelegate().toastShort(msg);
        finish();
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


    @OnClick(R.id.modified_creditconfirm_bt)
    public void onViewClicked() {
        getvDelegate().showLoading();
        getP().modifiedCreditCard(AppUser.getInstance().getUserId(),modifiedCreditcardnoTv.getNonSeparatorText(),modifiedCreditcardlimitTv.getNonSeparatorText(),
                modifiedCreditcardpaydayTv.getNonSeparatorText(),modifiedCreditcardrepaydayTv.getNonSeparatorText() );
    }
}
