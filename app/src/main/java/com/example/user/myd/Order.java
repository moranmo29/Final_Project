package com.example.user.myd;

import com.google.firebase.database.Exclude;

import java.util.Date;

/**
 * Created by User on 19/03/2017.
 */

public class Order {

    private String orderDesc; // description the order
    private int qUnitsOrder; // quantity units that order
    private String dateOrder;

    //Firebase needs a key property
    private String key;

    /** Default constructor required for calls to
     * DataSnapshot.getValue(Order.class)*/
    public Order()
    {
        //Needed for serialization
    }

    public Order(String orderDesc, int qUnitsOrder, String dateOrder)
    {
        this.orderDesc = orderDesc;
        this.qUnitsOrder = qUnitsOrder;
        this.dateOrder = dateOrder;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public int getqUnitsOrder() {
        return qUnitsOrder;
    }

    public void setqUnitsOrder(int qUnitsOrder) {
        this.qUnitsOrder = qUnitsOrder;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
