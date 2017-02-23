package com.example.ok.shipments.utils;

/**
 * Created by OK on 2016/4/29.
 */
public interface FragmentCallBack {

    void initData(int state);
    void initData(String documentNum);
    void initData(String timeStart, String timeEnd);
}
