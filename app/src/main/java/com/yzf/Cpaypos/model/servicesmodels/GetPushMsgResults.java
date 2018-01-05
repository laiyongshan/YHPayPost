package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

import java.util.List;

/**
 * ClaseName：GetPushMsgResults
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/12/12 21:44
 * Modified By：
 * Fixtime：2017/12/12 21:44
 * FixDescription：
 */

public class GetPushMsgResults extends BaseModel {

    /**
     * respCode : 00
     * respMsg : 查询成功
     * data : {"msgList":[{"msg_type":"00","msg_content":"智能还款，让天下没有难管的信用卡","msg_url":null,"msg_topbranch":null,"add1":null,"add2":null,"add3":null,"add4":null},{"msg_type":"00","msg_content":"智能还款，让天下没有难管的信用卡1","msg_url":null,"msg_topbranch":null,"add1":null,"add2":null,"add3":null,"add4":null},{"msg_type":"00","msg_content":"智能还款，让天下没有难管的信用卡2","msg_url":null,"msg_topbranch":null,"add1":null,"add2":null,"add3":null,"add4":null}]}
     * page : null
     */

    private String respCode;
    private String respMsg;
    private DataBean data;
    private Object page;

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

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public static class DataBean {
        private List<MsgListBean> msgList;

        public List<MsgListBean> getMsgList() {
            return msgList;
        }

        public void setMsgList(List<MsgListBean> msgList) {
            this.msgList = msgList;
        }

        public static class MsgListBean {
            /**
             * msg_type : 00
             * msg_content : 智能还款，让天下没有难管的信用卡
             * msg_url : null
             * msg_topbranch : null
             * add1 : null
             * add2 : null
             * add3 : null
             * add4 : null
             */

            private String msg_type;
            private String msg_content;
            private String msg_url;
            private String msg_topbranch;
            private String add1;
            private String add2;
            private String add3;
            private String add4;

            public String getMsg_type() {
                return msg_type;
            }

            public void setMsg_type(String msg_type) {
                this.msg_type = msg_type;
            }

            public String getMsg_content() {
                return msg_content;
            }

            public void setMsg_content(String msg_content) {
                this.msg_content = msg_content;
            }

            public String getMsg_url() {
                return msg_url;
            }

            public void setMsg_url(String msg_url) {
                this.msg_url = msg_url;
            }

            public String getMsg_topbranch() {
                return msg_topbranch;
            }

            public void setMsg_topbranch(String msg_topbranch) {
                this.msg_topbranch = msg_topbranch;
            }

            public String getAdd1() {
                return add1;
            }

            public void setAdd1(String add1) {
                this.add1 = add1;
            }

            public String getAdd2() {
                return add2;
            }

            public void setAdd2(String add2) {
                this.add2 = add2;
            }

            public String getAdd3() {
                return add3;
            }

            public void setAdd3(String add3) {
                this.add3 = add3;
            }

            public String getAdd4() {
                return add4;
            }

            public void setAdd4(String add4) {
                this.add4 = add4;
            }
        }
    }
}
