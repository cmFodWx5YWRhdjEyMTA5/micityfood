package com.verbosetech.cookfu.model;

/**
 * Created by a_man on 22-01-2018.
 */

public class Restaurant {
    private String name, description;
    private String imageRes;

    public Restaurant(String name, String description, String imageRes) {
        this.name = name;
        this.description = description;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageRes() {
        return imageRes;
    }
}
