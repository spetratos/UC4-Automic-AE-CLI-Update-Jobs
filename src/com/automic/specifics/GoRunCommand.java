package com.automic.specifics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import com.automic.AECredentials;
import com.automic.ConnectionManager;
import com.automic.objects.ObjectBroker;
import com.automic.std.HelpLabels;
import com.automic.std.ObjectUtils;
import com.automic.std.ProcessStandardCLI;
import com.automic.std.VersionUtils;
import com.automic.std.consistencyUtils;
import com.uc4.api.SearchResultItem;
import com.uc4.api.Template;
import com.uc4.api.UC4HostName;
import com.uc4.api.UC4ObjectName;
import com.uc4.api.UC4TimezoneName;
import com.uc4.api.VersionControlListItem;
import com.uc4.api.objects.AttributesSQL;
import com.uc4.api.objects.AttributesUnix;
import com.uc4.api.objects.AttributesWin;
import com.uc4.api.objects.CustomAttribute;
import com.uc4.api.objects.DeactivateCondition;
import com.uc4.api.objects.IFolder;
import com.uc4.api.objects.IHostAttributes;
import com.uc4.api.objects.Job;
import com.uc4.api.objects.OCVPanel.CITValue;
import com.uc4.api.objects.UC4Object;
import com.uc4.communication.Connection;
import com.uc4.communication.requests.GetLastRuntimes;
import com.uc4.communication.requests.SearchObject;
import com.uc4.communication.requests.VersionControlList;

public class GoRunCommand { 

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException, ParseException, XPathExpressionException, ParserConfigurationException, SAXException{
		
		ProcessStandardCLI StdProc = new ProcessStandardCLI(); 
		AECredentials creds = StdProc.processStdCredentials(args);
		if(creds == null){System.exit(1000);}
		//1- Add / modify the parameters you need for your action in ProcessSpecificCLI class
		ProcessSpecificCLI pr = new ProcessSpecificCLI();
		
		boolean	SpecParamOk = pr.processSpecificParameters(args);
		if(!SpecParamOk){System.exit(1000);}
		
		Connection ClientConnection = new ConnectionManager().connectToClient(creds);  
		ObjectBroker Objbroker = new ObjectBroker(ClientConnection,false);
		
		String version = Objbroker.automationEngine.getServerVersion();
		if(!VersionUtils.isCompatible(version, Version.MinimumAEVersion)){
			System.out.println(" -- Error: Incompatible Version. Minimum AE Version: " + Version.MinimumAEVersion);
			System.exit(99);
		}
		
		if(ProcessSpecificCLI.SIMULATE){
			System.out.println(HelpLabels.DISPLAY_SIMULATION);
		}
		//2- retrieve your parameters.. and do what you want.
		List<SearchResultItem> GlobalList = Objbroker.common.searchJobs(ProcessSpecificCLI.NAME);
		List<SearchResultItem> FilteredList = new ArrayList<SearchResultItem>();
		try{
			for(int i=0;i< GlobalList.size();i++){
				
				Job job = (Job) Objbroker.common.openObject(GlobalList.get(i).getName(), true);
				
				// Job type specific Attributes (Connection name for SQL job etc.)
				
				boolean JobSelected = true;
				if(JobSelected && ProcessSpecificCLI.F_ACTIVE.equals("Y")){ if(!job.header().isActive()) {JobSelected = false;}}
				if(JobSelected && ProcessSpecificCLI.F_ACTIVE.equals("N")){  if(job.header().isActive()) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_NAME.equals("")){  if(!job.getName().matches(ProcessSpecificCLI.F_NAME)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_ARCHIVE1.equals("")){  if(!job.header().getArchiveKey1().matches(ProcessSpecificCLI.F_ARCHIVE1)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_ARCHIVE2.equals("")){ if(!job.header().getArchiveKey2().matches(ProcessSpecificCLI.F_ARCHIVE2)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_TITLE.equals("")){ if(!job.header().getTitle().matches(ProcessSpecificCLI.F_TITLE)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_HOST.equals("")){ if(!job.attributes().getHost().toString().matches(ProcessSpecificCLI.F_HOST)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_LOGIN.equals("")){ if(!job.attributes().getLogin().toString().matches(ProcessSpecificCLI.F_LOGIN)) {JobSelected = false;}}
				// in the following line (?s) allows matching new lines.. which is essential when matching Process to a regex!!
				if(JobSelected && !ProcessSpecificCLI.F_POSTPROCESS_KEYWORD.equals("")){ if(!job.getPostProcess().matches("(?s)"+ProcessSpecificCLI.F_POSTPROCESS_KEYWORD)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_PREPROCESS_KEYWORD.equals("")){ if(!job.getPreProcess().matches("(?s)"+ProcessSpecificCLI.F_PREPROCESS_KEYWORD)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_PROCESS_KEYWORD.equals("")){ if(!job.getProcess().matches("(?s)"+ProcessSpecificCLI.F_PROCESS_KEYWORD)) {JobSelected = false;}}
				if(JobSelected && !ProcessSpecificCLI.F_QUEUE.equals("")){ if(!job.attributes().getQueue().toString().matches(ProcessSpecificCLI.F_QUEUE)) {JobSelected = false;}}
				
				if(JobSelected && !ProcessSpecificCLI.F_METADATA_NAME.equals("")){ 
					boolean AttNameFound = false;
					Iterator<CustomAttribute> it = job.header().customAttributeIterator();
					while(it.hasNext()){
						CustomAttribute attr = it.next();
						if(attr.getName().matches(ProcessSpecificCLI.F_METADATA_NAME)){AttNameFound = true;}
					}
					if(!AttNameFound){JobSelected = false;}
				}
				
				if(JobSelected && !ProcessSpecificCLI.F_METADATA_VALUE.equals("")){ 
					boolean AttValFound = false;
					Iterator<CustomAttribute> it = job.header().customAttributeIterator();
					while(it.hasNext()){
						CustomAttribute attr = it.next();
						if(attr.getValue().matches(ProcessSpecificCLI.F_METADATA_VALUE)){AttValFound = true;}
					}
					if(!AttValFound){JobSelected = false;}
				}
				
				if(JobSelected && !ProcessSpecificCLI.F_VARIABLE_NAME.equals("")){ 
					boolean VarNameFound = false;
					Iterator<String> it = job.values().valueKeyIterator();
					while(it.hasNext()){
						String key = it.next();
						if(key.matches(ProcessSpecificCLI.F_VARIABLE_NAME)){VarNameFound = true;}
					}
					if(!VarNameFound){JobSelected = false;}
				}
				
				if(JobSelected && !ProcessSpecificCLI.F_VARIABLE_VALUE.equals("")){ 
					boolean VarValueFound = false;
					Iterator<String> it = job.values().valueKeyIterator();
					
					while(it.hasNext()){
						String key = it.next();
						String value = job.values().getValue(key);
	
						if(value.matches(ProcessSpecificCLI.F_VARIABLE_VALUE)){VarValueFound = true;}
					}
					if(!VarValueFound){JobSelected = false;}
				}
	
				if(job.getType().equals("JOBS_SQL")){
					//AttributesSQL attr = (AttributesSQL) job.hostAttributes();
					// if needed to filter on SQL specific stuff - put here
				}
				
				if(job.getType().equals("JOBS_UNIX")){
					// if needed to filter on Unix specific stuff - put here
					//AttributesUnix attr = (AttributesUnix) job.hostAttributes();
				}
				
				if(job.getType().equals("WINDOWS")){
					// if needed to filter on Windows specific stuff - put here
					//AttributesWin attr = (AttributesWin) job.hostAttributes();
				}
				
				// if needed to filter on RA specific stuff - put here (Code below provided as an example)
				if(job.getType().equals("JOBS_CIT")){
					if(job.getRAJobType().equals("WEBSERVICE")){
						if(job.getRASubJobType().equals("REST")){
							// all values are XML formatted for RA Agents / RA Jobs
							//Iterator<CITValue> itValues = job.ocvValues().iterator();
							//while(itValues.hasNext()){
							//	CITValue CITVal = itValues.next();
								//System.out.println(CITVal.getXmlName()+":"+CITVal.getValue());
							}
						}
					}
	
				if (JobSelected){
					FilteredList.add(GlobalList.get(i));
				}
			}
		}catch (PatternSyntaxException e){
			System.out.println(" -- Internal Error. The Regular Expression Used for one of the Filters is invalid - See Error Below: " + e.getDescription());
			System.out.println("\t\t"+ e.toString());
			System.out.println(" %% HINT: '*' and '+' are NOT valid Regular Expressions on their own. '*' marks '0 or more' and '+' marks '1 or more', but you need to specify a preceeding character or string" );
			System.out.println(" %% HINT: Ex: invalid expressions: '*ABC*', '*.123', '+DEF'. Valid expressions: '.*ABC.*', '.*.123', '.+DEF' " );
	
			System.exit(999);
		}
		
		if(FilteredList.size() == 0){
			System.out.println(" %% => No Job Was Found for name: "+ProcessSpecificCLI.F_NAME);
		}
		
		for(int i=0;i<FilteredList.size();i++){
			
			String ObjectName = FilteredList.get(i).getName();
			String ObjectTitle = FilteredList.get(i).getTitle();
			
			// Behavior: if COMMIT flag is ON, then Object needs to be reclaimed before it is Open for Write
			// 			 if COMMIT flag is OFF, then no need to reclaim object as it can simply be open as read only
			UC4Object obj;
			if(ProcessSpecificCLI.COMMIT){
					Objbroker.common.reclaimObject(FilteredList.get(i).getName());
					obj = Objbroker.common.openObject(ObjectName, false);
			}else{
				obj = Objbroker.common.openObject(ObjectName, true);
			}
		
				if(obj != null){
					Job job = (Job) obj; 
					boolean Skip = false;
					
					System.out.println("\n ++  => Processing Matching JOBS: [ " + ObjectName +" | " + ObjectTitle +" ] ");

					if(!Skip && ProcessSpecificCLI.D_ATTRIBUTES){
						ObjectUtils.displayJobAttr(job);
					}
					if(!Skip && ProcessSpecificCLI.D_STATS){
						GetLastRuntimes runtimes = Objbroker.statistics.getLastRuntimes(job.getName());
						ObjectUtils.displayStats(Objbroker.statistics.getObjectStatistics(job.getName()),runtimes,job.getName()); 
						    
					}
					if(!Skip && ProcessSpecificCLI.D_HEADER){
						ObjectUtils.displayJobHeader(job);
					}
					if(!Skip && ProcessSpecificCLI.D_VARIABLES){
						ObjectUtils.displayJobVariables(job);
					}
					if(!Skip && ProcessSpecificCLI.D_PROCESS){
						ObjectUtils.displayJobProcess(job);
					}
					if(!Skip && ProcessSpecificCLI.D_PREPROCESS){
						ObjectUtils.displayJobPreProcess(job);
					}
					if(!Skip && ProcessSpecificCLI.D_POSTPROCESS){
						ObjectUtils.displayJobPostProcess(job);
					}
					if(!Skip && ProcessSpecificCLI.D_RUNTIME){
						ObjectUtils.displayJobRuntime(job);
					}
					if(!Skip && ProcessSpecificCLI.D_SPECIFIC){
						ObjectUtils.displayJobSpecific(job);
					}
					if(!Skip && ProcessSpecificCLI.U_RESTORE_PREVIOUS){
						System.out.println("\t ++ UPDATE: Job Marked for Restore to Previous Version: [ " + job.getName() + " | " + job.header().getTitle() + " ]" );
						if(ProcessSpecificCLI.COMMIT){Objbroker.common.restorePreviousVersion(job.getName());}
						Skip = true;
					}
					
					if(!Skip && ProcessSpecificCLI.U_RESTORE_VERSION!=-1){
						System.out.println("\t ++ UPDATE: Job Marked for Restore to Version "+ ProcessSpecificCLI.U_RESTORE_VERSION + " [ " + job.getName() + " | " + job.header().getTitle() + " ]" );
						if(ProcessSpecificCLI.COMMIT){Objbroker.common.restoreSpecificVersion(job.getName(),ProcessSpecificCLI.U_RESTORE_VERSION);}
						Skip = true;
					}
					
					if(!Skip && ProcessSpecificCLI.U_ACTIVE.equalsIgnoreCase("Y")){job.header().setActive(true);System.out.println("\t ++ UPDATE: Job Set To Active.");}
					if(!Skip && ProcessSpecificCLI.U_ACTIVE.equalsIgnoreCase("N")){job.header().setActive(false);System.out.println("\t ++ UPDATE: Job Set To Inactive.");}
					if(!Skip && ProcessSpecificCLI.U_GENERATEATRUNTIME.equalsIgnoreCase("Y")){job.attributes().setGenerateAtRuntime(true);System.out.println("\t ++ UPDATE: Job Set To Generate At Runtime.");}
					if(!Skip && ProcessSpecificCLI.U_GENERATEATRUNTIME.equalsIgnoreCase("N")){job.attributes().setGenerateAtRuntime(false);System.out.println("\t ++ UPDATE: Generate At Runtime Deactivate.");}
					if(!Skip && !ProcessSpecificCLI.U_TITLE.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_TITLE);
						String newValue = job.header().getTitle().replaceAll(Patterns[0], Patterns[1]);
						System.out.println("\t ++ UPDATE: Job Title Change from: " + job.header().getTitle() +" To: " + newValue );
						job.header().setTitle(newValue);
					}
					if(!Skip && !ProcessSpecificCLI.U_ARCH1.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_ARCH1);
						String newValue = job.header().getArchiveKey1().replaceAll(Patterns[0], Patterns[1]);
						System.out.println("\t ++ UPDATE: Job Archive Key 1 Change from: " + job.header().getArchiveKey1() +" To: " + newValue );
						job.header().setArchiveKey1(newValue);
					}
					if(!Skip && !ProcessSpecificCLI.U_ARCH2.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_ARCH2);
						String newValue = job.header().getArchiveKey1().replaceAll(Patterns[0], Patterns[1]);
						System.out.println("\t ++ UPDATE: Job Archive Key 2 Change from: " + job.header().getArchiveKey2() +" To: " + newValue );
						job.header().setArchiveKey1(newValue);
					}
					if(!Skip && !ProcessSpecificCLI.U_ADD_VARIABLE.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_ADD_VARIABLE);
						String VarName = Patterns[0];
						String VarValue = Patterns[1];
						job.values().addValue(VarName, VarValue,false);
						System.out.println("\t ++ UPDATE: Add Variable, Name: " + VarName +", Value: " + VarValue);
					}
					if(!Skip && !ProcessSpecificCLI.U_UPD_VARIABLE.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_UPD_VARIABLE);
						String VarName = Patterns[0];
						String VarValue = Patterns[1];
						job.values().addValue(VarName, VarValue,true);
						System.out.println("\t ++ UPDATE: Update Variable, Name: " + VarName +", Value: " + VarValue );
					}
					if(!Skip && !ProcessSpecificCLI.U_DEL_VARIABLE.equals("")){
						String VarName = ProcessSpecificCLI.U_UPD_VARIABLE;
						job.values().removeValue(VarName);
						System.out.println("\t ++ UPDATE: Delete Variable: " + VarName );
					}
					if(!Skip && !ProcessSpecificCLI.U_HOST.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_HOST);
						String newValue = job.attributes().getHost().toString().replaceAll(Patterns[0], Patterns[1]);
						UC4HostName ucHost = new UC4HostName(newValue);
						System.out.println("\t ++ UPDATE: Job Host Change from: " + job.attributes().getHost() +" To: " + newValue );
						job.attributes().setHost(ucHost);
					}
					if(!Skip && !ProcessSpecificCLI.U_LOGIN.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_LOGIN);
						String newValue = job.attributes().getLogin().toString().replaceAll(Patterns[0], Patterns[1]);
						UC4ObjectName ucLogin = new UC4ObjectName(newValue);
						System.out.println("\t ++ UPDATE: Job Login Change from: " + job.attributes().getLogin() +" To: " + newValue );
						job.attributes().setLogin(ucLogin);
					}
					if(!Skip && ProcessSpecificCLI.U_MAXNUMBERRUN != -1){
						System.out.println("\t ++ UPDATE: Job Max Parralel Runs Change from: " +job.attributes().maxParallel().getParallelTasks() +" To: " + ProcessSpecificCLI.U_MAXNUMBERRUN );
						job.attributes().maxParallel().setParallelTasks(ProcessSpecificCLI.U_MAXNUMBERRUN);
					}
					if(!Skip && ProcessSpecificCLI.U_PRIORITY != -1){
						System.out.println("\t ++ UPDATE: Job Priority Change from: " + job.attributes().getPriority() +" To: " + ProcessSpecificCLI.U_PRIORITY );
						job.attributes().setPriority(ProcessSpecificCLI.U_PRIORITY);
					}
					if(!Skip && !ProcessSpecificCLI.U_ADD_MDATA.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_ADD_MDATA);
						String AttrName = Patterns[0];
						String AttrValue = Patterns[1];
						CustomAttribute custAttr = new CustomAttribute(AttrName, AttrValue);
						job.header().addCustomAttribute(custAttr);
						System.out.println("\t ++ UPDATE: Add Metadata Tag to Job, Name: " + AttrName +" Value: " + AttrValue );
					}
					if(!Skip && !ProcessSpecificCLI.U_DEL_MDATA.equals("")){
						String AttName = ProcessSpecificCLI.U_ADD_MDATA;
						Iterator<CustomAttribute> it = job.header().customAttributeIterator();
						while (it.hasNext()){
							CustomAttribute myAtt = it.next();
							if(myAtt.getName().equalsIgnoreCase(AttName)){
								System.out.println("\t ++ UPDATE: Delete MEtadata Tag from job, Name: " + AttName );
								job.header().removeCustomAttribute(myAtt);
							}
						}
					}
					if(!Skip && !ProcessSpecificCLI.U_POSTPROCESS.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_POSTPROCESS);
						String newValue = job.getPostProcess().replaceAll(Patterns[0], Patterns[1]);
						System.out.println("\t ++ UPDATE: Job PostProcess Update From: \n"
						+ job.getPostProcess() + "\n"
						+ "\t ----- To: \n" +
						newValue.replace("\\n", "\n") +"\n" );
						job.setPostProcess(newValue.replace("\\n", "\n"));
					}
					if(!Skip && !ProcessSpecificCLI.U_PROCESS.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_PROCESS);
						String newValue = job.getProcess().replaceAll(Patterns[0], Patterns[1]);
						
						System.out.println("\t ++ UPDATE: Job Process Update From: \n"
						+ job.getProcess() + "\n"
						+ "\t ----- To: \n" +
						newValue.replace("\\n", "\n") +"\n" );
						job.setProcess(newValue.replace("\\n", "\n"));
					}
					
					if(!Skip && !ProcessSpecificCLI.U_PREPROCESS.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_PREPROCESS);
						String newValue = job.getPreProcess().replaceAll(Patterns[0], Patterns[1]);
						System.out.println("\t ++ UPDATE: Job PreProcess Update From: \n"
						+ job.getPreProcess() + "\n"
						+ "\t ----- To: \n" +
						newValue.replace("\\n", "\n") +"\n" );
						job.setPreProcess(newValue.replace("\\n", "\n"));
					}
					if(!Skip && !ProcessSpecificCLI.U_QUEUE.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_QUEUE);
						String newValue = job.attributes().getQueue().toString().replaceAll(Patterns[0], Patterns[1]);
						UC4ObjectName ucQueue = new UC4ObjectName(newValue);
						System.out.println("\t ++ UPDATE: Job Queue Change from: " + job.attributes().getQueue() +" To: " + newValue );
						job.attributes().setQueue(ucQueue);
					}
					if(!Skip && !ProcessSpecificCLI.U_TZ.equals("")){
						String[] Patterns = consistencyUtils.getUpdatePattern(ProcessSpecificCLI.U_TZ);
						String newValue = job.attributes().getTimezone().toString().replaceAll(Patterns[0], Patterns[1]);
						UC4TimezoneName ucTZ = new UC4TimezoneName(newValue);
						System.out.println("\t ++ UPDATE: Job Timezone Change from: " + job.attributes().getTimezone().toString()+" To: " + newValue );
						job.attributes().setQueue(ucTZ);
					}	

					if(!Skip && !ProcessSpecificCLI.SIMULATE){
						System.out.println(" %% Commiting all Updates Now for Job: " + job.getName());
						Objbroker.common.saveAndCloseObject(job);
					}
			}
		}
	}
}
