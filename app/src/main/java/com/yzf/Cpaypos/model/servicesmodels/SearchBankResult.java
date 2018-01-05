package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

import java.util.List;

/**
 * ClaseName：MainbankBean
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/14 15:01
 * Modified By：
 * Fixtime：2017/3/14 15:01
 * FixDescription：
 */

public class SearchBankResult extends BaseModel {

    /**
     * respCode : 00
     * respMsg : 成功
     * data : [{"bankNo":null,"bankName":null,"bankType":"01050000","bankTypeName":"中国建设银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"01020000","bankTypeName":"中国工商银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"01030000","bankTypeName":"中国农业银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"01040000","bankTypeName":"中国银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"02020000","bankTypeName":"中国进出口银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"02030000","bankTypeName":"中国农业发展银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"03030000","bankTypeName":"中国光大银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"03050000","bankTypeName":"中国民生银行","bankNames":null,"page":0,"pageNum":0},{"bankNo":null,"bankName":null,"bankType":"04030000","bankTypeName":"中国邮政储蓄银行","bankNames":null,"page":0,"pageNum":0}]
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
         * bankNo : null
         * bankName : null
         * bankType : 01050000
         * bankTypeName : 中国建设银行
         * bankNames : null
         * page : 0
         * pageNum : 0
         */

        private String bankNo;
        private String bankName;
        private String bankType;
        private String bankTypeName;
        private String bankNames;
        private int page;
        private int pageNum;

        public String getBankNo() {
            return bankNo;
        }

        public void setBankNo(String bankNo) {
            this.bankNo = bankNo;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            this.bankType = bankType;
        }

        public String getBankTypeName() {
            return bankTypeName;
        }

        public void setBankTypeName(String bankTypeName) {
            this.bankTypeName = bankTypeName;
        }

        public String getBankNames() {
            return bankNames;
        }

        public void setBankNames(String bankNames) {
            this.bankNames = bankNames;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }
    }
}
