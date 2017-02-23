package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.ShippingMessage;
import com.example.ok.shipments.model.ShippingMode;
import com.example.ok.shipments.model.ShippingTraceData;
import com.example.ok.shipments.model.ShippingTraceItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${lida} on 2016/6/13.
 */
public class ShipModeXmlParser extends DefaultHandler {
    private String tag = "";
    private ShippingMode user;
    private List<ShippingMode> list;

    public List<ShippingMode> getList() {
        return list;
    }

    public void setList(List<ShippingMode> list) {
        this.list = list;
    }

    public void startDocument() throws SAXException {
        list = new ArrayList<>();
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = qName;//给标签初始化
        if ("ShippingMode".equals(tag)) {
            user = new ShippingMode();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = "";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("ShippingMode".equals(qName)) {
            list.add(user);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content = new String(ch, start, length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("ModeId".equals(tag)) {
            user.setModeId(content);
        }else if ("ModeName".equals(tag)) {
            user.setModeName(content);
        }else if ("IsEnabled".equals(tag)) {
            user.setIsEnabled(content);
        }
    }

}
