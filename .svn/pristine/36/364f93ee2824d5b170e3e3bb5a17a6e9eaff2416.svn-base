package com.yzf.Cpaypos.ui.activitys;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzf.Cpaypos.kit.AppConfig;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppKit;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.fileUtill;
import com.yzf.Cpaypos.model.servicesmodels.GetOrderListResult;
import com.yzf.Cpaypos.present.PQrCode;
import com.yzf.Cpaypos.widget.StateButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
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
 * ClassName：QrCodeActivity
 * Description:二维码页面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/5/10 11:34
 * Modified By：
 * Fixtime：2017/5/10 11:34
 * FixDescription：
 */
public class QrCodeActivity extends XActivity<PQrCode> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.qrcode_name_tv)
    TextView qrcodeNameTv;
    @BindView(R.id.qrcode_amt_tv)
    TextView qrcodeAmtTv;
    @BindView(R.id.qrcode_code_iv)
    ImageView qrcodeCodeIv;
    @BindView(R.id.qrcode_type_tv)
    TextView qrcodeTypeTv;
    @BindView(R.id.qrcode_capture_ll)
    LinearLayout qrcodeCaptureLl;
    @BindView(R.id.qrcode_save_bt)
    StateButton qrcodeSaveBt;
    @BindView(R.id.qrcode_share_bt)
    StateButton qrcodeShareBt;
    private String serviceId;
    private String amt;
    private String titles;
    private String content;
    private String rootPath;
    private Timer timer;
    private Handler handlerdelay = new Handler();

    @Override
    public void initData(Bundle savedInstanceState) {
        rootPath = context.getExternalFilesDir("") + File.separator + "Pictures/";
        serviceId = getIntent().getStringExtra("serviceId");
        amt = getIntent().getStringExtra("amt");
        titles = getIntent().getStringExtra("titles");
        content = getIntent().getStringExtra("content");
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    public PQrCode newP() {
        return new PQrCode();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        initToolbar();
        if (AppTools.isEmpty(serviceId)) {//serviceId为空，表示的是邀请注册
            qrcodeAmtTv.setVisibility(View.GONE);
            qrcodeNameTv.setText(AppUser.getInstance().getMerchName() + "邀您加入" + getResources().getString(R.string.app_name));
            qrcodeTypeTv.setText("扫一扫即可下载应用");
            if (!AppTools.isEmpty(content)) {
                Bitmap mBitmap = CodeUtils.createImage(content, 400, 400, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
                if (null != mBitmap) {
                    qrcodeCodeIv.setImageBitmap(mBitmap);
                }
            } else {
                showToast("数据出错");
            }
        } else {//serviceId不为空，表示的是正扫
            qrcodeAmtTv.setVisibility(View.VISIBLE);
            qrcodeNameTv.setText(AppUser.getInstance().getMerchName() + "发起一笔收款");
            qrcodeAmtTv.setText("￥" + amt);
            if (serviceId.equals(AppConfig.WXZS)) {
                qrcodeTypeTv.setText("请使用微信完成支付");
            } else if (serviceId.equals(AppConfig.ZFBZS)) {
                qrcodeTypeTv.setText("请使用支付宝完成支付");
            } else {
                qrcodeTypeTv.setText("扫一扫完成支付");
            }
            if (!AppTools.isEmpty(content)) {
                Bitmap mBitmap = CodeUtils.createImage(content, 400, 400, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
                if (null != mBitmap) {//显示二维码后，定时查询该笔订单，四秒一次，持续一分半钟
                    qrcodeCodeIv.setImageBitmap(mBitmap);
                    timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            handlerdelay.post(qurreyRunnable);
                        }
                    };
                    timer.schedule(task, 5000, 4000);
                    handlerdelay.postDelayed(timeRunnable, 90000);
                }
            } else {
                showToast("数据出错");
            }
        }
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
        title.setText(titles);
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
            removeRunnable();
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
     * activity跳转
     */
    public void JumpActivity(Class<?> activity, boolean isfinish, GetOrderListResult.DataBean dataBean) {
        removeRunnable();
        Router.newIntent(context)
                .to(activity)
                .putSerializable("dataBean", dataBean)
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

    @OnClick({R.id.qrcode_save_bt, R.id.qrcode_share_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qrcode_save_bt:
                saveBitmap(qrcodeCaptureLl, 1);
                break;
            case R.id.qrcode_share_bt:
                saveBitmap(qrcodeCaptureLl, 2);
                break;
        }
    }

    /**
     * 登入方法
     *
     * @param activity
     * @param serviceId
     * @param amt
     * @param titles
     * @param content
     */
    public static void launch(Activity activity, String serviceId, String amt, String titles, String content) {
        Router.newIntent(activity)
                .to(QrCodeActivity.class)
                .putString("serviceId", serviceId)
                .putString("amt", amt)
                .putString("titles", titles)
                .putString("content", content)
                .launch();
    }

    /**
     * 将view作为一个图片保存
     *
     * @param view
     * @return
     */
    public void saveBitmap(final View view, final int type) {
        getRxPermissions()
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            //TODO 许可
                            final boolean drawingCacheEnabled = true;
                            view.setDrawingCacheEnabled(drawingCacheEnabled);
                            view.buildDrawingCache(drawingCacheEnabled);
                            final Bitmap drawingCache = view.getDrawingCache();
                            Bitmap bitmap;
                            if (drawingCache != null) {
                                bitmap = Bitmap.createBitmap(drawingCache);
                                view.setDrawingCacheEnabled(false);
                            } else {
                                bitmap = null;
                            }
                            // 以时间戳命名图片
                            String imageName = AppUser.getInstance().getUserId() + Kits.Random.getRandomCapitalLetters(6) + ".jpg";
                            ByteArrayOutputStream by = new ByteArrayOutputStream();
                            assert bitmap != null;
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, by);
                            byte[] stream = by.toByteArray();
                            fileUtill.writeToSdcard(stream, rootPath, imageName);
                            // 最后通知图库更新
                            File file = new File(rootPath, imageName);
                            Uri uri = Uri.fromFile(file);
                            AppKit.insertImage(context.getApplicationContext(), file);//图片插入相册
                            if (type == 1) {
                                showToast("保存成功");
                            }
                            if (type == 2) {
                                AppKit.shareImage(context, uri, "com.tencent.mm");
                            }
                        } else {
                            //TODO 未许可
                            showToast("权限未获取");
                        }
                    }
                });
    }


    private Runnable qurreyRunnable = new Runnable() {
        public void run() {
            getP().GetOrderList(1, 10, "", "", AppUser.getInstance().getUserId(), "", "", AppUser.getInstance().getOrderId(), "");
        }
    };
    private Runnable timeRunnable = new Runnable() {
        public void run() {
            timer.cancel();
        }
    };

    private void removeRunnable() {
        if (qurreyRunnable != null) {
            try {
                handlerdelay.removeCallbacks(qurreyRunnable);
                timer.cancel();
                handlerdelay.removeCallbacks(timeRunnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        removeRunnable();
        finish();
    }

    @Override
    public void onDestroy() {
        removeRunnable();
        super.onDestroy();
    }

}
