package com.jim.account.db;

import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/19.
 */
public class DbHelperFactory {
    public static <T extends SQLiteOpenHelper> T getSQLiteHelper(Class<T> clz){
        try {
            return clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
