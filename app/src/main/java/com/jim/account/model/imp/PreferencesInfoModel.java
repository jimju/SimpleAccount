package com.jim.account.model.imp;

import android.content.Context;
import android.content.SharedPreferences;

import com.jim.account.model.InfoModel;

/**
 * Created by jimju on 2016/12/23.
 */

public class PreferencesInfoModel implements InfoModel {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private static final String NAME ="UserInfo" ;
    private static final String NICKNAME ="nickname" ;
    private static final String SIGN ="sign" ;
    private static final String BUDGET ="budget" ;

    public PreferencesInfoModel(Context context) {
        this.mContext = context;
        mPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    @Override
    public String getNickName() {
       return mPreferences.getString(NICKNAME,"");
    }

    @Override
    public void setNickName(String nickName) {
        mEditor.putString(NICKNAME,nickName).commit();
    }

    @Override
    public String getUserSign() {
        return mPreferences.getString(SIGN,"");
    }

    @Override
    public void setUserSign(String sign) {
        mEditor.putString(SIGN,sign).commit();
    }

    @Override
    public double getBudget() {
        return mPreferences.getFloat(BUDGET,0);
    }

    @Override
    public void setBudget(double budget) {
        mEditor.putFloat(BUDGET, (float) budget).commit();
    }
}
