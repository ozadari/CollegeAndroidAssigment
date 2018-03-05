package com.restreviewer.restreviewer.Models;

import android.support.annotation.IntegerRes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by paz on 25/11/2017.
 */

public class Restaurant implements Serializable {
    private Integer Id;
    private String Name;
    private String Address;
    private String FoodType;
    private Boolean Deliveries;
    private Boolean Kosher;
    private String Telephone;
    String lastUpdated;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String address, String type, Boolean kosher, String phone,
                      Boolean deliveries){
        this.Id = id;
        this.Name = name;
        this.Address = address;
        this.Telephone = phone;
        this.FoodType = type;
        this.Kosher = kosher;
        this.Deliveries = deliveries;
    }


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
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

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
