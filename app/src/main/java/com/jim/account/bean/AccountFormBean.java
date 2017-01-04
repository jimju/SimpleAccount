package com.jim.account.bean;

/**
 * Created by zhuzhu on 2016/12/23.
 */

public class AccountFormBean extends AccountBean {
    private int color;
    private double percent;
    private double count;

    public AccountFormBean() {
    }



    public AccountFormBean(AccountBean bean, int color, double percent,double count) {
        super(bean.getId(), bean.getProject(), bean.getTime(), bean.getPay(), bean.getNormal(), bean.getRemark(), bean.getImageId());
        this.color = color;
        this.percent = percent;
        this.count = count;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
