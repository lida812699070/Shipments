package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.Login;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by ${lida} on 2016/5/30.
 */
public class LoginXmlparser extends DefaultHandler {

    private String tag="";

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private Login login;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        login = new Login();
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
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。

    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("UserId".equals(tag)) {
            login.setUserId(content);
        }else if ("ClientName".equals(tag)){
            login.setClientName(content);
        }else if ("SessionId".equals(tag)){
            login.setSessionId(content);
        }else if("RoleData".equals(tag)){
            login.setRoleData(content);
        }
    }

}
