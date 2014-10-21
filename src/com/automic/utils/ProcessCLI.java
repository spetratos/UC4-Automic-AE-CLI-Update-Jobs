package com.automic.utils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import com.automic.ConnectionManager;
import com.automic.config.QueryXML;
import com.automic.enums.ActionTypes;
import com.automic.enums.ActionTypesDispatcher;
import com.automic.enums.ClientActionTypes;
import com.automic.enums.DoTypes;
import com.automic.enums.ObjectTypes;
import com.automic.objects.clients.ClientBroker;
import com.uc4.api.objects.UC4Object;
import com.uc4.communication.Connection;


public class ProcessCLI {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ParseException, ParserConfigurationException, SAXException, XPathExpressionException{
		
		String Object ="";
		String Action = "";
		String Params = "";
		String Do = "";
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
		options.addOption( "d", "do", true, "Execute a General action");
		options.addOption( "p", "params", true, "Parameters");
		args = new String[]{ "--object", "JOBS", "--action", "LIST", "--params","PRIORITY4=14" };

		
		    // parse the command line arguments
		    CommandLine line = parser.parse( options, args );
		
		    if( !line.hasOption( 'o' ) && !line.hasOption('d') ) {System.out.println( "Should get at least object or do" );}   
		    if(line.hasOption('o')){
		    	if(!line.hasOption('a')){
		    		System.out.println("Should specify an action with an object!");}
		    } 
		    if(line.hasOption('d')){
		    	if(line.hasOption('o')){System.out.println("cant have object with DO");}
		    	if(line.hasOption('a')){System.out.println("cant have action with DO");}
		    	if(line.hasOption('p')){System.out.println("should have params with DO");}
		    }
		    if(line.hasOption('a') || line.hasOption('o')){
		    	if(line.hasOption('d')){System.out.println("cant have DO and Object or Action");}
		    	//if(!line.hasOption('p')){System.out.println("should have params with action or object");}
		    }
		    if(line.hasOption('o') && line.hasOption('a')){ 	
		    	Object = line.getOptionValue('o');
		    	Action = line.getOptionValue('a');
		    	if(line.hasOption('p')){Params = line.getOptionValue('p');}
		    	//if(!ObjectTypes.contains(Object)){
		    	//	System.out.println(" -- Wrong Object Type. Here is a list of available ones:");
		    	//	System.out.println(java.util.Arrays.asList(ObjectTypes.values()));
		    	//}
		    }

					QueryXML query = new QueryXML();
					
					if(!query.doesObjectExist(Object)){
						System.out.println(" -- Error, Object "+Object+" is not available. Here is the list of Objects Available:");
						System.out.println(query.getListObject().toString());
						System.exit(1);
					}
					if(!query.doesActionExist(Object, Action)){
						System.out.println(" -- Error, Action "+ Action +" for Object "+ Object + " Does not exist. Here is the list of Actions available:");
						System.out.println(query.getListActionForObject(Object).toString());
						System.exit(1);
					}
					if(query.doesActionRequireParameters(Object, Action)){
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
					
					// initiate Broker based on object and Action and Parameters
					// 
					ActionTypesDispatcher dispatcher = new ActionTypesDispatcher(Object, Action, Params);
					
		}
		
}


