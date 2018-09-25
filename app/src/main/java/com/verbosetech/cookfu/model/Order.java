package com.verbosetech.cookfu.model;

/**
 * Created by a_man on 24-01-2018.
 */

public class Order {
    private String date, placeName, placeAddress;
    private float orderTotal;
    private int status;

    public Order(String date, String placeName, String placeAddress, float orderTotal, int status) {
        this.date = date;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.orderTotal = orderTotal;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public int getStatus() {
        return status;
    }
}
