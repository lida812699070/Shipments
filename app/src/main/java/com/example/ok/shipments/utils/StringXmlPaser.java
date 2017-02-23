package com.example.ok.shipments.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by ${lida} on 2016/7/28.
 */
public class StringXmlPaser extends DefaultHandler {
    private String name="";
    private String RecordId;
    private String tag;//标签（变化的）

    public void setName(String name) {
        this.name = name;
    }

    public void startDocument() throws SAXException {
        RecordId="";
    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag=qName;//给标签初始化
        if (name.equals(tag)) {//如果标签是“student”那么就new一个学生用于后面赋值存储
            RecordId="";
        }
    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag="";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if (name.equals(qName)) {
            //list.add(user);
        }
    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if (name.equals(tag)) {
            RecordId=content;
        }
    }
    public String getString(){
        return RecordId;
    }
}
