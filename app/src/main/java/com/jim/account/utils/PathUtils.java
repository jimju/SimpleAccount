package com.jim.account.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhuzhu on 2016/12/27.
 */

public class PathUtils {
    //sd卡文件夹路名称
    public static final String ACCOUNT_PATH = "Account";

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            return sdDir.getPath();
        }
        return null;
    }

    //获取项目的路径文件夹
    public static String getAccountSDPath() {
        String sddir = getSDPath();
        if (sddir != null) {
            File file = new File(sddir, ACCOUNT_PATH);
            if (!file.exists()) {
                file.mkdir();
            }
            return file.getPath();
        }
        return null;
    }

    //获取xls的路径文件夹
    public static String getXlsSDPath() {
        String sddir = getAccountSDPath();
        if (sddir != null) {
            File file = new File(sddir, "xls");
            if (!file.exists()) {
                file.mkdir();
            }
            return file.getPath();
        }
        return null;
    }
}
