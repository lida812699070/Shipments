package com.example.ok.shipments.utils;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * xml解析
 * @author Jerry
 *
 */
public class XmlParserUtil {
	 private DocumentBuilder documentBuilder;  
	    String currentPath;  
	    Map<String, String> map;  
	    Map<String, Integer> countMap;  
	  
	    private static XmlParserUtil parserInstance;  
	      
	    public static XmlParserUtil getInstance() {  
	        if (parserInstance == null) {  
	            parserInstance = new XmlParserUtil();  
	        }  
	        return parserInstance;  
	    }  
	  
	    private XmlParserUtil() {  
	        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();  
	        map = new HashMap<String, String>();  
	        countMap = new HashMap<String, Integer>();  
	        try {  
	            documentBuilder = documentBuilderFactory.newDocumentBuilder();  
	        } catch (ParserConfigurationException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    private void parseDocument(String xmlString) {  
	        Document doc = null;  
	        map.clear();  
	        countMap.clear();  
	        try {  
	            doc = documentBuilder.parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));  
	  
	            Element root = doc.getDocumentElement();  
	            currentPath = "/" + root.getNodeName() + "#0";  
	            if (root.getNodeValue() != null) {  
	                map.put(currentPath, root.getNodeValue());  
	            }  
	            parseNode(root, currentPath);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }
	    
	    private void parseDocument(File xmlString) {  
	        Document doc = null;  
	        map.clear();  
	        countMap.clear();  
	        try {  
	        	File file = new File(Environment.getExternalStorageDirectory(),"text.txt"); 
	            //InputStream in = new FileInputStream(file); 
	            doc = documentBuilder.parse(file);  
	  
	            Element root = doc.getDocumentElement();  
	            currentPath = "/" + root.getNodeName() + "#0";  
	            if (root.getNodeValue() != null) {  
	                map.put(currentPath, root.getNodeValue());  
	            }  
	            parseNode(root, currentPath);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    } 
	  
	    private void parseNode(Node e, String path) {  
	        NamedNodeMap attrs = e.getAttributes();  
	        if (attrs != null) {  
	            for (int i = 0; i < attrs.getLength(); i++) {  
	                map.put(path + "@" + attrs.item(i).getNodeName(), attrs.item(i)  
	                        .getNodeValue());  
	            }  
	        }  
	  
	        NodeList list = e.getChildNodes();  
	        if (list != null) {  
	            for (int i = 0; i < list.getLength(); i++) {  
	                Node node = list.item(i);  
	                if (e.getNodeValue() == null) {  
	                    if (node.getNodeValue() != null) { /* text node */  
	                        map.put(path, node.getNodeValue());  
	                    }  
	                }  
	                if (countMap.containsKey(path + "/" + node.getNodeName())) {  
	                    countMap.put(path + "/" + node.getNodeName(),  
	                            countMap.get(path + "/" + node.getNodeName()) + 1);  
	                } else {  
	                    countMap.put(path + "/" + node.getNodeName(), 0);  
	                }  
	                parseNode(node, path + "/" + node.getNodeName() + "#"  
	                        + countMap.get(path + "/" + node.getNodeName()));  
	            }  
	        }  
	    }  
	    
	    public void parse(File file){
	    	parseDocument(file);
	    }
	      
	      
	    public void parse(String xmlString) {  
	        parseDocument(xmlString);  
	    }  
	  
	      
	    public int getNodeCount(String path) {  
	        Integer i = countMap.get(path) == null ? countMap.get(formatPath(path, true))  
	                : countMap.get(path);  
	        return i == null ? 0 : i + 1;  
	    }  
	  
	  
	    public static String formatPath(String path, boolean isGetCount) {  
	        String t = path.replaceFirst("^/", "");
	        t = t.replaceAll("/", "#0/").replaceAll("(#\\d+)#0\\/", "$1/");  
	        t = t.replaceAll("@", "#0@").replaceAll("(#\\d+)#0@", "$1@");  
	        if (!t.contains("@") && !t.matches(".*#\\d+$") && !isGetCount) {  
	            t += "#0";  
	        }
	        return "/" + t;  
	    }  
	  
	    public String getNodeValue(String path) {  
	        return map.get(path) == null ? map.get(formatPath(path, false)) : map  
	                .get(path);  
	    }  
	      
	    private void debug(){  
	        for (Map.Entry<String, String> entry : map.entrySet()) {  
	            System.out.println(entry.getKey() + ": " + entry.getValue());  
	        }  
	        System.out.println("~~~~~~~");  
	        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {  
	            System.out.println(entry.getKey() + ": " + entry.getValue());  
	        }  
	    } 
}
