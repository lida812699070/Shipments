package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.SimpleDocument;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/24.
 */
public class SimpleDocumentXmlParser extends DefaultHandler {

    private String tag="";
    private ArrayList<SimpleDocument> list;
    private SimpleDocument user;

    public ArrayList<SimpleDocument> getList() {
        return list;
    }

    public void setList(ArrayList<SimpleDocument> list) {
        this.list = list;
    }

    public void startDocument() throws SAXException {
        list = new ArrayList<>();
    }
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag=qName;//给标签初始化
        if ("SimpleDocument".equals(tag)){
            user = new SimpleDocument();
        }
    }
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag="";//
        if ("SimpleDocument".equals(qName)){
            list.add(user);
        }

    }
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content=new String(ch,start,length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("DocumentId".equals(tag)) {
            user.setDocumentId(content);
        }else if ("DocumentNumber".equals(tag)){
            user.setDocumentNumber(content);
        }else if ("CreateTime".equals(tag)){
            user.setCreateTime(content);
        }else if ("ShipperName".equals(tag)){
            user.setShipperName(content);
        }else if ("ShipperContactName".equals(tag)){
            user.setShipperContactName(content);
        }else if ("ShipperAddress".equals(tag)){
            user.setShipperAddress(content);
        }else if ("ShipperPhoneNumber".equals(tag)){
            user.setShipperPhoneNumber(content);
        }else if ("NeedSignUpNotifyMessage".equals(tag)){
            user.setNeedSignUpNotifyMessage(content);
        }else if ("ConsigneeName".equals(tag)){
            user.setConsigneeName(content);
        }else if ("ConsigneeContactPerson".equals(tag)){
            user.setConsigneeContactPerson(content);
        }else if ("ConsigneeAddress".equals(tag)){
            user.setConsigneeAddress(content);
        }else if ("ConsigneePhoneNumber".equals(tag)){
            user.setConsigneePhoneNumber(content);
        }else if ("NeedDelivery".equals(tag)){
            user.setNeedDelivery(content);
        }else if ("Upstair".equals(tag)){
            user.setUpstair(content);
        }else if ("DeliveryCharge".equals(tag)){
            user.setDeliveryCharge(content);
        }else if ("FromCity".equals(tag)){
            user.setFromCity(content);
        }else if ("ToCity".equals(tag)){
            user.setToCity(content);
        }else if ("ShippingMode".equals(tag)){
            user.setShippingMode(content);
        }else if ("Weight".equals(tag)){
            user.setWeight(content);
        }else if ("Volumn".equals(tag)){
            user.setVolumn(content);
        }else if ("ProductName".equals(tag)){
            user.setProductName(content);
        }else if ("Quantity".equals(tag)){
            user.setQuantity(content);
        }else if ("Carriage".equals(tag)){
            user.setCarriage(content);
        }else if ("TotalCharge".equals(tag)){
            user.setTotalCharge(content);
        }else if ("BalanceMode".equals(tag)){
            user.setBalanceMode(content);
        }else if ("MonthlyBalanceAccount".equals(tag)){
            user.setMonthlyBalanceAccount(content);
        }else if ("NeedInsurance".equals(tag)){
            user.setNeedInsurance(content);
        }else if ("InsuranceAmt".equals(tag)){
            user.setInsuranceAmt(content);
        }else if ("Premium".equals(tag)){
            user.setPremium(content);
        }else if ("GoodsAmount".equals(tag)){
            user.setGoodsAmount(content);
        }else if ("Remarks".equals(tag)){
            user.setRemarks(content);
        }else if ("PickupBy".equals(tag)){
            user.setPickupBy(content);
        }else if ("Creator".equals(tag)){
            user.setCreator(content);
        }else if ("ShippingStatus".equals(tag)){
            user.setShippingStatus(content);
        }else if ("DocumentState".equals(tag)){
            user.setDocumentState(content);
        }else if ("DeliveryAfterNotify".equals(tag)){
            user.setDeliveryAfterNotify(content);
        }else if ("Location".equals(tag)){
            user.setLocation(content);
        }else if ("PictureCount".equals(tag)){
            user.setPictureCount(content);
        }else if ("NeedWoodenFrame".equals(tag)){
            user.setNeedWoodenFrame(content);
        }
    }
}
