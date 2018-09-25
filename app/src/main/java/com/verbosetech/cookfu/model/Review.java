package com.verbosetech.cookfu.model;

/**
 * Created by a_man on 24-01-2018.
 */

public class Review {
    private String title, location, review, date;

    public Review(String title, String location, String review) {
        this.title = title;
        this.location = location;
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }
}
