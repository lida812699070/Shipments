package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.GetProfile;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by ${lida} on 2016/6/4.
 */
public class GetProfileXmlparser extends DefaultHandler {
    private String tag = "";

    private GetProfile user;

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tag = qName;
        if ("Value".equals(tag)) {
            user = new GetProfile();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = "";//

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length);//标签里面的内容
        if ("UserName".equals(tag)) {
            user.setUserName(content);
        } else if ("MobileNumber".equals(tag)) {
            user.setMobileNumber(content);
        } else if ("Address".equals(tag)) {
            user.setAddress(content);
        } else if ("EMail".equals(tag)) {
            user.setEMail(content);
        }
    }

    public GetProfile getUser() {
        return user;
    }

    public void setUser(GetProfile user) {
        this.user = user;
    }
}
