package com.example.customer_prototype;

import java.util.Comparator;

public class Model {

    private String title,description;
    private int img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public static final Comparator<Model>By_TITLE_ASCENDING=new Comparator<Model>() {
        @Override
        public int compare(Model model, Model t1) {
            return model.getTitle().compareTo(t1.getTitle());
        }
    };

    public static final Comparator<Model>By_TITLE_DESCENDINgG=new Comparator<Model>() {
        @Override
        public int compare(Model model, Model t1) {
            return t1.getTitle().compareTo(model.getTitle());
        }
    };
}
