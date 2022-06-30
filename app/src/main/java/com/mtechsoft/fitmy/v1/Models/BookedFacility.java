package com.mtechsoft.fitmy.v1.Models;

public class BookedFacility {

    String name;
    String date;
    String type;
    String unit;
    String price;

    public BookedFacility(String name, String date, String type, String unit, String price) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.unit = unit;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
