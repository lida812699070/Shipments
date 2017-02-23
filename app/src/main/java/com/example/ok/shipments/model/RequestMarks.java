package com.example.ok.shipments.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by OK on 2016/4/27.
 */
public class RequestMarks implements Serializable{
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RequestMarks{" +
                "list=" + list +
                '}';
    }
}
