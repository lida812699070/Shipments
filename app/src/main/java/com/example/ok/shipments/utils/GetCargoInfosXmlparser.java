package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.CargoInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/9/26.
 */
public class GetCargoInfosXmlparser extends DefaultHandler {
    private String tag;
    private CargoInfo user;
    private ArrayList<CargoInfo> list;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        list = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        tag = qName;
        if ("CargoInfo".equals(qName)) {
            user=new CargoInfo();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if ("CargoInfo".equals(qName)) {
            list.add(user);
        }
        tag = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String content=new String(ch,start,length);//标签里面的内容
        if ("RecordId".equals(tag)){
            user.setRecordId(content);
        }else if ("ProductName".equals(tag)){
            user.setProductName(content);
        }else if ("Length".equals(tag)){
            user.setLength(content);
        }else if ("Width".equals(tag)){
            user.setWidth(content);
        }else if ("Height".equals(tag)){
            user.setHeight(content);
        }else if ("Quantity".equals(tag)){
            user.setQuantity(content);
        }else if ("Volumn".equals(tag)){
            user.setVolumn(content);
        }else if ("Remarks".equals(tag)){
            user.setRemarks(content);
        }
    }

    public ArrayList<CargoInfo> getList() {
        return list;
    }
}
