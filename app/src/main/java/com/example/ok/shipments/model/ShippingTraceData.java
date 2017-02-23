package com.example.ok.shipments.model;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class ShippingTraceData {

    private String DocumentNumber;
    private String Status;
    private String FromCity;
    private String ToCity;
    private String Shipper;
    private String Consignee;
    private ShippingMessage shippingMessage;

    @Override
    public String toString() {
        return "ShippingTraceData{" +
                "DocumentNumber='" + DocumentNumber + '\'' +
                ", Status='" + Status + '\'' +
                ", FromCity='" + FromCity + '\'' +
                ", ToCity='" + ToCity + '\'' +
                ", Shipper='" + Shipper + '\'' +
                ", Consignee='" + Consignee + '\'' +
                ", shippingMessage=" + shippingMessage +
                '}';
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public ShippingMessage getShippingMessage() {
        return shippingMessage;
    }

    public void setShippingMessage(ShippingMessage shippingMessage) {
        this.shippingMessage = shippingMessage;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getShipper() {
        return Shipper;
    }

    public void setShipper(String shipper) {
        Shipper = shipper;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String toCity) {
        ToCity = toCity;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String fromCity) {
        FromCity = fromCity;
    }
}
