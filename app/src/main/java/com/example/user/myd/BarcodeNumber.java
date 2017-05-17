package com.example.user.myd;

import com.google.firebase.database.Exclude;

/**
 * Created by User on 26/03/2017.
 */

public class BarcodeNumber {

    private String barcodeDesc; // description the product name
    private String barcodeNum;
    private int qUnitsBarcode; // status of quantity units
    //Firebase needs a key property
    private String key;

    /** Default constructor required for calls to
     * DataSnapshot.getValue(BarcodeNumber.class)*/
    public BarcodeNumber()
    {
        //Needed for serialization
    }

    public BarcodeNumber(String barcodeDesc, String barcodeNum, int qUnitsBarcode)
    {
        this.barcodeDesc = barcodeDesc;
        this.barcodeNum = barcodeNum;
        this.qUnitsBarcode = qUnitsBarcode;
    }

    public String getBarcodeDesc() {
        return barcodeDesc;
    }
    public void setBarcodeDesc(String barcodeDesc) {
        this.barcodeDesc = barcodeDesc;
    }

    public String getBarcodeNum() {
        return barcodeNum;
    }

    public void setBarcodeNum(String barcodeNum) {
        this.barcodeNum = barcodeNum;
    }

    public int getqUnitsBarcode() {
        return qUnitsBarcode;
    }

    public void setqUnitsBarcode(int qUnitsBarcode) {
        this.qUnitsBarcode = qUnitsBarcode;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
