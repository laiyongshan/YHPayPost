package com.yzf.Cpaypos.widget;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.yzf.Cpaypos.R;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

/**
 * ClaseName：DateDialogPopup
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/6 20:30
 * Modified By：
 * Fixtime：2017/9/6 20:30
 * FixDescription：
 */

public class TypeDialogPopup extends BasePopupWindow implements View.OnClickListener {
    @BindView(R.id.success_confirm_bt)
    Button successConfirmBt;
    private TagFlowLayout tagFlowLayout;
    private static IPopupCallBack iPopupCallBack;

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.success_confirm_bt)
    public void onViewClicked() {
    }

    public interface IPopupCallBack {
        void sentType(Set<Integer> selectPosSet);
    }

    public static void setmPopupCallBack(IPopupCallBack iPopupCallBack) {
        TypeDialogPopup.iPopupCallBack = iPopupCallBack;
    }

    public TypeDialogPopup(final Activity context) {
        super(context);
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation shakeAnima = new RotateAnimation(0, 4, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(1));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.typepopup);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }


}