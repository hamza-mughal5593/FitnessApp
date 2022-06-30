package com.mtechsoft.fitmy.v1.common;

public class ProfileObj {

//    "id": "bd6c410a-1fe5-48b2-bbe7-586c1ab63a35",
//    "phone_num": "ebbf1f1fb20b46a9a16a6c56a8a049bb",
//    "app_password": null,
//    "user_password": null,
//    "full_name": "Test",
//    "nick_name": "User 1",
//    "birth_year": 1990,
//    "birth_date": null,
//    "weight_kg": 65.00,
//    "height_cm": 185.00,
//    "gender": "M",
//    "date_created": "2019-09-23T00:57:59.743"

    private String id;
    private String phone_num;
    private String app_password;
    private String user_password;
    private String full_name;
    private String nick_name;
    private String email;
    private String race;
    private String nationality;
    private int birth_year;
    private String birth_date;
    private int weight_kg;
    private int height_cm;
    private String gender;
    private String date_created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getApp_password() {
        return app_password;
    }

    public void setApp_password(String app_password) {
        this.app_password = app_password;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public int getWeight_kg() {
        return weight_kg;
    }

    public void setWeight_kg(int weight_kg) {
        this.weight_kg = weight_kg;
    }

    public int getHeight_cm() {
        return height_cm;
    }

    public void setHeight_cm(int height_cm) {
        this.height_cm = height_cm;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
