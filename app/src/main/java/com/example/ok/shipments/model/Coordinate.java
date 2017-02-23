package com.example.ok.shipments.model;

import java.io.Serializable;

/**
 * Created by ${lida} on 2016/6/13.
 */
public class Coordinate implements Serializable{
    private String Longitude;
    private String Latitude;

    @Override
    public String toString() {
        return "Coordinate{" +
                "Longitude='" + Longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                '}';
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
}
