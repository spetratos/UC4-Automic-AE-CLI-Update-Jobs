package com.automic.utils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.automic.ConnectionManager;
import com.automic.enums.ClientActionTypes;
import com.automic.enums.DoTypes;
import com.automic.enums.ObjectTypes;
import com.automic.objects.clients.ClientStuff;
import com.uc4.api.objects.UC4Object;
import com.uc4.communication.Connection;


public class ProcessCLI {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException{
		
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
		args = new String[]{ "--object", "CLIENT", "--action", "LIST", "--params","VARIABLE=VALUE" };

		try {
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
		    	if(!ObjectTypes.contains(Object)){
		    		System.out.println(" -- Wrong Object Type. Here is a list of available ones:");
		    		System.out.println(java.util.Arrays.asList(ObjectTypes.values()));
		    	}
		    	//
		    	String ClassName = ObjectTypes.valueOf(Object).getActionTypeClass();
		    	if(!ClientActionTypes.contains(Action)){
		    		System.out.println(" -- Error, Action is not compatible with Object "+Object+". here is the list:");
		    		System.out.println(java.util.Arrays.asList(ClientActionTypes.values()));
		    	}
		    	
		    	ClassLoader classLoader = ProcessCLI.class.getClassLoader();

		        try {
		            Class aClass = classLoader.loadClass(ClassName);
		            aClass.
		            System.out.println("aClass.getName() = " + aClass.getName());
		        } catch (ClassNotFoundException e) {
		            e.printStackTrace();
		        }
		        
		    
	
		    		ProcessObjects myObj = new ProcessObjects(Object,ObjectTypes.valueOf(Object).getActionTypeClass());
		    	System.out.println(ObjectTypes.valueOf(Object).getActionTypeClass());
		    	
	
		    	
		    	if(!ObjectTypes.contains(Object)){
		    		System.out.println(" -- Wrong Object Type. Here is a list of available ones:");
		    		System.out.println(java.util.Arrays.asList(ObjectTypes.values()));
		    	}
		    	
		    }
		    if(line.hasOption('d')){
		    	Do = line.getOptionValue('d');
		    	Params = line.getOptionValue('p');
		    	if(!DoTypes.contains(Do)){
		    		System.out.println(" -- Wrong Do Type. Here is a list of available ones:");
		    		System.out.println(java.util.Arrays.asList(DoTypes.values()));
		    	}
		    }
		    
		    
		    System.out.println(Object+":"+Action+":"+Params);
		}
		catch( ParseException exp ) {
		    System.out.println( "Unexpected exception:" + exp.getMessage() );
		} 
	}
}
