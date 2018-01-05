package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

import java.io.Serializable;

/**
 * 虚拟账户信息
 *
 * @author liujian
 */
public class GetAccountInfoResult extends BaseModel {


    /**
     * resp_code : 00
     * resp_msg : 成功
     * data : {"acctId":"70011599951980602","acctType":"01","acctIssuer":"00000000","avlbBal":"0","fznAmt":"0","effDate":null,"expirDate":"20991231","updateTime":null,"insertTime":null,"merchId":null,"acctStatus":"10"}
     */

    private String respCode;
    private String respMsg;
    private DataBean data;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * acctId : 70011599951980602
         * acctType : 01
         * acctIssuer : 00000000
         * avlbBal : 0
         * fznAmt : 0
         * effDate : null
         * expirDate : 20991231
         * updateTime : null
         * insertTime : null
         * merchId : null
         * acctStatus : 10
         */

        private String acctId;
        private String acctType;
        private String acctIssuer;
        private String avlbBal;
        private String fznAmt;
        private String effDate;
        private String expirDate;
        private String updateTime;
        private String insertTime;
        private String merchId;
        private String acctStatus;
        private String acctStatusCn;
        private String syAmt;//收益余额
        private String sumAmt;
        private String fee;
        private String feeType;
        private String syfee;
        private String syfeeType;

        public String getAcctId() {
            return acctId;
        }

        public void setAcctId(String acctId) {
            this.acctId = acctId;
        }

        public String getAcctType() {
            return acctType;
        }

        public void setAcctType(String acctType) {
            this.acctType = acctType;
        }

        public String getAcctIssuer() {
            return acctIssuer;
        }

        public void setAcctIssuer(String acctIssuer) {
            this.acctIssuer = acctIssuer;
        }

        public String getAvlbBal() {
            return avlbBal;
        }

        public void setAvlbBal(String avlbBal) {
            this.avlbBal = avlbBal;
        }

        public String getFznAmt() {
            return fznAmt;
        }

        public void setFznAmt(String fznAmt) {
            this.fznAmt = fznAmt;
        }

        public Object getEffDate() {
            return effDate;
        }

        public void setEffDate(String effDate) {
            this.effDate = effDate;
        }

        public String getExpirDate() {
            return expirDate;
        }

        public void setExpirDate(String expirDate) {
            this.expirDate = expirDate;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getInsertTime() {
            return insertTime;
        }

        public void setInsertTime(String insertTime) {
            this.insertTime = insertTime;
        }

        public Object getMerchId() {
            return merchId;
        }

        public void setMerchId(String merchId) {
            this.merchId = merchId;
        }

        public String getAcctStatus() {
            return acctStatus;
        }

        public void setAcctStatus(String acctStatus) {
            this.acctStatus = acctStatus;
        }

        public String getAcctStatusCn() {
            return acctStatusCn;
        }

        public void setAcctStatusCn(String acctStatusCn) {
            this.acctStatusCn = acctStatusCn;
        }

        public String getSyAmt() {
            return syAmt;
        }

        public void setSyAmt(String syAmt) {
            this.syAmt = syAmt;
        }

        public String getSumAmt() {
            return sumAmt;
        }

        public void setSumAmt(String sumAmt) {
            this.sumAmt = sumAmt;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getFeeType() {
            return feeType;
        }

        public void setFeeType(String feeType) {
            this.feeType = feeType;
        }

        public String getSyfee() {
            return syfee;
        }

        public void setSyfee(String syfee) {
            this.syfee = syfee;
        }

        public String getSyfeeType() {
            return syfeeType;
        }

        public void setSyfeeType(String syfeeType) {
            this.syfeeType = syfeeType;
        }
    }
}
