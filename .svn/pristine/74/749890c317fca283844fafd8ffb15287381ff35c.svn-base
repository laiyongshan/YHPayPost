package com.yzf.Cpaypos.kit;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.droidlover.xdroidmvp.kit.Codec;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.log.Logger;

/**
 * ClaseName：AppTools
 * Description：APP工具方法
 * Author：JensenWei
 * QQ: 2188307188
 * Createtime：2017/3/17 17:23
 * Modified By：
 * Fixtime：2017/3/17 17:23
 * FixDescription：
 */

public class AppTools {

    /**
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTextReturnStr(Context context, String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName+".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取字符串中连续字符串的最大个数
     * @param s
     * @return
     */
    public static int getContinuousCount(String s) {
        String b[] = s.split("\\.");
        int maxSucc = 1;
        int temp = 1;
        if (b[0].length() >= 3) {
            char array[] = b[0].toCharArray();
            for (int i = 1; i < array.length; i++) {
                if (array[i] == array[i - 1]) {
                    temp++;
                } else {
                    if (temp > maxSucc) {
                        maxSucc = temp;
                    }
                    temp = 1;
                }
            }
            maxSucc = maxSucc > temp ? maxSucc : temp;
        }
        return maxSucc;
    }

    /**
     * 将以分为单位的金额转为元
     * @param s
     * @return
     */
    public static String formatIntAmt(String s)
    {
        if (!AppTools.isEmpty(s)) {
            double temp = Double.parseDouble(s)/100;
            String amt=String.valueOf(temp);
            amt=formatAmt(amt);
            return amt;
        }
        return s;
    }

    /**
     * 将以元为单位的金额转为分
     * @param s
     * @return
     */
    public static String formatFenAmt(String s)
    {
        if (!AppTools.isEmpty(s)) {
            double temp = Double.parseDouble(s)*100;
            String amt=String.valueOf(temp);
            amt=formatAmt(amt);
            return amt;
        }
        return s;
    }


    /**
     * 获取微信分享url
     *
     * @param upMerchId
     * @param branchId
     * @return
     */
    public static String getWxUrl(String upMerchId, String branchId) {
        String url = AppConfig.WX_URL + "upMerchId=" + upMerchId + "&branchId=" + branchId;
        return url;
    }

    /**
     * 判断是否为邮箱
     *
     * @param strInput
     * @return
     */

    public static boolean isEmail(String strInput) {
        if (strInput != null && !strInput.trim().equalsIgnoreCase("")) {
            String Regular = "^([a-z0-9A-Z]+[-|\\_.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern pattern = Pattern.compile(Regular, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(strInput);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /**
     * 功能：判断是不是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        return mobile.length() == 11 && Pattern.matches("^(1[3|4|5|7|8])\\d{9}$", mobile);
    }

    /**
     * 如果字符串为null,或空串，或全为空格，返回true;否则返回false
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return !((str != null) && (str.trim().length() > 0));
    }

    /**
     * APP加密算法
     *
     * @param centent
     * @return
     */
    public static String appEncrypt(String centent) {
        String sign = centent;
        Logger.i("sign:" + sign);
        sign = Codec.MD5.getMD5(sign, 1).toUpperCase();
        Logger.i("sign1:" + sign);
            sign = Codec.MD5.getMD5((sign + AppUser.getInstance().getKey()), 0).toUpperCase();
            Logger.i("key:" + AppUser.getInstance().getKey());
        Logger.i("sign2:" + sign);
        return sign;
    }

    /**
     * 以当前时间数+四位随机数作为订单号
     *
     * @return
     */
    public static String craeateOrderId() {
        String temp;
        String time = Kits.Date.getyyyyMMddHHmmss();
        temp = "Android" + time + Kits.Random.getRandomNumbers(4);
        return temp;
    }

    /**
     * 将金额补足小数点(加上最后两位00) 15->15.00
     *
     * @param amt
     * @return
     */
    public static String formatAmt(String amt) {
        return new DecimalFormat("0.00").format(Double.parseDouble(amt));
    }

    /**
     * string类型转换为long类型
     *
     * @param strTime    要转换的String类型的时间
     * @param formatType 时间格式 strTime的时间格式和formatType的时间格式必须相同
     * @return
     * @throws ParseException
     */
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * string类型转换为long类型
     *
     * @param strTime    要转换的String类型的时间
     * @param formatType 时间格式 strTime的时间格式和formatType的时间格式必须相同
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * date类型转换为long类型
     *
     * @param date 要转换的date类型的时间
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 获取星期
     *
     * @param time       时间字符串    time的时间格式必须要与formatType的时间格式相同
     * @param formatType 日期格式 yyyy-MM-dd HH:mm:ss
     * @return 0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
     */
    public static String getWeek(String time, String formatType) {
        try {
            long mills;
            mills = stringToLong(time, formatType);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mills);
            int temp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            switch (temp) {
                case 0:
                    time = "周日";
                    break;
                case 1:
                    time = "周一";
                    break;
                case 2:
                    time = "周二";
                    break;
                case 3:
                    time = "周三";
                    break;
                case 4:
                    time = "周四";
                    break;
                case 5:
                    time = "周五";
                    break;
                case 6:
                    time = "周六";
                    break;
                default:
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
