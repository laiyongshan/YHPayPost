package com.yzf.Cpaypos.model;

/**
 * ClaseName：CgiQuickPay
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/7 15:52
 * Modified By：
 * Fixtime：2017/9/7 15:52
 * FixDescription：
 */

public class CgiQuickPay {
    private String cardNo;
    private String CVN2;
    private String expDate;
    private String phoneNo;
    private String idNo;
    private String name;
    private String orderAmt;
    private String code;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCVN2() {
        return CVN2;
    }

    public void setCVN2(String CVN2) {
        this.CVN2 = CVN2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
