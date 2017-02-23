package com.example.ok.shipments.model;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class ShippingMessage {
    ArrayList<ShippingTraceItem> list;

    public ArrayList<ShippingTraceItem> getList() {
        return list;
    }

    public void setList(ArrayList<ShippingTraceItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ShippingMessage{" +
                "list=" + list +
                '}';
    }
}
