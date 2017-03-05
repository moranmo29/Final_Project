package com.example.user.myd;

import com.google.firebase.database.Exclude;

/**
 * Created by User on 27/02/2017.
 */

public class Supplier {

    private String name;
    private String company, address, email, phoneNumber, comments;
    //Firebase needs a key property
    private String key;

    public Supplier()
    {
        //Needed for serialization
    }

    public Supplier(String name, String company, String address, String email, String phoneNumber, String comments)
    {
        this.name = name;
        this.company = company;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.comments = comments;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
