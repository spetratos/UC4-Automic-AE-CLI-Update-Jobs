package com.automic.objects.clients;

import java.io.IOException;

import com.automic.AECredentials;
import com.automic.ConnectionManager;
import com.automic.objects.ObjectBroker;
import com.uc4.communication.Connection;

public class CommonBroker{

	public ObjectBroker broker;
	public String ActionName;
	public String ObjectType;
	public String Parameters;
	
	public CommonBroker() throws IOException {

		String AEHostnameOrIP = "192.168.11.135";
		//String AEHostnameOrIP = "172.16.148.35";
		int AECPPrimaryPort = 2217;
		int AEClientNumber = 5; // 330;
		String AEUserLogin = "ARA"; //"BSP";
		String AEUserDepartment = "ARA"; //"AUTOMIC";
		String AEUserPassword = "ARA"; //"oneAutomation";
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
	public void process(){
		//broker.jobs.getAllJobs()
	}
	
	
	
	
}
