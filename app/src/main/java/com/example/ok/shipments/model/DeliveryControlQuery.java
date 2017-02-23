package com.example.ok.shipments.model;

import java.io.Serializable;

/**
 * Created by ${lida} on 2017/1/19.
 */
public class DeliveryControlQuery implements Serializable{
    private String StowageItemId;
    private String DocumentId;
    private String BillingTime;
    private String StowageNumber;
    private String DocumentNumber;
    private String ToCity;
    private String NotifyNetworkComment;
    private String NetworkConfirmFlag;
    private String ApplyComment;
    private String ApplyConfirmFlag;
    private String ConsigneePerson;
    private String ShipperName;
    private String Consignee;
    private String ConsigneeTel;
    private String ConsigneeAddress;
    private String CargoName;
    private String PickupTel;
    private String ToTools;
    private String LoadedTime;
    private String ArrivedTime;
    private String CargoQty;
    private String IsStowage;
    private String Weight;
    private String ShipperTel;
    private ToNetwork ToNetwork;
    private String SignupInfo;
    private String ReleaseControlFlag;
    private boolean DeliveryAfterNotify ;

    public boolean isDeliveryAfterNotify() {
        return DeliveryAfterNotify;
    }

    public void setDeliveryAfterNotify(boolean deliveryAfterNotify) {
        DeliveryAfterNotify = deliveryAfterNotify;
    }

    public String getReleaseControlFlag() {
        return ReleaseControlFlag;
    }

    public void setReleaseControlFlag(String releaseControlFlag) {
        ReleaseControlFlag = releaseControlFlag;
    }

    @Override
    public String toString() {
        return "DeliveryControlQuery{" +
                "StowageItemId='" + StowageItemId + '\'' +
                ", DocumentId='" + DocumentId + '\'' +
                ", BillingTime='" + BillingTime + '\'' +
                ", StowageNumber='" + StowageNumber + '\'' +
                ", DocumentNumber='" + DocumentNumber + '\'' +
                ", ToCity='" + ToCity + '\'' +
                ", NotifyNetworkComment='" + NotifyNetworkComment + '\'' +
                ", NetworkConfirmFlag='" + NetworkConfirmFlag + '\'' +
                ", ApplyComment='" + ApplyComment + '\'' +
                ", ApplyConfirmFlag='" + ApplyConfirmFlag + '\'' +
                ", ConsigneePerson='" + ConsigneePerson + '\'' +
                ", ShipperName='" + ShipperName + '\'' +
                ", Consignee='" + Consignee + '\'' +
                ", ConsigneeTel='" + ConsigneeTel + '\'' +
                ", ConsigneeAddress='" + ConsigneeAddress + '\'' +
                ", CargoName='" + CargoName + '\'' +
                ", PickupTel='" + PickupTel + '\'' +
                ", ToTools='" + ToTools + '\'' +
                ", LoadedTime='" + LoadedTime + '\'' +
                ", ArrivedTime='" + ArrivedTime + '\'' +
                ", CargoQty='" + CargoQty + '\'' +
                ", IsStowage='" + IsStowage + '\'' +
                ", Weight='" + Weight + '\'' +
                ", ShipperTel='" + ShipperTel + '\'' +
                ", ToNetwork=" + ToNetwork +
                ", SignupInfo='" + SignupInfo + '\'' +
                '}';
    }

    public String getStowageItemId() {
        return StowageItemId;
    }

    public void setStowageItemId(String stowageItemId) {
        StowageItemId = stowageItemId;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }

    public String getBillingTime() {
        return BillingTime;
    }

    public void setBillingTime(String billingTime) {
        BillingTime = billingTime;
    }

    public String getStowageNumber() {
        return StowageNumber;
    }

    public void setStowageNumber(String stowageNumber) {
        StowageNumber = stowageNumber;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String toCity) {
        ToCity = toCity;
    }

    public String getNotifyNetworkComment() {
        return NotifyNetworkComment;
    }

    public void setNotifyNetworkComment(String notifyNetworkComment) {
        NotifyNetworkComment = notifyNetworkComment;
    }

    public String getNetworkConfirmFlag() {
        return NetworkConfirmFlag;
    }

    public void setNetworkConfirmFlag(String networkConfirmFlag) {
        NetworkConfirmFlag = networkConfirmFlag;
    }

    public String getApplyComment() {
        return ApplyComment;
    }

    public void setApplyComment(String applyComment) {
        ApplyComment = applyComment;
    }

    public String getConsigneePerson() {
        return ConsigneePerson;
    }

    public void setConsigneePerson(String consigneePerson) {
        ConsigneePerson = consigneePerson;
    }

    public String getApplyConfirmFlag() {
        return ApplyConfirmFlag;
    }

    public void setApplyConfirmFlag(String applyConfirmFlag) {
        ApplyConfirmFlag = applyConfirmFlag;
    }

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String shipperName) {
        ShipperName = shipperName;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getConsigneeTel() {
        return ConsigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        ConsigneeTel = consigneeTel;
    }

    public String getConsigneeAddress() {
        return ConsigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        ConsigneeAddress = consigneeAddress;
    }

    public String getCargoName() {
        return CargoName;
    }

    public void setCargoName(String cargoName) {
        CargoName = cargoName;
    }

    public String getPickupTel() {
        return PickupTel;
    }

    public void setPickupTel(String pickupTel) {
        PickupTel = pickupTel;
    }

    public String getToTools() {
        return ToTools;
    }

    public void setToTools(String toTools) {
        ToTools = toTools;
    }

    public String getLoadedTime() {
        return LoadedTime;
    }

    public void setLoadedTime(String loadedTime) {
        LoadedTime = loadedTime;
    }

    public String getArrivedTime() {
        return ArrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        ArrivedTime = arrivedTime;
    }

    public String getCargoQty() {
        return CargoQty;
    }

    public void setCargoQty(String cargoQty) {
        CargoQty = cargoQty;
    }

    public String getIsStowage() {
        return IsStowage;
    }

    public void setIsStowage(String isStowage) {
        IsStowage = isStowage;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getShipperTel() {
        return ShipperTel;
    }

    public void setShipperTel(String shipperTel) {
        ShipperTel = shipperTel;
    }

    public String getSignupInfo() {
        return SignupInfo;
    }

    public void setSignupInfo(String signupInfo) {
        SignupInfo = signupInfo;
    }

    public com.example.ok.shipments.model.ToNetwork getToNetwork() {
        return ToNetwork;
    }

    public void setToNetwork(com.example.ok.shipments.model.ToNetwork toNetwork) {
        ToNetwork = toNetwork;
    }
}
