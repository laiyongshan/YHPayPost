package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

import java.util.List;

/**
 * ClaseName：MerchFee
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/6 15:05
 * Modified By：
 * Fixtime：2017/3/6 15:05
 * FixDescription：
 */

public class GetMerchFeeResult extends BaseModel {

    /**
     * respCode : 00
     * respMsg : 成功
     * data : [{"serviceName":"代付","feeType":"按比例","feeValue":0.01,"merchId":"13189081178"},{"serviceName":"代付","feeType":"按笔","feeValue":0.02,"merchId":"13189081178"},{"serviceName":"境内快捷支付","feeType":"按笔","feeValue":0.02,"merchId":"13189081178"},{"serviceName":"境内快捷支付","feeType":"按比例","feeValue":0.02,"merchId":"13189081178"}]
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
         * serviceName : 代付
         * feeType : 按比例
         * feeValue : 0.01
         * merchId : 13189081178
         */

        private String serviceName;
        private String feeType;
        private String feeValue;
        private String merchId;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getFeeType() {
            return feeType;
        }

        public void setFeeType(String feeType) {
            this.feeType = feeType;
        }

        public String getFeeValue() {
            return feeValue;
        }

        public void setFeeValue(String feeValue) {
            this.feeValue = feeValue;
        }

        public String getMerchId() {
            return merchId;
        }

        public void setMerchId(String merchId) {
            this.merchId = merchId;
        }
    }
}
