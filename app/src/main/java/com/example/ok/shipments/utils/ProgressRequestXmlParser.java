package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.FindInProgressRequest;
import com.example.ok.shipments.model.GetMarkNames;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/22.
 */
public class ProgressRequestXmlParser extends DefaultHandler {


    private String tag="";
    private ArrayList<String> list;
    private GetMarkNames getMarkNames;
    private FindInProgressRequest progressRequest;

    public FindInProgressRequest getProgressRequest() {
        return progressRequest;
    }

    public void setProgressRequest(FindInProgressRequest progressRequest) {
        this.progressRequest = progressRequest;
    }

    public void startDocument() throws SAXException {

    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag=qName;//给标签初始化
        if ("RequestMarks".equals(tag)) {//如果标签是“student”那么就new一个学生用于后面赋值存储
            getMarkNames=new GetMarkNames();
            list = new ArrayList<>();
        }else if ("Value".equals(tag)){
            progressRequest=new FindInProgressRequest();
        }
    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag="";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("RequestMarks".equals(qName)) {
            getMarkNames.setListNames(list);
            progressRequest.setRequestMarks(getMarkNames);
        }else if ("string".equals(qName)){

        }
    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("RequestId".equals(tag)) {
            progressRequest.setRequestId(content);
        }else if ("Appointment".equals(tag)){
            progressRequest.setAppointment(content);
        }else if ("RequestTime".equals(tag)){
            progressRequest.setRequestTime(content);
        }else if ("Address".equals(tag)){
            progressRequest.setAddress(content);
        }else if ("ShipperName".equals(tag)){
            progressRequest.setShipperName(content);
        }else if ("ContactPerson".equals(tag)){
            progressRequest.setContactPerson(content);
        }else if ("ContactNumber".equals(tag)){
            progressRequest.setContactNumber(content);
        }else if ("Longitude".equals(tag)){
            progressRequest.setLongitude(content);
        }else if ("Latitude".equals(tag)){
            progressRequest.setLatitude(content);
        }else if ("CargoVolumn".equals(tag)){
            progressRequest.setCargoVolumn(content);
        }else if ("CargoWeight".equals(tag)){
            progressRequest.setCargoWeight(content);
        }else if ("ToCity".equals(tag)){
            progressRequest.setToCity(content);
        }else if ("Remarks".equals(tag)){
            progressRequest.setRemarks(content);
        }else if ("Status".equals(tag)){
            progressRequest.setStatus(content);
        }else if ("StatusDescription".equals(tag)){
            progressRequest.setStatusDescription(content);
        }else if ("Handler".equals(tag)){
            progressRequest.setHandler(content);
        }else if ("HandlerId".equals(tag)){
            progressRequest.setHandlerId(content);
        }else if ("TruckNumber".equals(tag)){
            progressRequest.setTruckNumber(content);
        }else if ("DriverNumber".equals(tag)){
            progressRequest.setDriverNumber(content);
        }else if ("Score".equals(tag)){
            progressRequest.setScore(content);
        }else if ("ScoreDescription".equals(tag)){
            progressRequest.setScoreDescription(content);
        }else if ("ScoreMark".equals(tag)){
            progressRequest.setScoreMark(content);
        }else if ("RequestUser".equals(tag)){
            progressRequest.setRequestUser(content);
        }else if ("string".equals(tag)){
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
