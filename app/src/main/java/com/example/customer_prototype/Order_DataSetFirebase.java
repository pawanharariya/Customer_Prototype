package com.example.customer_prototype;
public class Order_DataSetFirebase {

    String status;
    String orderid;
    String date;
    String shop;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Order_DataSetFirebase(String status, String orderid, String date, String shop) {
        this.status = status;
        this.orderid = orderid;
        this.date = date;
        this.shop = shop;
    }


    public Order_DataSetFirebase() {
    }
}

