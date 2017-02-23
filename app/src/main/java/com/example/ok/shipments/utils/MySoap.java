package com.example.ok.shipments.utils;

/**
 * Created by OK on 2016/3/19.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.ServiceConnection;
import org.ksoap2.transport.ServiceConnectionSE;
import org.ksoap2.transport.Transport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

public class MySoap extends Transport {
    private ServiceConnection connection;
    private String info;
    public MySoap(String url) {
        super((Proxy)null, url);
    }

    public void setinfo(String info){
        try {
            info=new String(info.getBytes(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.info=info;
    }
    public MySoap(Proxy proxy, String url) {
        super(proxy, url);
    }

    public MySoap(String url, int timeout) {
        super(url, timeout);
    }

    public void call(String soapAction, SoapEnvelope envelope) throws IOException, XmlPullParserException {
        this.call(soapAction, envelope, (List) null);
    }

    public List call(String soapAction, SoapEnvelope envelope, List headers) throws IOException, XmlPullParserException {
        if(soapAction == null) {
            soapAction = "\"\"";
        }
        byte[] requestData = info.getBytes("utf-8");
        this.requestDump = this.debug?new String(requestData):null;
        this.responseDump = null;
        this.connection = this.getServiceConnection();
        this.connection.setRequestProperty("User-Agent", "kSOAP/2.0");
        this.connection.setRequestProperty("SOAPAction", soapAction);
        this.connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        this.connection.setRequestProperty("Connection", "close");

        this.connection.setRequestProperty("Content-Length", "" + requestData.length);
        if(headers != null) {
            for(int os = 0; os < headers.size(); ++os) {
                HeaderProperty is = (HeaderProperty)headers.get(os);
                this.connection.setRequestProperty(is.getKey(), is.getValue());
            }
        }

        this.connection.setRequestMethod("POST");
        this.connection.connect();
        OutputStream var13 = this.connection.openOutputStream();
        var13.write(requestData, 0, requestData.length);
        var13.flush();
        var13.close();
        Object var12 = null;
        List retHeaders = null;

        Object var14;
        try {
            this.connection.connect();
            var14 = this.connection.openInputStream();
            retHeaders = this.connection.getResponseProperties();
        } catch (IOException var11) {
            var14 = this.connection.getErrorStream();
            if(var14 == null) {
                this.connection.disconnect();
                throw var11;
            }
        }

        if(this.debug) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];

            while(true) {
                int rd = ((InputStream)var14).read(buf, 0, 256);
                if(rd == -1) {
                    bos.flush();
                    buf = bos.toByteArray();
                    this.responseDump = new String(buf);
                    ((InputStream)var14).close();
                    var14 = new ByteArrayInputStream(buf);
                    break;
                }

                bos.write(buf, 0, rd);
            }
        }
        InputStream in = (InputStream) var14;
        //String s = IoUtils.convertStreamToString(in);
        this.parseResponse(envelope, (InputStream)var14);
        return retHeaders;
    }
    public String call(String soapAction, SoapEnvelope envelope, List headers,String ss) throws IOException, XmlPullParserException {
        if(soapAction == null) {
            soapAction = "\"\"";
        }
        byte[] requestData = info.getBytes("utf-8");
        this.requestDump = this.debug?new String(requestData):null;
        this.responseDump = null;
        this.connection = this.getServiceConnection();
        this.connection.setRequestProperty("User-Agent", "kSOAP/2.0");
        this.connection.setRequestProperty("SOAPAction", soapAction);
        this.connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        this.connection.setRequestProperty("Connection", "close");
        this.connection.setRequestProperty("Content-Length", "" + requestData.length);
        if(headers != null) {
            for(int os = 0; os < headers.size(); ++os) {
                HeaderProperty is = (HeaderProperty)headers.get(os);
                this.connection.setRequestProperty(is.getKey(), is.getValue());
            }
        }

        this.connection.setRequestMethod("POST");
        this.connection.connect();
        OutputStream var13 = this.connection.openOutputStream();
        var13.write(requestData, 0, requestData.length);
        var13.flush();
        var13.close();
        Object var12 = null;
        List retHeaders = null;

        Object var14;
        try {
            this.connection.connect();
            var14 = this.connection.openInputStream();
            retHeaders = this.connection.getResponseProperties();
        } catch (IOException var11) {
            var14 = this.connection.getErrorStream();
            if(var14 == null) {
                this.connection.disconnect();
                throw var11;
            }
        }

        if(this.debug) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];

            while(true) {
                int rd = ((InputStream)var14).read(buf, 0, 256);
                if(rd == -1) {
                    bos.flush();
                    buf = bos.toByteArray();
                    this.responseDump = new String(buf);
                    ((InputStream)var14).close();
                    var14 = new ByteArrayInputStream(buf);
                    break;
                }

                bos.write(buf, 0, rd);
            }
        }
        InputStream in = (InputStream) var14;
        return IoUtils.convertStreamToString(in);
    }

    public ServiceConnection getConnection() {
        return (ServiceConnectionSE)this.connection;
    }

    protected ServiceConnection getServiceConnection() throws IOException {
        return new ServiceConnectionSE(this.proxy, this.url);
    }

    public String getHost() {
        String retVal = null;

        try {
            retVal = (new URL(this.url)).getHost();
        } catch (MalformedURLException var3) {
            var3.printStackTrace();
        }

        return retVal;
    }

    public int getPort() {
        int retVal = -1;

        try {
            retVal = (new URL(this.url)).getPort();
        } catch (MalformedURLException var3) {
            var3.printStackTrace();
        }

        return retVal;
    }

    public String getPath() {
        String retVal = null;

        try {
            retVal = (new URL(this.url)).getPath();
        } catch (MalformedURLException var3) {
            var3.printStackTrace();
        }

        return retVal;
    }
}

