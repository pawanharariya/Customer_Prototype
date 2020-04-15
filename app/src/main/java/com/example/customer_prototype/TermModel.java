package com.example.customer_prototype;

class TermModel {

    String Offer;
    String genre;

    public TermModel() {
    }

    public TermModel(String offer, String genre) {
        Offer = offer;
        this.genre = genre;
    }

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String offer) {
        Offer = offer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
