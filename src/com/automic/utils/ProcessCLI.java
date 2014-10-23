package com.automic.utils;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import com.automic.config.QueryListXML;
import com.automic.config.QueryObjectXML;
import com.automic.enums.ActionTypesDispatcher;


public class ProcessCLI {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ParseException, ParserConfigurationException, SAXException, XPathExpressionException{
		
		String Object ="";
		String Action = "";
		String Params = "";
		// create the command line parser
		CommandLineParser parser = new BasicParser();

		/*
		 *  cli --object CLIENT --action SET_VARIABLE "VARIABLE=VALUE"
			cli --do SEARCH  "OBJECT=*;NAME=*;LABEL=*"
			cli --object AGENT --action START
			cli --do EXPORT "OBJECT=*;NAME=*;LABEL=*" > toFile
			cli --do IMPORT "FILE=/tmp/file.import"
			cli --object TRANSPORT_CASE --action ADD_OBJECT "OBJECT=*;NAME=*;LABEL=*"
			cli --object JOBP --action ADD_JOB "NAME=MYJOB"
			cli --object JOBP --action LINK_JOB "NAME=MYJOB;PREDECESSOR=START;SUCCESSOR=END"
			cli --object CALE --action GET_OBJECT_VERSIONS "NAME=MY.CALE.1"
			cli --object FOLD --action LIST
			cli --object FOLD -- action SHOW "NAME=MYFOLD"
			cli --object ACTIVITY --action LIST
			cli --object ACTIVITY --action SHOW "NAME=*"
			
			cli [--object or --do]
				--object 
					-> --action
						ACTION NAME (ADD_JOB etc. Depends on the Object)
							PARAMETERS 
			
				--do 
					-> OPERATION (EXPORT / IMPORT etc)
		 */
		// create the Options
		Options options = new Options();
		options.addOption( "o", "object", true, "Pick an Object Type" );
		options.addOption( "a", "action", true, "Pick an Action to apply to Objects" );
		options.addOption( "p", "params", true, "Parameters");
		options.addOption( "h", "help", false, "display help");
		
		//args = new String[]{ "--object", "JOBS", "--action", "SHOW", "--params","NAME=JOBS.WIN.TEST" };

		QueryListXML queryList = new QueryListXML("OBJECT_list.xml");
		    // parse the command line arguments
		    CommandLine line = parser.parse( options, args );
		    
		    if(line.hasOption('h') && !line.hasOption( 'o' ) && !line.hasOption( 'a' )) {
		    	Help.displayHelp();
		    	System.exit(88);
		    }
		    
		    if( !line.hasOption( 'o' )) {
		    	System.out.println( " -- Error, No object specified!");
		    	System.out.println( "%% => Objects Available: "+queryList.getListObject());  
		    	System.out.println( "%% => Example: "+"--object "+ queryList.getListObject().get(0));  
		    	System.exit(99);
		    }else{
		    	Object = line.getOptionValue('o');
		    	if(!queryList.doesObjectExist(Object)){
		    		System.out.println( " -- Error, Object is not a valid one!");
		    		System.out.println( "%% => Objects Available: "+queryList.getListObject());  
			    	System.exit(99);
		    	}
		    }
		    
		    if(line.hasOption('h') && line.hasOption( 'o' ) && !line.hasOption( 'a' )) {
		    	Help.displayHelpWithObject(Object);
		    	System.exit(88);
		    }
		    
		    QueryObjectXML query = new QueryObjectXML(Object+"_config.xml");
		    
		    if(!line.hasOption('a')){ 	
		    	System.out.println(" -- Error, No Action specified for Object " + Object + " !");
		    	System.out.println( "%% => Actions Available: "+query.getListActionForObject(Object)); 
		    	System.out.println( "%% => Example: "+"--action "+ query.getListActionForObject(Object).get(0));  
		    	System.exit(99);
		    }else{
		    	Action = line.getOptionValue('a');
		    	if(!query.doesActionExist(Object, Action)){
		    		System.out.println( " -- Error, Action is not a valid one for Object "+ Object + " !");
		    		System.out.println( "%% => Actions Available: "+query.getListActionForObject(Object));  
			    	System.exit(99);
		    }
			    if(line.hasOption('h') && line.hasOption( 'o' ) && line.hasOption( 'a' )) {
			    	Help.displayHelpWithObjectAndAction(Object,Action);
			    	System.exit(88);
			    }
		    }
		    Params = line.getOptionValue('p');
		    if(Params == null){Params = "";}
			if(query.doesActionRequireParameters(Object, Action)){
				if(Params == null || Params.equals("")){
					System.out.println(" -- Error, mandatory parameters missing!");
					System.out.println(" %% => List of ALL Parameters: " + query.getListParametersForAction(Object,Action));
					System.out.println(" %% => List of MANDATORY Parameters: " + query.getListMandatoryParametersForAction(Object,Action));
					System.out.println(" %% => FORMAT TO USE: --params \"NAME1=VALUE1;NAME2=VALUE2\"");
					System.exit(99);
				}
				Parameters params = new Parameters(Params);	
				if(!params.areAllParamsOK()){
					System.out.println(" -- Error processing parameters!");
					System.exit(1);
				}
				String[] AllParams = params.getSanitizedParameters();
				ArrayList<String> MandatoryParams = query.getListMandatoryParametersForAction(Object,Action);
				for(String param : AllParams){
					String Name = param.split("=")[0];
					if(!MandatoryParams.contains(Name)){
						System.out.println(" -- Error, Mandatory Parameter Missing.");
						System.out.println("\n %% => List of all Parameters Available:");
						System.out.println(query.getListParametersForAction(Object,Action));
						System.out.println("\n %% => List of all MANDATORY Parameters:");
						System.out.println(query.getListMandatoryParametersForAction(Object,Action));
					}
				}
			}
					
		ActionTypesDispatcher dispatcher = new ActionTypesDispatcher(Object, Action, Params);
					
		}
		
}


