package com.jim.account.bean;

import java.io.Serializable;

/**
 * Created by zhuzhu on 2016/12/11.
 */

public class AccountBean implements Serializable {
    private int id;
    private String project;
    private String time;
    private double pay;
    private String normal;
    private String remark;
    private int imageId;

    public AccountBean() {
    }

    public AccountBean(int id, String project, String time, double pay, String normal, String remark, int imageId) {
        this.id = id;
        this.project = project;
        this.time = time;
        this.pay = pay;
        this.normal = normal;
        this.remark = remark;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public void setPay(String pay) {
        this.pay = Double.parseDouble(pay);
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getImageId() {
        return imageId;
    }


    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "project='" + project + '\'' +
                ", time='" + time + '\'' +
                ", pay=" + pay +
                ", normal='" + normal + '\'' +
                ", remark='" + remark + '\'' +
                ", imageId=" + imageId +
                '}';
    }
}
