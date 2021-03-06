package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchInfoResult;
import com.yzf.Cpaypos.present.PMerchInfo;
import com.yzf.Cpaypos.widget.MultipleStatusView;
import com.yzf.Cpaypos.widget.StateView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xstatecontroller.XStateController;

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
 * ClassName：MerchInfoActivity
 * Description: 商户信息界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/31 16:39
 * Modified By：
 * Fixtime：2017/3/31 16:39
 * FixDescription：
 */
public class MerchInfoActivity extends XActivity<PMerchInfo> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.merchinfo_name_tv)
    TextView merchinfoNameTv;
    @BindView(R.id.merchinfo_status_tv)
    TextView merchinfoStatusTv;
    @BindView(R.id.merchinfo_idcard_tv)
    TextView merchinfoIdcardTv;
    @BindView(R.id.merchinfo_phone_tv)
    TextView merchinfoPhoneTv;
    @BindView(R.id.merchinfo_acctno_tv)
    TextView merchinfoAcctnoTv;
    @BindView(R.id.merchinfo_acctbank_tv)
    TextView merchinfoAcctbankTv;
    @BindView(R.id.merchinfo_acctname_tv)
    TextView merchinfoAcctnameTv;
    @BindView(R.id.multiplestatusview)
    MultipleStatusView multipleStatusView;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        if (savedInstanceState == null) {
            multipleStatusView.showLoading();
        }
        getP().getMerchInfo(AppUser.getInstance().getUserId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_merch_info;
    }

    @Override
    public PMerchInfo newP() {
        return new PMerchInfo();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multipleStatusView.showLoading();
                getP().getMerchInfo(AppUser.getInstance().getUserId());getP().getMerchInfo(AppUser.getInstance().getUserId());
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
        title.setText("商户结算信息");
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

    public void showErrorView(String msg) {
        multipleStatusView.showError(msg);
    }

    public void showErrorView(NetError error) {
        multipleStatusView.showError(getvDelegate().getErrorType(error));
    }

    public void showEmptyView(String msg) {
        multipleStatusView.showEmpty(msg);
    }

    public void setMerchInfo(GetMerchInfoResult.DataBean dataBean) {
//        getvDelegate().dismissLoading();
        multipleStatusView.showContent();
        merchinfoNameTv.setText(dataBean.getMerchName());
        merchinfoStatusTv.setText(dataBean.getMerchStatus());
        String idcard = dataBean.getIdCard();
        idcard = AppTools.isEmpty(idcard) ? "" : idcard.substring(0, 4) + "****" + idcard.substring(idcard.length() - 4, idcard.length());
        merchinfoIdcardTv.setText(idcard);
        String phone = dataBean.getPhoneNo();
        phone = AppTools.isEmpty(phone) ? "" : phone.substring(0, 4) + "****" + phone.substring(phone.length() - 4, phone.length());
        merchinfoPhoneTv.setText(phone);
        String acctno = dataBean.getAcctNo();
        acctno = AppTools.isEmpty(acctno) ? "" : acctno.substring(0, 4) + "****" + acctno.substring(acctno.length() - 4, acctno.length());
        merchinfoAcctnoTv.setText(acctno);
        merchinfoAcctbankTv.setText(dataBean.getBankName());
        String AcctName = dataBean.getAcctName();
        AcctName = AppTools.isEmpty(AcctName) ? "" : "*" + AcctName.substring(1, AcctName.length());
        merchinfoAcctnameTv.setText(AcctName);
    }
}
