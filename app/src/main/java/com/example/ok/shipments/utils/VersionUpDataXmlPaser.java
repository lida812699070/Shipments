package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.UpDataInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Created by OK on 2016/4/19.
 */
public class VersionUpDataXmlPaser extends DefaultHandler {

    private UpDataInfo user;

    private String tag;
    public UpDataInfo getUpDataInfo() {
        return user;
    }

    public void startDocument() throws SAXException {

    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        tag=qName;//给标签初始化
        if ("Value".equals(tag)) {//如果标签是“student”那么就new一个学生用于后面赋值存储
            user=new UpDataInfo();
        }

    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        tag="";

    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("AppName".equals(tag)) {
            user.setAppName(content);
        }else if ("AppVersion".equals(tag)) {
            user.setAppVersion(content);
        }else if ("FileUrl".equals(tag)) {
            user.setFileUrl(content);
        }else if ("UpdateDescription".equals(tag)) {
            user.setUpdateDescription(content);
        }else if ("UpdateTime".equals(tag)) {
            user.setUpdateTime(content);
        }else if ("Md5CheckSum".equals(tag)) {
            user.setMd5CheckSum(content);
        }
    }
}
