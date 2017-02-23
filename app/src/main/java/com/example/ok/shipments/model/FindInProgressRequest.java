package com.example.ok.shipments.model;

/**
 * Created by ${lida} on 2016/5/22.
 */
public class FindInProgressRequest {
    private String RequestId;
    private String Appointment;
    private String RequestTime;
    private String Address;
    private String ShipperName;
    private String ContactPerson;
    private String ContactNumber;
    private String Longitude;
    private String Latitude;
    private String CargoVolumn;
    private String CargoWeight;
    private String ToCity;
    private String Remarks;
    private GetMarkNames RequestMarks;

    @Override
    public String toString() {
        return "FindInProgressRequest{" +
                "RequestId='" + RequestId + '\'' +
                ", Appointment='" + Appointment + '\'' +
                ", RequestTime='" + RequestTime + '\'' +
                ", Address='" + Address + '\'' +
                ", ShipperName='" + ShipperName + '\'' +
                ", ContactPerson='" + ContactPerson + '\'' +
                ", ContactNumber='" + ContactNumber + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", CargoVolumn='" + CargoVolumn + '\'' +
                ", CargoWeight='" + CargoWeight + '\'' +
                ", ToCity='" + ToCity + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", RequestMarks=" + RequestMarks +
                ", Status='" + Status + '\'' +
                ", StatusDescription='" + StatusDescription + '\'' +
                ", Handler='" + Handler + '\'' +
                ", HandlerId='" + HandlerId + '\'' +
                ", TruckNumber='" + TruckNumber + '\'' +
                ", DriverNumber='" + DriverNumber + '\'' +
                ", Score='" + Score + '\'' +
                ", ScoreDescription='" + ScoreDescription + '\'' +
                ", ScoreMark='" + ScoreMark + '\'' +
                ", RequestUser='" + RequestUser + '\'' +
                '}';
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getRequestUser() {
        return RequestUser;
    }

    public void setRequestUser(String requestUser) {
        RequestUser = requestUser;
    }

    public String getScoreDescription() {
        return ScoreDescription;
    }

    public void setScoreDescription(String scoreDescription) {
        ScoreDescription = scoreDescription;
    }

    public String getScoreMark() {
        return ScoreMark;
    }

    public void setScoreMark(String scoreMark) {
        ScoreMark = scoreMark;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getDriverNumber() {
        return DriverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        DriverNumber = driverNumber;
    }

    public String getTruckNumber() {
        return TruckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        TruckNumber = truckNumber;
    }

    public String getHandlerId() {
        return HandlerId;
    }

    public void setHandlerId(String handlerId) {
        HandlerId = handlerId;
    }

    public String getHandler() {
        return Handler;
    }

    public void setHandler(String handler) {
        Handler = handler;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusDescription() {
        return StatusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        StatusDescription = statusDescription;
    }

    public GetMarkNames getRequestMarks() {
        return RequestMarks;
    }

    public void setRequestMarks(GetMarkNames requestMarks) {
        RequestMarks = requestMarks;
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

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String shipperName) {
        ShipperName = shipperName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRequestTime() {
        return RequestTime;
    }

    public void setRequestTime(String requestTime) {
        RequestTime = requestTime;
    }

    public String getAppointment() {
        return Appointment;
    }

    public void setAppointment(String appointment) {
        Appointment = appointment;
    }

    private String Status;
    private String StatusDescription;
    private String Handler;
    private String HandlerId;
    private String TruckNumber;
    private String DriverNumber;
    private String Score;
    private String ScoreDescription;
    private String ScoreMark;
    private String RequestUser;
}
