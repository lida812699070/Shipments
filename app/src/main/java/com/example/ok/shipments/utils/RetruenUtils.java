package com.example.ok.shipments.utils;

import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by OK on 2016/4/22.
 */
public class RetruenUtils<T> {
    public static RetrueCodeHandler getReturnInfo(String data) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            RetrueCodeHandler handler = new RetrueCodeHandler();
            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(data.getBytes());
            parser.parse(tInputStringStream, handler);
            return handler;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends DefaultHandler> T getReturnInfo(String data, T handler) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(data.getBytes());
            parser.parse(tInputStringStream, handler);
            if (handler==null){
                return null;
            }
            return handler;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
