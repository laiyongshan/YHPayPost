package com.yzf.Cpaypos.kit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import cn.droidlover.xdroidmvp.log.Logger;
import okhttp3.MediaType;
import okhttp3.Request;
import okio.Buffer;


/**
 * APP工具类
 */
public class AppKit {

    public static void copyToClipBoard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("xdroid_copy", text));
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }

    public static void openInBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "打开失败了，没有可打开的应用", Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareText(Context context, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    public static void shareImage(Context context, Uri uri, String packageName) {
        if (AppTools.isEmpty(packageName)) {//默认分享
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
        } else {//指定应用分享
            if (checkInstall(context, packageName)) {
                Intent shareIntent = new Intent();
                shareIntent.setPackage(packageName);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/*");
                context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
            } else {//指定应用找不到使用默认分享
//                Toast.makeText(context, "请先安装微信才能分享", Toast.LENGTH_SHORT).show();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/*");
                context.startActivity(Intent.createChooser(shareIntent, "分享图片"));
            }
        }



        /*Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("image*//*");
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(it, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("image*//*");
                ActivityInfo activityInfo = info.activityInfo;

                if (activityInfo.packageName.contains("com.tencent.mm") || activityInfo.name.contains("com.tencent.mobileqq")) {
                    targeted.putExtra(Intent.EXTRA_STREAM, uri);
                    targeted.setPackage(activityInfo.packageName);
                    targetedShareIntents.add(targeted);
                }

            }
            if (targetedShareIntents.size() > 0) {
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "分享图片");
                if (chooserIntent == null) {
                    return;
                }
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                try {
                    context.startActivity(chooserIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "找不到该分享应用组件", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "找不到该分享应用组件", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    public static boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }

    /**
     * 获取应用包名
     *
     * @param context
     * @return
     */
    public static String getpackageNames(Context context) {
        PackageManager pManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null ? packageInfo.packageName : null;
    }

    /**
     * 是否安装分享app
     *
     * @param packageName
     */
    public static boolean checkInstall(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
//            Toast.makeText(context, "请先安装应用app", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 实现数字串与数字串的相减
     *
     * @param a 数字串，即只包含数字的字符串
     * @param b 数字串，即只包含数字的字符串
     */
    public static Double subString(String a, String b) {
        Double tempc;
        BigDecimal bg = new BigDecimal("0.00");
        try {
            Double tempa = Double.parseDouble(a);
            Double tempb = Double.parseDouble(b);
            tempc = tempa - tempb;
            bg = new BigDecimal(tempc).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) { // str为非数字串时的异常处理
            System.out.println(e.getMessage());
        }
        return bg.doubleValue();
    }

    /**
     * 实现数字串与数字串的相加
     *
     * @param a 数字串，即只包含数字的字符串
     * @param b 数字串，即只包含数字的字符串
     */
    public static String addString(String a, String b) {
        Double tempc;
        BigDecimal bg = new BigDecimal("0.00");
        try {
            Double tempa = Double.parseDouble(a);
            Double tempb = Double.parseDouble(b);
            tempc = tempa + tempb;
            bg = new BigDecimal(tempc).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) { // str为非数字串时的异常处理
            System.out.println(e.getMessage());
        }
        return String.valueOf(bg);
    }

    /**
     * 图片插入MediaProvider,通知图库更新
     *
     * @param context context
     * @param file    文件
     */
    public static void insertImage(final Context context, final File file) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

        MediaScannerConnection.scanFile(context,
                new String[]{file.getAbsolutePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Logger.d("file " + path + " was scanned seccessfully: " + uri);
                    }
                });

        ContentValues localContentValues = new ContentValues();
        localContentValues.put("_data", file.toString());
        localContentValues.put("description", "save image ---");
        localContentValues.put("mime_type", "image/jpeg");
        ContentResolver localContentResolver = context.getContentResolver();
        Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        localContentResolver.insert(localUri, localContentValues);

    }


}
