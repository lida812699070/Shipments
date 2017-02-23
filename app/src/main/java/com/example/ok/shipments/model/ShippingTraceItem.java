package com.example.ok.shipments.model;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class ShippingTraceItem {
    private String Time;
    private String Message;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    @Override
    public String toString() {
        return "ShippingTraceItem{" +
                "Time='" + Time + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
