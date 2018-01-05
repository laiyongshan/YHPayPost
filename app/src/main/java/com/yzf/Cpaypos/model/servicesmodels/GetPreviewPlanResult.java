package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

/**
 * ClaseName：GetPreviewPlanResult
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/11/30 11:15
 * Modified By：
 * Fixtime：2017/11/30 11:15
 * FixDescription：
 */

public class GetPreviewPlanResult extends BaseModel{


    /**
     * respCode : 00
     * respMsg : 查询成功
     * data : {"schedule":"0/2","cardBean":{"merchId":"15999519806","acctNo":"6225768771872395","cardType":"00","status":"00","add1":"451","add2":"2108","add3":"06","add4":"22","idCard":"452128199309192011","phoneNo":"15999519806","acctName":"韦俊星","cardInst":"03080000","cardDesc":"招商银行","cardLimit":"6000"},"times":1,"repayment_count":2,"cardDesc":"招商银行","repayment_amount":"1000.00","card_id":"6225768771872395","fee_amt":"34.00"}
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
        /**
         * schedule : 0/2
         * cardBean : {"merchId":"15999519806","acctNo":"6225768771872395","cardType":"00","status":"00","add1":"451","add2":"2108","add3":"06","add4":"22","idCard":"452128199309192011","phoneNo":"15999519806","acctName":"韦俊星","cardInst":"03080000","cardDesc":"招商银行","cardLimit":"6000"}
         * times : 1
         * repayment_count : 2
         * cardDesc : 招商银行
         * repayment_amount : 1000.00
         * card_id : 6225768771872395
         * fee_amt : 34.00
         */

        private String schedule;
        private CardBeanBean cardBean;
        private String times;
        private String repayment_count;
        private String cardDesc;
        private String repayment_amount;
        private String card_id;
        private String fee_amt;
        private String cardInst;

        public String getSchedule() {
            return schedule;
        }

        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public CardBeanBean getCardBean() {
            return cardBean;
        }

        public void setCardBean(CardBeanBean cardBean) {
            this.cardBean = cardBean;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public String getRepayment_count() {
            return repayment_count;
        }

        public void setRepayment_count(String repayment_count) {
            this.repayment_count = repayment_count;
        }

        public String getCardDesc() {
            return cardDesc;
        }

        public void setCardDesc(String cardDesc) {
            this.cardDesc = cardDesc;
        }

        public String getRepayment_amount() {
            return repayment_amount;
        }

        public void setRepayment_amount(String repayment_amount) {
            this.repayment_amount = repayment_amount;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getFee_amt() {
            return fee_amt;
        }

        public void setFee_amt(String fee_amt) {
            this.fee_amt = fee_amt;
        }

        public String getCardInst() {
            return cardInst;
        }

        public void setCardInst(String cardInst) {
            this.cardInst = cardInst;
        }

        public static class CardBeanBean{
            /**
             * merchId : 15999519806
             * acctNo : 6225768771872395
             * cardType : 00
             * status : 00
             * add1 : 451
             * add2 : 2108
             * add3 : 06
             * add4 : 22
             * idCard : 452128199309192011
             * phoneNo : 15999519806
             * acctName : 韦俊星
             * cardInst : 03080000
             * cardDesc : 招商银行
             * cardLimit : 6000
             */

            private String merchId;
            private String acctNo;
            private String cardType;
            private String status;
            private String add1;
            private String add2;
            private String add3;
            private String add4;
            private String idCard;
            private String phoneNo;
            private String acctName;
            private String cardInst;
            private String cardDesc;
            private String cardLimit;

            public String getMerchId() {
                return merchId;
            }

            public void setMerchId(String merchId) {
                this.merchId = merchId;
            }

            public String getAcctNo() {
                return acctNo;
            }

            public void setAcctNo(String acctNo) {
                this.acctNo = acctNo;
            }

            public String getCardType() {
                return cardType;
            }

            public void setCardType(String cardType) {
                this.cardType = cardType;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

            public String getAcctName() {
                return acctName;
            }

            public void setAcctName(String acctName) {
                this.acctName = acctName;
            }

            public String getCardInst() {
                return cardInst;
            }

            public void setCardInst(String cardInst) {
                this.cardInst = cardInst;
            }

            public String getCardDesc() {
                return cardDesc;
            }

            public void setCardDesc(String cardDesc) {
                this.cardDesc = cardDesc;
            }

            public String getCardLimit() {
                return cardLimit;
            }

            public void setCardLimit(String cardLimit) {
                this.cardLimit = cardLimit;
            }
        }
    }
}
