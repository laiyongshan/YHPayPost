package com.yzf.Cpaypos.ui.activitys;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;

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
 * ClassName：TransDetailActivity
 * Description: 订单详情页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:50
 * Modified By：
 * Fixtime：2017/5/18 11:50
 * FixDescription：
 */
public class TransDetailActivity extends XActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.transdetail_type_tv)
    TextView transdetailTypeTv;
    @BindView(R.id.transdetail_state_tv)
    TextView transdetailStateTv;
    @BindView(R.id.transdetail_fee_tv)
    TextView transdetailFeeTv;
    @BindView(R.id.transdetail_type1_tv)
    TextView transdetailType1Tv;
    @BindView(R.id.transdetail_time_tv)
    TextView transdetailTimeTv;
    @BindView(R.id.transdetail_merchid_tv)
    TextView transdetailMerchidTv;
    @BindView(R.id.transdetail_orderid_tv)
    TextView transdetailOrderidTv;
    @BindView(R.id.transdetail_amt_tv)
    TextView transdetailAmtTv;
    @BindView(R.id.transdetail_type_iv)
    ImageView transdetailTypeIv;
    private GetOrderListResult.DataBean dataBean;


    @Override
    public Object newP() {
        return null;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        dataBean = (GetOrderListResult.DataBean) getIntent().getSerializableExtra("dataBean");
        setOrderDetail();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trans_detail;
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
        title.setText("交易详情");
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

    private void setOrderDetail() {
        String merchantNo = dataBean.getMerchId();
        String orderId = dataBean.getOrderId();
        String tradeCard = dataBean.getAcctNo();
        String payType = dataBean.getServiceName();
        String orderState = dataBean.getResult();
        String fee = dataBean.getFee();
        String tradeTime = dataBean.getTransTime();
        String tradeMoney = dataBean.getAmt();
        // 设置
        // mMerchantNameTx.setText(merchantName);
        transdetailMerchidTv.setText(merchantNo);
        transdetailOrderidTv.setText(orderId);
        // mShopNameTx.setText(shopName);
        transdetailTypeTv.setText(payType);

//        OrderState.setTradeState(context, orderState, mOrderStateTx);
        if (AppTools.isEmpty(orderState)) {
            orderState = "已接收";
        }
        if (!orderState.contains("成功")) {
            transdetailStateTv.setTextColor(Color.RED);
        }
        transdetailStateTv.setText(orderState);
        transdetailFeeTv.setText(fee);
        transdetailTimeTv.setText(tradeTime);
        transdetailType1Tv.setText(payType);
        transdetailAmtTv.setText("￥" + tradeMoney);
        int id = getImgId("t" + dataBean.getServiceId());
        if (id <=0) {
            id = getImgId("t00" );
        }
        RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), id2Bitmap(id));
        //设置为圆形
        circleDrawable.setCircular(true);
        transdetailTypeIv.setImageDrawable(circleDrawable);
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

}
