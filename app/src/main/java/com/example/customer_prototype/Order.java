package com.example.customer_prototype;

public class Order {
    int imageid;
    int amount;
    String itemname;
    int imageids;
    String name;

    public Order() {
    }

    public Order(int imageids, String name) {
        this.imageids = imageids;
        this.name = name;
    }

    public Order(int imageid, int amount, String itemname) {
        this.imageid = imageid;
        this.amount = amount;
        this.itemname = itemname;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
