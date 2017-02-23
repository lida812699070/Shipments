package com.example.ok.shipments.utils;

import com.baidu.mapapi.model.LatLng;
import com.example.ok.shipments.model.GetMarkNames;
import com.example.ok.shipments.model.HandlerCoordinate;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/22.
 */
public class HandlerCoordinateXmlParser extends DefaultHandler {

    private String tag="";
    private HandlerCoordinate coordinate;

    public HandlerCoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(HandlerCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    private String[] arr;

    public void startDocument() throws SAXException {
        coordinate = new HandlerCoordinate();
    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag=qName;//给标签初始化
        if ("Coordinate".equals(tag)) {//如果标签是“student”那么就new一个学生用于后面赋值存储
            arr = new String[] {"0","0"};
        }
    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag="";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("Coordinate".equals(qName)) {
            coordinate.setCoordinate(arr);
        }
    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("TruckNumber".equals(tag)) {
            coordinate.setTruckNumber(content);
        }else if ("DriverMobile".equals(tag)){
            coordinate.setDriverMobile(content);
        }else if ("DriverName".equals(tag)){
            coordinate.setDriverName(content);
        }else if ("Longitude".equals(tag)){
            arr[1]=content;
        }else if ("Latitude".equals(tag)){
            arr[0]=content;
        }
    }

}
