package com.example.ok.shipments.utils;

import android.content.Intent;

import com.example.ok.shipments.model.Login;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${lida} on 2016/6/3.
 */
public class XmlParser extends DefaultHandler {
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<Login> getList() {
        return list;
    }

    public void setList(List<Login> list) {
        this.list = list;
    }

    private String tag="";
    private Login login;
    private List<Login> list;
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        list=new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        tag=qName;

        if ("Value".equals(tag)){
            login = new Login();
        }

        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("Value".equals(qName)){
            list.add(login);
        }
        tag="";
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容  1068    <UserId>1068</UserId>
        if ("UserId".equals(tag)){
            login.setUserId(content);
        }else if ("ClientName".equals(tag)){
            login.setClientName(content);
        }else if ("SessionId".equals(tag)){
            login.setSessionId(content);
        }else if ("RoleData".equals(tag)){
            login.setRoleData(content);
        }
        super.characters(ch, start, length);
    }
}
