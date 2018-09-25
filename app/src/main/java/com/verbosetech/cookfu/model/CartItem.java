package com.verbosetech.cookfu.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by a_man on 24-01-2018.
 */

public class CartItem implements Parcelable {
    private String name,imageRes;
    private int price,quantity ;
    private float  priceTotal;

    public CartItem(){

    }

    public CartItem(String name, int price, String imageRes,int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageRes = imageRes;
        this.priceTotal = this.price * this.quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getImageRes() {
        return imageRes;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.priceTotal = this.price * this.quantity;
    }

    public float getPrice() {
        return price;
    }

    public float getPriceTotal() {
        return priceTotal;
    }
    public CartItem(Parcel in) {
        name = in.readString();
        price = in.readInt();
        imageRes = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeString(imageRes);
    }
    public static final Parcelable.Creator<CartItem> CREATOR = new Parcelable.Creator<CartItem>() {
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        public CartItem[] newArray(int size) {
            return new CartItem[size];

        }
    };

}
