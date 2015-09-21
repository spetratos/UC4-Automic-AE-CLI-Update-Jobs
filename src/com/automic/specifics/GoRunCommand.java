package com.automic.specifics;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import com.automic.AECredentials;
import com.automic.ConnectionManager;
import com.automic.objects.ObjectBroker;
import com.automic.std.ProcessStandardCLI;
import com.uc4.api.SearchResultItem;
import com.uc4.api.Template;
import com.uc4.api.UC4HostName;
import com.uc4.api.UC4ObjectName;
import com.uc4.api.UC4TimezoneName;
import com.uc4.api.objects.DeactivateCondition;
import com.uc4.api.objects.IFolder;
import com.uc4.api.objects.Job;
import com.uc4.api.objects.UC4Object;
import com.uc4.communication.Connection;

public class GoRunCommand {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException, ParseException, XPathExpressionException, ParserConfigurationException, SAXException{
		
		ProcessStandardCLI StdProc = new ProcessStandardCLI();
		AECredentials creds = StdProc.processStdCredentials(args);
		
		//1- Add / modify the parameters you need for your action in ProcessSpecificCLI class
		ProcessSpecificCLI.processSpecificParameters(args);

		Connection ClientConnection = new ConnectionManager().connectToClient(creds);
		ObjectBroker Objbroker = new ObjectBroker(ClientConnection,false);
	
		//2- retrieve your parameters.. and do what you want.
		List<SearchResultItem> list = Objbroker.common.searchJobs(ProcessSpecificCLI.JOBNAME);
		
		
		if(ProcessSpecificCLI.SIMULATE){
			System.out.println("Simulation Mode - Nothing will be updated. List of Jobs Selected:");
		}
		
		if(list.size() == 0){
			System.out.println(" %% => No Job Was Found for name: "+ProcessSpecificCLI.JOBNAME);
		}
		
		for(int i=0;i<list.size();i++){
			
			String ObjectName = list.get(i).getName();
			
			if(ProcessSpecificCLI.SIMULATE){
				System.out.println("  => Job Found: " + ObjectName);
			}
			else{
				
				UC4Object obj = Objbroker.common.openObject(ObjectName, false);
				Job job = (Job) obj; 
				
				System.out.println(" ++ Updating Job Definition for Job: " + ObjectName);

				
				if(ProcessSpecificCLI.ADDVARIABLE && !ProcessSpecificCLI.ERRORINVARIABLE){
					
					System.out.println("  => Adding Variable: "+ProcessSpecificCLI.VARNAME +" with Value: "+ProcessSpecificCLI.VARVALUE);
					job.values().addValue(ProcessSpecificCLI.VARNAME, ProcessSpecificCLI.VARVALUE,false);
				}
				
				if(ProcessSpecificCLI.REMOVEVARIABLE && !ProcessSpecificCLI.ERRORINVARIABLE){
					System.out.println("  => Removing Variable: "+ProcessSpecificCLI.VARNAME);
					job.values().removeValue(ProcessSpecificCLI.VARNAME);
				}
				
				if(ProcessSpecificCLI.UPDATEVARIABLE && !ProcessSpecificCLI.ERRORINVARIABLE){
					System.out.println("  => Updating Variable: "+ProcessSpecificCLI.VARNAME +" with Value: "+ProcessSpecificCLI.VARVALUE);
					job.values().removeValue(ProcessSpecificCLI.VARNAME);
					
					job.values().addValue(ProcessSpecificCLI.VARNAME, ProcessSpecificCLI.VARVALUE,true);
				}
				
				if(!ProcessSpecificCLI.JOBLOGIN.equals("")){System.out.println("  => Changing Login to: "+ProcessSpecificCLI.JOBLOGIN);job.attributes().setLogin(new UC4ObjectName(ProcessSpecificCLI.JOBLOGIN));}
				if(!ProcessSpecificCLI.JOBHOST.equals("")){System.out.println("  => Changing Host to: "+ProcessSpecificCLI.JOBHOST);job.attributes().setHost(new UC4HostName(ProcessSpecificCLI.JOBHOST));}
				if(!ProcessSpecificCLI.JOBPROCESS.equals("")){System.out.println("  => Changing Process");job.setProcess(ProcessSpecificCLI.JOBPROCESS);}
				if(!ProcessSpecificCLI.JOBPREPROCESS.equals("")){System.out.println("  => Changing Pre-Process");job.setPreProcess(ProcessSpecificCLI.JOBPREPROCESS);}
				if(!ProcessSpecificCLI.JOBPOSTPROCESS.equals("")){System.out.println("  => Changing Post-Process");job.setPostProcess(ProcessSpecificCLI.JOBPOSTPROCESS);}
				if(!ProcessSpecificCLI.JOBTITLE.equals("")){System.out.println("  => Changing Title to: "+ProcessSpecificCLI.JOBTITLE);job.header().setTitle(ProcessSpecificCLI.JOBTITLE);}
				if(!ProcessSpecificCLI.JOBQUEUE.equals("")){System.out.println("  => Changing Queue to: "+ProcessSpecificCLI.JOBQUEUE);job.attributes().setQueue(new UC4ObjectName(ProcessSpecificCLI.JOBQUEUE));}
				if(!ProcessSpecificCLI.JOBTZ.equals("")){System.out.println("  => Changing Timezone to: "+ProcessSpecificCLI.JOBTZ);job.attributes().setTimezone(new UC4TimezoneName(ProcessSpecificCLI.JOBTZ));}
				if(ProcessSpecificCLI.JOBPRIORITY != 0){System.out.println("  => Changing Priority to: "+ProcessSpecificCLI.JOBPRIORITY);job.attributes().setPriority(ProcessSpecificCLI.JOBPRIORITY);}
				if(ProcessSpecificCLI.JOBGENERATEATRUNTIME){System.out.println("  => Changing Generate At Runtime");job.attributes().setGenerateAtRuntime(true);}
				if(ProcessSpecificCLI.JOBACTIVE){System.out.println("  => Activate Job");job.header().setActive(true);}
				if(ProcessSpecificCLI.JOBINACTIVE){System.out.println("  => Deactivate Job");job.header().setActive(false);}
				
				System.out.println("");
				Objbroker.common.saveAndCloseObject(job);
			}
			

			
			
		}
			
		
		
		
	
	}
}
