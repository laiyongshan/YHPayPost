package com.yzf.Cpaypos.widget;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.xw.repo.xedittext.XEditText;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.CgiQuickPay;

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

public class QuickPayDialogPopup extends BasePopupWindow{

    private XEditText cardno,cvn2,expdate,phoneno,idno,name;
    private TextView ok,cancle;
    private static QuickPayDialogPopup.IPopupCallBack iPopupCallBack;

    public interface IPopupCallBack {
        void OnOk(CgiQuickPay cgiQuickPay);
        void OnCancle();
    }
    public static void setPopupCallBack(QuickPayDialogPopup.IPopupCallBack iPopupCallBack) {
        QuickPayDialogPopup.iPopupCallBack = iPopupCallBack;
    }

    public QuickPayDialogPopup(final Activity context) {
        super(context);
        cardno= (XEditText) findViewById(R.id.cgiquickpay_cardno_et);
        cvn2= (XEditText) findViewById(R.id.cgiquickpay_cvn2_et);
        expdate= (XEditText) findViewById(R.id.cgiquickpay_expdate_et);
        phoneno= (XEditText) findViewById(R.id.cgiquickpay_phoneno_et);
        idno= (XEditText) findViewById(R.id.cgiquickpay_idno_et);
        name= (XEditText) findViewById(R.id.cgiquickpay_name_et);
        ok= (TextView) findViewById(R.id.cgiquickpay_ok_tv);
        cancle= (TextView) findViewById(R.id.cgiquickpay_cancle_tv);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CgiQuickPay cgiQuickPay=new CgiQuickPay();
                cgiQuickPay.setCardNo(cardno.getNonSeparatorText());
                cgiQuickPay.setPhoneNo(phoneno.getNonSeparatorText());
                cgiQuickPay.setCVN2(cvn2.getNonSeparatorText());
                cgiQuickPay.setExpDate(expdate.getNonSeparatorText());
                cgiQuickPay.setIdNo(idno.getNonSeparatorText());
                cgiQuickPay.setName(name.getNonSeparatorText());
                iPopupCallBack.OnOk(cgiQuickPay);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iPopupCallBack.OnCancle();
            }
        });
    }

    @Override
    protected Animation initShowAnimation() {


        AnimationSet set=new AnimationSet(false);
        Animation shakeAnima=new RotateAnimation(0,4,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
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
        return createPopupById(R.layout.quickpaypopup);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

}