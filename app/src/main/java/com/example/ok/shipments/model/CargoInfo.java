package com.example.ok.shipments.model;

import java.io.Serializable;

/**
 * Created by ${lida} on 2016/9/26.
 */
public class CargoInfo implements Serializable {
    private String RecordId;
    private String ProductName;
    private String Length;
    private String Width;
    private String Height;
    private String Quantity;
    private String Volumn;
    private String Remarks;


    @Override
    public String toString() {
        return "CargoInfo{" +
                "ProductName='" + ProductName + '\'' +
                ", Length='" + Length + '\'' +
                ", Width='" + Width + '\'' +
                ", Height='" + Height + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", Volumn='" + Volumn + '\'' +
                ", Remarks='" + Remarks + '\'' +
                '}';
    }

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getVolumn() {
        return Volumn;
    }

    public void setVolumn(String volumn) {
        Volumn = volumn;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = width;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }
}
