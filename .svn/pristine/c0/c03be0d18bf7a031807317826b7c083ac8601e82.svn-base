package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 交易管理info
 *
 * @author liujian
 */
public class GetOrderListResult extends BaseModel {


    /**
     * respCode : 00
     * respMsg : 成功
     * data : [{"merchId":"15999519806","orderId":"Android201705050900001478","amt":1,"acctNo":"70011599951980702","serviceId":"05","result":"小商户编码异常，下单失败","resultCode":"02","goodsName":null,"transTime":"2017-05-05 16:59:45","fee":0,"serviceName":"支付宝二维码支付"},{"merchId":"15999519806","orderId":"Android201705050859460049","amt":1,"acctNo":"70011599951980702","serviceId":"04","result":"最低交易费率不能低于0.30","resultCode":"02","goodsName":null,"transTime":"2017-05-05 16:59:31","fee":0,"serviceName":"微信二维码支付"},{"merchId":"15999519806","orderId":"Android201705050859415706","amt":1,"acctNo":"70011599951980702","serviceId":"03","result":"未支付","resultCode":"00","goodsName":null,"transTime":"2017-05-05 16:59:27","fee":0,"serviceName":"境外快捷支付"},{"merchId":"15999519806","orderId":"Android201705050859304114","amt":1,"acctNo":"70011599951980702","serviceId":"02","result":"未支付","resultCode":"00","goodsName":null,"transTime":"2017-05-05 16:59:17","fee":0,"serviceName":"境内快捷支付"},{"merchId":"15999519806","orderId":"Android201705050852154453","amt":1,"acctNo":"70011599951980702","serviceId":"04","result":"最低交易费率不能低于0.30","resultCode":"02","goodsName":null,"transTime":"2017-05-05 16:52:01","fee":0,"serviceName":"微信二维码支付"},{"merchId":"15999519806","orderId":"Android201705050819512734","amt":1,"acctNo":"70011599951980702","serviceId":"04","result":"最低交易费率不能低于0.30","resultCode":"02","goodsName":null,"transTime":"2017-05-05 16:19:36","fee":0,"serviceName":"微信二维码支付"},{"merchId":"15999519806","orderId":"Android201705051550579814","amt":0.1,"acctNo":"70011599951980702","serviceId":"04","result":"最低交易费率不能低于0.30","resultCode":"02","goodsName":null,"transTime":"2017-05-05 15:50:40","fee":0,"serviceName":"微信二维码支付"},{"merchId":"15999519806","orderId":"Android201705051550038149","amt":0.1,"acctNo":"70011599951980702","serviceId":"05","result":"小商户编码异常，下单失败","resultCode":"02","goodsName":null,"transTime":"2017-05-05 15:49:46","fee":0,"serviceName":"支付宝二维码支付"},{"merchId":"15999519806","orderId":"Android201705051549497161","amt":1,"acctNo":"70011599951980702","serviceId":"04","result":"最低交易费率不能低于0.30","resultCode":"02","goodsName":null,"transTime":"2017-05-05 15:49:32","fee":0,"serviceName":"微信二维码支付"},{"merchId":"15999519806","orderId":"Android201705041451034232","amt":1,"acctNo":"70011599951980702","serviceId":"04","result":"交易关闭","resultCode":"02","goodsName":null,"transTime":"2017-05-04 22:52:22","fee":0,"serviceName":"微信二维码支付"}]
     * page : {"page":1,"pageNum":10,"pageSize":313}
     */

    private String respCode;
    private String respMsg;
    private PageBean page;
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

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * page : 1
         * pageNum : 10
         * pageSize : 313
         */

        private String page;
        private String pageNum;
        private String pageSize;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class DataBean implements Serializable {
        /**
         * merchId : 15999519806
         * orderId : Android201705050900001478
         * amt : 1
         * acctNo : 70011599951980702
         * serviceId : 05
         * result : 小商户编码异常，下单失败
         * resultCode : 02
         * goodsName : null
         * transTime : 2017-05-05 16:59:45
         * fee : 0
         * serviceName : 支付宝二维码支付
         */

        private String merchId;
        private String orderId;
        private String amt;
        private String acctNo;
        private String serviceId;
        private String result;
        private String resultCode;
        private Object goodsName;
        private String transTime;
        private String fee;
        private String serviceName;

        public String getMerchId() {
            return merchId;
        }

        public void setMerchId(String merchId) {
            this.merchId = merchId;
        }

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

        public String getAcctNo() {
            return acctNo;
        }

        public void setAcctNo(String acctNo) {
            this.acctNo = acctNo;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public Object getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(Object goodsName) {
            this.goodsName = goodsName;
        }

        public String getTransTime() {
            return transTime;
        }

        public void setTransTime(String transTime) {
            this.transTime = transTime;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }
}
