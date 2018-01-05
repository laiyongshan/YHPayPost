package com.yzf.Cpaypos.model.servicesmodels;

import com.google.gson.Gson;
import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.BaseModel;

import java.util.List;

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

public class GetRepaymentResults extends BaseModel {

    /**
     * respCode : 00
     * respMsg :
     * data : {"rows":[{"returnAmt":"0","returnDate":"20170903","transAmt":"-260"},{"returnAmt":"1000","returnDate":"20170903","transAmt":"1260"}]}
     * page : null
     */

    private String respCode;
    private String respMsg;
    private String data;
    private Object page;
    private DataBean dataBean;

    public DataBean getDataBean() {
        String data=getData();
        Gson gson = new Gson();
        DataBean dataBean=gson.fromJson(data, DataBean.class);
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
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

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }


    public static  class DataBean {

        private List<RowsBean> rows;

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * returnAmt : 2000
             * returnDate : 20170903
             * transAmt : 1800
             */

            private String returnAmt;
            private String returnDate;
            private String transAmt;

            public String getReturnAmt() {
                return AppTools.formatIntAmt(returnAmt);
            }

            public void setReturnAmt(String returnAmt) {
                this.returnAmt = returnAmt;
            }

            public String getReturnDate() {
                return returnDate;
            }

            public void setReturnDate(String returnDate) {
                this.returnDate = returnDate;
            }

            public String getTransAmt() {
                return AppTools.formatIntAmt(transAmt);
            }

            public void setTransAmt(String transAmt) {
                this.transAmt = transAmt;
            }
        }
    }







}
