package com.yzf.Cpaypos.ui.activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.HelpAdapter;
import com.yzf.Cpaypos.kit.DividerListItemDecoration;
import com.yzf.Cpaypos.model.HelpSource;
import com.yzf.Cpaypos.widget.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;

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
 * ClassName：HelpActivity
 * Description:帮助中心页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/10 17:36
 * Modified By：
 * Fixtime：2017/5/10 17:36
 * FixDescription：
 */
public class HelpActivity extends XActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.help_contentLayout)
    XRecyclerContentLayout contentLayout;
    private HelpAdapter adapter;
    StateView errorView;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        initAdapter();
        contentLayout.showLoading();
        setAdapter();
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new HelpAdapter(context);
        }
        contentLayout.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.theme));
        contentLayout.getRecyclerView().verticalLayoutManager(this);
        contentLayout.getRecyclerView().addItemDecoration(new DividerListItemDecoration(context, DividerListItemDecoration.VERTICAL_LIST));
        contentLayout.getRecyclerView().setAdapter(adapter);
        if (errorView == null) {
            errorView = new StateView(context);
        }
        contentLayout.errorView(errorView);
        contentLayout.getRecyclerView().setRefreshEnabled(false);
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
        contentLayout.emptyView(View.inflate(context, R.layout.view_empty, null));

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
        title.setText("帮助中心");
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

    public void showErrorView(String msg, int id) {
        errorView.setMsg(msg);
        errorView.setImg(id);
        contentLayout.showError();
    }

    public void showErrorView(NetError error, int id) {
        errorView.setMsg(getvDelegate().getErrorType(error));
        errorView.setImg(id);
        contentLayout.showError();
    }

    private List<HelpSource> getData() {
        List<HelpSource> helpSources = new ArrayList<>();
        String[] answer = context.getResources().getStringArray(R.array.answer);
        String[] question = context.getResources().getStringArray(R.array.question);
        for (int i = 0; i < answer.length; i++) {
            HelpSource helpSource = new HelpSource();
            helpSource.setQuestion(question[i]);
            helpSource.setAnswer(answer[i]);
            helpSources.add(helpSource);
        }
        return helpSources;

    }

    private void setAdapter() {
        adapter.setData(getData());
    }

}
