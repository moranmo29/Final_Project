package com.example.user.myd;

import com.google.firebase.database.Exclude;

/**
 * Created by User on 20/03/2017.
 */

public class Shop {

    // Declare variables
    private String shopName, shopAddress, contactMan, shopEmail, shopNumberPhone;
    private int qSold;
    private String key; //Firebase needs a key property

    // Default constructor required for calls to DataSnapshot.getValue(Shop.class)
    public Shop()
    {
        //Needed for serialization
    }

    public Shop(String shopName, String shopAddress, String contactMan, String shopEmail, String shopNumberPhone, int qSold)
    {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.contactMan = contactMan;
        this.shopEmail = shopEmail;
        this.shopNumberPhone = shopNumberPhone;
        this.qSold = qSold;
    }

    //set and get methods
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    public String getShopEmail() {
        return shopEmail;
    }

    public void setShopEmail(String shopEmail) {
        this.shopEmail = shopEmail;
    }

    public String getShopNumberPhone() {
        return shopNumberPhone;
    }

    public void setShopNumberPhone(String shopNumberPhone) {
        this.shopNumberPhone = shopNumberPhone;
    }

    public int getqSold() {
        return qSold;
    }

    public void setqSold(int qSold) {
        this.qSold = qSold;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
