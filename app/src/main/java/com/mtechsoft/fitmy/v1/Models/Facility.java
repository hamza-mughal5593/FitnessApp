package com.mtechsoft.fitmy.v1.Models;

public class Facility {

    int image;
    String name;
    String nickName;
    String day_per_hour;
    String night_per_hour;
    String day_rate;

    public Facility(int image, String name, String nickName, String day_per_hour, String night_per_hour, String day_rate) {
        this.image = image;
        this.name = name;
        this.nickName = nickName;
        this.day_per_hour = day_per_hour;
        this.night_per_hour = night_per_hour;
        this.day_rate = day_rate;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDay_per_hour() {
        return day_per_hour;
    }

    public void setDay_per_hour(String day_per_hour) {
        this.day_per_hour = day_per_hour;
    }

    public String getNight_per_hour() {
        return night_per_hour;
    }

    public void setNight_per_hour(String night_per_hour) {
        this.night_per_hour = night_per_hour;
    }

    public String getDay_rate() {
        return day_rate;
    }

    public void setDay_rate(String day_rate) {
        this.day_rate = day_rate;
    }
}
