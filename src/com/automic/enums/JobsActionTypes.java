package com.automic.enums;

import java.io.IOException;
import java.util.HashMap;

import com.automic.objects.clients.CommonBroker;
import com.automic.objects.clients.JobBroker;
import com.uc4.api.FolderListItem;
import com.uc4.api.objects.UC4Object;


public class JobsActionTypes extends ActionTypes{

	public String ActionName;
	public String Parameters;
	HashMap<String, String> availableActions = new HashMap<String, String>();
	

	public JobsActionTypes(String ActionName, String Parameters) throws IOException{
		this.ActionName = ActionName;
		this.Parameters = Parameters;
		processActions();
	}

	public void processActions() throws IOException{
		if(this.ActionName.equals("LIST")){
			CommonBroker commonBroker = new CommonBroker();
			for(FolderListItem obj :commonBroker.broker.common.listAllObjects("JOBS")){
				System.out.println(obj.getName() +":"+ obj.getId());;
			}
		//	commonBroker.setAction(this.ActionName);
		//	commonBroker.setObjectType("JOBS");
		//	commonBroker.setParameters(this.Parameters);
		//	commonBroker.Process();
		}
		
	}
}
