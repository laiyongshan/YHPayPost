package com.yzf.Cpaypos.model.servicesmodels;

import com.yzf.Cpaypos.model.BaseModel;

/**
 * ClaseName：GetCodeResults
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/2/25 15:20
 * Modified By：
 * Fixtime：2017/2/25 15:20
 * FixDescription：
 */

public class FindPwdResults extends BaseModel {

    /**
     * respCode : 00
     * respMsg : 验证码发送成功
     * data : null
     */

    private String respCode;
    private String respMsg;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
