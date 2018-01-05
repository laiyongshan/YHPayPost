package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.BaseModel;

import java.util.List;


/**
 * 登录
 *
 * @author liujian
 */
public class LoginResult extends BaseModel {


    /**
     * respCode : 00
     * respMsg : 登录成功
     * data : {"serviceList":[{"id":"08","name":"vip购买"},{"id":"10","name":"订单结果查询"},{"id":"11","name":"微信固定二维码支付"},{"id":"13","name":"支付宝支付(反扫)"},{"id":"12","name":"微信支付(反扫)"},{"id":"04","name":"微信支付(正扫)"},{"id":"03","name":"境外快捷支付"},{"id":"02","name":"境内快捷支付"},{"id":"01","name":"代付"},{"id":"05","name":"支付宝支付(正扫)"},{"id":"06","name":"无卡支付"},{"id":"07","name":"四要素认证"},{"id":"09","name":"腾讯公众号支付"}],"merchStatus":"00","beginTime":"1","endTime":"1","key":"jVZbbJO44Gc0bMiSm3KhCKJeUIKoTqjoH6RwDYSgeGJfH/TtZFgYnQ==","minAmt":"1","maxAmt":"111"}
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
         * serviceList : [{"id":"08","name":"vip购买"},{"id":"10","name":"订单结果查询"},{"id":"11","name":"微信固定二维码支付"},{"id":"13","name":"支付宝支付(反扫)"},{"id":"12","name":"微信支付(反扫)"},{"id":"04","name":"微信支付(正扫)"},{"id":"03","name":"境外快捷支付"},{"id":"02","name":"境内快捷支付"},{"id":"01","name":"代付"},{"id":"05","name":"支付宝支付(正扫)"},{"id":"06","name":"无卡支付"},{"id":"07","name":"四要素认证"},{"id":"09","name":"腾讯公众号支付"}]
         * merchStatus : 00
         * beginTime : 1
         * endTime : 1
         * key : jVZbbJO44Gc0bMiSm3KhCKJeUIKoTqjoH6RwDYSgeGJfH/TtZFgYnQ==
         * minAmt : 1
         * maxAmt : 111
         */

        private String merchStatus;
        private String beginTime;
        private String endTime;
        private String key;
        private String minAmt;
        private String branchNo;
        private String maxAmt;
        private String planCardStatus;
        private String merchName;
        private List<ServiceListBean> serviceList;

        public String getMerchStatus() {
            return merchStatus;
        }

        public void setMerchStatus(String merchStatus) {
            this.merchStatus = merchStatus;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMinAmt() {
            return minAmt;
        }

        public void setMinAmt(String minAmt) {
            this.minAmt = minAmt;
        }

        public String getMaxAmt() {
            return maxAmt;
        }

        public void setMaxAmt(String maxAmt) {
            this.maxAmt = maxAmt;
        }

        public String getPlanCardStatus() {
            if(AppTools.isEmpty(planCardStatus))
            {
                planCardStatus="0";
            }
            return planCardStatus;
        }

        public void setPlanCardStatus(String planCardStatus) {
            this.planCardStatus = planCardStatus;
        }

        public List<ServiceListBean> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<ServiceListBean> serviceList) {
            this.serviceList = serviceList;
        }

        public String getBranchNo() {
            return branchNo;
        }

        public void setBranchNo(String branchNo) {
            this.branchNo = branchNo;
        }

        public String getMerchName() {
            return merchName;
        }

        public void setMerchName(String merchName) {
            this.merchName = merchName;
        }

        public static class ServiceListBean {
            /**
             * id : 08
             * name : vip购买
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }
    }
}
