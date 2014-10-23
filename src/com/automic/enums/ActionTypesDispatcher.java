package com.automic.enums;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ActionTypesDispatcher {

	public String ObjectName;
	public String ActionName;
	public String Parameters;
	public HashMap<String, String> ParamTable = new HashMap<String, String>();
	
	public ActionTypesDispatcher(String ObjectName, String ActionName, String Parameters) throws IOException, ParserConfigurationException, SAXException{
		this.ObjectName = ObjectName;
		this.ActionName = ActionName;
		this.Parameters = Parameters;
		transformParameters();
		loadActionsFromObject();

	}
	private void transformParameters(){
		if(!this.Parameters.equals("") && this.Parameters != null){
			String[] params = this.Parameters.split(";");
			for(String param : params){
				ParamTable.put(param.split("=")[0], param.split("=")[1]);
			}
		}
	}

	public void loadActionsFromObject() throws IOException, ParserConfigurationException, SAXException {
		if(this.ObjectName.equalsIgnoreCase("JOBS")){JobsActionTypes actions = new JobsActionTypes(this.ActionName, this.ParamTable);}
		//if(this.ObjectName.equalsIgnoreCase("JOBP")){JobpActionTypes actions = new JobpActionTypes(this.ActionName, this.Parameters);}
	}
	

}
