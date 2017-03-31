package com.zoutu.gotolibrary.Bean;

/**
 * Created by admin on 2017/3/30.
 */

public class AngleOrderBean {
    /**
     * clientaddrAddr1 : 交通东路16号(近老车站)
     * orderstatusOrderstatus : 下单成功
     * orderCarriage : 1.0
     * orderNo : 74923513825844
     * orderOrdertime : Mar 26, 2017 1:34:16 PM
     * clientaddrAddr :  中国浙江省温州市乐清市车站路688号 在飞龙大厦-2幢附近
     * orderName :
     * orderOrderprice : 10.92
     * orderRemark :
     */

    private String clientaddrAddr1;
    private String orderstatusOrderstatus;
    private double orderCarriage;
    private String orderNo;
    private String orderOrdertime;
    private String clientaddrAddr;
    private String orderName;
    private double orderOrderprice;
    private String orderRemark;

    public String getClientaddrAddr1() {
        return clientaddrAddr1;
    }

    public void setClientaddrAddr1(String clientaddrAddr1) {
        this.clientaddrAddr1 = clientaddrAddr1;
    }

    public String getOrderstatusOrderstatus() {
        return orderstatusOrderstatus;
    }

    public void setOrderstatusOrderstatus(String orderstatusOrderstatus) {
        this.orderstatusOrderstatus = orderstatusOrderstatus;
    }

    public double getOrderCarriage() {
        return orderCarriage;
    }

    public void setOrderCarriage(double orderCarriage) {
        this.orderCarriage = orderCarriage;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderOrdertime() {
        return orderOrdertime;
    }

    public void setOrderOrdertime(String orderOrdertime) {
        this.orderOrdertime = orderOrdertime;
    }

    public String getClientaddrAddr() {
        return clientaddrAddr;
    }

    public void setClientaddrAddr(String clientaddrAddr) {
        this.clientaddrAddr = clientaddrAddr;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public double getOrderOrderprice() {
        return orderOrderprice;
    }

    public void setOrderOrderprice(double orderOrderprice) {
        this.orderOrderprice = orderOrderprice;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
}
