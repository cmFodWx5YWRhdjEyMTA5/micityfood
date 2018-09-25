package com.verbosetech.cookfu.DataAdapters;

import android.os.Parcel;
import android.os.Parcelable;


public class CuisineItems implements Parcelable {
    public static final Parcelable.Creator<CuisineItems> CREATOR = new Parcelable.Creator<CuisineItems>() {
        @Override
        public CuisineItems createFromParcel(Parcel in) {
            return new CuisineItems(in);
        }

        @Override
        public CuisineItems[] newArray(int size) {
            return new CuisineItems[size];
        }
    };
    private String code, msg, index, merchant_id, cat_id, disabled_ordering, mobile_menu, item_id, item_name, item_description, discount, photo, spicydish, single_item, single_details, not_available, icon_dish, price, size, size_id, size_trans, formatted_price, discount_price, formatted_discount_price, price_pretty;
    private boolean added;

    public CuisineItems() {
    }

    public CuisineItems(String code, String msg, String index, String merchant_id, String cat_id, String disabled_ordering, String mobile_menu, String item_id, String item_name, String item_description, String discount, String photo, String spicydish, String single_item, String single_details, String not_available, String icon_dish, String price, String size, String size_id, String size_trans, String formatted_price, String discount_price, String formatted_discount_price, String price_pretty) {
        this.code = code;
        this.msg = msg;
        this.index = index;
        this.merchant_id = merchant_id;
        this.cat_id = cat_id;
        this.disabled_ordering = disabled_ordering;
        this.mobile_menu = mobile_menu;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_description = item_description;
        this.discount = discount;
        this.photo = photo;
        this.spicydish = spicydish;
        this.single_item = single_item;
        this.single_details = single_details;
        this.not_available = not_available;
        this.icon_dish = icon_dish;
        this.price = price;
        this.size = size;
        this.size_id = size_id;
        this.size_trans = size_trans;
        this.formatted_price = formatted_price;
        this.discount_price = discount_price;
        this.formatted_discount_price = formatted_discount_price;
        this.price_pretty = price_pretty;
    }

    protected CuisineItems(Parcel in) {
        item_name = in.readString();
        item_description = in.readString();
        added = in.readByte() != 0;
        price = in.readString();
        photo = in.readString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getDisabled_ordering() {
        return disabled_ordering;
    }

    public void setDisabled_ordering(String disabled_ordering) {
        this.disabled_ordering = disabled_ordering;
    }

    public String getMobile_menu() {
        return mobile_menu;
    }

    public void setMobile_menu(String mobile_menu) {
        this.mobile_menu = mobile_menu;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSpicydish() {
        return spicydish;
    }

    public void setSpicydish(String spicydish) {
        this.spicydish = spicydish;
    }

    public String getSingle_item() {
        return single_item;
    }

    public void setSingle_item(String single_item) {
        this.single_item = single_item;
    }

    public String getSingle_details() {
        return single_details;
    }

    public void setSingle_details(String single_details) {
        this.single_details = single_details;
    }

    public String getNot_available() {
        return not_available;
    }

    public void setNot_available(String not_available) {
        this.not_available = not_available;
    }

    public String getIcon_dish() {
        return icon_dish;
    }

    public void setIcon_dish(String icon_dish) {
        this.icon_dish = icon_dish;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize_id() {
        return size_id;
    }

    public void setSize_id(String size_id) {
        this.size_id = size_id;
    }

    public String getSize_trans() {
        return size_trans;
    }

    public void setSize_trans(String size_trans) {
        this.size_trans = size_trans;
    }

    public String getFormatted_price() {
        return formatted_price;
    }

    public void setFormatted_price(String formatted_price) {
        this.formatted_price = formatted_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getFormatted_discount_price() {
        return formatted_discount_price;
    }

    public void setFormatted_discount_price(String formatted_discount_price) {
        this.formatted_discount_price = formatted_discount_price;
    }

    public String getPrice_pretty() {
        return price_pretty;
    }

    public void setPrice_pretty(String price_pretty) {
        this.price_pretty = price_pretty;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(item_name);
        parcel.writeString(item_description);
        parcel.writeByte((byte) (added ? 1 : 0));
        parcel.writeString(price);
        parcel.writeString(photo);
    }
}
