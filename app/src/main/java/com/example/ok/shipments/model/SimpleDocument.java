package com.example.ok.shipments.model;

import java.io.Serializable;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class SimpleDocument implements Serializable{
    private String DocumentId;
    private String DocumentNumber;
    private String CreateTime;
    private String ShipperName;
    private String ShipperContactName;
    private String ShipperAddress;
    private String ShipperPhoneNumber;
    private String NeedSignUpNotifyMessage;
    private String ConsigneeName;
    private String ConsigneeContactPerson;
    private String ConsigneeAddress;
    private String ConsigneePhoneNumber;
    private String NeedDelivery;
    private String Upstair;
    private String DeliveryCharge;
    private String FromCity;
    private String ToCity;
    private String ShippingMode;
    private String Weight;
    private String Volumn;
    private String ProductName;
    private String Quantity;
    private String Carriage;
    private String TotalCharge;
    private String BalanceMode;
    private String MonthlyBalanceAccount;
    private String NeedInsurance;
    private String InsuranceAmt;
    private String Premium;
    private String GoodsAmount;
    private String Remarks;
    private String PickupBy;
    private String Creator;
    private String ShippingStatus;
    private String DocumentState;
    private String DeliveryAfterNotify;
    private String Location;
    private String PictureCount;
    private String NeedWoodenFrame;

    @Override
    public String toString() {
        return "SimpleDocument{" +
                "DocumentId='" + DocumentId + '\'' +
                ", DocumentNumber='" + DocumentNumber + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", ShipperName='" + ShipperName + '\'' +
                ", ShipperContactName='" + ShipperContactName + '\'' +
                ", ShipperAddress='" + ShipperAddress + '\'' +
                ", ShipperPhoneNumber='" + ShipperPhoneNumber + '\'' +
                ", NeedSignUpNotifyMessage='" + NeedSignUpNotifyMessage + '\'' +
                ", ConsigneeName='" + ConsigneeName + '\'' +
                ", ConsigneeContactPerson='" + ConsigneeContactPerson + '\'' +
                ", ConsigneeAddress='" + ConsigneeAddress + '\'' +
                ", ConsigneePhoneNumber='" + ConsigneePhoneNumber + '\'' +
                ", NeedDelivery='" + NeedDelivery + '\'' +
                ", Upstair='" + Upstair + '\'' +
                ", DeliveryCharge='" + DeliveryCharge + '\'' +
                ", FromCity='" + FromCity + '\'' +
                ", ToCity='" + ToCity + '\'' +
                ", ShippingMode='" + ShippingMode + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Volumn='" + Volumn + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", Carriage='" + Carriage + '\'' +
                ", TotalCharge='" + TotalCharge + '\'' +
                ", BalanceMode='" + BalanceMode + '\'' +
                ", MonthlyBalanceAccount='" + MonthlyBalanceAccount + '\'' +
                ", NeedInsurance='" + NeedInsurance + '\'' +
                ", InsuranceAmt='" + InsuranceAmt + '\'' +
                ", Premium='" + Premium + '\'' +
                ", GoodsAmount='" + GoodsAmount + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", PickupBy='" + PickupBy + '\'' +
                ", Creator='" + Creator + '\'' +
                ", ShippingStatus='" + ShippingStatus + '\'' +
                ", DocumentState='" + DocumentState + '\'' +
                ", DeliveryAfterNotify='" + DeliveryAfterNotify + '\'' +
                ", Location='" + Location + '\'' +
                ", PictureCount='" + PictureCount + '\'' +
                ", NeedWoodenFrame='" + NeedWoodenFrame + '\'' +
                '}';
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }

    public String getNeedWoodenFrame() {
        return NeedWoodenFrame;
    }

    public void setNeedWoodenFrame(String needWoodenFrame) {
        NeedWoodenFrame = needWoodenFrame;
    }

    public String getPictureCount() {
        return PictureCount;
    }

    public void setPictureCount(String pictureCount) {
        PictureCount = pictureCount;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDeliveryAfterNotify() {
        return DeliveryAfterNotify;
    }

    public void setDeliveryAfterNotify(String deliveryAfterNotify) {
        DeliveryAfterNotify = deliveryAfterNotify;
    }

    public String getDocumentState() {
        return DocumentState;
    }

    public void setDocumentState(String documentState) {
        DocumentState = documentState;
    }

    public String getShippingStatus() {
        return ShippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        ShippingStatus = shippingStatus;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getPickupBy() {
        return PickupBy;
    }

    public void setPickupBy(String pickupBy) {
        PickupBy = pickupBy;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getGoodsAmount() {
        return GoodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        GoodsAmount = goodsAmount;
    }

    public String getPremium() {
        return Premium;
    }

    public void setPremium(String premium) {
        Premium = premium;
    }

    public String getInsuranceAmt() {
        return InsuranceAmt;
    }

    public void setInsuranceAmt(String insuranceAmt) {
        InsuranceAmt = insuranceAmt;
    }

    public String getNeedInsurance() {
        return NeedInsurance;
    }

    public void setNeedInsurance(String needInsurance) {
        NeedInsurance = needInsurance;
    }

    public String getMonthlyBalanceAccount() {
        return MonthlyBalanceAccount;
    }

    public void setMonthlyBalanceAccount(String monthlyBalanceAccount) {
        MonthlyBalanceAccount = monthlyBalanceAccount;
    }

    public String getBalanceMode() {
        return BalanceMode;
    }

    public void setBalanceMode(String balanceMode) {
        BalanceMode = balanceMode;
    }

    public String getTotalCharge() {
        return TotalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        TotalCharge = totalCharge;
    }

    public String getCarriage() {
        return Carriage;
    }

    public void setCarriage(String carriage) {
        Carriage = carriage;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getVolumn() {
        return Volumn;
    }

    public void setVolumn(String volumn) {
        Volumn = volumn;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getShippingMode() {
        return ShippingMode;
    }

    public void setShippingMode(String shippingMode) {
        ShippingMode = shippingMode;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String toCity) {
        ToCity = toCity;
    }

    public String getFromCity() {
        return FromCity;
    }

    public void setFromCity(String fromCity) {
        FromCity = fromCity;
    }

    public String getUpstair() {
        return Upstair;
    }

    public void setUpstair(String upstair) {
        Upstair = upstair;
    }

    public String getDeliveryCharge() {
        return DeliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        DeliveryCharge = deliveryCharge;
    }

    public String getNeedDelivery() {
        return NeedDelivery;
    }

    public void setNeedDelivery(String needDelivery) {
        NeedDelivery = needDelivery;
    }

    public String getConsigneePhoneNumber() {
        return ConsigneePhoneNumber;
    }

    public void setConsigneePhoneNumber(String consigneePhoneNumber) {
        ConsigneePhoneNumber = consigneePhoneNumber;
    }

    public String getConsigneeAddress() {
        return ConsigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        ConsigneeAddress = consigneeAddress;
    }

    public String getConsigneeContactPerson() {
        return ConsigneeContactPerson;
    }

    public void setConsigneeContactPerson(String consigneeContactPerson) {
        ConsigneeContactPerson = consigneeContactPerson;
    }

    public String getConsigneeName() {
        return ConsigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        ConsigneeName = consigneeName;
    }

    public String getNeedSignUpNotifyMessage() {
        return NeedSignUpNotifyMessage;
    }

    public void setNeedSignUpNotifyMessage(String needSignUpNotifyMessage) {
        NeedSignUpNotifyMessage = needSignUpNotifyMessage;
    }

    public String getShipperPhoneNumber() {
        return ShipperPhoneNumber;
    }

    public void setShipperPhoneNumber(String shipperPhoneNumber) {
        ShipperPhoneNumber = shipperPhoneNumber;
    }

    public String getShipperAddress() {
        return ShipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        ShipperAddress = shipperAddress;
    }

    public String getShipperContactName() {
        return ShipperContactName;
    }

    public void setShipperContactName(String shipperContactName) {
        ShipperContactName = shipperContactName;
    }

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String shipperName) {
        ShipperName = shipperName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }
}
