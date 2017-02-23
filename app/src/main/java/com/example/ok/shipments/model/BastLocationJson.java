package com.example.ok.shipments.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${LiDa} on 2016/4/5.
 */
public class BastLocationJson {

    /**
     * name : 深圳百度国际大厦
     * location : {"lat":22.530721,"lng":113.949078}
     * address : 广东省深圳市南山区学府路东
     * street_id : 09470c3449c10420a80dd80e
     * detail : 1
     * uid : 09470c3449c10420a80dd80e
     */

    private String name;

    @Override
    public String toString() {
        return "BastLocationJson{" +
                "name='" + name + '\'' +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", street_id='" + street_id + '\'' +
                ", detail=" + detail +
                ", uid='" + uid + '\'' +
                '}';
    }

    /**
     * lat : 22.530721
     * lng : 113.949078
     */

    private LocationEntity location;
    private String address;
    private String street_id;
    private int detail;
    private String uid;

    public static BastLocationJson objectFromData(String str) {

        return new com.google.gson.Gson().fromJson(str, BastLocationJson.class);
    }

    public static BastLocationJson objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new com.google.gson.Gson().fromJson(jsonObject.getString(str), BastLocationJson.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<BastLocationJson> arrayBastLocationJsonFromData(String str) {

        Type listType = new com.google.gson.reflect.TypeToken<ArrayList<BastLocationJson>>() {
        }.getType();

        return new com.google.gson.Gson().fromJson(str, listType);
    }

    public static List<BastLocationJson> arrayBastLocationJsonFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new com.google.gson.reflect.TypeToken<ArrayList<BastLocationJson>>() {
            }.getType();

            return new com.google.gson.Gson().fromJson(jsonObject.getString(key).toString(), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStreet_id(String street_id) {
        this.street_id = street_id;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getStreet_id() {
        return street_id;
    }

    public int getDetail() {
        return detail;
    }

    public String getUid() {
        return uid;
    }

    public static class LocationEntity {
        private double lat;
        private double lng;

        @Override
        public String toString() {
            return "LocationEntity{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }

        public static LocationEntity objectFromData(String str) {

            return new com.google.gson.Gson().fromJson(str, LocationEntity.class);
        }

        public static LocationEntity objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new com.google.gson.Gson().fromJson(jsonObject.getString(str), LocationEntity.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<LocationEntity> arrayLocationEntityFromData(String str) {

            Type listType = new com.google.gson.reflect.TypeToken<ArrayList<LocationEntity>>() {
            }.getType();

            return new com.google.gson.Gson().fromJson(str, listType);
        }

        public static List<LocationEntity> arrayLocationEntityFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new com.google.gson.reflect.TypeToken<ArrayList<LocationEntity>>() {
                }.getType();

                return new com.google.gson.Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }
}
