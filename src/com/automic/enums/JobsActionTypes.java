package com.automic.enums;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.automic.objects.clients.CommonBroker;
import com.uc4.api.FolderListItem;
import com.uc4.api.objects.Job;
import com.uc4.api.objects.UC4Object;


public class JobsActionTypes extends ActionTypes{

	public String ActionName;
	public HashMap<String, String> Parameters = new HashMap<String, String>();

	public JobsActionTypes(String ActionName, HashMap<String, String> Params) throws IOException, ParserConfigurationException, SAXException{
		this.ActionName = ActionName;
		this.Parameters = Params;
		processActions();
	}

	public void processActions() throws IOException, ParserConfigurationException, SAXException{
		if(this.ActionName.equals("LIST")){getList();}
		if(this.ActionName.equals("SHOW")){getShow();}
		if(this.ActionName.equals("DELETE")){;}
		if(this.ActionName.equals("UPDATE")){;}
		if(this.ActionName.equals("CREATE")){;}
		if(this.ActionName.equals("DUPLICATE")){;}
		if(this.ActionName.equals("RENAME")){;}
		if(this.ActionName.equals("MOVE")){;}
		if(this.ActionName.equals("EXECUTE")){;}
		if(this.ActionName.equals("CANCEL")){;}
		if(this.ActionName.equals("SET_PRIORITY")){;}
		
	}

	private void getShow() throws IOException, ParserConfigurationException, SAXException{
		CommonBroker commonBroker = new CommonBroker();
		UC4Object obj = commonBroker.broker.common.openObject(this.Parameters.get("NAME"), true);
		Job job = (Job) obj;
		System.out.println(job.getName()+":"+job.getType()+":"+job.attributes().getHost()+":"+job.attributes().getLogin()+":"+job.attributes().getQueue());
		
	}
	
	private void getList() throws IOException, ParserConfigurationException, SAXException{
		CommonBroker commonBroker = new CommonBroker();
		String Filter = ".*";
		if(!this.Parameters.isEmpty()){Filter = this.Parameters.get("NAME").replace("*",".*");} // Why? because people will use * rather than .* 999 times out of 1000.
			for(FolderListItem obj :commonBroker.broker.common.listAllObjectsWithNameFilter("JOBS",Filter)){
				System.out.println(obj.getName() +":"+ obj.getId());
			}
		 
	}
	
}
