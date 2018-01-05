package com.yzf.Cpaypos.widget;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yzf.Cpaypos.R;
import com.yzf.Cpaypos.model.servicesmodels.LoginResult;

import java.util.Calendar;
import java.util.List;

import cn.droidlover.xdroidmvp.kit.Kits;
import razerdp.basepopup.BasePopupWindow;

/**
 * ClaseName：PrpfitPopup
 * Description：收益条件查询窗口
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/27 14:46
 * Modified By：
 * Fixtime：2017/3/27 14:46
 * FixDescription：
 */

public class PrpfitPopup extends BasePopupWindow implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    TextView beginTime;
    RelativeLayout profitpopBegintimeRl;
    TextView endTime;
    RelativeLayout profitpopEndtimeRl;
    StateButton profitpopSearchBt;

    private static IProfitPopCallBack iProfitPopCallBack;

    private List<LoginResult.DataBean.ServiceListBean> typelist;
    private List<LoginResult.DataBean.ServiceListBean> resultlist;
    private Activity activity;
    private FragmentManager fragmentManager;
    boolean dateStart = false;

    public interface IProfitPopCallBack {
        void query(String startTime, String endTime);
    }

    public static void setmPopupCallBack(IProfitPopCallBack iProfitPopCallBack) {
        PrpfitPopup.iProfitPopCallBack = iProfitPopCallBack;
    }

    public PrpfitPopup(Activity context, FragmentManager fragmentManager) {
        super(context);
        this.activity = context;
        this.fragmentManager = fragmentManager;
        beginTime = (TextView) findViewById(R.id.begin_time);
        endTime = (TextView) findViewById(R.id.end_time);

        profitpopBegintimeRl = (RelativeLayout) findViewById(R.id.profitpop_begintime_rl);
        profitpopEndtimeRl = (RelativeLayout) findViewById(R.id.profitpop_endtime_rl);
        profitpopSearchBt = (StateButton) findViewById(R.id.profitpop_search_bt);
        setViewClickListener(this, profitpopBegintimeRl, profitpopEndtimeRl, profitpopSearchBt);
        String end_time = Kits.Date.getYmd();
        String begin_time = Kits.Date.backDate(-7);
        beginTime.setText(begin_time);
        endTime.setText(end_time);
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation shakeAnima = new RotateAnimation(0, 15, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(2));
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
        return createPopupById(R.layout.profitpopup);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profitpop_begintime_rl:
                Calendar calendarStart = Calendar.getInstance();
                DatePickerDialog datePickerDialogStart = DatePickerDialog.newInstance(
                        PrpfitPopup.this,
                        calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialogStart.show(fragmentManager, "Datepickerdialog");
                dateStart = true;
                break;
            case R.id.profitpop_endtime_rl:
                Calendar calendarEnd = Calendar.getInstance();
                DatePickerDialog datePickerDialogEnd = DatePickerDialog.newInstance(
                        PrpfitPopup.this,
                        calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialogEnd.show(fragmentManager, "Datepickerdialog");
                dateStart = false;
                break;
            case R.id.profitpop_search_bt:
                iProfitPopCallBack.query(beginTime.getText().toString(), endTime.getText().toString());
                break;
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear = monthOfYear + 1;
        if (dateStart) {
            beginTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        } else {
            endTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }

    }

    public class Xdapter extends BaseAdapter {
        private Context context;
        private List<LoginResult.DataBean.ServiceListBean> mData;

        public Xdapter(Context context, List<LoginResult.DataBean.ServiceListBean> mData) {
            this.context = context;
            this.mData = mData;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        private void setData(List<LoginResult.DataBean.ServiceListBean> mData) {
            this.mData = mData;
            notifyDataSetChanged();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            PrpfitPopup.Xdapter.ViewHolder mHolder;
            if (null == convertView) {
                convertView = LayoutInflater.from(context).inflate(R.layout.dialog_list_item, null);
                mHolder = new PrpfitPopup.Xdapter.ViewHolder();
                mHolder.tx = (TextView) convertView.findViewById(R.id.dialoglist_item_tv);
                convertView.setTag(mHolder);
            } else {
                mHolder = (PrpfitPopup.Xdapter.ViewHolder) convertView.getTag();
            }
            mHolder.tx.setText(mData.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            private TextView tx;
        }

    }
}
