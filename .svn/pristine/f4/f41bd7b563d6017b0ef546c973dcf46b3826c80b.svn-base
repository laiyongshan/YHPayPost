package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.BankDialogAdapter;
import com.yzf.Cpaypos.adapter.SubBankDialogAdapter;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.SearchBankResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.present.PModifiedSettleCard;
import com.yzf.Cpaypos.widget.StateButton;
import com.xw.repo.xedittext.XEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
 * ClassName：ModifiedSettleCardctivity
 * Description:
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/11/7 11:03
 * Modified By：
 * Fixtime：2017/11/7 11:03
 * FixDescription：
 */
public class ModifiedSettleCardctivity extends XActivity<PModifiedSettleCard> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.modified_phoneno_tv)
    XEditText modifiedPhonenoTv;
    @BindView(R.id.modified_cardno_tv)
    XEditText modifiedCardnoTv;
    @BindView(R.id.modified_bank_tv)
    XEditText modifiedBankTv;
    @BindView(R.id.modified_bank_bt)
    StateButton modifiedBankBt;
    @BindView(R.id.modified_confirm_bt)
    StateButton modifiedConfirmBt;


    private String subBankCode =null;
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modified_settle_cardctivity;
    }

    @Override
    public PModifiedSettleCard newP() {
        return new PModifiedSettleCard();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        modifiedPhonenoTv.setPattern((new int[]{3, 5, 5}));
        modifiedPhonenoTv.setSeparator(" ");
        modifiedCardnoTv.setPattern((new int[]{4, 5, 5, 5, 5}));
        modifiedCardnoTv.setSeparator(" ");
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
        title.setText("结算卡修改");
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

    @OnClick({R.id.modified_bank_bt, R.id.modified_confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modified_bank_bt:
                getvDelegate().showLoading();
                getP().searchSubBank(modifiedBankTv.getNonSeparatorText(), 1, pagenum, "");
                break;
            case R.id.modified_confirm_bt:
                getvDelegate().showLoading();
                getP().modifiedSettleCard(AppUser.getInstance().getUserId(),modifiedCardnoTv.getNonSeparatorText(),modifiedPhonenoTv.getNonSeparatorText());
                break;
        }
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
                    Api.getAPPService().searchSubBank(modifiedBankTv.getNonSeparatorText(), currentPage, pagenum, "")
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
                        modifiedBankTv.setText(item.getBankName());
                        subBankCode = item.getBankNo();
                        dialog.dismiss();
                        break;
                }
            }
        });
    }
}
