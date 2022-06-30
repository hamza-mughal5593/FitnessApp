package com.mtechsoft.fitmy.v1.Models;

public class Complex {

    String name;
    String description;
    String tel_no;
    String fax_no;
    String email;
    String fasility_list;


    public Complex(String name, String description, String tel_no, String fax_no, String email, String fasility_list) {
        this.name = name;
        this.description = description;
        this.tel_no = tel_no;
        this.fax_no = fax_no;
        this.email = email;
        this.fasility_list = fasility_list;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public String getFax_no() {
        return fax_no;
    }

    public void setFax_no(String fax_no) {
        this.fax_no = fax_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFasility_list() {
        return fasility_list;
    }

    public void setFasility_list(String fasility_list) {
        this.fasility_list = fasility_list;
    }
}
