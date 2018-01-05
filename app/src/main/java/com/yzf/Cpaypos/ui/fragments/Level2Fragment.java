package com.yzf.Cpaypos.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.servicesmodels.GetMerchLevelResults;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.mvp.XFragment;
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
 * ClassName：Level2Fragment
 * Description: 铂金等级界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:58
 * Modified By：
 * Fixtime：2017/5/18 11:58
 * FixDescription：
 */
public class Level2Fragment extends XFragment {


    @BindView(R.id.fmlevel_wxfee_tv)
    TextView fmlevelWxfeeTv;
    @BindView(R.id.fmlevel_wxdf_tv)
    TextView fmlevelWxdfTv;
    @BindView(R.id.fmlevel_wxone_tv)
    TextView fmlevelWxoneTv;
    @BindView(R.id.fmlevel_wxday_tv)
    TextView fmlevelWxdayTv;
    @BindView(R.id.fmlevel_jnfee_tv)
    TextView fmlevelJnfeeTv;
    @BindView(R.id.fmlevel_jndf_tv)
    TextView fmlevelJndfTv;
    @BindView(R.id.fmlevel_jnone_tv)
    TextView fmlevelJnoneTv;
    @BindView(R.id.fmlevel_jnday_tv)
    TextView fmlevelJndayTv;
    @BindView(R.id.fmlevel_jwfee_tv)
    TextView fmlevelJwfeeTv;
    @BindView(R.id.fmlevel_jwdf_tv)
    TextView fmlevelJwdfTv;
    @BindView(R.id.fmlevel_jwone_tv)
    TextView fmlevelJwoneTv;
    @BindView(R.id.fmlevel_jwday_tv)
    TextView fmlevelJwdayTv;
    @BindView(R.id.fmlevel_tips_tv)
    TextView fmlevelTipsTv;
    Unbinder unbinder;
    List<GetMerchLevelResults.DataBean.FeeListBean> feeListBean = new ArrayList<>();
    private String levelAmout;
    private String merchLevel;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        feeListBean = (List<GetMerchLevelResults.DataBean.FeeListBean>) getArguments().getSerializable("feeListBean");
        levelAmout = getArguments().getString("levelAmout");
        merchLevel = getArguments().getString("merchLevel");
        setAData();
    }

    /**
     * 初始化界面
     */
    private void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_level;
    }

    @Override
    public Object newP() {
        return null;
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

    public void showError(NetError error) {
        getvDelegate().showError(error);
    }


    public void setAData() {
        if (feeListBean.size() > 0) {
            for (GetMerchLevelResults.DataBean.FeeListBean bean : feeListBean) {
                if (bean.getMerchLevel().equals("2") && bean.getServiceId().equals("04"))//微信
                {
                    fmlevelWxfeeTv.setText(bean.getFeeValue());
                    fmlevelWxdfTv.setText(bean.getDfFee() + "元/笔");
                    fmlevelWxoneTv.setText("1000");
                    fmlevelWxdayTv.setText("10000");

                }
                if (bean.getMerchLevel().equals("2") && bean.getServiceId().equals("02"))//境内快捷
                {
                    fmlevelJnfeeTv.setText(bean.getFeeValue());
                    fmlevelJndfTv.setText(bean.getDfFee() + "元/笔");
                    fmlevelJnoneTv.setText("20000");
                    fmlevelJndayTv.setText("200000");

                }
                if (bean.getMerchLevel().equals("2") && bean.getServiceId().equals("03"))//境外快捷
                {
                    fmlevelJwfeeTv.setText(bean.getFeeValue());
                    fmlevelJwdfTv.setText(bean.getDfFee() + "元/笔");
                    fmlevelJwoneTv.setText("200HK");
                    fmlevelJwdayTv.setText("1000HK");

                }
            }
        }
        String name = "";
        String tips = "null";
        if (merchLevel.equals("1")) {
            name = "铂金";
        } else if (merchLevel.equals("2")) {
            name = "钻石";
        } else if (merchLevel.equals("3")) {
            tips = "若想升级为合伙人，请直接联系我们的客服";
        } else if (merchLevel.equals("4")) {
            tips = "";
        } else {
            name = merchLevel;
        }
        if (!tips.equals("null")) {
            if (AppTools.isEmpty(tips)) {
                fmlevelTipsTv.setVisibility(View.GONE);
            } else {
                fmlevelTipsTv.setText(tips);
            }
        } else {
            fmlevelTipsTv.setText(String.format(getResources().getString(R.string.merchlevel_tips), levelAmout, name));
        }
    }
}