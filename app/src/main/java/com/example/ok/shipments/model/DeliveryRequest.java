package com.example.ok.shipments.model;

import java.io.Serializable;

/**
 * Created by OK on 2016/4/11.
 */
public class DeliveryRequest implements Serializable{
    private String RequestId;
    private String RequestUser;
    private String RequestTime;
    private String CargoVolumn;
    private String CargoWeight;
    private String Address;
    private String ContactPerson;
    private String ContactNumber;
    private String Longitude;
    private String Latitude;
    private String ToCity;
    private String Remarks;
    private String Status;
    private String StatusDescription;
    private String Handler;
    private String CancelReason;
    private String TruckNumber;
    private String Score;
    private String ScoreDescription;
    private String ScoreMark;
    private String Appointment;
    private String ShipperName;
    private RequestMarks requestMarks;

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String shipperName) {
        ShipperName = shipperName;
    }

    public RequestMarks getRequestMarks() {
        return requestMarks;
    }

    public void setRequestMarks(RequestMarks requestMarks) {
        this.requestMarks = requestMarks;
    }

    public String getAppointment() {
        return Appointment;
    }

    public void setAppointment(String appointment) {
        Appointment = appointment;
    }

    @Override
    public String toString() {
        return "DeliveryRequest{" +
                "RequestId='" + RequestId + '\'' +
                ", RequestUser='" + RequestUser + '\'' +
                ", RequestTime='" + RequestTime + '\'' +
                ", CargoVolumn='" + CargoVolumn + '\'' +
                ", CargoWeight='" + CargoWeight + '\'' +
                ", Address='" + Address + '\'' +
                ", ContactPerson='" + ContactPerson + '\'' +
                ", ContactNumber='" + ContactNumber + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", ToCity='" + ToCity + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", Status='" + Status + '\'' +
                ", StatusDescription='" + StatusDescription + '\'' +
                ", Handler='" + Handler + '\'' +
                ", CancelReason='" + CancelReason + '\'' +
                ", TruckNumber='" + TruckNumber + '\'' +
                ", Score='" + Score + '\'' +
                ", ScoreDescription='" + ScoreDescription + '\'' +
                ", ScoreMark='" + ScoreMark + '\'' +
                ", Appointment='" + Appointment + '\'' +
                '}';
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getScoreMark() {
        return ScoreMark;
    }

    public void setScoreMark(String scoreMark) {
        ScoreMark = scoreMark;
    }

    public String getScoreDescription() {
        return ScoreDescription;
    }

    public void setScoreDescription(String scoreDescription) {
        ScoreDescription = scoreDescription;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getTruckNumber() {
        return TruckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        TruckNumber = truckNumber;
    }

    public String getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(String cancelReason) {
        CancelReason = cancelReason;
    }

    public String getHandler() {
        return Handler;
    }

    public void setHandler(String handler) {
        Handler = handler;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getToCity() {
        return ToCity;
    }

    public void setToCity(String toCity) {
        ToCity = toCity;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCargoWeight() {
        return CargoWeight;
    }

    public void setCargoWeight(String cargoWeight) {
        CargoWeight = cargoWeight;
    }

    public String getCargoVolumn() {
        return CargoVolumn;
    }

    public void setCargoVolumn(String cargoVolumn) {
        CargoVolumn = cargoVolumn;
    }

    public String getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(String requestTime) {
        RequestTime = requestTime;
    }

    public String getRequestUser() {
        return RequestUser;
    }

    public void setRequestUser(String requestUser) {
        RequestUser = requestUser;
    }
}
