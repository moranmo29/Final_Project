package com.example.user.myd;

import com.google.firebase.database.Exclude;

/**
 * Created by User on 16/03/2017.
 */

public class Cost {

    private String description;
    private double priceUnit, priceTotal;
    private int quantityUnits;
    //Firebase needs a key property
    private String key;

    // Default constructor required for calls to
    // DataSnapshot.getValue(Cost.class)
    public Cost()
    {
        //Needed for serialization
    }

    public Cost(String description, double priceUnit, double priceTotal, int quantityUnits)
    {
        this.description = description;
        this.priceUnit = priceUnit;
        this.priceTotal = priceTotal;
        this.quantityUnits = quantityUnits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getQuantityUnits() {
        return quantityUnits;
    }

    public void setQuantityUnits(int quantityUnits) {
        this.quantityUnits = quantityUnits;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
