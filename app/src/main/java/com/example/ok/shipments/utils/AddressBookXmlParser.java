package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.AddressBook;
import com.example.ok.shipments.model.Coordinate;
import com.example.ok.shipments.model.ShippingMode;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${lida} on 2016/6/13.
 */
public class AddressBookXmlParser extends DefaultHandler {

    private String tag = "";
    private AddressBook user;
    private List<AddressBook> list;
    private Coordinate coordinate;

    public List<AddressBook> getList() {
        return list;
    }

    public void setList(List<AddressBook> list) {
        this.list = list;
    }

    public void startDocument() throws SAXException {
        list = new ArrayList<>();
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = qName;//给标签初始化
        if ("AddressBook".equals(tag)) {
            user = new AddressBook();
        }else if ("Coordinate".equals(tag)){
            coordinate = new Coordinate();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = "";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("AddressBook".equals(qName)) {
            list.add(user);
        }else if ("Coordinate".equals(qName)){
            user.setCoordinate(coordinate);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content = new String(ch, start, length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("RecordId".equals(tag)) {
            user.setRecordId(content);
        }else if ("CompanyName".equals(tag)) {
            user.setCompanyName(content);
        }else if ("ContactName".equals(tag)) {
            user.setContactName(content);
        }else if ("MobileNumber".equals(tag)) {
            user.setMobileNumber(content);
        }else if ("Address".equals(tag)) {
            user.setAddress(content);
        }else if ("Tel".equals(tag)) {
            user.setTel(content);
        }else if ("Fax".equals(tag)) {
            user.setFax(content);
        }else if ("EMail".equals(tag)) {
            user.setEMail(content);
        }else if ("Remarks".equals(tag)) {
            user.setRemarks(content);
        }else if ("Longitude".equals(tag)) {
            coordinate.setLongitude(content);
        }else if ("Latitude".equals(tag)) {
            coordinate.setLatitude(content);
        }
    }
}
