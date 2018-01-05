package com.yzf.Cpaypos.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yzf.Cpaypos.R;


/**
 * ProjectName：mall
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2016/8/31 15:41
 * Modified By：
 * Fixtime：2016/8/31 15:41
 * FixDescription：
 */
public class QRcodeDialog extends Dialog {

    private Context context;
    private String content;
    private String tip;

    public QRcodeDialog(Context context, String content, String tip) {
        super(context, R.style.custom_dialog);
        this.context = context;
        this.content = content;
        this.tip = tip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        // 点击对话框区域外禁止取消对话框
        setCanceledOnTouchOutside(true);
        initDialog();
    }

    private void initDialog() {
        ImageView imageView = (ImageView) findViewById(R.id.iv_qrcode);
        TextView textView = (TextView) findViewById(R.id.tv_tip);
        textView.setText(tip);
        String textContent = content;
        final Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        if (mBitmap != null) {
            imageView.setImageBitmap(mBitmap);
        }
       /* PhotoProcess.savePhotoToLocal(mBitmap, AppConfig.REGIEST_IMG, "qrcode.png");
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CodeUtils.analyzeBitmap(AppConfig.REGIEST_IMG + "qrcode.png", new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(context, "解析二维码失败", Toast.LENGTH_SHORT);
                    }
                });
                return false;
            }
        });*/
    }


}