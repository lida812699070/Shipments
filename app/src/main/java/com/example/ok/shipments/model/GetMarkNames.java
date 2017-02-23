package com.example.ok.shipments.model;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/22.
 */
public class GetMarkNames {
    ArrayList<String> listNames;

    @Override
    public String toString() {
        return "GetMarkNames{" +
                "listNames=" + listNames +
                '}';
    }

    public ArrayList<String> getListNames() {
        return listNames;
    }

    public void setListNames(ArrayList<String> listNames) {
        this.listNames = listNames;
    }
}
