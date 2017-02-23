package com.example.ok.shipments.utils;

/**
 * Created by OK on 2016/3/23.
 */
public class MySoapHttpRequest {

    //传入的endPoint 是url
    //info是替换占位符后的字符串
    public static void getString(final CallBackString callBack, String endPoint, final String soapAction, String info) {
        final MySoap transport = new MySoap(endPoint);
        transport.setinfo(info);
        //Log.e("info",info);
        transport.debug = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    String call = transport.call(soapAction, null, null, "");
                    callBack.httFinsh(call);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //mListView.onRefreshComplete();
            }
        }.start();
    }
}
