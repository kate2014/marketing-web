package com.zhongmei.yunfu.util;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha1Hash;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ToolsUtil {

    /**
     * 生成随机4位数数
     *
     * @param n 表示生成数据位数
     * @return
     */
    public static String getCard(int n) {
        Random rand = new Random();//生成随机数
        String cardNnumer = "";
        for (int a = 0; a < n; a++) {
            cardNnumer += rand.nextInt(10);//生成6位数字
        }
        return cardNnumer;
    }

    /**
     * 获取区间内的随机数
     *
     * @param n
     * @param m
     * @return
     */
    public static double getRandomData(double n, double m) {
        double a = Math.random() * (m - n) + n;
        double value = new BigDecimal(a).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

    /**
     * 获取数据最近整数
     *
     * @return
     */
    public static Long getMaxData(Long data) {

        if (data <= 10) {
            return 10l;
        } else if (10 < data && data <= 100) {
            return (data / 10 + 1) * 10;
        } else if (100 < data && data <= 1000) {
            return (data / 10 + 1) * 10;
        } else if (1000 < data && data <= 10000) {
            return (data / 100 + 1) * 100;
        } else if (10000 < data && data <= 100000) {
            return (data / 1000 + 1) * 1000;
        } else if (100000 < data && data <= 1000000) {
            return (data / 10000 + 1) * 10000;
        } else if (1000000 < data && data <= 10000000) {
            return (data / 100000 + 1) * 100000;
        } else if (10000000 < data && data <= 100000000) {
            return (data / 1000000 + 1) * 1000000;
        } else {
            return (data / 100000000 + 1) * 10000000;
        }
    }


    /**
     * 产生一个唯一性的标识做为订单uuid
     *
     * @return
     */
    public static String genOnlyIdentifier() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成一个交易单号。用于tradeNo
     *
     * @param customerId
     * @return
     */
    public static String getBillNumber(Long customerId) {
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS", Locale.getDefault());
        return "201" + df.format(new Date()) + Long.toString(customerId);
    }

    public static String encodeValue(String password,String phoneNo){
        String firstPasswordNum = new Sha1Hash(password, phoneNo, 100).toHex();
        return  firstPasswordNum;
    }

    /**
     * emoji表情替换
     *
     * @param source 原字符串
     * @param slipStr emoji表情替换成的字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source,String slipStr) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        }else{
            return source;
        }
    }

    //静态方法，便于作为工具类
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("utf-8"));

            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Map ConvertObjToMap(Object obj){
        Map<String,Object> reMap = new HashMap<String,Object>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for(int i=0;i<fields.length;i++){
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reMap;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}
