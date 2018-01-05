package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

/**
 * ClaseName：GetProfitResult
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/18 16:03
 * Modified By：
 * Fixtime：2017/4/18 16:03
 * FixDescription：
 */

public class GetProfitResult extends BaseModel {

    /**
     * respCode : 00
     * respMsg : 获取成功
     * data : {"sonMerchNum":3,"frozenAmt":0,"profitAmt":0}
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

    public static class DataBean {
        /**
         * sonMerchNum : 3
         * frozenAmt : 0
         * profitAmt : 0
         */

        private String sonMerchNum;
        private String frozenAmt;//收益冻结金额
        private String profitAmt;//收益可用金额
        private String merchLevel;
        private String sumSyAmt;//收益总金额
        private String yesterdayAmt;//昨日收益金额
        private String qrcodeUrl;
        private String tips;

        public String getSonMerchNum() {
            return sonMerchNum;
        }

        public void setSonMerchNum(String sonMerchNum) {
            this.sonMerchNum = sonMerchNum;
        }

        public String getFrozenAmt() {
            return frozenAmt;
        }

        public void setFrozenAmt(String frozenAmt) {
            this.frozenAmt = frozenAmt;
        }

        public String getProfitAmt() {
            return profitAmt;
        }

        public void setProfitAmt(String profitAmt) {
            this.profitAmt = profitAmt;
        }

        public String getMerchLevel() {
            return merchLevel;
        }

        public void setMerchLevel(String merchLevel) {
            this.merchLevel = merchLevel;
        }

        public String getSumSyAmt() {
            return sumSyAmt;
        }

        public void setSumSyAmt(String sumSyAmt) {
            this.sumSyAmt = sumSyAmt;
        }

        public String getYesterdayAmt() {
            return yesterdayAmt;
        }

        public void setYesterdayAmt(String yesterdayAmt) {
            this.yesterdayAmt = yesterdayAmt;
        }

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
}
