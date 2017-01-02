package com.jim.account.utils;

import java.math.BigDecimal;

/**
 * Created by zhuzhu on 2016/12/23.
 */

public class NumberUtils {

    //限制小数点后两位
    public static String format2point(double d) {
        return String.valueOf(formatPoint(d,2));
    }

    //限制小数点
    public static double formatPoint(double d,int i) {
        BigDecimal b = new BigDecimal(d);
        double f = b.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }

    public static double formatPoint(String d,int i){
        double n = 0.00;
        try {
            n = Double.parseDouble(d);
        }catch (Exception e){
        }
        return formatPoint(n,i);
    }



}
