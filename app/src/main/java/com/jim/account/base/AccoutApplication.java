package com.jim.account.base;

import android.app.Application;

/**
 * Created by zhuzhu on 2016/12/10.
 */

public class AccoutApplication extends Application {

    private static AccoutApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static AccoutApplication getInstance() {
        return mInstance;
    }


    
}
