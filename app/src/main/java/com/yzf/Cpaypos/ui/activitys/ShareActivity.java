package com.yzf.Cpaypos.ui.activitys;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.kit.AppKit;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.kit.utils.fileUtill;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
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
 * ClassName：ShareActivity
 * Description: 分享界面
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/18 13:37
 * Modified By：
 * Fixtime：2017/4/18 13:37
 * FixDescription：
 */
public class ShareActivity extends XActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.share_tips_tv)
    TextView shareTipsTv;
    @BindView(R.id.share_qrcode_iv)
    ImageView shareQrcodeIv;
    @BindView(R.id.share_capture_ll)
    LinearLayout shareCaptureIl;
    private String rootPath;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        rootPath = context.getExternalFilesDir("") + File.separator + "Pictures/";

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
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
        String url = AppTools.getWxUrl(AppUser.getInstance().getUserId(), AppUser.getInstance().getBranchNo());
        final Bitmap mBitmap = CodeUtils.createImage(url, 500, 500, null);
        if (mBitmap != null) {
            shareQrcodeIv.setImageBitmap(mBitmap);
        }
        shareTipsTv.setText(String.format(getResources().getString(R.string.invite_you), AppUser.getInstance().getMerchName(), getResources().getString(R.string.app_name)));
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
        title.setText("分享");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_share;
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
            case R.id.action_share:
                saveBitmap(shareCaptureIl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 将view作为一个图片保存并分享
     *
     * @param view
     * @return
     */
    public void saveBitmap(final View view) {
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
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, by);
                            byte[] stream = by.toByteArray();
                            fileUtill.writeToSdcard(stream, rootPath, imageName);
                            File file = new File(rootPath, imageName);
                            Uri uri = Uri.fromFile(file);
                            AppKit.insertImage(context.getApplicationContext(), file);// 通知图库更新
                            AppKit.shareImage(context, uri, "com.tencent.mm");//调用原生分享，且只分享到微信

                        } else {
                            //TODO 未许可
                            showToast("权限未获取");
                        }
                    }
                });
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


}
