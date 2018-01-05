package com.yzf.Cpaypos.kit.db;


import cn.droidlover.xdroidmvp.log.Logger;

/**
 * ProjectName：ParamDao
 * Description：数据库操作封装
 * Author：JensenWei
 * CreateTime：2016/7/1  15:15
 * QQ：2188307188
 * FixTime：
 * FixContent：
 */
public class ParamDao {
    public final String MERCH_ID = "merchId";
    public final String PASSWORD = "password";
    public final String STATES = "states";
    public final String ORDERID = "orderId";
    public final String AMT = "amt";
    public final String SN = "sn";
    public final String BLUETOOTHMAC = "bluetoothMac";
    public final String SIGNTIME = "signTime";
    public final String APPID = "APPID";
    public final String BEGINTIME = "beginTime";
    public final String ENDTIME = "endTime";
    public final String KEY = "key";
    public final String MINAMT = "minAmt";
    public final String MAXAMT = "maxAmt";
    public final String SERVICELIST = "serviceList";
    public final String PAN = "pan";
    public final String AVL_AMT = "avl_amt";
    public final String BRANCHNO = "branchNo";
    public final String MERCHNAME = "merchName";
    public final String SY_AMT = "sy_amt";
    public final String FEE = "fee";
    public final String PLANCARDSTATUS = "planCardStatus";
    public final String CARDWHITEINFO = "cardWhiteInfo";
    public final String CARDINFO = "cardInfo";
    public final String SETTLEINFO = "settleInfo";

    /**
     * 插入或者更新数据
     *
     * @param paramName
     * @param paramValue
     */
    public void saveOrUpdate(String paramName, String paramValue) {
        try {
            Tparam tparam = new Tparam();
            tparam.setKey(paramName);
            tparam.setValue(paramValue);
            TparamDao tparamDao = DBManager.getInstance().getDaoSession().getTparamDao();
            tparamDao.insertOrReplace(tparam);
        } catch (Exception e) {
            Logger.e(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 检查数据是否存在
     *
     * @param paramName
     * @return
     */
    public boolean exist(String paramName) {
        try {
            TparamDao tparamDao = DBManager.getInstance().getDaoSession().getTparamDao();
            Tparam tparam = tparamDao.queryBuilder().where(TparamDao.Properties.Key.eq(paramName)).unique();
            if (tparam != null) {
                return true;
            }
        } catch (Exception e) {
            Logger.e(e.toString());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据名称获取数据
     *
     * @param paramName
     * @return
     */
    public String getByName(String paramName) {
        try {
            TparamDao tparamDao = DBManager.getInstance().getDaoSession().getTparamDao();
            Tparam tparam = tparamDao.queryBuilder().where(TparamDao.Properties.Key.eq(paramName)).unique();
            if (tparam != null) {
                return tparam.getValue();
            }
        } catch (Exception e) {
            Logger.e(e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化数据库
     */
    public void initDbBeforeLogin() {
        if (!exist(MERCH_ID)) {
            saveOrUpdate(MERCH_ID, "");
        }
        if (!exist(PASSWORD)) {
            saveOrUpdate(PASSWORD, "");
        }
        saveOrUpdate(STATES, "");
        saveOrUpdate(ORDERID, "");
        saveOrUpdate(AMT, "");
        saveOrUpdate(SN, "");
        saveOrUpdate(BLUETOOTHMAC, "");
        saveOrUpdate(SIGNTIME, "");
        saveOrUpdate(APPID, "");
        saveOrUpdate(BEGINTIME, "");
        saveOrUpdate(ENDTIME, "");
        saveOrUpdate(KEY, "");
        saveOrUpdate(MINAMT, "");
        saveOrUpdate(MAXAMT, "");
        saveOrUpdate(SERVICELIST, "");
        saveOrUpdate(PAN, "");
        saveOrUpdate(AVL_AMT, "");
        saveOrUpdate(BRANCHNO, "");
        saveOrUpdate(MERCHNAME, "");
        saveOrUpdate(SY_AMT, "");
        saveOrUpdate(PLANCARDSTATUS, "");
        saveOrUpdate(CARDWHITEINFO, "");
        saveOrUpdate(CARDINFO, "");
        saveOrUpdate(SETTLEINFO, "");
    }

    /**
     * 交易的时候重置交易数据信息
     */
    public void resetDbTradeInfo() {
        saveOrUpdate(ORDERID, "");
        saveOrUpdate(AMT, "");
        saveOrUpdate(SN, "");
        saveOrUpdate(BLUETOOTHMAC, "");
        saveOrUpdate(SIGNTIME, "");
        saveOrUpdate(APPID, "");
        saveOrUpdate(KEY, "");
        saveOrUpdate(PAN, "");
        saveOrUpdate(AVL_AMT, "");
        saveOrUpdate(BRANCHNO, "");
        saveOrUpdate(MERCHNAME, "");
        saveOrUpdate(SY_AMT, "");
        saveOrUpdate(FEE, "");
        saveOrUpdate(PLANCARDSTATUS, "");
        saveOrUpdate(CARDWHITEINFO, "");
        saveOrUpdate(CARDINFO, "");
        saveOrUpdate(SETTLEINFO, "");
    }


}
