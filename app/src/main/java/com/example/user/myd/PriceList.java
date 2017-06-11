package com.example.user.myd;

import com.google.firebase.database.Exclude;

/**
 * Created by User on 28/03/2017.
 */

public class PriceList {

    // Declare variables
    private String productName;
    private String unitCoinPrice;
    private String priceComments;
    private int qMin;
    private double priceUnit;
    private String key; //Firebase needs a key property

    // Default constructor required for calls to DataSnapshot.getValue(Shop.class)
    public PriceList()
    {
        //Needed for serialization
    }

    public PriceList(String productName,int qMin, double priceUnit, String unitCoinPrice, String priceComments)
    {
        this.productName = productName;
        this.qMin = qMin;
        this.priceUnit = priceUnit;
        this.unitCoinPrice = unitCoinPrice;
        this.priceComments = priceComments;
    }

    //set and get methods
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public int getqMin() {
        return qMin;
    }

    public void setqMin(int qMin) {
        this.qMin = qMin;
    }

    public double getPriceUnit() {
        return priceUnit;
    }

    public String getPriceComments() {
        return priceComments;
    }

    public void setPriceComments(String priceComments) {
        this.priceComments = priceComments;
    }

    public void setPriceUnit(double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getUnitCoinPrice() {
        return unitCoinPrice;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
