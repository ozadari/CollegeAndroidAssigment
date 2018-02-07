package com.restreviewer.restreviewer.Models;

/**
 * Created by paz on 25/11/2017.
 */

public class Restaurant {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private Boolean isDelivery;
    private Boolean isKosher;
    private Object opening_hours;
    private String type;

    public Restaurant()
    {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Boolean delivery) {
        this.isDelivery = delivery;
    }

    public Boolean getIsKosher() {
        return isKosher;
    }

    public void setIsKosher(Boolean kosher) {
        this.isKosher = kosher;
    }

    public Object getOpeningHours() {
        return opening_hours;
    }

    public void setOpeningHours(Object opening_Hours) {
        this.opening_hours = opening_Hours;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
