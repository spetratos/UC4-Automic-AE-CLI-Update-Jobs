package com.automic.utils;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.automic.config.QueryListXML;
import com.automic.config.QueryObjectXML;

public class Help {

	public static void displayHelp() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{

		System.out.println(" => Parameters:");
		System.out.println("  -o or --object OBJECTNAME  					[Mandatory]");
		System.out.println("  -a or --action ACTIONNAME  					[Mandatory]");
		System.out.println("  -p or --params \"NAME1=VALUE1;NAME2=VALUE2\"			[Optional]");
		System.out.println("\n Example: java -jar automic_CLI.jar --object JOBS --action LIST --params \"NAME=ABCD*\"");
		System.out.println("\n Current List Of Objects Supported:\n");
		QueryListXML queryList = new QueryListXML("OBJECT_list.xml");
		for(String obj : queryList.getListObject()){
			System.out.println("   =>  " + obj);
		}		
		
		System.out.println("\n !!! HINT !!! Rerun the --help option after the --object option to get a list of available Actions");
		System.out.println(" !!! HINT !!! Rerun the --help option after the --object option AND the --action option to get a list of available Parameters");
	}

	public static void displayHelpWithObject(String object) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		System.out.println(" => Actions available for Object " + object + " :");
		QueryObjectXML query = new QueryObjectXML(object+"_config.xml");
		for(String action : query.getListActionForObject(object)){
			System.out.println("   =>  " + action);
		}		
	}

	public static void displayHelpWithObjectAndAction(String object,String action) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		System.out.println(" => Parameters available for Object " + object + " and Action " + action +" :");
		QueryObjectXML query = new QueryObjectXML(object+"_config.xml");
		for(String param : query.getListParametersForAction(object, action)){
			String type = query.getParameterType(object, action, param);
			if(query.isParameterMandatory(object, action, param)){
				System.out.println("   =>  " + param + " [MANDATORY] ("+type+")");
			}else{
				System.out.println("   =>  " + param + " [OPTIONAL] ("+type+")");
			}
		}		
	}
}
