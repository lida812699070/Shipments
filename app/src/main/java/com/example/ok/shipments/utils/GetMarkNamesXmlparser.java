package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.GetMarkNames;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/22.
 */
public class GetMarkNamesXmlparser extends DefaultHandler {

    private String tag="";
    private ArrayList<String> list;
    private GetMarkNames getMarkNames;

    public void startDocument() throws SAXException {
        getMarkNames=new GetMarkNames();
    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag=qName;//给标签初始化
        if ("ReturnList".equals(tag)) {//如果标签是“student”那么就new一个学生用于后面赋值存储
            list = new ArrayList<>();
        }
    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag="";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("ReturnList".equals(qName)) {
            getMarkNames.setListNames(list);
        }else if ("string".equals(qName)){

        }
    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("string".equals(tag)) {
            list.add(content);
        }
    }

    public GetMarkNames getGetMarkNames() {
        return getMarkNames;
    }

    public void setGetMarkNames(GetMarkNames getMarkNames) {
        this.getMarkNames = getMarkNames;
    }
}
