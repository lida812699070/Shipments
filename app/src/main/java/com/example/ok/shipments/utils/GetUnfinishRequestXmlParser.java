package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.DeliveryRequest;
import com.example.ok.shipments.model.RequestMarks;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OK on 2016/4/11.
 */
public class GetUnfinishRequestXmlParser extends DefaultHandler {

    private List<DeliveryRequest> list;
    private String tag;//标签（变化的）
    private DeliveryRequest user;
    private ArrayList<String> list1;
    private RequestMarks requestMarks;

    public void startDocument() throws SAXException {
        list = new ArrayList<DeliveryRequest>();
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = qName;//给标签初始化
        if ("DeliveryRequest".equals(tag)) {//如果标签是“student”那么就new一个学生用于后面赋值存储
            user = new DeliveryRequest();
        }else if ("RequestMarks".equals(tag)){
            requestMarks = new RequestMarks();
            list1 = new ArrayList<>();
            requestMarks.setList(list1);
            user.setRequestMarks(requestMarks);
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = "";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("DeliveryRequest".equals(qName)) {
            list.add(user);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content = new String(ch, start, length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("RequestId".equals(tag)) {
            user.setRequestId(content);
        } else if ("RequestUser".equals(tag)) {
            user.setRequestUser(content);
        } else if ("RequestTime".equals(tag)) {
            user.setRequestTime(content);
        } else if ("CargoVolumn".equals(tag)) {
            user.setCargoVolumn(content);
        } else if ("CargoWeight".equals(tag)) {
            user.setCargoWeight(content);
        } else if ("Address".equals(tag)) {
            user.setAddress(content);
        } else if ("ContactPerson".equals(tag)) {
            user.setContactPerson(content);
        } else if ("ContactNumber".equals(tag)) {
            user.setContactNumber(content);
        } else if ("Longitude".equals(tag)) {
            user.setLongitude(content);
        } else if ("Latitude".equals(tag)) {
            user.setLatitude(content);
        } else if ("ToCity".equals(tag)) {
            user.setToCity(content);
        } else if ("Remarks".equals(tag)) {
            user.setRemarks(content);
        } else if ("Status".equals(tag)) {
            user.setStatus(content);
        } else if ("StatusDescription".equals(tag)) {
            user.setStatusDescription(content);
        } else if ("Handler".equals(tag)) {
            user.setHandler(content);
        } else if ("CancelReason".equals(tag)) {
            user.setCancelReason(content);
        } else if ("TruckNumber".equals(tag)) {
            user.setTruckNumber(content);
        } else if ("Score".equals(tag)) {
            user.setScore(content);
        } else if ("ScoreDescription".equals(tag)) {
            user.setScoreDescription(content);
        } else if ("ScoreMark".equals(tag)) {
            user.setScoreMark(content);
        }else if ("Appointment".equals(tag)) {
            user.setAppointment(content);
        }else if ("string".equals(tag)) {
            list1.add(content);
        }else if ("ShipperName".equals(tag)) {
            user.setShipperName(content);
        }
    }

    public List<DeliveryRequest> getList() {
        return list;
    }
}
