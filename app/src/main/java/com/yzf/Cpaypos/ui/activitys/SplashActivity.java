package com.yzf.Cpaypos.ui.activitys;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.yzf.Cpaypos.kit.AppConfig;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.db.ParamDao;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppKit;
import com.yzf.Cpaypos.model.servicesmodels.GetBannerListResult;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;
import com.yzf.Cpaypos.present.PSplash;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.imageloader.ILoader;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
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
 * ClassName：SplashActivity
 * Description: 初始页面(此处一直有内存泄露........)
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/20 9:19
 * Modified By：
 * Fixtime：2017/3/20 9:19
 * FixDescription：
 */
public class SplashActivity extends XActivity<PSplash> {

    @BindView(R.id.splash_iv)
    ImageView splashIv;

    /**
     * Handler
     */
    private static Handler handlerdelay = new Handler();

    /**
     * 初始化数据
     */
    @Override
    public void initData(Bundle savedInstanceState) {
//        checkUpdate(false);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    /*    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//隐藏标题栏，弄成全屏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        initDB();//初始化数据库
        checkUpdate(false);//检查更新
        getP().getWelcomeImg(AppConfig.TOPBRANCHNO);//获取欢迎图片
        String welcomeImg = SharedPref.getInstance(context).getString("welcomeImg", "");//获取本地缓存
        if (!AppTools.isEmpty(welcomeImg)) {
            Gson gson = new Gson();
            List<GetBannerListResult.DataBean> plist = new ArrayList<>();
            plist = gson.fromJson(welcomeImg,
                    new TypeToken<List<GetBannerListResult.DataBean>>() {
                    }.getType());
            if (plist.size() > 0 && plist.get(0).getUrl() != null) {
                String url = plist.get(0).getUrl();
                ILFactory.getLoader().loadNet(splashIv, url, new ILoader.Options(-1, R.mipmap.loading_fail_img).scaleType(ImageView.ScaleType.CENTER_CROP));
            }
        }

        handlerdelay.postDelayed(jumpRunnable, 2000);//两秒后跳转
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public PSplash newP() {
        return new PSplash();
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        ParamDao paramDao = new ParamDao();
        paramDao.initDbBeforeLogin();
    }


    /**
     * Runnable
     */
    private final Runnable jumpRunnable = new Runnable() {
        public void run() {
            /*getWindow().setFlags(//跳转之前弄成不全屏
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);*/
            Router.newIntent(context)
                    .to(LoginActivity.class)
                    .data(new Bundle())
                    .launch();
            Router.pop(context);
        }
    };

    /**
     * 检查更新
     *
     * @param forceUpdate 强制更新标志
     */
    private void checkUpdate(final boolean forceUpdate) {
        PgyUpdateManager.register(SplashActivity.this, AppKit.getpackageNames(context) + ".fileprovider",
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        // 将新版本信息封装到AppBean中
                        //{"code":0,"message":"","data":{"lastBuild":"9","downloadURL":"http:\/\/qiniu-app-cdn.pgyer.com\/2bd97e24ecdea088cd1d0e170c034124.apk?e=1476694444&attname=Mall_pgyer_v1.2_20161014.apk&token=6fYeQ7_TVB5L0QSzosNFfw2HU8eJhAirMF5VxV9G:4laj0OzXJZUXTiQc9MThSRHBqlg=","versionCode":"12","versionName":"1.2","appUrl":"http:\/\/www.pgyer.com\/xnJu","build":"9","releaseNote":"\u66f4\u65b0\u5230\u7248\u672c: 1.2(build9)"}
//                        Logger.i("updateresult="+result.toString());
                        handlerdelay.removeCallbacks(jumpRunnable);
                        if (SplashActivity.this!=null&&!SplashActivity.this.isFinishing()) {
                            getRxPermissions()
                                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .subscribe(new Action1<Boolean>() {
                                        @Override
                                        public void call(Boolean granted) {
                                            if (granted) {
                                                //TODO 许可
                                                if (!SplashActivity.this.isFinishing()) {
                                                    final AppBean appBean = getAppBeanFromString(result);
                                                    if (!forceUpdate) {
                                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                                        builder.setTitle(R.string.update_version);
                                                        builder.setCancelable(false);
                                                        builder.setMessage(appBean.getReleaseNote());
                                                        builder.setNegativeButton(R.string.update_later, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Router.newIntent(context)
                                                                        .to(LoginActivity.class)
                                                                        .data(new Bundle())
                                                                        .launch();
                                                                Router.pop(context);
                                                            }
                                                        });
                                                        builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                startDownloadTask(SplashActivity.this, appBean.getDownloadURL());
                                                            }
                                                        });
                                                        builder.show();
                                                        PgyUpdateManager.unregister();
                                                    } else {
                                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                                        builder.setTitle(R.string.update_version);
                                                        builder.setCancelable(false);
                                                        builder.setMessage(appBean.getReleaseNote());
                                                        builder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                startDownloadTask(SplashActivity.this, appBean.getDownloadURL());
                                                            }
                                                        });
                                                        builder.show();
                                                        PgyUpdateManager.unregister();
                                                    }
                                                } else {
                                                    PgyUpdateManager.unregister();
                                                }
                                            } else {
                                                //TODO 未许可
                                                getvDelegate().toastShort("权限未获取");
                                            }
                                        }
                                    });
                        }else {
                            Logger.i("activity has destory");
                        }

                    }

                    @Override
                    public void onNoUpdateAvailable() {
//                        MyToast.showToast(context, "当前已是最新版本", 1);
                        PgyUpdateManager.unregister();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handlerdelay.removeCallbacks(jumpRunnable);
    }

    @Override
    protected void onDestroy() {
        handlerdelay.removeCallbacks(jumpRunnable);
        super.onDestroy();
    }


}
