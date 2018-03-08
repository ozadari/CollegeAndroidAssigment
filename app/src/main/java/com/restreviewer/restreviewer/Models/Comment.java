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
    private String Id;
    private String Title;
    private String Content;
    private Float Rate;
    private String Date;
    private String Restaurant_Id;

    public Comment() {
    }

    public Comment(String title, String content, Float rate, String restaurantId) {
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

    public String getRestaurantId() {
        return Restaurant_Id;
    }

    public void setRestaurantId(String restaurantId) {
        this.Restaurant_Id = restaurantId;
    }
}
