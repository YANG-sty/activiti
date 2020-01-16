package com.sys.demo;

import java.io.Serializable;
import java.util.Date;

/**
 * 一定要继承序列化类
 *
 * @author y_zzu 2020-01-16-10:28
 */
public class AppayBillBean implements Serializable {
    private Integer id;
    //金额
    private Integer cost;
    //申请人
    private String appayPerson;
    //申请日期
    private Date date;

    public AppayBillBean() {
    }

    public AppayBillBean(Integer id, Integer cost, String appayPerson, Date date) {
        this.id = id;
        this.cost = cost;
        this.appayPerson = appayPerson;
        this.date = date;
    }

    @Override
    public String toString() {
        return "AppayBillBean{" +
                "id=" + id +
                ", cost=" + cost +
                ", appayPerson='" + appayPerson + '\'' +
                ", date=" + date +
                '}';
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCost() {
        return cost;
    }
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    public String getAppayPerson() {
        return appayPerson;
    }
    public void setAppayPerson(String appayPerson) {
        this.appayPerson = appayPerson;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
