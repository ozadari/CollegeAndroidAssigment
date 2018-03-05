package com.restreviewer.restreviewer.Models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by paz on 07/02/2018.
 */

public class Comment {
    public String Id;
    public String Title;
    public String Content;
    public Float Rate;
    public String Date;
    public Integer Restaurant_Id;

    public Comment() {
    }

    public Comment(String title, String content, Float rate, Integer restaurantId) {
        this.Title= title;
        this.Content = content;
        this.Rate = rate;
        this.Restaurant_Id = restaurantId;

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        java.util.Date today = Calendar.getInstance().getTime();
        this.Date = df.format(today);
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

    public Float getRate() {
        return Rate;
    }

    public void setRate(Float rate) {
        this.Rate = rate;
    }

    public Integer getRestaurantId() {
        return Restaurant_Id;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.Restaurant_Id = restaurantId;
    }
}
