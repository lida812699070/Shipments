package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.GetMarkNames;
import com.example.ok.shipments.model.ShippingMessage;
import com.example.ok.shipments.model.ShippingTraceData;
import com.example.ok.shipments.model.ShippingTraceItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class ShippingTraceDataXmlparser extends DefaultHandler{

    private String tag = "";
    private ShippingTraceData user;
    private ShippingMessage message;
    private ShippingTraceItem item;
    private ArrayList<ShippingTraceItem> items;

    public void startDocument() throws SAXException {
        user = new ShippingTraceData();
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = qName;//给标签初始化
        if ("ShippingMessage".equals(tag)) {
            message = new ShippingMessage();
            items = new ArrayList<>();
            message.setList(items);
        }else if ("ShippingTraceItem".equals(tag)){
            item = new ShippingTraceItem();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = "";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("ShippingMessage".equals(qName)) {
            user.setShippingMessage(message);
        } else if ("ShippingTraceItem".equals(qName)) {
            items.add(item);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content = new String(ch, start, length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("DocumentNumber".equals(tag)) {
            user.setDocumentNumber(content);
        }else if ("Status".equals(tag)){
            user.setStatus(content);
        }else if ("FromCity".equals(tag)){
            user.setFromCity(content);
        }else if ("ToCity".equals(tag)){
            user.setToCity(content);
        }else if ("Shipper".equals(tag)){
            user.setShipper(content);
        }else if ("Consignee".equals(tag)){
            user.setConsignee(content);
        }else if ("Time".equals(tag)){
            item.setTime(content);
        }else if ("Message".equals(tag)){
            item.setMessage(content);
        }
    }

    public ShippingTraceData getUser() {
        return user;
    }

    public void setUser(ShippingTraceData user) {
        this.user = user;
    }
}
