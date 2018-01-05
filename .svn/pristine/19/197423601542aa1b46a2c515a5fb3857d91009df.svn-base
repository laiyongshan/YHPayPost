package com.yzf.Cpaypos.ui.activitys;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.TransDialogAdapter;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.CgiQuickPay;
import com.yzf.Cpaypos.model.TransSource;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.present.PInputMoney;
import com.yzf.Cpaypos.widget.ListViewDialog;
import com.yzf.Cpaypos.widget.QRcodeDialog;
import com.yzf.Cpaypos.widget.QuickPayDialogPopup;
import com.yzf.Cpaypos.widget.StateButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
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
 * ClassName：InputMoneyActivity
 * Description: 我要收款界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/23 15:37
 * Modified By：
 * Fixtime：2017/3/23 15:37
 * FixDescription：
 */

public class InputMoneyActivity extends XActivity<PInputMoney> implements QuickPayDialogPopup.IPopupCallBack, ListViewDialog.ICardNoCallBack {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.input_number_et)
    EditText inputNumberEt;
    @BindView(R.id.id_keypad_1)
    StateButton idKeypad1;
    @BindView(R.id.id_keypad_4)
    StateButton idKeypad4;
    @BindView(R.id.id_keypad_7)
    StateButton idKeypad7;
    @BindView(R.id.id_keypad_point)
    StateButton idKeypadPoint;
    @BindView(R.id.id_keypad_2)
    StateButton idKeypad2;
    @BindView(R.id.id_keypad_5)
    StateButton idKeypad5;
    @BindView(R.id.id_keypad_8)
    StateButton idKeypad8;
    @BindView(R.id.id_keypad_0)
    StateButton idKeypad0;
    @BindView(R.id.id_keypad_3)
    StateButton idKeypad3;
    @BindView(R.id.id_keypad_6)
    StateButton idKeypad6;
    @BindView(R.id.id_keypad_9)
    StateButton idKeypad9;
    @BindView(R.id.id_keypad_del)
    ImageView idKeypadDel;
    @BindView(R.id.id_keypad_wx)
    StateButton idKeypadWx;
    @BindView(R.id.id_keypad_zfb)
    StateButton idKeypadZfb;
    @BindView(R.id.id_keypad_yl)
    StateButton idKeypadYl;

    private StringBuffer sb;
    private boolean isPoint = false;
    private boolean isTwoNum = false;
    private int REQUEST_CODE = 100;
    private String serviceId;
    private QRcodeDialog qRcodeDialog;
    private Timer timer;
    private Handler handlerdelay = new Handler();
    private MaterialDialog dialog;
    private final Double minAmt = 10.00;
    private QuickPayDialogPopup popup;
    private ListViewDialog listViewDialog;

    @Override
    public void initData(Bundle savedInstanceState) {
        serviceId = getIntent().getStringExtra("serviceId");
        initView();
        sb = new StringBuffer();
        sb.delete(0, sb.length());
    }

    @Override
    public void onResume() {
        super.onResume();
        QuickPayDialogPopup.setPopupCallBack(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_input_money;
    }

    @Override
    public PInputMoney newP() {
        return new PInputMoney();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        if (serviceId!=null&&serviceId.equals(AppConfig.KJZFH5)) {
            idKeypadWx.setVisibility(View.GONE);
            idKeypadZfb.setVisibility(View.GONE);
            title.setText("信用卡取款");
        }else if (serviceId!=null&&serviceId.equals(AppConfig.APIKJ))
        {
            title.setText("商户收款");
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
        title.setText("商户收款");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

/*    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_limits;
    }*/

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
            /*case R.id.menu_text:
                WebActivity.launch(context, AppConfig.BASE_URL + "home/getBankLimit.do", "限额说明");
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * activity跳转
     */
    public void JumpActivity(Class<?> activity, boolean isfinish, GetOrderListResult.DataBean dataBean) {
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
    public void JumpActivity(Class<?> activity, boolean isfinish, String serviceId) {
        getvDelegate().dismissLoading();
        Router.newIntent(context)
                .to(activity)
                .putString("serviceId", serviceId)
                .launch();
        if (isfinish) {
            Router.pop(context);
        }
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

    @OnClick({R.id.id_keypad_1, R.id.id_keypad_4, R.id.id_keypad_7, R.id.id_keypad_point, R.id.id_keypad_2, R.id.id_keypad_5, R.id.id_keypad_8, R.id.id_keypad_0, R.id.id_keypad_3, R.id.id_keypad_6, R.id.id_keypad_9, R.id.id_keypad_del, R.id.id_keypad_wx, R.id.id_keypad_zfb, R.id.id_keypad_yl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_keypad_1:
                changeKey("1");
                break;
            case R.id.id_keypad_4:
                changeKey("4");
                break;
            case R.id.id_keypad_7:
                changeKey("7");
                break;
            case R.id.id_keypad_point:
                String s = sb.toString();
                if (s.contains(".")) {
                } else {
                    changeKey(".");
                }
                break;
            case R.id.id_keypad_2:
                changeKey("2");
                break;
            case R.id.id_keypad_5:
                changeKey("5");
                break;
            case R.id.id_keypad_8:
                changeKey("8");
                break;
            case R.id.id_keypad_0:
                changeKey("0");
                break;
            case R.id.id_keypad_3:
                changeKey("3");
                break;
            case R.id.id_keypad_6:
                changeKey("6");
                break;
            case R.id.id_keypad_9:
                changeKey("9");
                break;
            case R.id.id_keypad_del:
                if (0 != sb.length()) {
                    inputNumberEt.setText(sb.deleteCharAt(sb.length() - 1));
                    if (sb.toString().contains(".")) {
                        isTwoNum = false;
                    } else {
                        isPoint = false;
                    }
                } else {
                    isPoint = false;
                    isTwoNum = false;
                    return;
                }
                // 清除Edit中的内容
                if (null == inputNumberEt.getText()) {
                    sb.delete(0, sb.length());
                    sb = new StringBuffer();
                }
                break;
            case R.id.id_keypad_wx:
                if (!AppTools.isEmpty(inputNumberEt.getText().toString())) {
                    List<TransSource> list = new ArrayList<>();
                    TransSource transSource = new TransSource();
                    transSource.setId(AppConfig.WXZS);
                    transSource.setDesc("二维码收款");
                    transSource.setImgRes(R.mipmap.icon_qrcode);
                    TransSource transSource1 = new TransSource();
                    transSource1.setId(AppConfig.WXFS);
                    transSource1.setDesc("扫一扫收款");
                    transSource1.setImgRes(R.mipmap.icon_scan);
                    list.add(transSource);
                    list.add(transSource1);
                    showDialog(list);
                } else {
                    showToast("请输入金额");
                }
                break;
            case R.id.id_keypad_zfb:
                if (!AppTools.isEmpty(inputNumberEt.getText().toString())) {
                    List<TransSource> list = new ArrayList<>();
                    TransSource transSource = new TransSource();
                    transSource.setId(AppConfig.ZFBZS);
                    transSource.setDesc("二维码收款");
                    transSource.setImgRes(R.mipmap.icon_qrcode);
                    TransSource transSource1 = new TransSource();
                    transSource1.setId(AppConfig.ZFBFS);
                    transSource1.setDesc("扫一扫收款");
                    transSource1.setImgRes(R.mipmap.icon_scan);
                    list.add(transSource);
                    list.add(transSource1);
                    showDialog(list);
                } else {
                    showToast("请输入金额");
                }
                break;
            case R.id.id_keypad_yl:
                if (!AppTools.isEmpty(inputNumberEt.getText().toString())) {
                    /*List<TransSource> list = new ArrayList<>();
                    TransSource transSource = new TransSource();
                    transSource.setId(AppConfig.KJZFH5);
                    transSource.setDesc("Api快捷");
                    transSource.setImgRes(R.mipmap.icon_jnkj);
                    TransSource transSource1 = new TransSource();
                    transSource1.setId(AppConfig.SSKKXG);
                    transSource1.setDesc("H5快捷");
                    transSource1.setImgRes(R.mipmap.icon_jwkj);
                    list.add(transSource);
                    list.add(transSource1);
                    showDialog(list);*/
                    if (serviceId.equals(AppConfig.APIKJ)) {
                       /* getvDelegate().showLoading();
                        getP().getWhiteCardList(AppUser.getInstance().getUserId());*/
                        showCardDialog(AppUser.getInstance().getcList());
                    }else if (serviceId.equals(AppConfig.KJZFH5)){
                        getP().route(serviceId, inputNumberEt.getText().toString());
                    }
                } else {
                    showToast("请输入金额");
                }
                break;
        }
    }

    /**
     * 选择交易类型对话框
     *
     * @param list
     */
    private void showDialog(List<TransSource> list) {
        String amt = inputNumberEt.getText().toString();
        Double tempamt = Double.valueOf(amt);
        if (tempamt < minAmt) {
            showToast("最小交易金额为" + minAmt + "元");
            return;
        }
        final TransDialogAdapter adapter = new TransDialogAdapter(this);
        adapter.setData(list);
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .adapter(adapter, null)
                .build();
        adapter.setRecItemClick(new RecyclerItemCallback<TransSource, TransDialogAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, TransSource item, int tag, TransDialogAdapter.ViewHolder holder) {
                super.onItemClick(position, item, tag, holder);
                switch (tag) {
                    case TransDialogAdapter.TAG_VIEW:
                        dialog.dismiss();
                        toPay(item);
                        break;
                    default:
                        break;
                }
            }
        });
        RecyclerView recyclerView = dialog.getRecyclerView();
        recyclerView.setLayoutManager(new GridLayoutManager(context, list.size()));
        dialog.show();
    }

    /**
     * 调用摄像头
     */
    private void toCaptureActivity() {
        getRxPermissions()
                .request(Manifest.permission.CAMERA)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            //TODO 许可
                            Router.newIntent(context)
                                    .to(CaptureActivity.class)
                                    .requestCode(REQUEST_CODE)
                                    .launch();

                        } else {
                            //TODO 未许可
                            showToast("权限未获取");
                        }
                    }
                });
    }


    /**
     * 回调结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    getvDelegate().showLoading();
                    getP().reverseScan(serviceId, inputNumberEt.getText().toString(), result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    showToast("解析二维码失败");
                }
            }
        }
    }

    /**
     * 自定义数字键盘转换
     *
     * @param i
     */
    private void changeKey(String i) {
        String bt = i;
        if (sb.length() < 10) {
            if (isPoint) {// 已经有小数点
                if (isTwoNum) {// 小数点后已经有两位

                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(bt);

                } else {
                    sb.append(bt);
                    String sPoint = "\\.";
                    String[] content = sb.toString().split(sPoint);
                    if (content[1].length() == 2) {
                        isTwoNum = true;
                    }
                }
            } else {
                sb.append(bt);
                if (sb.substring(0, 1).equals("."))// 如果第一次输入为点则不让输入
                {
                    sb.deleteCharAt(sb.length() - 1);
                    inputNumberEt.setText(sb);
                    return;
                }
                int length = sb.length();
                if (length == 2 && sb.substring(0, 1).equals("0")) {
                    if (sb.substring(1, 2).equals("0"))// 如果第一次输入为0则第二次不让输入0，只能输入点或是其他字母
                    {
                        sb.deleteCharAt(sb.length() - 1);// 第二位为0则剪切掉
                    } else if (sb.substring(1, 2).equals(".")) {// 为点的时候正常加入

                    } else// 为 1,2,3,4,5,6..时就把最开始的0改成第二次按的数字
                    {
                        sb.deleteCharAt(sb.length() - 2);// 剪掉第一位0
                    }
                }

                if (sb.toString().contains(".")) {// 如果
                    isPoint = true;
                }
            }
        }
        if (sb.length() == 10 && isTwoNum) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(bt);
        }
        if (sb.length() == 10) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(bt);
        }
        inputNumberEt.setText(sb);
    }

    /**
     * 跳到QrCodeActivity
     *
     * @param content  二维码内容
     * @param orderAmt 交易金额
     */
    public void showQrCodeActivity(String content, String orderAmt) {
        getvDelegate().dismissLoading();
        QrCodeActivity.launch(context, serviceId, orderAmt, "二维码收款", content);
    }

    /**
     * 按交易类型去支付
     *
     * @param transSource
     */
    private void toPay(TransSource transSource) {
        serviceId = transSource.getId();
        String amt = inputNumberEt.getText().toString();
        if (serviceId.equals(AppConfig.WXZS) || serviceId.equals(AppConfig.ZFBZS))//正扫
        {
            getvDelegate().showLoading();
            getP().positiveScan(serviceId, amt);
        }
        if (serviceId.equals(AppConfig.WXFS) || serviceId.equals(AppConfig.ZFBFS))//反扫
        {
            toCaptureActivity();
        }
        if (serviceId.equals(AppConfig.SSKKXG))//银联境外快捷
        {
            getP().route(transSource.getId(), inputNumberEt.getText().toString());
        }
        if (serviceId.equals(AppConfig.KJZFH5))//银联境内快捷
        {
            /*getvDelegate().showLoading();
            getP().getWhiteCardList(AppUser.getInstance().getUserId());*/
            showCardDialog(AppUser.getInstance().getcList());
        }
    }

    @Override
    public void OnOk(CgiQuickPay cgiQuickPay) {
        if (AppTools.isEmpty(cgiQuickPay.getCardNo())) {
            showToast("银行卡号为空");
            return;
        }
        if (AppTools.isEmpty(cgiQuickPay.getCVN2())) {
            showToast("CVN2为空");
            return;
        }
        if (AppTools.isEmpty(cgiQuickPay.getExpDate())) {
            showToast("有效期为空");
            return;
        }
        if (AppTools.isEmpty(cgiQuickPay.getPhoneNo())) {
            showToast("手机号为空");
            return;
        }
        if (AppTools.isEmpty(cgiQuickPay.getIdNo())) {
            showToast("身份证号为空");
            return;
        }
        if (AppTools.isEmpty(cgiQuickPay.getName())) {
            showToast("姓名为空");
            return;
        }
        if (popup != null) {
            popup.dismiss();
        }
        getvDelegate().showLoading();
        cgiQuickPay.setOrderAmt(inputNumberEt.getText().toString());
        getP().cgiQuickPay(cgiQuickPay);
    }

    /**
     * 白名单卡对话框
     *
     * @param list
     */
    public void showCardDialog(List<GetWhiteCardListResult.DataBean> list) {
        getvDelegate().dismissLoading();
        ListViewDialog.setmCardNOCallBack(this);
        listViewDialog = new ListViewDialog(context, list);
        listViewDialog.setTitle("选择卡号");
        listViewDialog.setCanceledOnTouchOutside(false);
        listViewDialog.show();
    }

    @Override
    public void OnCancle() {
        if (popup != null) {
            popup.dismiss();
        }
    }

    @Override
    public void getCardno(String cardNo) {

    }

    @Override
    public void getCardBean(GetWhiteCardListResult.DataBean dataBean) {
        if (listViewDialog != null) {
            listViewDialog.dismiss();
        }
        CgiQuickPay cgiQuickPay = new CgiQuickPay();
        cgiQuickPay.setOrderAmt(inputNumberEt.getText().toString());
        if (AppTools.isEmpty(dataBean.getAdd1())||AppTools.isEmpty(dataBean.getAdd2()))
        {
            popup=new QuickPayDialogPopup(context);
            popup.setAdjustInputMethod(true);
            popup.setBackPressEnable(false);
            popup.showPopupWindow();
        }else{

            cgiQuickPay.setCVN2(dataBean.getAdd1());
            cgiQuickPay.setExpDate(dataBean.getAdd2());
            cgiQuickPay.setPhoneNo(dataBean.getPhoneNo());
            cgiQuickPay.setIdNo(dataBean.getIdCard());
            cgiQuickPay.setName(dataBean.getAcctName());
            cgiQuickPay.setCardNo(dataBean.getAcctNo());

            getvDelegate().showLoading();
            getP().cgiQuickPay(cgiQuickPay);
        }


    }

    @Override
    public void bondCard() {
        if (listViewDialog != null) {
            listViewDialog.dismiss();
        }
        JumpActivity(CgiBindCardActivity.class, true);
    }
}
