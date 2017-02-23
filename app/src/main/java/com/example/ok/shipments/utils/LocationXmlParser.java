package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.GetMarkNames;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class LocationXmlParser extends DefaultHandler {

    private String tag="";
    private String address="";
    private String province="";
    private String city="";
    private String district="";
    private String street="";

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void startDocument() throws SAXException {

    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag=qName;//给标签初始化

    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag="";//

    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("formatted_address".equals(tag)) {
            address=content;
        }else if ("province".equals(tag)) {
            province=content;
        }else if ("city".equals(tag)) {
            city=content;
        }else if ("district".equals(tag)) {
            district=content;
        }else if ("street".equals(tag)) {
            street=content;
        }
    }

}