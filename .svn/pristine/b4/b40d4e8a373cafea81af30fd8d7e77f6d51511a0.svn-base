package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

import java.util.List;

/**
 * ClaseName：GetProfitListResult
 * Description：获取收益明细列表
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/4/19 10:42
 * Modified By：
 * Fixtime：2017/4/19 10:42
 * FixDescription：
 */

public class GetProfitListResult extends BaseModel {

    /**
     * respCode : 00
     * respMsg : 获取成功
     * data : [{"orderId":"bc_test201704111640422877","amt":"10000","serviceName":"绑卡","serviceId":"23","transTime":"2017-04-11 16:42:03.0","incomeAmt":"3","yMerchId":"990120745840"}]
     */

    private String respCode;
    private String respMsg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderId : bc_test201704111640422877
         * amt : 10000
         * serviceName : 绑卡
         * serviceId : 23
         * transTime : 2017-04-11 16:42:03.0
         * incomeAmt : 3
         * yMerchId : 990120745840
         */

        private String orderId;
        private String amt;
        private String serviceName;
        private String serviceId;
        private String transTime;
        private String incomeAmt;
        private String yMerchId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAmt() {
            return amt;
        }

        public void setAmt(String amt) {
            this.amt = amt;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getTransTime() {
            return transTime;
        }

        public void setTransTime(String transTime) {
            this.transTime = transTime;
        }

        public String getIncomeAmt() {
            return incomeAmt;
        }

        public void setIncomeAmt(String incomeAmt) {
            this.incomeAmt = incomeAmt;
        }

        public String getYMerchId() {
            return yMerchId;
        }

        public void setYMerchId(String yMerchId) {
            this.yMerchId = yMerchId;
        }
    }
}
