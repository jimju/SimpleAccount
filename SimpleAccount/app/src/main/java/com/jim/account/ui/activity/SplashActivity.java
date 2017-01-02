package com.jim.account.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jim.account.MainActivity;
import com.jim.account.R;
import com.jim.account.model.InfoModel;
import com.jim.account.model.imp.PreferencesInfoModel;

public class SplashActivity extends Activity {
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        mHandler.postDelayed(mRunnable,3000);
        initInfo();
    }

    private void initInfo() {
        InfoModel model = new PreferencesInfoModel(this);
        model.setNickName(getResources().getString(R.string.default_nickname));
        model.setUserSign("");
        model.setBudget(0.0);
    }
}
