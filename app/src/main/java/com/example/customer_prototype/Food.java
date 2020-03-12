package com.example.customer_prototype;

public class Food {
    int imageid;
    String food_name;

    public Food() {
    }

    public Food(int imageid, String food_name) {
        this.imageid = imageid;
        this.food_name = food_name;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }
}
