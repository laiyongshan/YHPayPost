package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.BankDialogAdapter;
import com.yzf.Cpaypos.adapter.SubBankDialogAdapter;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.SearchBankResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.present.PUploadData;
import com.yzf.Cpaypos.widget.StateButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;

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
 * ClassName：UploadDataActivity
 * Description: 上传资料界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/21 16:58
 * Modified By：
 * Fixtime：2017/3/21 16:58
 * FixDescription：
 */
public class UploadDataActivity extends XActivity<PUploadData> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.uldata_idcard_et)
    XEditText uldataIdcardEt;
    @BindView(R.id.uldata_bank_et)
    XEditText uldataBankEt;
    @BindView(R.id.uldata_bank_bt)
    StateButton uldataBankBt;
    @BindView(R.id.uldata_subbank_et)
    XEditText uldataSubbankEt;
    @BindView(R.id.uldata_sunbank_bt)
    StateButton uldataSunbankBt;
    @BindView(R.id.uldata_acctname_et)
    XEditText uldataAcctnameEt;
    @BindView(R.id.uldata_acctno_et)
    XEditText uldataAcctnoEt;
    @BindView(R.id.uldata_acctphone_et)
    XEditText uldataAcctphoneEt;
    @BindView(R.id.uldata_confirm_bt)
    StateButton uldataConfirmBt;

    private String bankCode = "";
    private String subBankCode = "";


    //声明一个LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    //当前页，从0开始
    private int currentPage = 1;
    //已经加载出来的Item的数量
    private int totalItemCount;

    //主要用来存储上一个totalItemCount
    private int previousTotal = 0;

    //在屏幕上可见的item数量
    private int visibleItemCount;

    //在屏幕可见的Item中的第一个
    private int firstVisibleItem;

    //是否正在上拉数据
    private boolean loading = true;

    private int pagenum = 10;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        uldataAcctphoneEt.setPattern((new int[]{3, 5, 5}));
        uldataAcctphoneEt.setSeparator(" ");
        uldataIdcardEt.setPattern((new int[]{6, 5, 5, 5, 5}));
        uldataIdcardEt.setSeparator(" ");
        uldataAcctnoEt.setPattern((new int[]{4, 5, 5, 5, 5}));
        uldataAcctnoEt.setSeparator(" ");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_upload_data;
    }

    @Override
    public PUploadData newP() {
        return new PUploadData();
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
        title.setText("完善用户信息");
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
     * 显示大行对话框
     *
     * @param list
     */
    public void showBankDialog(List<SearchBankResult.DataBean> list) {
        getvDelegate().dismissLoading();
        BankDialogAdapter adapter = new BankDialogAdapter(this);
        adapter.setData(list);
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .backgroundColor(ContextCompat.getColor(context,R.color.bg_white))
                .title("选择银行")
                .adapter(adapter, null)
                .show();


        adapter.setRecItemClick(new RecyclerItemCallback<SearchBankResult.DataBean, BankDialogAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, SearchBankResult.DataBean item, int tag, BankDialogAdapter.ViewHolder holder) {
                super.onItemClick(position, item, tag, holder);
                switch (tag) {
                    case BankDialogAdapter.TAG_VIEW:
                        uldataBankEt.setText(item.getBankTypeName());
                        uldataSubbankEt.setText(item.getBankTypeName());
                        bankCode = item.getBankType();
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        break;
                }
            }
        });
    }

    /**
     * 显示支行对话框
     *
     * @param list
     */
    public void showSubBankDialog(List<SearchBankResult.DataBean> list) {
        visibleItemCount = 0;
        totalItemCount = 0;
        firstVisibleItem = 0;
        previousTotal = 0;
        loading = true;
        getvDelegate().dismissLoading();
        final SubBankDialogAdapter adapter = new SubBankDialogAdapter(this);
        adapter.setData(list);
        final MaterialDialog dialog = new MaterialDialog.Builder(this)
//                .backgroundColor(ContextCompat.getColor(context,R.color.bg_white))
                .title("选择支行")
                .adapter(adapter, null)
                .build();
        RecyclerView recyclerView = dialog.getRecyclerView();
        dialog.show();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        //说明数据已经加载结束
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                //这里需要好好理解
                if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
                    currentPage++;
                    loading = true;
                    Api.getAPPService().searchSubBank(uldataSubbankEt.getNonSeparatorText(), currentPage, pagenum, bankCode)
                            .compose(XApi.<SearchBankResult>getApiTransformer())
                            .compose(XApi.<SearchBankResult>getScheduler())
                            .subscribe(new ApiSubcriber<SearchBankResult>() {
                                @Override
                                public void onNext(SearchBankResult searchBankResult) {
                                    if (searchBankResult.getRespCode().equals("00")) {
                                        if (searchBankResult.getData().size() > 0) {
                                            adapter.addData(searchBankResult.getData());
                                        } else {
                                            showToast(searchBankResult.getRespMsg());
                                        }
                                    } else {
                                        showToast(searchBankResult.getRespMsg());
                                    }
                                }

                                @Override
                                protected void onFail(NetError error) {
                                    showError(error);
                                }

                            });
                }
            }
        });

        adapter.setRecItemClick(new RecyclerItemCallback<SearchBankResult.DataBean, SubBankDialogAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, SearchBankResult.DataBean item, int tag, SubBankDialogAdapter.ViewHolder holder) {
                super.onItemClick(position, item, tag, holder);
                switch (tag) {
                    case BankDialogAdapter.TAG_VIEW:
                        uldataSubbankEt.setText(item.getBankName());
                        subBankCode = item.getBankNo();
                        dialog.dismiss();
                        break;
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


    @OnClick({R.id.uldata_bank_bt, R.id.uldata_sunbank_bt, R.id.uldata_confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uldata_bank_bt:
                getvDelegate().showLoading();
                getP().searchBank(uldataBankEt.getNonSeparatorText());
                break;
            case R.id.uldata_sunbank_bt:
                getvDelegate().showLoading();
                getP().searchSubBank(uldataSubbankEt.getNonSeparatorText(), 1, pagenum, bankCode);
                break;
            case R.id.uldata_confirm_bt:
                getvDelegate().showLoading();
                getP().upLoadData(AppUser.getInstance().getUserId(), uldataIdcardEt.getNonSeparatorText(),
                        uldataAcctnoEt.getNonSeparatorText(), uldataAcctphoneEt.getNonSeparatorText(), uldataAcctnameEt.getNonSeparatorText());
                /*getP().upLoadData(AppUser.getInstance().getUserId(), uldataIdcardEt.getNonSeparatorText(), uldataSubbankEt.getNonSeparatorText(),
                        uldataAcctnoEt.getNonSeparatorText(), uldataAcctphoneEt.getNonSeparatorText(), uldataAcctnameEt.getNonSeparatorText(), subBankCode);*/
                break;
        }
    }
}
