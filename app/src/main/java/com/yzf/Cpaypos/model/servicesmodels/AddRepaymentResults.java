package com.yzf.Cpaypos.model.servicesmodels;

import com.google.gson.Gson;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.BaseModel;

import java.io.Serializable;

/**
 * ClaseName：GetRepaymentResults
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/9/2 18:25
 * Modified By：
 * Fixtime：2017/9/2 18:25
 * FixDescription：
 */

public class AddRepaymentResults extends BaseModel {


    /**
     * respCode : 00
     * respMsg :
     * data : {"alvbAmount":"521","depositAmount":"4000","feeAmount":"0","orderId":"20170904110839000000004201","respCode":"0000","respDesc":"新增规划成功","status":"00"}
     * page :
     */

    private String respCode;
    private String respMsg;
    private String data;
    private String page;
    private AddRepaymentResults.DataBean dataBean;

    public AddRepaymentResults.DataBean getDataBean() {
        String data=getData();
        Gson gson = new Gson();
        AddRepaymentResults.DataBean dataBean=gson.fromJson(data, AddRepaymentResults.DataBean.class);
        return dataBean;
    }

    public void setDataBean(AddRepaymentResults.DataBean dataBean) {
        this.dataBean = dataBean;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public static class DataBean implements Serializable {
        /**
         * alvbAmount : 521
         * depositAmount : 4000
         * feeAmount : 0
         * orderId : 20170904110839000000004201
         * respCode : 0000
         * respDesc : 新增规划成功
         * status : 00
         */

        private String totalAmount;
        private String cardId;
        private String alvbAmount;
        private String depositAmount;
        private String feeAmount;
        private String orderId;
        private String respCode;
        private String respDesc;
        private String status;
        private String times;
        private String count;
        private String detailDays;

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getTotalAmount() {
            return AppTools.formatIntAmt(totalAmount);
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getAlvbAmount() {
            return AppTools.formatIntAmt(alvbAmount);
        }

        public void setAlvbAmount(String alvbAmount) {
            this.alvbAmount = alvbAmount;
        }

        public String getDepositAmount() {
            return AppTools.formatIntAmt(depositAmount);
        }

        public void setDepositAmount(String depositAmount) {
            this.depositAmount = depositAmount;
        }

        public String getFeeAmount() {
            return AppTools.formatIntAmt(feeAmount);
        }

        public void setFeeAmount(String feeAmount) {
            this.feeAmount = feeAmount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getRespCode() {
            return respCode;
        }

        public void setRespCode(String respCode) {
            this.respCode = respCode;
        }

        public String getRespDesc() {
            return respDesc;
        }

        public void setRespDesc(String respDesc) {
            this.respDesc = respDesc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDetailDays() {
            return detailDays;
        }

        public void setDetailDays(String detailDays) {
            this.detailDays = detailDays;
        }
    }
}
