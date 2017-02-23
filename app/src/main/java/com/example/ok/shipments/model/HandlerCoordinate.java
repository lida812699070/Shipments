package com.example.ok.shipments.model;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by ${lida} on 2016/5/22.
 */
public class HandlerCoordinate {
    private String TruckNumber;
    private String DriverName;
    private String DriverMobile;
    private String[] Coordinate;

    @Override
    public String toString() {
        return "HandlerCoordinate{" +
                "TruckNumber='" + TruckNumber + '\'' +
                ", DriverName='" + DriverName + '\'' +
                ", DriverMobile='" + DriverMobile + '\'' +
                ", Coordinate=" + Coordinate +
                '}';
    }

    public String getTruckNumber() {
        return TruckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        TruckNumber = truckNumber;
    }

    public String[] getCoordinate() {
        return Coordinate;
    }

    public void setCoordinate(String[] coordinate) {
        Coordinate = coordinate;
    }

    public String getDriverMobile() {
        return DriverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        DriverMobile = driverMobile;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }
}
