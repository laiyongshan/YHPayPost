package com.yzf.Cpaypos.present;

import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.AddRepaymentResults;
import com.yzf.Cpaypos.model.servicesmodels.GetRepaymentResults;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.PayBondActivity;
import com.yzf.Cpaypos.ui.activitys.RepaymentActivity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.log.Logger;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubcriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * ClaseName：PBindCard
 * Description：绑卡逻辑
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/1 14:31
 * Modified By：
 * Fixtime：2017/4/1 14:31
 * FixDescription：
 */

public class PRepayment extends XPresent<RepaymentActivity> {
    /**
     * 养卡规划随机生成
     *
     * @param amount
     * @param count
     * @param days
     */
    public void RandomPlanCard(String amount, final String count, final String days, final String times, int dateSize,String type,String scale,String balance) {
        if (AppTools.isEmpty(amount)) {
            getV().showToast("请输入金额");
            return;
        }
        BigDecimal money = new BigDecimal(amount);
        if (money.compareTo(new BigDecimal(1000)) < 0) {
            getV().showToast("最低金额为1000元");
            return;
        }
        if (money.compareTo(new BigDecimal(300000)) > 0) {
            getV().showToast("最大金额为300000元");
            return;
        }
        if (money.divideAndRemainder(new BigDecimal(100))[1].compareTo(new BigDecimal(0)) > 0) {
            getV().showToast("金额应为100的整数倍");
            return;
        }
        money = money.multiply(new BigDecimal(100));

        amount = String.valueOf(money);
        if(type.equals("00")){
            if (AppTools.isEmpty(count)) {
                getV().showToast("请输入笔数");
                return;
            }
            if (AppTools.isEmpty(days)) {
                getV().showToast("请选择还款日期");
                return;
            }
            RandomPlanCard(amount, count, days, times,type,null,null);
        }else if(type.equals("01"))
        {
            if (AppTools.isEmpty(scale)) {
                getV().showToast("请选择还款比例");
                return;
            }
            RandomPlanCard(amount, null, null, null,type,scale,null);

        }else if(type.equals("02"))
        {
            if (AppTools.isEmpty(balance)) {
                getV().showToast("请输入信用卡可用余额");
                return;
            }
            RandomPlanCard(amount, null, null, null,type,null,balance);

        }else {
            getV().showToast("无效还款模式");
            return;
        }



    }

    private void RandomPlanCard(String amount, String count, String days, String times,String type,String scale,String balance) {
        Api.getAPPService().RandomPlanCard(amount, count, days, times,type,scale,balance)
                .compose(XApi.<GetRepaymentResults>getApiTransformer())
                .compose(XApi.<GetRepaymentResults>getScheduler())
                .compose(getV().<GetRepaymentResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<GetRepaymentResults>() {
                    @Override
                    public void onNext(GetRepaymentResults getRepaymentResults) {
                        if (getRepaymentResults.getRespCode().equals("00")) {
                            if (AppTools.isEmpty(getRepaymentResults.getData())) {
                                getV().showToast("数据为空");
                            } else {
                                getV().setAdapter(getRepaymentResults);
                            }
                        } else {
                            getV().showToast(getRepaymentResults.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }


    /**
     * 新增养卡规划
     *
     * @param amount
     * @param count
     * @param merchId
     * @param cardId
     * @param detail
     */
    public void CreatePlanCard(String amount, final String count, final String merchId, final String cardId, final String detail, int dateSize) {
        if (AppTools.isEmpty(amount)) {
            getV().showToast("请输入金额");
            return;
        }
        if (AppTools.isEmpty(count)) {
            getV().showToast("请输入笔数");
            return;
        }
        if (AppTools.isEmpty(merchId)) {
            getV().showToast("商户号为空");
            return;
        }
        if (AppTools.isEmpty(cardId)) {
            getV().showToast("卡号为空");
            return;
        }
        if (AppTools.isEmpty(detail)) {
            getV().showToast("请生成规划后再提交");
            return;
        }
        BigDecimal money = new BigDecimal(amount);
        money = money.multiply(new BigDecimal(100));
        amount = String.valueOf(money);
        double dcount = Double.parseDouble(count);
        if (dcount > dateSize * 2) {
            final String finalAmount = amount;
            getV().showNoticeDialog("请注意该计划单日消费笔数可能超过银行交易风控笔数，是否确定提交？", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which == DialogAction.POSITIVE) {
                        CreatePlanCard(finalAmount, count, merchId, cardId, detail);
                    }
                }
            });
        } else {
            CreatePlanCard(amount, count, merchId, cardId, detail);
        }


    }

    private void CreatePlanCard(final String amount, String count, String merchId, final String cardId, String detail) {
        Api.getAPPService().CreatePlanCard(amount, count, merchId, cardId, detail)
                .compose(XApi.<AddRepaymentResults>getApiTransformer())
                .compose(XApi.<AddRepaymentResults>getScheduler())
                .compose(getV().<AddRepaymentResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<AddRepaymentResults>() {
                    @Override
                    public void onNext(AddRepaymentResults addRepaymentResults) {
                        if (addRepaymentResults.getRespCode().equals("00")) {
                            AddRepaymentResults.DataBean dataBean = addRepaymentResults.getDataBean();
                            dataBean.setTotalAmount(amount);
                            dataBean.setCardId(cardId);
                            IBus.IEvent iEvent = new IEvent();
                            iEvent.setId("refresh_plan");//交易成功后发消息去更新金额
                            BusProvider.getBus().post(iEvent);
                            getV().JumpActivity(PayBondActivity.class, true, dataBean);
                        } else {
                            getV().showToast(addRepaymentResults.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * 获取下个月的月份
     *
     * @param
     * @return
     */
    public String getNextMonth(String time) {
        String DAY_FORMAT = "yyyyMMdd";
        Calendar calendar = Calendar.getInstance();
        DateFormat fmt = new SimpleDateFormat(DAY_FORMAT);
        try {
            Date date = fmt.parse(time);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, 1);
            return getFormatDate(calendar.getTime(), DAY_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format   日期格式，如yyyy-MM-dd
     * @return String 返回格式化后的日期，格式由参数<code>format</code>
     * 定义，如yyyy-MM-dd，如2006-02-15
     * @see java.text.SimpleDateFormat#format(java.util.Date)
     */
    public static String getFormatDate(java.util.Date currDate, String format) {
        String DAY_FORMAT = "yyyyMMdd";
        if (currDate == null) {
            return "";
        }
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.format(currDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getBetweenDays(String endTime) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String nowtime = format.format(date);
        endTime = endTime + " 00:00:00";
        Date begindate = null;
        Date enddate = null;
        long betweendate = 1;
        try {
            begindate = format.parse(nowtime);
            enddate = format.parse(endTime);
            betweendate = (enddate.getTime() - begindate.getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return betweendate;
    }


}
