package com.yzf.Cpaypos.kit;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.just.library.AgentWeb;
import com.yzf.Cpaypos.model.servicesmodels.QuickPayResult;
import com.yzf.Cpaypos.ui.activitys.PayBondActivity;
import com.yzf.Cpaypos.ui.fragments.AgentWebFragment;

import org.greenrobot.eventbus.EventBus;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.kit.SimpleCallback;
import cn.droidlover.xdroidmvp.log.Logger;

/**
 * Created by cenxiaozhong on 2017/5/14.
 * source CODE  https://github.com/Justson/AgentWeb
 */

public class AndroidInterface {

    private Handler deliver = new Handler(Looper.getMainLooper());
    private AgentWeb agent;
    private Context context;
    private String content;

    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }


    @JavascriptInterface
    public void callAndroid(final String jsonStr) {
        Logger.i("后台回调：" + jsonStr);
        content = jsonStr;
        if (!AppTools.isEmpty(content)) {
            Gson gson = new Gson();
            final QuickPayResult quickPayResult = gson.fromJson(content, QuickPayResult.class);
            if (context != null) {
                deliver.post(new Runnable() {
                    @Override
                    public void run() {
                        if (((Activity) context).hasWindowFocus()) {
                            try {
                                new MaterialDialog.Builder(context)
                                        .title("提示")
                                        .content(quickPayResult.getRespDesc())
                                        .positiveText("确定")
                                        .onAny(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                if (which == DialogAction.POSITIVE) {
                                                    ActivityManager.getInstance().finishActivity(context.getClass());
                                                }
                                            }
                                        })
                                        .show();
                            } catch (Exception e) {
                                Toast.makeText(context.getApplicationContext(),quickPayResult.getRespDesc(),Toast.LENGTH_LONG);
                                Logger.e(e.toString());
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }


    }
}
