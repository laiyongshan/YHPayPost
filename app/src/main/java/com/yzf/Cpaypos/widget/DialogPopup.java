package com.yzf.Cpaypos.widget;

import android.app.Activity;
import android.content.Context;
import android.app.FragmentManager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.adapter.XAdapter;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.droidlover.xdroidmvp.kit.Kits;
import razerdp.basepopup.BasePopupWindow;

/**
 * ClaseName：DialogPopup
 * Description：交易条件查询对话框
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/27 14:46
 * Modified By：
 * Fixtime：2017/3/27 14:46
 * FixDescription：
 */

public class DialogPopup extends BasePopupWindow implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    TextView beginTime;
    RelativeLayout popupBegintimeRl;
    TextView endTime;
    RelativeLayout popupEndtimeRl;
    AppCompatSpinner transTypeSpinner;
    RelativeLayout popupTranstypeRl;
    AppCompatSpinner transResultSpinner;
    RelativeLayout popupTransresultRl;
    StateButton popupSearchBt;

    private static IPopupCallBack iPopupCallBack;

    private List<LoginResult.DataBean.ServiceListBean> typelist;
    private List<LoginResult.DataBean.ServiceListBean> resultlist;
    private String selectType;
    private String selectResult;
    private FragmentManager fragmentManager;
    boolean dateStart = false;

    public interface IPopupCallBack {
        void query(String startTime, String endTime, String type, String result);
    }

    public static void setmPopupCallBack(IPopupCallBack iPopupCallBack) {
        DialogPopup.iPopupCallBack = iPopupCallBack;
    }

    public DialogPopup(Activity context, FragmentManager fragmentManager) {
        super(context);
        Activity activity = context;
        this.fragmentManager = fragmentManager;
        beginTime = (TextView) findViewById(R.id.begin_time);
        endTime = (TextView) findViewById(R.id.end_time);

        popupBegintimeRl = (RelativeLayout) findViewById(R.id.popup_begintime_rl);
        popupEndtimeRl = (RelativeLayout) findViewById(R.id.popup_endtime_rl);
        transTypeSpinner = (AppCompatSpinner) findViewById(R.id.trans_type_spinner);
        transResultSpinner = (AppCompatSpinner) findViewById(R.id.trans_result_spinner);
        popupSearchBt = (StateButton) findViewById(R.id.popup_search_bt);
        setViewClickListener(this, popupBegintimeRl, popupEndtimeRl, popupSearchBt);

        initResultDatas();
        initTypeDatas();
        String end_time = Kits.Date.getYmd();
        String begin_time = Kits.Date.backDate(-7);
        beginTime.setText(begin_time);
        endTime.setText(end_time);

//        transTypeSpinner.setPrompt("请选择交易类型");
        XAdapter typedapter = new XAdapter(context, typelist);
        transTypeSpinner.setAdapter(typedapter);
        transTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectType = typelist.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        transResultSpinner.setPrompt("请选择交易结果类型");
        XAdapter resultadapter = new XAdapter(context, resultlist);
        transResultSpinner.setAdapter(resultadapter);
        transResultSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectResult = resultlist.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation shakeAnima = new RotateAnimation(0, 4, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(1));
        shakeAnima.setDuration(200);
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
        return createPopupById(R.layout.dialogpopup);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_begintime_rl:
                Calendar calendarStart = Calendar.getInstance();
                DatePickerDialog datePickerDialogStart = DatePickerDialog.newInstance(
                        DialogPopup.this,
                        calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialogStart.show(fragmentManager, "Datepickerdialog");
                dateStart = true;
                break;
            case R.id.popup_endtime_rl:
                Calendar calendarEnd = Calendar.getInstance();
                DatePickerDialog datePickerDialogEnd = DatePickerDialog.newInstance(
                        DialogPopup.this,
                        calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialogEnd.show(fragmentManager, "Datepickerdialog");
                dateStart = false;
                break;
            case R.id.popup_search_bt:
                iPopupCallBack.query(beginTime.getText().toString(), endTime.getText().toString(), selectType, selectResult);
                break;
        }
    }

    /**
     * 初始化交易类型
     */
    private void initTypeDatas() {
        typelist = new ArrayList<>();
        LoginResult.DataBean.ServiceListBean serviceListBean0 = new LoginResult.DataBean.ServiceListBean();
        serviceListBean0.setId("");
        serviceListBean0.setName("全部");
        typelist.add(0, serviceListBean0);
        typelist.addAll(AppUser.getInstance().getsList());//从登陆返回的支持交易类型中获取数据
    }

    /**
     * 初始化交易结果
     */
    private void initResultDatas() {
        resultlist = new ArrayList<>();
        LoginResult.DataBean.ServiceListBean serviceListBean0 = new LoginResult.DataBean.ServiceListBean();
        serviceListBean0.setId("");
        serviceListBean0.setName("全部");
        LoginResult.DataBean.ServiceListBean serviceListBean1 = new LoginResult.DataBean.ServiceListBean();
        serviceListBean1.setId("01");
        serviceListBean1.setName("成功");
        LoginResult.DataBean.ServiceListBean serviceListBean2 = new LoginResult.DataBean.ServiceListBean();
        serviceListBean2.setId("02");
        serviceListBean2.setName("失败");
        LoginResult.DataBean.ServiceListBean serviceListBean3 = new LoginResult.DataBean.ServiceListBean();
        serviceListBean3.setId("00");
        serviceListBean3.setName("已接收");
        resultlist.add(serviceListBean0);
        resultlist.add(serviceListBean1);
        resultlist.add(serviceListBean2);
        resultlist.add(serviceListBean3);
    }

    /**
     * 选择日期结果
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear = monthOfYear + 1;
        if (dateStart) {
            beginTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        } else {
            endTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }

    }
}
