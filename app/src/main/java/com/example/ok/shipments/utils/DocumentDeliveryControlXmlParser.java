package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.DeliveryControlQuery;
import com.example.ok.shipments.model.ToNetwork;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${lida} on 2017/1/19.
 */
public class DocumentDeliveryControlXmlParser extends DefaultHandler{

    private String tag = "";
    private DeliveryControlQuery user;
    private List<DeliveryControlQuery> list;
    private ToNetwork mToNetwork;

    public List<DeliveryControlQuery> getList() {
        return list;
    }


    public void startDocument() throws SAXException {
        list = new ArrayList<>();
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = qName;//给标签初始化
        if ("DocumentDeliveryControl".equals(tag)) {
            user = new DeliveryControlQuery();
        }else if ("ToNetwork".equals(tag)){
            mToNetwork = new ToNetwork();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = "";//
        //如果再次读取到student标签那么代表这个学生对象已经读取完毕然后进行存储。
        if ("DocumentDeliveryControl".equals(qName)) {
            list.add(user);
        }else if ("ToNetwork".equals(qName)){
            user.setToNetwork(mToNetwork);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content = new String(ch, start, length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("StowageItemId".equals(tag)) {
            user.setStowageItemId(content);
        }else if ("DocumentId".equals(tag)) {
            user.setDocumentId(content);
        }else if ("DeliveryAfterNotify".equals(tag)) {
            try{
                user.setDeliveryAfterNotify(Boolean.valueOf(content));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if ("BillingTime".equals(tag)) {
            user.setBillingTime(content);
        }else if ("StowageNumber".equals(tag)) {
            user.setStowageNumber(content);
        }else if ("DocumentNumber".equals(tag)) {
            user.setDocumentNumber(content);
        }else if ("ReleaseControlFlag".equals(tag)) {
            user.setReleaseControlFlag(content);
        }else if ("ToCity".equals(tag)) {
            user.setToCity(content);
        }else if ("NotifyNetworkComment".equals(tag)) {
            user.setNotifyNetworkComment(content);
        }else if ("NetworkConfirmFlag".equals(tag)) {
            user.setNetworkConfirmFlag(content);
        }else if ("ApplyComment".equals(tag)) {
            user.setApplyComment(content);
        }else if ("ApplyConfirmFlag".equals(tag)) {
            user.setApplyConfirmFlag(content);
        }else if ("ConsigneePerson".equals(tag)) {
            user.setConsigneePerson(content);
        }else if ("ShipperName".equals(tag)) {
            user.setShipperName(content);
        }else if ("Consignee".equals(tag)) {
            user.setConsignee(content);
        }else if ("ConsigneeTel".equals(tag)) {
            user.setConsigneeTel(content);
        }else if ("ConsigneeAddress".equals(tag)) {
            user.setConsigneeAddress(content);
        }else if ("CargoName".equals(tag)) {
            user.setCargoName(content);
        }else if ("PickupTel".equals(tag)) {
            user.setPickupTel(content);
        }else if ("ToTools".equals(tag)) {
            user.setToTools(content);
        }else if ("LoadedTime".equals(tag)) {
            user.setLoadedTime(content);
        }else if ("ArrivedTime".equals(tag)) {
            user.setArrivedTime(content);
        }else if ("CargoQty".equals(tag)) {
            user.setCargoQty(content);
        }else if ("IsStowage".equals(tag)) {
            user.setIsStowage(content);
        }else if ("Weight".equals(tag)) {
            user.setWeight(content);
        }else if ("ShipperTel".equals(tag)) {
            user.setShipperTel(content);
        }else if ("SignupInfo".equals(tag)) {
            user.setSignupInfo(content);
        }else if ("CompanyId".equals(tag)) {
            mToNetwork.setCompanyId(content);
        }else if ("CompanyName".equals(tag)) {
            mToNetwork.setCompanyName(content);
        }
    }
}
