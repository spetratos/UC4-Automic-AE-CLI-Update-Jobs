package com.automic.config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class QueryObjectXML {

	XPath xpath;
	Document doc;

	public QueryObjectXML(String Filename) throws ParserConfigurationException, SAXException,
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
	public boolean doesActionRequireParameters(String ObjectName, String ActionName) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/actions/action[action_name='" + ActionName + "']"
				+ "/action_parameters/parameter[parameter_mandatory=Y]/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		if(nodes.getLength()==0){return false;}
		 return true;
	}
	public boolean doesActionExist(String ObjectName, String ActionName) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		if(!doesObjectExist(ObjectName)){System.out.println(" -- Error, Object "+ ObjectName + " Does Not Exist!"); return false;}
		ArrayList<String> actions =  getListActionForObject(ObjectName);
		 for(String action:actions){
			 if(action.equals(ActionName)){return true;}
		 }
		 return false;
	}
	public boolean doesParameterExist(String ObjectName, String ActionName, String ParameterName) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		if(!doesObjectExist(ObjectName)){System.out.println(" -- Error, Object "+ ObjectName + " Does Not Exist!"); return false;}
		if(!doesActionExist(ObjectName, ActionName)){System.out.println(" -- Error, Action "+ ActionName + " Does Not Exist!"); return false;}
		ArrayList<String> parameters =  getListParametersForAction(ObjectName,ActionName);
		 for(String param:parameters){
			 if(param.equals(ParameterName)){return true;}
		 }
		 return false;
	}

	public String getParameterType(String ObjectName, String ActionName,
			String ParameterName) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/actions/action[action_name='" + ActionName + "']"
				+ "/action_parameters/parameter[parameter_name='"
				+ ParameterName + "']/parameter_type/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		return nodes.item(0).getNodeValue();
	}
	public boolean isParameterMandatory(String ObjectName, String ActionName,
			String ParameterName) throws XPathExpressionException {
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/actions/action[action_name='" + ActionName + "']"
				+ "/action_parameters/parameter[parameter_name='"
				+ ParameterName + "']/parameter_mandatory/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		if(nodes.item(0).getNodeValue().equalsIgnoreCase("Y")){return true;}
		else{return false;}
	}
	public ArrayList<String> getListParametersForAction(String ObjectName,
			String ActionName) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {
		ArrayList<String> listOfparams = new ArrayList<String>();
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/actions/action[action_name='" + ActionName
				+ "']/action_parameters/parameter/parameter_name/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			listOfparams.add((nodes.item(i).getNodeValue()));
			//System.out.println("DEBUG1:" + nodes.item(i).getNodeValue());
		}
		return listOfparams;
	}
	public ArrayList<String> getListMandatoryParametersForAction(String ObjectName,
			String ActionName) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException {
		ArrayList<String> listOfparams = new ArrayList<String>();
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/actions/action[action_name='" + ActionName
				+ "']/action_parameters/parameter[parameter_mandatory='Y']/parameter_name/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			listOfparams.add((nodes.item(i).getNodeValue()));
			//System.out.println("DEBUG1:" + nodes.item(i).getNodeValue());
		}
		return listOfparams;
	}

	public boolean isObjectExecutable(String ObjectName)
			throws XPathExpressionException {
		boolean isExecutable = false;
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/is_executable/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		if (nodes.item(0).getNodeValue().equalsIgnoreCase("Y")
				|| nodes.item(0).getNodeValue().equalsIgnoreCase("YES")) {
			isExecutable = true;
		}
		return isExecutable;
	}

	public String getActionType(String ObjectName, String ActionName)
			throws XPathExpressionException {
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/actions/action[action_name='" + ActionName
				+ "']/action_type/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		return nodes.item(0).getNodeValue();
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

	public ArrayList<String> getListActionForObject(String ObjectName)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		ArrayList<String> listOfObjects = new ArrayList<String>();
		XPathExpression expr = xpath.compile("//object[name='" + ObjectName
				+ "']/actions/action/action_name/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			listOfObjects.add((nodes.item(i).getNodeValue()));
		}
		return listOfObjects;
	}


}