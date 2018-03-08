package com.restreviewer.restreviewer.Models;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.IntegerRes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by paz on 25/11/2017.
 */

public class Restaurant implements Serializable {
    private String Id;
    private String Name;
    private String Address;
    private String FoodType;
    private Boolean Deliveries;
    private Boolean Kosher;
    private String Telephone;
    private Bitmap image;
    private Uri imageUri;
    String lastUpdated;

    public Restaurant() {
    }

    public Restaurant(String id, String name, String address, String type, Boolean kosher, String phone,
                      Boolean deliveries){
        this.Id = id;
        this.Name = name;
        this.Address = address;
        this.Telephone = phone;
        this.FoodType = type;
        this.Kosher = kosher;
        this.Deliveries = deliveries;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone() {
        return Telephone;
    }

    public void setPhone(String phone) {
        this.Telephone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getType() {
        return FoodType;
    }

    public void setType(String type) {
        this.FoodType = type;
    }

    public Boolean getKosher() {
        return Kosher;
    }

    public void setKosher(Boolean kosher) {
        this.Kosher = kosher;
    }

    public Boolean getDeliveries() {
        return Deliveries;
    }

    public void setDeliveries(Boolean parking) {
        this.Deliveries = parking;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void emptyImage() {
        this.setImage(null);
        this.setImageUri(null);
    }
}
