package com.example.ok.shipments.utils;

import com.example.ok.shipments.model.DocumentPicture;
import com.example.ok.shipments.model.GetMarkNames;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/5/25.
 */
public class DocumentPictureXmlparser extends DefaultHandler {

    private String tag = "";
    private DocumentPicture picture;
    private String  RecordCount;
    private ArrayList<DocumentPicture> list;

    public ArrayList<DocumentPicture> getList() {
        return list;
    }

    public void setList(ArrayList<DocumentPicture> list) {
        this.list = list;
    }

    public void startDocument() throws SAXException {
        list = new ArrayList<>();
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = qName;//给标签初始化

        if ("DocumentPicture".equals(tag)){
            picture=new DocumentPicture();
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        //System.out.println("uri="+uri+",lcaolName="+localName+"qNmae="+qName);
        tag = "";//
        if ("DocumentPicture".equals(qName)){
            list.add(picture);
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String content = new String(ch, start, length);//标签里面的内容
        //如果标签是student的属性就进行对new出来的那个学生进行设置属性
        if ("RecordId".equals(tag)){
            picture.setRecordId(content);
        }else if ("DocumentId".equals(tag)){
            picture.setDocumentId(content);
        }else if ("IamgeData".equals(tag)){
            if (content.equals("\n")){
                return;
            }
            picture.setIamgeData(content);
        }else if ("IsSignUpPicture".equals(tag)){
            picture.setIsSignUpPicture(content);
        }else if ("Uploader".equals(tag)){
            picture.setUploader(content);
        }else if ("UploadTime".equals(tag)){
            picture.setUploadTime(content);
        }else if ("Description".equals(tag)){
            picture.setDescription(content);
        }else if ("RecordCount".equals(tag)){
            RecordCount=content;
        }
    }

    public String getRecordCount() {
        return RecordCount;
    }
}
