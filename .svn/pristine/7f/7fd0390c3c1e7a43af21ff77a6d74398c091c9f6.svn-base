package cn.droidlover.xdroidmvp.mvp;

import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface VDelegate {
    void resume();

    void pause();

    void destory();

    void visible(boolean flag, View view);
    void gone(boolean flag, View view);
    void inVisible(View view);

    void toastShort(String msg);
    void toastLong(String msg);

    void showSnackbar(View view,String msg,String tip,View.OnClickListener listener);

    void showNoticeDialog(String msg, MaterialDialog.SingleButtonCallback callback);
    void showErrorDialog(String msg);
    void showErrorDialog(String msg,MaterialDialog.SingleButtonCallback callback);

    void showLoading(String msg);
    void showLoading();
    void dismissLoading();

    void showError(NetError error);

    String getErrorType(NetError error);

}
