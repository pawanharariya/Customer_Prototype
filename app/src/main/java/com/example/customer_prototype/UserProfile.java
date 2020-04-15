package com.example.customer_prototype;

public class UserProfile {

    String userImage;
    String userName;
    String userEmail;
    String userCarNumber;
    String userCarModel;

    public UserProfile() {
    }

    public UserProfile(String userName, String userEmail, String userCarNumber, String userCarModel) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCarNumber = userCarNumber;
        this.userCarModel = userCarModel;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCarNumber() {
        return userCarNumber;
    }

    public void setUserCarNumber(String userCarNumber) {
        this.userCarNumber = userCarNumber;
    }

    public String getUserCarModel() {
        return userCarModel;
    }

    public void setUserCarModel(String userCarModel) {
        this.userCarModel = userCarModel;
    }
}
