package com.automic.enums;

import java.io.IOException;

public class ActionTypesDispatcher {

	public String ObjectName;
	public String ActionName;
	public String Parameters;
	
	public ActionTypesDispatcher(String ObjectName, String ActionName, String Parameters) throws IOException{
		this.ObjectName = ObjectName;
		this.ActionName = ActionName;
		this.Parameters = Parameters;
		loadActionsFromObject();
		

	}

	public void loadActionsFromObject() throws IOException {
		if(this.ObjectName.equalsIgnoreCase("JOBS")){JobsActionTypes actions = new JobsActionTypes(this.ActionName, this.Parameters);}
		//if(this.ObjectName.equalsIgnoreCase("JOBP")){JobpActionTypes actions = new JobpActionTypes(this.ActionName, this.Parameters);}
	}

}
