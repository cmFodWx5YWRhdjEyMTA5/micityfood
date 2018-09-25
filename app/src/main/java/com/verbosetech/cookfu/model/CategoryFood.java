package com.verbosetech.cookfu.model;

/**
 * Created by a_man on 20-01-2018.
 */

public class CategoryFood {
    private String name;
    private int countRestaurant, imageRes;

    public CategoryFood(String name, int countRestaurant, int imageRes) {
        this.name = name;
        this.countRestaurant = countRestaurant;
        this.imageRes = imageRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getName() {
        return name;
    }

    public String getCountRestaurant() {
        return countRestaurant + " restaurants";
    }

}
