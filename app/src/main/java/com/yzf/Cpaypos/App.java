package com.yzf.Cpaypos;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.yzf.Cpaypos.kit.AppConfig;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.yzf.Cpaypos.kit.db.DBManager;
import com.yzf.Cpaypos.ui.activitys.MainActivity;
import com.pgyersdk.crash.PgyCrashManager;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yzf.Cpaypos.kit.ActivityManager;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.log.LogLevel;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by wanglei on 2016/12/31.
 */

public class App extends Application  {
    private static Context context;

    public interface BehaviourListener{
        void handleBehaviour(int code,String msg,int handlePatchVersion);
    }
    public  static BehaviourListener behaviourListener=null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
       /* if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/

        // 注册activity监听器
        registerActivityListener();

        if (AppConfig.DEV){
            Logger.init(getString(R.string.app_name)).hideThreadInfo().methodCount(2).logLevel(LogLevel.FULL);//logger日志初始化
        }else {
            Logger.init(getString(R.string.app_name)).hideThreadInfo().methodCount(2).logLevel(LogLevel.NONE);//logger日志初始化
        }

        Recovery.getInstance()//crash崩溃监听处理
                .debug(AppConfig.DEV)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .callback(new MyCrashCallback())
                .silent(true, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .init(this);

        try {
            initHotfix();
        } catch (Exception e) {
            Log.e("热修复", e.toString());
            e.printStackTrace();
        }

        DBManager.initialize(context);//初始化数据库

        ZXingLibrary.initDisplayOpinion(this);//扫描二维码初始化

        PgyCrashManager.register(this);//蒲公英注册

        XApi.registerProvider(new NetProvider() {//网络初始化

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }
        });
    }

    /**
     * 初始化hotfix
     */
    private void initHotfix() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
        final String packgename=this.getPackageName();
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                //.setAesKey("0123456789123456")
                .setEnableDebug(AppConfig.DEV)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        Logger.i("code:"+code+", info:"+info+", packagename:"+packgename);
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Logger.i("补丁加载成功");

                            /*if (behaviourListener!=null){
                                behaviourListener.handleBehaviour(code,info,handlePatchVersion);
                            }*/
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后应用自杀
                            //如果应用不在前台启用，补丁生效完毕直接杀掉应用，否则弹出对话框提示用户bug更新完毕，需要用户选择是否重启应用
                            Logger.i("补丁生效需要重启");
                            if (Kits.Package.isApplicationInBackground(context)){
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }else{
                                if (behaviourListener!=null){
                                    behaviourListener.handleBehaviour(code,info,handlePatchVersion);
                                }
                            }

                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                            Logger.i("清空本地补丁");
                            SophixManager.getInstance().cleanPatches();
                        } else if (code ==PatchStatus.CODE_DOWNLOAD_SUCCESS){
                            //patch文件下载成功
                            Logger.i("补丁文件下载成功");
                            /*if (behaviourListener!=null){
                                behaviourListener.handleBehaviour(code,info,handlePatchVersion);
                            }*/
                        }
                        else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                        /*if (behaviourListener!=null){
                            behaviourListener.handleBehaviour(code,info,handlePatchVersion);
                        }*/
                    }
                }).initialize();
    }

    public static Context getContext() {
        return context;
    }

    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    PgyCrashManager.register(activity);//蒲公英在每一个activity中的oncreate中注册监听崩溃信息
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    ActivityManager.getInstance().pushActivity(activity);
                    /**
                     * 栈顶元素名称
                     */
                    Logger.d("TopActivityName:" + ActivityManager.getInstance().getTopActivityName());
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == ActivityManager.getInstance().getActivitys() && ActivityManager.getInstance().getActivitys().isEmpty()) {
                        return;
                    }
                    PgyCrashManager.unregister();//蒲公英在每一个activity中的ondestory中注销监听崩溃信息
                    if (ActivityManager.getInstance().getActivitys().contains(activity)) {
                        /**  * 监听到 Activity销毁事件 将该Activity 从list中移除  */
                        ActivityManager.getInstance().popActivity(activity);
                    }
                }
            });
        }
    }

    static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
            Logger.e("exceptionMessage:" + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
            Logger.e("cause:" + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            Logger.e("exceptionClassName:" + exceptionType);
            Logger.e("throwClassName:" + throwClassName);
            Logger.e("throwMethodName:" + throwMethodName);
            Logger.e("throwLineNumber:" + throwLineNumber);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }
}
