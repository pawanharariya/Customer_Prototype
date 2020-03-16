package com.example.customer_prototype;

import java.util.Comparator;

public class ShowDataOnMap {

    String Name,ShopName,email,genre;
    double Latitude,Longitude;

    public ShowDataOnMap() {
    }

    public ShowDataOnMap(String name, String shopName, String email, String genre, double latitude, double longitude) {
        Name = name;
        ShopName = shopName;
        this.email = email;
        this.genre = genre;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    /*public static final Comparator<Model> By_TITLE_ASCENDING=new Comparator<ShowDataOnMap>() {
        @Override
        public int compare(Model model, Model t1) {
            return model.getTitle().compareTo(t1.getTitle());
        }
    };

    public static final Comparator<Model>By_TITLE_DESCENDINgG=new Comparator<ShowDataOnMap>() {
        @Override
        public int compare(ShowDataOnMap model, ShowDataOnMap t1) {
            return t1.getTitle().compareTo(model.getTitle());
        }
    };*/
}
