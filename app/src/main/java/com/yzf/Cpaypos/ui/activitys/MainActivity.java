package com.yzf.Cpaypos.ui.activitys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;

import com.yzf.Cpaypos.kit.AppConfig;
import com.taobao.sophix.SophixManager;
import com.yzf.Cpaypos.App;
import com.yzf.Cpaypos.R;

import com.yzf.Cpaypos.kit.ActivityManager;
import com.yzf.Cpaypos.ui.fragments.CardFragment;
import com.yzf.Cpaypos.ui.fragments.TransFragment;
import com.yzf.Cpaypos.ui.fragments.PersonlFragment;
import com.yzf.Cpaypos.ui.fragments.HomeFragment;
import com.yzf.Cpaypos.widget.CustomViewPager;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.mvp.XActivity;
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
 * ClassName：MainActivity
 * Description: 主页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/18 11:43
 * Modified By：
 * Fixtime：2017/5/18 11:43
 * FixDescription：
 */
public class MainActivity extends XActivity implements App.BehaviourListener{
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    List<Fragment> fragmentList = new ArrayList<>();
    String[] titles = {"首页", "卡管理", "账单", "我的"};

    XFragmentAdapter adapter;
    private long lastTime = 0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData(Bundle savedInstanceState) {
        useEventBus(true);
//        App hotFixApplication = (App) getApplication();
        App.behaviourListener = this;

        if (!AppConfig.DEV) {
            SophixManager.getInstance().queryAndLoadNewPatch();
        }

        fragmentList.clear();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CardFragment());
        fragmentList.add(new TransFragment());
        fragmentList.add(new PersonlFragment());

        if (adapter == null) {
            adapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.footer_home_selector);
        tabLayout.getTabAt(1).setIcon(R.drawable.footer_card_selector);
        tabLayout.getTabAt(2).setIcon(R.drawable.footer_trans_selector);
        tabLayout.getTabAt(3).setIcon(R.drawable.footer_person_selector);
    }

    @Override
    public void bindEvent() {
        BusProvider.getBus().toObservable(IEvent.class)
                .subscribe(new Action1<IEvent>() {
                    @Override
                    public void call(IEvent iEvent) {
                        //TODO 事件处理
                        if (iEvent.getId().equals("to_tab")) {
                           int position=(int) iEvent.getObject();
                            viewPager.setCurrentItem(position);
                        }
                    }
                });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime < 2000) {
            ActivityManager.getInstance().appExit();
            super.onBackPressed();
        } else {
            lastTime = System.currentTimeMillis();
            getvDelegate().toastShort("再按一次退出");
        }
    }

    @Override
    public void handleBehaviour(final int code, final String msg, final int handlePatchVersion) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createDialog(msg);
            }
        });

    }
    public void createDialog(String msg) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Bug更新");
        builder.setCancelable(false);
        builder.setMessage("修复了一些bug,是否马上重启？");
        builder.setNegativeButton("稍后重启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("马上重启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartApplication();
                SophixManager.getInstance().killProcessSafely();
            }
        });
        builder.show();
    }

    private void restartApplication() {
        Intent intent = getPackageManager().getLaunchIntentForPackage(getApplication().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        ActivityManager.getInstance().appExit();
        /*ActivityManager.getInstance().appExit();
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
    }
}
