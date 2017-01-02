package com.jim.account.utils;

import java.util.Calendar;

/**
 * Created by zhuzhu on 2016/12/23.
 */

public class DateUtils {

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }
}
