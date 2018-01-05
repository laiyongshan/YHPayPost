package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.kit.AppTools;
import com.yzf.Cpaypos.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * ClaseName：GetWhiteCardListResult
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/6 21:05
 * Modified By：
 * Fixtime：2017/3/6 21:05
 * FixDescription：
 */

public class GetWhiteCardListResult extends BaseModel {

    /**
     * respCode : 00
     * respMsg : 成功
     * data : [{"merchId":"13189081178","acctNo":"6212263602055557755","cardType":null,"status":"00","add1":null,"add2":null,"add3":null,"add4":null,"idCard":null,"phoneNo":null,"acctName":null},{"merchId":"13189081178","acctNo":"qjfiqy5Qia8=","cardType":null,"status":"00","add1":null,"add2":null,"add3":null,"add4":null,"idCard":null,"phoneNo":null,"acctName":null},{"merchId":"13189081178","acctNo":"3","cardType":null,"status":"00","add1":null,"add2":null,"add3":null,"add4":null,"idCard":null,"phoneNo":null,"acctName":null},{"merchId":"13189081178","acctNo":"4","cardType":null,"status":"00","add1":null,"add2":null,"add3":null,"add4":null,"idCard":null,"phoneNo":null,"acctName":null},{"merchId":"13189081178","acctNo":"5","cardType":null,"status":"00","add1":null,"add2":null,"add3":null,"add4":null,"idCard":null,"phoneNo":null,"acctName":null},{"merchId":"13189081178","acctNo":"1","cardType":null,"status":"00","add1":null,"add2":null,"add3":null,"add4":null,"idCard":null,"phoneNo":null,"acctName":null}]
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

    public static class DataBean implements Serializable {
        /**
         * merchId : 13189081178
         * acctNo : 6212263602055557755
         * cardType : null
         * status : 00
         * add1 : null
         * add2 : null
         * add3 : null
         * add4 : null
         * idCard : null
         * phoneNo : null
         * acctName : null
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
            if(AppTools.isEmpty(cardDesc))
            {
                return "";
            }
            return cardDesc;
        }

        public void setCardDesc(String cardDesc) {
            this.cardDesc = cardDesc;
        }

        public String getCardLimit() {
            if(AppTools.isEmpty(cardLimit))
            {
                cardLimit="0";
            }
            return cardLimit;
        }

        public void setCardLimit(String cardLimit) {
            this.cardLimit = cardLimit;
        }
    }
}
