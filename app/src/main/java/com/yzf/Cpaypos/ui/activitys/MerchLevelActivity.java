package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchLevelResults;
import com.yzf.Cpaypos.present.PMerchLevel;
import com.yzf.Cpaypos.ui.fragments.Level1Fragment;
import com.yzf.Cpaypos.ui.fragments.Level2Fragment;
import com.yzf.Cpaypos.ui.fragments.Level3Fragment;
import com.yzf.Cpaypos.ui.fragments.Level4Fragment;
import com.yzf.Cpaypos.widget.StateButton;
import com.yzf.Cpaypos.widget.StateView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
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
 * ClassName：MerchLevelActivity
 * Description:商户等级界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/11 16:39
 * Modified By：
 * Fixtime：2017/5/11 16:39
 * FixDescription：
 */
public class MerchLevelActivity extends XActivity<PMerchLevel> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.merchlevel_level_tv)
    TextView merchlevelLevelTv;
    @BindView(R.id.merchlevel_profit_tv)
    TextView merchlevelProfitTv;
    @BindView(R.id.merchlevel_time_tv)
    TextView merchlevelTimeTv;
    @BindView(R.id.merchlevel_update_bt)
    StateButton merchlevelUpdateBt;
    @BindView(R.id.merchlevel_level1_ll)
    LinearLayout merchlevelLevel1Ll;
    @BindView(R.id.merchlevel_level2_ll)
    LinearLayout merchlevelLevel2Ll;
    @BindView(R.id.merchlevel_level3_ll)
    LinearLayout merchlevelLevel3Ll;
    @BindView(R.id.merchlevel_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.merchlevel_viewPager)
    ViewPager viewPager;

    @BindView(R.id.merchlevel_controller)
    XStateController controller;
    StateView errorView;

    List<Fragment> fragmentList = new ArrayList<>();
    String[] titles = {"黄金", "铂金", "钻石", "合伙人"};

    XFragmentAdapter adapter;
    @BindView(R.id.merchlevel_level1_tv)
    TextView merchlevelLevel1Tv;
    @BindView(R.id.merchlevel_level2_tv)
    TextView merchlevelLevel2Tv;
    @BindView(R.id.merchlevel_level3_tv)
    TextView merchlevelLevel3Tv;
    List<GetMerchLevelResults.DataBean.FeeListBean> feeListBean = new ArrayList<>();
    Level1Fragment level1Fragment;
    Level2Fragment level2Fragment;
    Level3Fragment level3Fragment;
    Level4Fragment level4Fragment;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        controller.showLoading();
        getP().getMerchLevel(AppUser.getInstance().getUserId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_merch_level;
    }

    @Override
    public PMerchLevel newP() {
        return new PMerchLevel();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        controller.loadingView(View.inflate(this, R.layout.view_loading, null));
        controller.emptyView(View.inflate(this, R.layout.view_empty, null));
        if (errorView == null) {
            errorView = new StateView(context);
        }
        controller.errorView(errorView);
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
        title.setText(AppUser.getInstance().getMerchName());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_level;
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
                WebActivity.launch(context, AppConfig.BASE_URL + "home/getBusinessRule.do", "创业规则");
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

    public void showErrorView(String msg, int id) {
        errorView.setMsg(msg);
        errorView.setImg(id);
        controller.showError();
    }

    public void showErrorView(NetError error, int id) {
        errorView.setMsg(getvDelegate().getErrorType(error));
        errorView.setImg(id);
        controller.showError();
    }

    @OnClick({R.id.merchlevel_update_bt, R.id.merchlevel_level1_ll, R.id.merchlevel_level2_ll, R.id.merchlevel_level3_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.merchlevel_update_bt:
                break;
            case R.id.merchlevel_level1_ll:
                break;
            case R.id.merchlevel_level2_ll:
                break;
            case R.id.merchlevel_level3_ll:
                break;
        }
    }

    public void setData(GetMerchLevelResults getMerchLevelResults) {
        if (getMerchLevelResults.getData() != null) {
            controller.showContent();
            feeListBean = getMerchLevelResults.getData().getFeeList();
            merchlevelLevelTv.setText("等级：" + setLevel(getMerchLevelResults.getData().getMerchLevel()));
            merchlevelProfitTv.setText("累计收益：" + getMerchLevelResults.getData().getSumSyAmt() + "元");
            merchlevelLevel1Tv.setText(getMerchLevelResults.getData().getLevel1() + "个");
            merchlevelLevel2Tv.setText(getMerchLevelResults.getData().getLevel2() + "个");
            merchlevelLevel3Tv.setText(getMerchLevelResults.getData().getLevel3() + "个");
            merchlevelTimeTv.setText("加入时间：" + getMerchLevelResults.getData().getCreateTime());

            fragmentList.clear();
            Bundle args = new Bundle();
            args.putSerializable("feeListBean", (Serializable) feeListBean);
            args.putString("merchLevel", getMerchLevelResults.getData().getMerchLevel());
            args.putString("levelAmout", getMerchLevelResults.getData().getLevelAmout());
            level1Fragment = new Level1Fragment();
            level1Fragment.setArguments(args);
            level2Fragment = new Level2Fragment();
            level2Fragment.setArguments(args);
            level3Fragment = new Level3Fragment();
            level3Fragment.setArguments(args);
            level4Fragment = new Level4Fragment();
            level4Fragment.setArguments(args);
            fragmentList.add(level1Fragment);
            fragmentList.add(level2Fragment);
            fragmentList.add(level3Fragment);
            fragmentList.add(level4Fragment);

            if (adapter == null) {
                adapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
            }
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(4);
            tabLayout.setupWithViewPager(viewPager);
        } else {
            controller.showEmpty();
        }

    }

    private String setLevel(String merchLevel) {
        if (!AppTools.isEmpty(merchLevel)) {
            if (merchLevel.equals("1")) {
                merchLevel = "黄金";
            } else if (merchLevel.equals("2")) {
                merchLevel = "铂金";
            } else if (merchLevel.equals("3")) {
                merchLevel = "钻石";
            } else if (merchLevel.equals("4")) {
                merchLevel = "合伙人";
            } else if (merchLevel.equals("5")) {
                merchLevel = "五星";
            }
        }
        return merchLevel;
    }


}
