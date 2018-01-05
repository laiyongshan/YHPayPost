package com.yzf.Cpaypos.present;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.kit.AppUser;
import com.yzf.Cpaypos.model.servicesmodels.BaseResults;
import com.yzf.Cpaypos.model.servicesmodels.GetWhiteCardListResult;
import com.yzf.Cpaypos.net.Api;
import com.yzf.Cpaypos.ui.activitys.CgiBindCardActivity;

import java.math.BigDecimal;

import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.IBus;
import cn.droidlover.xdroidmvp.event.IEvent;
import cn.droidlover.xdroidmvp.kit.Kits;
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

public class PCgiBindCard extends XPresent<CgiBindCardActivity> {
    /**
     * 绑定银行卡
     *
     * @param dataBean
     */
    public void BindCard(GetWhiteCardListResult.DataBean dataBean) {
        if (AppTools.isEmpty(dataBean.getAcctNo())) {
            getV().showToast("银行卡号为空");
            return;
        }
        if (AppTools.isEmpty(dataBean.getAdd1())) {
            getV().showToast("CVN2为空");
            return;
        }
        if (AppTools.isEmpty(dataBean.getAdd2())) {
            getV().showToast("卡有效期为空");
            return;
        }
        if (AppTools.isEmpty(dataBean.getAdd3())) {
            getV().showToast("账单日为空");
            return;
        }
        if (AppTools.isEmpty(dataBean.getAdd4())) {
            getV().showToast("还款日为空");
            return;
        }
        BigDecimal add3 = new BigDecimal(dataBean.getAdd3());
        if (add3.compareTo(new BigDecimal(31)) > 0) {
            getV().showToast("账单日不能大于31号");
            return;
        }
        BigDecimal add4 = new BigDecimal(dataBean.getAdd4());
        if (add4.compareTo(new BigDecimal(31)) > 0) {
            getV().showToast("还款日不能大于31号");
            return;
        }
        if (AppTools.isEmpty(dataBean.getPhoneNo())) {
            getV().showToast("银行预留手机号为空");
            return;
        }
        if (!AppTools.isMobile(dataBean.getPhoneNo())) {
            getV().showToast("手机号码格式不正确");
            return;
        }
        /*if (AppTools.isEmpty(dataBean.getIdCard())) {
            getV().showToast("身份证号为空");
            return;
        }
        if (AppTools.isEmpty(dataBean.getAcctName())) {
            getV().showToast("姓名为空");
            return;
        }*/
        final String ORDER_ID = AppTools.craeateOrderId();
        String ORDER_AMT = "10.00";
        String ORDER_TIME = Kits.Date.getyyyyMMddHHmmss();
        String USER_TYPE = "02";
        String USER_ID = AppUser.getInstance().getUserId();
        String SIGN_TYPE = "03";
        String BUS_CODE = "1033";
        String CCT = "CNY";
        String CARD_NO = dataBean.getAcctNo();
        String CVN2 = dataBean.getAdd1();
        String EXPDATE = dataBean.getAdd2();
        String PHONE_NO = dataBean.getPhoneNo();
        String ID_NO = dataBean.getIdCard();
        String NAME = dataBean.getAcctName();
        String ADD3 = dataBean.getAdd3();
        String ADD4 = dataBean.getAdd4();
        /*String sign = acctNo + phoneNo;
        sign = AppTools.appEncrypt(sign);
        try {
            acctNo = Des3.encode(acctNo);//des3加密
            phoneNo = Des3.encode(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Api.getAPPService().CgiBindCard(ORDER_ID, ORDER_AMT, ORDER_TIME, USER_TYPE, USER_ID, SIGN_TYPE, BUS_CODE, CCT, CARD_NO, CVN2, EXPDATE, PHONE_NO, ID_NO, NAME, ADD3, ADD4)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {
                        if (baseResults.getRespCode().equals("00")) {
                            IBus.IEvent iEvent = new IEvent();
                            iEvent.setId("show_code");//交易成功后发消息去更新金额
                            iEvent.setObject(ORDER_ID);//交易成功后发消息去更新金额
                            BusProvider.getBus().post(iEvent);
                            getV().showToast("验证码已发送");
                            getV().startTimer();
                        } else {
                            getV().showToast(baseResults.getRespMsg());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }

    /**
     * CGI快捷绑卡确认
     *
     * @param ORDER_AMT
     */
    public void BindCardConfirm(String ORDER_AMT, String CARD_NO, String SMS_CODE, String ORIG_ORDER_ID) {
        if (AppTools.isEmpty(ORDER_AMT)) {
            getV().showToast("订单金额为空");
            return;
        }
        if (AppTools.isEmpty(CARD_NO)) {
            getV().showToast("银行卡号为空");
            return;
        }
        if (AppTools.isEmpty(SMS_CODE)) {
            getV().showToast("验证码为空");
            return;
        }
        if (AppTools.isEmpty(ORIG_ORDER_ID)) {
            getV().showToast("预留订单号为空");
            return;
        }
        ORDER_AMT = AppTools.formatAmt(ORDER_AMT);
        String ORDER_ID = AppTools.craeateOrderId();
        String ORDER_TIME = Kits.Date.getyyyyMMddHHmmss();
        String USER_TYPE = "02";
        String USER_ID = AppUser.getInstance().getUserId();
        String SIGN_TYPE = "03";
        String BUS_CODE = "1035";
        String CCT = "CNY";
        Api.getAPPService().ConfirmBindCard(ORDER_ID, ORDER_AMT, ORDER_TIME, USER_TYPE, USER_ID, SIGN_TYPE, BUS_CODE, CCT, CARD_NO, SMS_CODE, ORIG_ORDER_ID)
                .compose(XApi.<BaseResults>getApiTransformer())
                .compose(XApi.<BaseResults>getScheduler())
                .compose(getV().<BaseResults>bindToLifecycle())
                .subscribe(new ApiSubcriber<BaseResults>() {
                    @Override
                    public void onNext(BaseResults baseResults) {

                        if (baseResults.getRespCode().equals("00")) {
                            IBus.IEvent iEvent = new IEvent();
                            iEvent.setId("refresh_card");//交易成功后发消息去更新金额
                            BusProvider.getBus().post(iEvent);
                            getV().finishActivity(baseResults.getRespMsg());
                        } else {
                            getV().showToast(baseResults.getRespMsg());
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                });
    }
}
