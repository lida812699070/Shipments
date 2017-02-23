package com.example.ok.shipments.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by OK on 2016/3/16.
 */
public class RetrueCodeHandler extends DefaultHandler {
    private String code;
    private String tag;//标签（变化的）
    private String error;
    private String value;
    private String RecordCount;

    public String getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(String recordCount) {
        RecordCount = recordCount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void startDocument() throws SAXException {
        code="";
        error="";
        RecordCount="";
    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag=qName;//给标签初始化
        if ("ReturnCode".equals(tag)) {//如果标签是“student”那么就new一个学生用于后面赋值存储
            code="";
        }else if ("Error".equals(tag)){
            error="";
        }else if ("Value".equals(tag)){
            value="";
        }else if("RecordCount".equals(tag)){
            RecordCount="";
        }
    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag="";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("ReturnCode".equals(qName)) {
            //list.add(user);
        }else if ("Error".equals(qName)){

        }else if ("Value".equals(qName)){

        }
    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("ReturnCode".equals(tag)) {
            code=content;
        }else if ("Error".equals(tag)){
            error=content;
        }else if ("Value".equals(tag)){
            value=content;
        }else if("RecordCount".equals(tag)){
            RecordCount=content;
        }
    }
    public String getString(){
        return code;
    }
    public String getError(){
        return error;
    }
}
