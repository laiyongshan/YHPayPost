package com.yzf.Cpaypos.model;

/**
 * ClaseName：HomeSource
 * Description：
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/18 15:27
 * Modified By：
 * Fixtime：2017/3/18 15:27
 * FixDescription：
 */

public class HomeSource {
    private int id;
    private int backRes;
    private int imgRes;
    private String strRes;
    private String targetStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackRes() {
        return backRes;
    }

    public void setBackRes(int backRes) {
        this.backRes = backRes;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getStrRes() {
        return strRes;
    }

    public void setStrRes(String strRes) {
        this.strRes = strRes;
    }
    public String getTargetStr() {
        return targetStr;
    }

    public void setTargetStr(String targetStr) {
        this.targetStr = targetStr;
    }
}
