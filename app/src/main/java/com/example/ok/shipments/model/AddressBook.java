package com.example.ok.shipments.model;

import java.io.Serializable;

/**
 * Created by ${lida} on 2016/6/13.
 */
public class AddressBook implements Serializable {
    private String RecordId;
    private String CompanyName;
    private String ContactName;
    private String MobileNumber;
    private String Address;
    private String Tel;
    private String Fax;
    private String EMail;
    private String Remarks;
    private Coordinate coordinate;

    @Override
    public String toString() {
        return "AddressBook{" +
                "RecordId='" + RecordId + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", ContactName='" + ContactName + '\'' +
                ", MobileNumber='" + MobileNumber + '\'' +
                ", Address='" + Address + '\'' +
                ", Tel='" + Tel + '\'' +
                ", Fax='" + Fax + '\'' +
                ", EMail='" + EMail + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", coordinate=" + coordinate +
                '}';
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
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

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
