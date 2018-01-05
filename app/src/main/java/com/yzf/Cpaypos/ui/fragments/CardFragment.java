package com.yzf.Cpaypos.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.CgiBankCardAdapter;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.ChooseItem;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.present.PCardFragment;
import com.yzf.Cpaypos.ui.activitys.CgiBindCardActivity;
import com.yzf.Cpaypos.ui.activitys.ModifiedCreditCardctivity;
import com.yzf.Cpaypos.ui.activitys.PlanCardActivity;
import com.yzf.Cpaypos.widget.BottomDialog;
import com.yzf.Cpaypos.widget.MultipleStatusView;
import com.yzf.Cpaypos.widget.StateView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.XRecyclerView;
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
 * ClassName：FissionFragment
 * Description: 微创业页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:55
 * Modified By：
 * Fixtime：2017/5/18 11:55
 * FixDescription：
 */

public class CardFragment extends XFragment<PCardFragment> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cgibankcard_multiplestatusview)
    MultipleStatusView multiplestatusview;
    @BindView(R.id.cgibankcard_swipemenurecyclerview)
    SwipeMenuRecyclerView swipemenurecyclerview;
    @BindView(R.id.cgibankcard_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    Unbinder unbinder;
    private CgiBankCardAdapter adapter;
    private int delposition = 0;

    @Override
    public void initData(Bundle savedInstanceState) {
        useEventBus(true);
        initView();
        if (savedInstanceState == null) {
            multiplestatusview.showLoading();
        }
        getP().getWhiteCardList(AppUser.getInstance().getUserId());
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_card;
    }

    @Override
    public PCardFragment newP() {
        return new PCardFragment();
    }


    @Override
    public void bindEvent() {
        BusProvider.getBus().toObservable(IEvent.class)
                .subscribe(new Action1<IEvent>() {
                    @Override
                    public void call(IEvent iEvent) {
                        //TODO 事件处理
                        if (iEvent.getId().equals("refresh_card")) {
                            getP().getWhiteCardList(AppUser.getInstance().getUserId());
                        }
                    }
                });
    }


    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        initAdapter();
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplestatusview.showLoading();
                getP().getWhiteCardList(AppUser.getInstance().getUserId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        toolbar.inflateMenu(R.menu.menu_bankcard);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_bankcard:
                        String state = AppUser.getInstance().getStatus();
                        if (getP().isVerifyPass(state)) {
                            JumpActivity(CgiBindCardActivity.class, false);
                        }
                        break;
                }
                return true;
            }
        });
        title.setText("卡管理");
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new CgiBankCardAdapter(context);
            adapter.setOnMyItemClickListener(new CgiBankCardAdapter.OnMyItemClickListener() {
                @Override
                public void myClick(View v, int pos, GetWhiteCardListResult.DataBean item) {
                    Router.newIntent(context)
                            .to(PlanCardActivity.class)
                            .putSerializable("dataBean", item)
                            .launch();
                }

                @Override
                public void mLongClick(View v, final int pos, final GetWhiteCardListResult.DataBean item) {
                    BottomDialog bottomDailog = new BottomDialog(context);
                    ArrayList<ChooseItem> imgArray = new ArrayList<>();
                    imgArray.add(new ChooseItem("删除银行卡", null));
                    imgArray.add(new ChooseItem("修改银行卡", null));
                    bottomDailog.showAlert(null, imgArray, new BottomDialog.OnAlertSelectId() {
                        @Override
                        public void onClick(int whichButton) {
                            if (whichButton == 0) {
                                showNoticeDialog("是否删除该银行卡", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {
                                            getvDelegate().showLoading();
                                            delposition = pos;
                                            getP().deleteBankCard(AppUser.getInstance().getUserId(), item.getAcctNo());
                                        }
                                    }
                                });
                            } else if (whichButton == 1) {
                                Router.newIntent(context)
                                        .to(ModifiedCreditCardctivity.class)
                                        .putSerializable("dataBean", item)
                                        .launch();
                            }
                        }
                    });
                }
            });

        }

        /*swipemenurecyclerview.setSwipeMenuCreator(swipeMenuCreator);
        swipemenurecyclerview.setSwipeMenuItemClickListener(mMenuItemClickListener);*/


        swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.theme));
        swipemenurecyclerview.setLayoutManager(new LinearLayoutManager(context));
        swipemenurecyclerview.setAdapter(adapter);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getP().getWhiteCardList(AppUser.getInstance().getUserId());
            }
        });


    }

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            final int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition==0) {
                    showNoticeDialog("是否删除该银行卡", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which == DialogAction.POSITIVE) {
                                getvDelegate().showLoading();
                                delposition = menuPosition;
                                getP().deleteBankCard(AppUser.getInstance().getUserId(), adapter.getDataSource().get(menuPosition).getAcctNo());
                            }
                        }
                    });
                }else if(menuPosition==1)
                {
                    Router.newIntent(context)
                            .to(ModifiedCreditCardctivity.class)
                            .putSerializable("dataBean", adapter.getDataSource().get(menuPosition))
                            .launch();
                }
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                showToast( "list第" + adapterPosition + "; 左侧菜单第" + menuPosition);
            }
        }
    };

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.text_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                        .setBackground(R.drawable.delete_selector)
                        .setText("删除")
                        .setTextColor(getResources().getColor(R.color.text_white))
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(context)
                        .setBackground(R.drawable.modify_selector)
                        .setText("修改")
                        .setTextColor(getResources().getColor(R.color.text_white))
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };

    /**
     * 解绑卡后刷新卡列表
     *
     * @param msg
     */
    public void refresh(String msg) {
        getvDelegate().toastShort(msg);
        adapter.removeElement(delposition);
    }

    public void showSnackbar(final String msg) {
        getvDelegate().showSnackbar(toolbar, msg, "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(msg);
            }
        });
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

    public void JumpActivity(Class<?> activity, String serviceId) {
        Router.newIntent(context)
                .to(activity)
                .putString("serviceId", serviceId)
                .launch();
    }

    /**
     * 显示Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        getvDelegate().toastShort(msg);
    }

    public void JumpActivity(Class<?> activity) {
        Router.newIntent(context)
                .to(activity)
                .launch();
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
     * 显示双按钮对话框
     *
     * @param msg
     * @param callback
     */
    public void showNoticeDialog(String msg, MaterialDialog.SingleButtonCallback callback) {
        getvDelegate().showNoticeDialog(msg, callback);
    }

    /**
     * 显示错误信息
     *
     * @param error
     */
    public void showError(NetError error) {
        getvDelegate().showError(error);
    }

    public void showEmptyView(String msg) {
        multiplestatusview.showEmpty(msg);
    }


    public void showErrorView(String msg) {
        multiplestatusview.showError(msg);
    }

    public void showErrorView(NetError error) {
        multiplestatusview.showError(getvDelegate().getErrorType(error));
    }

    public void setAdapter(GetWhiteCardListResult getWhiteCardListResult) {
        swiperefreshlayout.setRefreshing(false);
        adapter.setData(getWhiteCardListResult.getData());
        multiplestatusview.showContent();
        if (adapter.getItemCount() < 1) {
            multiplestatusview.showEmpty();
            return;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
