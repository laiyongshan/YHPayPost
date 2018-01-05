package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

/**
 * ClaseName：RegiestResult
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/11 0:34
 * Modified By：
 * Fixtime：2017/3/11 0:34
 * FixDescription：
 */

public class RegiestResult extends BaseModel {


    /**
     * respCode : 00
     * respMsg : 注册成功
     * data : {"merchKey":"4711A000D32D440107DA6535"}
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
         * merchKey : 4711A000D32D440107DA6535
         */

        private String merchKey;

        public String getMerchKey() {
            return merchKey;
        }

        public void setMerchKey(String merchKey) {
            this.merchKey = merchKey;
        }
    }
}
