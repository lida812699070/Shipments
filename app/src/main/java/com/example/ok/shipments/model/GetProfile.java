package com.example.ok.shipments.model;

/**
 * Created by ${lida} on 2016/6/4.
 */
public class GetProfile {
    private String UserName;
    private String MobileNumber;
    private String Address;
    private String EMail;

    @Override
    public String toString() {
        return "GetProfile{" +
                "UserName='" + UserName + '\'' +
                ", MobileNumber='" + MobileNumber + '\'' +
                ", Address='" + Address + '\'' +
                ", EMail='" + EMail + '\'' +
                '}';
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
}
