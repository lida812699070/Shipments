package com.example.ok.shipments.model;

/**
 * Created by ${lida} on 2017/1/19.
 */
public class ToNetwork {
    private String CompanyId;
    private String CompanyName;

    @Override
    public String toString() {
        return "ToNetwork{" +
                "CompanyId='" + CompanyId + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                '}';
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
