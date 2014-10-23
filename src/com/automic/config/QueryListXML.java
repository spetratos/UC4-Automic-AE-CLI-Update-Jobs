package com.automic.config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QueryListXML {

	XPath xpath;
	Document doc;

	public QueryListXML(String Filename) throws ParserConfigurationException, SAXException,
			IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		URL url = getClass().getResource(Filename);
		doc = builder.parse(url.toString());
		XPathFactory xFactory = XPathFactory.newInstance();
		xpath = xFactory.newXPath();
	}
	
	public boolean doesObjectExist(String ObjectName) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		 ArrayList<String> objects =  getListObject();
		 for(String object:objects){
			 if(object.equals(ObjectName)){return true;}
		 }
		 return false;
	}
	public ArrayList<String> getListObject()
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		ArrayList<String> listOfObjects = new ArrayList<String>();
		XPathExpression expr = xpath.compile("//object/name/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			listOfObjects.add((nodes.item(i).getNodeValue()));
		}
		return listOfObjects;
	}
}
