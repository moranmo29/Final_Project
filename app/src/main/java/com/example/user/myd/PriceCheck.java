package com.example.user.myd;

/**
 * Created by User on 28/03/2017.
 */

public class PriceCheck {

    private double totalCost, customerOffer, totalEarn;
    private int qPurchase;

    public PriceCheck(double totalCost, double customerOffer, int qPurchase)
    {
        this.totalCost = totalCost;
        this.customerOffer = customerOffer;
        this.qPurchase = qPurchase;
        this.totalEarn = totalEarn;
    }


    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getCustomerOffer() {
        return customerOffer;
    }

    public void setCustomerOffer(double customerOffer) {
        this.customerOffer = customerOffer;
    }

    public double getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(double totalEarn) {
        this.totalEarn = totalEarn;
    }

    public int getqPurchase() {
        return qPurchase;
    }

    public void setqPurchase(int qPurchase) {
        this.qPurchase = qPurchase;
    }
}
