package com.yzf.Cpaypos.ui.fragments;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.HolderView.AdHolderView;
import com.yzf.Cpaypos.adapter.HomeAdapter;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.LocationUtils;
import com.yzf.Cpaypos.model.HomeSource;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;
import com.yzf.Cpaypos.model.servicesmodels.GetPreviewPlanResult;
import com.yzf.Cpaypos.model.servicesmodels.GetPushMsgResults;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.present.PHomeFragment;
import com.yzf.Cpaypos.ui.activitys.PlanCardActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XFragment;
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
 * ClassName：HomeFragment
 * Description: 首页界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:56
 * Modified By：
 * Fixtime：2017/5/18 11:56
 * FixDescription：
 */

public class HomeFragment extends XFragment<PHomeFragment> implements OnItemClickListener {

    HomeAdapter adapter;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.notice_tv)
    TextView noticeTv;
    @BindView(R.id.home_recyclerview)
    SwipeMenuRecyclerView homeRecyclerview;
    @BindView(R.id.home_planbank_iv)
    ImageView homePlanbankIv;
    @BindView(R.id.home_planbankname_tv)
    TextView homePlanbanknameTv;
    @BindView(R.id.home_planbankno_tv)
    TextView homePlanbanknoTv;
    @BindView(R.id.bank_bg)
    LinearLayout bankBg;
    @BindView(R.id.home_planamt_tv)
    TextView homePlanamtTv;
    @BindView(R.id.home_plancount_tv)
    TextView homePlancountTv;
    @BindView(R.id.home_planprogress_tv)
    TextView homePlanprogressTv;
    @BindView(R.id.home_planfee_tv)
    TextView homePlanfeeTv;
    @BindView(R.id.home_plan_ll)
    LinearLayout homePlanLl;
    @BindView(R.id.home_loans_iv)
    ImageView homeLoansIv;
    @BindView(R.id.home_credit_iv)
    ImageView homeCreditIv;
    @BindView(R.id.home_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;


    private List<GetBannerListResult.DataBean> bannerList;
    private GetWhiteCardListResult.DataBean cardBean;


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initAdapter();
//        setBanner();
        getP().getBannerList(AppConfig.TOPBRANCHNO);
        getP().getPreviewPlan(AppUser.getInstance().getUserId());
        getP().getPushMsg(AppConfig.TOPBRANCHNO);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText(getText(R.string.app_name));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public PHomeFragment newP() {
        return new PHomeFragment();
    }


    public void initAdapter() {
        homeRecyclerview.setLayoutManager(new GridLayoutManager(context, 4));
        if (adapter == null) {
            adapter = new HomeAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<HomeSource, HomeAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, final HomeSource item, int tag, HomeAdapter.ViewHolder holder) {
                    super.onItemClick(position, item, tag, holder);
                    switch (tag) {
                        case HomeAdapter.TAG_VIEW:
                            if (item.getId() == 2 || item.getId() == 3) {
                                getRxPermissions()
                                        .request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                                        .subscribe(new Action1<Boolean>() {
                                            @Override
                                            public void call(Boolean granted) {
                                                if (granted) {
                                                    //TODO 许可
                                                    LocationUtils.getInstance(context).showLocation();
                                                    getP().toPay(item);
                                                } else {
                                                    //TODO 未许可
                                                    showToast("权限未获取");
                                                }
                                            }
                                        });
                            } else {
                                getP().toPay(item);
                            }

                            break;
                    }
                }
            });
        }
        homeRecyclerview.setAdapter(adapter);
        adapter.setData(getData());
        swiperefreshlayout.setColorSchemeColors(getResources().getColor(R.color.theme));
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getP().refresh(AppUser.getInstance().getUserId());
                getP().getPreviewPlan(AppUser.getInstance().getUserId());
                getP().getPushMsg(AppConfig.TOPBRANCHNO);
            }
        });

    }

    public void setBanner(GetBannerListResult getBannerListResult) {
        bannerList = new ArrayList<>();
        if (getBannerListResult.getData() != null && getBannerListResult.getData().size() > 0) {
            bannerList.addAll(getBannerListResult.getData());
        }
        showBanner();

    }

    public void setPreviewPlan(GetPreviewPlanResult getPreviewPlanResult, boolean flag) {
        if (flag) {
            GetPreviewPlanResult.DataBean dataBean = getPreviewPlanResult.getData();
            homePlanbanknameTv.setText(dataBean.getCardDesc());
            String card_id = dataBean.getCard_id();
            if (card_id != null && card_id.length() > 4) {
                card_id = card_id.substring(card_id.length() - 4, card_id.length());
            }
            homePlanbanknoTv.setText(card_id);
            homePlanamtTv.setText(dataBean.getRepayment_amount());
            homePlancountTv.setText(dataBean.getTimes());
            homePlanprogressTv.setText(dataBean.getSchedule());
            homePlanfeeTv.setText(dataBean.getFee_amt());
            int id = getImgId("b" + dataBean.getCardInst());
            if (id > 0) {
                RoundedBitmapDrawable circleDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), id2Bitmap(id));
                //设置为圆形
                circleDrawable.setCircular(true);
                homePlanbankIv.setImageDrawable(circleDrawable);
            }
            cardBean = new GetWhiteCardListResult.DataBean();
            GetPreviewPlanResult.DataBean.CardBeanBean cardBeanBean = dataBean.getCardBean();
            cardBean.setAcctName(cardBeanBean.getAcctName());
            cardBean.setAcctNo(cardBeanBean.getAcctNo());
            cardBean.setAdd1(cardBeanBean.getAdd1());
            cardBean.setAdd2(cardBeanBean.getAdd2());
            cardBean.setAdd3(cardBeanBean.getAdd3());
            cardBean.setAdd4(cardBeanBean.getAdd4());
            cardBean.setCardInst(cardBeanBean.getCardInst());
            cardBean.setCardDesc(cardBeanBean.getCardDesc());
            cardBean.setCardLimit(cardBeanBean.getCardLimit());
            cardBean.setCardType(cardBeanBean.getCardType());
            cardBean.setIdCard(cardBeanBean.getIdCard());
            cardBean.setMerchId(cardBeanBean.getMerchId());
            cardBean.setPhoneNo(cardBeanBean.getPhoneNo());
            cardBean.setStatus(cardBeanBean.getStatus());
        } else {
            homePlanbanknameTv.setText("暂无规划");
        }

    }

    public void setBanner() {
        bannerList = new ArrayList<>();
        GetBannerListResult.DataBean dataBean = new GetBannerListResult.DataBean();
        dataBean.setUrl("banner1.png");
        bannerList.add(dataBean);
        dataBean = new GetBannerListResult.DataBean();
        dataBean.setUrl("banner2.png");
        bannerList.add(dataBean);
        dataBean = new GetBannerListResult.DataBean();
        dataBean.setUrl("banner3.png");
        bannerList.add(dataBean);
        showBanner();

    }

    private void showBanner() {
        if (bannerList.size() > 0) {
            convenientBanner.setPages(
                    new CBViewHolderCreator<AdHolderView>() {
                        @Override
                        public AdHolderView createHolder() {
                            return new AdHolderView();
                        }
                    }, bannerList)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                    //设置指示器的方向
                    //                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                    //                .setOnPageChangeListener(this)//监听翻页事件
                    .setOnItemClickListener(this);
        }
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onItemClick(int position) {

    }

    private List<HomeSource> getData() {
        List<HomeSource> list = new ArrayList<>();
        HomeSource homeSource = new HomeSource();
        homeSource.setId(0);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("信用卡管理");
        HomeSource homeSource1 = new HomeSource();
        homeSource1.setId(1);
        homeSource1.setImgRes(R.mipmap.icon_withdraw_credit);
        homeSource1.setStrRes("信用卡取款");
        HomeSource homeSource2 = new HomeSource();
        homeSource2.setId(2);
        homeSource2.setImgRes(R.mipmap.icon_loans);
        homeSource2.setStrRes("贷款申请");
        homeSource2.setTargetStr("qianmApp");
        HomeSource homeSource3 = new HomeSource();
        homeSource3.setId(3);
        homeSource3.setImgRes(R.mipmap.icon_apply_credit);
        homeSource3.setStrRes("信用卡申请");
        homeSource3.setTargetStr("004800000000");
        HomeSource homeSource4 = new HomeSource();
        homeSource4.setId(4);
        homeSource4.setImgRes(R.mipmap.icon_convience_service);
        homeSource4.setStrRes("便民服务");
        HomeSource homeSource5 = new HomeSource();
        homeSource5.setId(5);
        homeSource5.setImgRes(R.mipmap.icon_recharge_phone);
        homeSource5.setStrRes("话费充值");
        HomeSource homeSource6 = new HomeSource();
        homeSource6.setId(6);
        homeSource6.setImgRes(R.mipmap.icon_recharge_water);
        homeSource6.setStrRes("水电缴费");
        HomeSource homeSource7 = new HomeSource();
        homeSource7.setId(7);
        homeSource7.setImgRes(R.mipmap.icon_home_more);
        homeSource7.setStrRes("更多");
        list.add(homeSource);
        list.add(homeSource1);
        list.add(homeSource2);
        list.add(homeSource3);
        list.add(homeSource4);
        list.add(homeSource5);
        list.add(homeSource6);
        list.add(homeSource7);
        return list;
    }

    private List<HomeSource> getCreditData() {
        List<HomeSource> list = new ArrayList<>();
        HomeSource homeSource = new HomeSource();
        homeSource.setId(0);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("招商银行");
        homeSource.setTargetStr("004800000000");
        list.add(homeSource);
        homeSource = new HomeSource();
        homeSource.setId(1);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("建设银行");
        homeSource.setTargetStr("004800000000");
        list.add(homeSource);
        homeSource = new HomeSource();
        homeSource.setId(2);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("工商银行");
        homeSource.setTargetStr("004800000000");
        list.add(homeSource);
        homeSource = new HomeSource();
        homeSource.setId(3);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("交通银行");
        homeSource.setTargetStr("004800000000");
        list.add(homeSource);
        return list;
    }

    private List<HomeSource> getLoansData() {
        List<HomeSource> list = new ArrayList<>();
        HomeSource homeSource = new HomeSource();
        homeSource.setId(0);
        homeSource.setImgRes(R.mipmap.icon_yrd);
        homeSource.setStrRes("宜人贷");
        homeSource.setTargetStr("qianmApp");
        list.add(homeSource);
        /*homeSource = new HomeSource();
        homeSource.setId(1);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("宝典财富");
        homeSource.setTargetStr("qianmApp");
        list.add(homeSource);
        homeSource = new HomeSource();
        homeSource.setId(2);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("百度钱包");
        homeSource.setTargetStr("qianmApp");
        list.add(homeSource);
        homeSource = new HomeSource();
        homeSource.setId(3);
        homeSource.setImgRes(R.mipmap.icon_manage_credit);
        homeSource.setStrRes("高源小额贷款");
        homeSource.setTargetStr("qianmApp");
        list.add(homeSource);*/
        return list;
    }

    public void JumpActivity(Class<?> activity) {
        Router.newIntent(context)
                .to(activity)
                .launch();
    }

    public void JumpActivity(Class<?> activity, String serviceId) {
        Router.newIntent(context)
                .to(activity)
                .putString("serviceId", serviceId)
                .launch();
    }

    public void showToast(String msg) {
        getvDelegate().toastShort(msg);
    }

    /**
     * 已经刷新完毕
     */
    public void refreshed() {
        swiperefreshlayout.setRefreshing(false);
        adapter.setData(getData());
    }

    /**
     * 显示单按钮对话框
     *
     * @param msg
     */
    public void showErrorDialog(String msg) {
        refreshed();
        getvDelegate().showErrorDialog(msg);
    }


    /**
     * 显示双按钮对话框
     *
     * @param msg
     * @param callback
     */
    public void showNoticeDialog(String msg, MaterialDialog.SingleButtonCallback callback) {
        refreshed();
        getvDelegate().showNoticeDialog(msg, callback);
    }

    public void showError(NetError error) {
        refreshed();
        getvDelegate().showError(error);
    }

    public void showLoading() {
        getvDelegate().showLoading();
    }

    public void dismissLoading() {
        getvDelegate().dismissLoading();
    }


    @OnClick({R.id.home_plan_ll, R.id.home_loans_iv, R.id.home_credit_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_plan_ll:
                if (cardBean != null) {
                    Router.newIntent(context)
                            .to(PlanCardActivity.class)
                            .putSerializable("dataBean", cardBean)
                            .launch();
                }else {
                    showToast("暂无规划");
                }
                break;
            case R.id.home_loans_iv:
                getRxPermissions()
                        .request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean granted) {
                                if (granted) {
                                    //TODO 许可
                                    LocationUtils.getInstance(context).showLocation();
                                    getP().getLoansUrlParms();
                                } else {
                                    //TODO 未许可
                                    showToast("权限未获取");
                                }
                            }
                        });
                break;
            case R.id.home_credit_iv:
                getRxPermissions()
                        .request(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean granted) {
                                if (granted) {
                                    //TODO 许可
                                    LocationUtils.getInstance(context).showLocation();
                                    getP().getCreditUrlParms();
                                } else {
                                    //TODO 未许可
                                    showToast("权限未获取");
                                }
                            }
                        });
                break;
        }
    }

    public void setMarqueeView(List<GetPushMsgResults.DataBean.MsgListBean> msgList) {
        List<SpannableString> info = new ArrayList<>();
        if (msgList.size() > 0) {
            for (GetPushMsgResults.DataBean.MsgListBean msgListBean : msgList) {
                SpannableString spannableString = new SpannableString("    " + msgListBean.getMsg_content());
                ImageSpan imageSpan = new ImageSpan(context, R.mipmap.icon_notice);
                spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                info.add(spannableString);
                noticeTv.setVisibility(View.GONE);
                marqueeView.setVisibility(View.VISIBLE);
                marqueeView.startWithList(info);
                marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
            }
        }
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
