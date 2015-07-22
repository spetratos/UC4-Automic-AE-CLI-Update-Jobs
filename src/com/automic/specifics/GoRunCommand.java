package com.automic.specifics;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import com.automic.AECredentials;
import com.automic.ConnectionManager;
import com.automic.objects.ObjectBroker;
import com.automic.std.ProcessStandardCLI;
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
		IFolder myFolder = Objbroker.folders.getFolderByName(ProcessSpecificCLI.JOBFOLDER);
		Objbroker.jobs.createJob(ProcessSpecificCLI.JOBNAME, ProcessSpecificCLI.JOBTEMPLATE, myFolder);
		
		UC4Object obj = Objbroker.common.openObject(ProcessSpecificCLI.JOBNAME, false);
		Job job = (Job) obj; 
		if(!ProcessSpecificCLI.JOBLOGIN.equals("")){job.attributes().setLogin(new UC4ObjectName(ProcessSpecificCLI.JOBLOGIN));}
		if(!ProcessSpecificCLI.JOBHOST.equals("")){job.attributes().setHost(new UC4HostName(ProcessSpecificCLI.JOBHOST));}
		if(!ProcessSpecificCLI.JOBPROCESS.equals("")){job.setProcess(ProcessSpecificCLI.JOBPROCESS);}
		if(!ProcessSpecificCLI.JOBPREPROCESS.equals("")){job.setPreProcess(ProcessSpecificCLI.JOBPREPROCESS);}
		if(!ProcessSpecificCLI.JOBPOSTPROCESS.equals("")){job.setPostProcess(ProcessSpecificCLI.JOBPOSTPROCESS);}
		if(!ProcessSpecificCLI.JOBTITLE.equals("")){job.header().setTitle(ProcessSpecificCLI.JOBTITLE);}
		if(!ProcessSpecificCLI.JOBQUEUE.equals("")){job.attributes().setQueue(new UC4ObjectName(ProcessSpecificCLI.JOBQUEUE));}
		if(!ProcessSpecificCLI.JOBTZ.equals("")){job.attributes().setTimezone(new UC4TimezoneName(ProcessSpecificCLI.JOBTZ));}
		if(ProcessSpecificCLI.JOBPRIORITY != 0){job.attributes().setPriority(ProcessSpecificCLI.JOBPRIORITY);}
		if(ProcessSpecificCLI.JOBGENERATEATRUNTIME){job.attributes().setGenerateAtRuntime(true);}
		if(!ProcessSpecificCLI.JOBACTIVE){job.header().setActive(false);}
		
		Objbroker.common.saveAndCloseObject(job);
	}
}
