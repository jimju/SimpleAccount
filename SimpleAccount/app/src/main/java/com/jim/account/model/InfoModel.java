package com.jim.account.model;

/**
 * Created by zhuzhu on 2016/12/23.
 */

public interface InfoModel {

    //昵称
    String getNickName();

    void setNickName(String nickName);

    //签名
    String getUserSign();

    void setUserSign(String sign);

    //预算
    double getBudget();

    void setBudget(double budget);

}
