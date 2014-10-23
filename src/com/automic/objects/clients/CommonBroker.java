package com.automic.objects.clients;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.automic.AECredentials;
import com.automic.ConnectionManager;
import com.automic.objects.ObjectBroker;
import com.uc4.communication.Connection;

public class CommonBroker{

	public ObjectBroker broker;
	public String ActionName;
	public String ObjectType;
	public String Parameters;
	
	public CommonBroker() throws IOException, ParserConfigurationException, SAXException {

		String CONFIG_FILENAME = "./connection.config";
		File f = new File(CONFIG_FILENAME);
		if(!f.exists()){
			System.out.println(" -- ERROR, file containing the connection parameters could not be found: "+CONFIG_FILENAME);
			System.exit(100);
			}
		FileInputStream file = new FileInputStream(CONFIG_FILENAME);
		Properties configFile = new java.util.Properties();
		configFile.load(file);
		file.close();

		String AEHostnameOrIP = configFile.getProperty("AE_IP_ADR"); //"192.168.202.163";
		int AECPPrimaryPort = Integer.parseInt(configFile.getProperty("AE_PRIMARY_PORT")); //2217;
		int AEClientNumber = Integer.parseInt(configFile.getProperty("AE_CLIENT")); //500; // 330;
		String AEUserLogin = configFile.getProperty("AE_LOGIN"); //"RM"; //"BSP";
		String AEUserDepartment = configFile.getProperty("AE_DEPT"); //"RM"; //"AUTOMIC";
		String AEUserPassword = configFile.getProperty("AE_PASSWORD"); //"password"; //"oneAutomation";
		char AEMessageLanguage = 'E';
		AECredentials myClient = new AECredentials(AEHostnameOrIP,AECPPrimaryPort,AEClientNumber,AEUserDepartment,AEUserLogin,AEUserPassword,AEMessageLanguage);
		ConnectionManager mgr = new ConnectionManager();
		Connection conn = mgr.connectToClient(myClient);
		broker = new ObjectBroker(conn,false);
	}
	
	public void setAction(String ActionName){
		this.ActionName = ActionName;
	}
	public void setObjectType(String ObjectType){
		this.ObjectType = ObjectType;
	}
	public void setParameters(String Params){
		this.Parameters = Params;
	}	
}
