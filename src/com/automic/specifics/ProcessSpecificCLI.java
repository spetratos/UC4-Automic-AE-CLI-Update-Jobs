package com.automic.specifics;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.automic.std.ExtendedParser;

public class ProcessSpecificCLI {

	public static String JOBNAME = "";
	public static String JOBLOGIN = "";
	public static String JOBHOST = "";
	public static String JOBPROCESS = "";
	//public static String JOBTEMPLATE = "";
	//public static String JOBFOLDER = "";
	public static String JOBTITLE = "";
	public static String JOBQUEUE = "";
	public static String JOBTZ = "";
	public static int JOBPRIORITY = 0;
	public static boolean JOBGENERATEATRUNTIME = false;
	public static int JOBMAXNUMBERRUN = 0;
	public static boolean JOBACTIVE = false;
	public static boolean JOBINACTIVE = false;
	public static String JOBPREPROCESS = "";
	public static String JOBPOSTPROCESS = "";
	
	public static boolean ADDVARIABLE = false;
	public static boolean REMOVEVARIABLE = false;
	public static boolean UPDATEVARIABLE = false;
	
	public static boolean ERRORINVARIABLE = false;
	
	public static String VARNAME = "";
	public static String VARVALUE = "";
	
	public static boolean SIMULATE = false;
	
	public static void processSpecificParameters(String[] args) throws ParseException {
		
		CommandLineParser parser = new ExtendedParser(true);
		Options options = new Options();

		// 1- add your options below this line
		options.addOption( "jobname", true, "[MANDATORY] Job Name To Update (can use '*' or '?')" );
		//options.addOption( "template", "template", true, "[MANDATORY] Template for Job [JOBS.WIN, JOBS.UNX, etc.]");
		//options.addOption( "folder", "folder", true, "[MANDATORY] Existing folder where Job is to be created");
		options.addOption( "login", true, "[OPTIONAL] Login used in Job" );
		options.addOption( "host", true, "[OPTIONAL] Host used in Job");
		options.addOption( "process", true, "[OPTIONAL] Process for Job");
		options.addOption( "title", true, "[OPTIONAL] Title for Job");
		options.addOption( "queue", true, "[OPTIONAL] Queue for Job");
		options.addOption( "timezone", true, "[OPTIONAL] Timezone for Job");
		options.addOption( "priority", true, "[OPTIONAL] Priority for Job");
		options.addOption( "genatruntime", false, "[OPTIONAL] FLAG - Generate at Runtime");
		options.addOption( "simulate", false, "[OPTIONAL] Simulate Update (does not update anything)" );
		//options.addOption( "maxparallelrun", "maxparallelrun", true, "[OPTIONAL] Max Parallel Runs for Job (Default:0)");
		options.addOption( "inactive", false, "[OPTIONAL] - Deactivate Job");
		options.addOption( "active", false, "[OPTIONAL] - Activate Job");
		options.addOption( "preprocess", true, "[OPTIONAL] Pre-Process for Job");
		options.addOption( "postprocess", true, "[OPTIONAL] Post-Process for Job");
		
		options.addOption( "varadd", false, "[OPTIONAL] Add Variable and Value to Job \n"
				+ "	  => Requires -variable option (represents Variable name)\n"
				+ "	  => Requires -value option (represents Variable value)\n");
		options.addOption( "varrem", false, "[OPTIONAL] Remove Variable from Job \n"
							+ "	  => Requires -variable option (represents Variable name)\n"
							+ "	  => Requires -value option (represents Variable value)\n");
		options.addOption( "varupd", false, "[OPTIONAL] Update Value of a Variable in Job \n"
				+ "	  => Requires -variable option (represents Variable name)\n");
		
		options.addOption( "variable", true, ".. only used with varupd, varrem and varadd options");
		options.addOption( "value", true, ".. only used with varupd and varadd options");
	
		options.addOption( "h", "help", false, "display help");
		
		// parse the command line arguments
		CommandLine line = parser.parse( options, args );
	
		// 2- retrieve the value pf required parameters
	    if( line.hasOption( "jobname" )) {JOBNAME = line.getOptionValue("jobname");}
	    if( line.hasOption( "login" )) {JOBLOGIN = line.getOptionValue("login");}
	    if( line.hasOption( "host" )) {JOBHOST = line.getOptionValue("host");}
	    if( line.hasOption( "process" )) {JOBPROCESS = line.getOptionValue("process");}
	   // if( line.hasOption( "template" )) {JOBTEMPLATE = line.getOptionValue("template");}
	   // if( line.hasOption( "folder" )) {JOBFOLDER = line.getOptionValue("folder");}
	    if( line.hasOption( "title" )) {JOBTITLE = line.getOptionValue("title");}
	    if( line.hasOption( "queue" )) {JOBQUEUE = line.getOptionValue("queue");}
	    if( line.hasOption( "timezone" )) {JOBTZ = line.getOptionValue("timezone");}
	    if( line.hasOption( "simulate" )) {SIMULATE = true;}
	    
	    boolean LockOnVariableAction = false;
	    
	    if( line.hasOption("varadd")){
	    	if(!LockOnVariableAction){
	    		ADDVARIABLE = true;
	    		LockOnVariableAction=true;
	    		if(! line.hasOption( "variable" )){
	    			System.out.println(" -- Error. Variable Name missing. Parameter to add is \"-variable\"");
	    			ERRORINVARIABLE = true;
	    		}
	    		if(! line.hasOption( "value" )){
	    			System.out.println(" -- Error. Variable Value missing. Parameter to add is \"-value\"");
	    			ERRORINVARIABLE = true;
	    		}
	    		if (!ERRORINVARIABLE){
	    			VARNAME = line.getOptionValue("variable");
	    			VARVALUE = line.getOptionValue("value");
	    		}
	    	
	    	}
	    }

	    if( line.hasOption("varupd")){
	    	if(!LockOnVariableAction){
	    		UPDATEVARIABLE = true;LockOnVariableAction=true;

	    		if(! line.hasOption( "variable" )){
	    			System.out.println(" -- Error. Variable Name missing. Parameter to add is \"-variable\"");
	    			ERRORINVARIABLE = true;
	    		}
	    		if(! line.hasOption( "value" )){
	    			System.out.println(" -- Error. Variable Value missing. Parameter to add is \"-value\"");
	    			ERRORINVARIABLE = true;
	    		}
	    		if (!ERRORINVARIABLE){
	    			VARNAME = line.getOptionValue("variable");
	    			VARVALUE = line.getOptionValue("value");
	    		}
	    	}
	    }
	    
	    if( line.hasOption("varrem")){
	    	if(!LockOnVariableAction){
	    		REMOVEVARIABLE = true;LockOnVariableAction=true;

	    		if(! line.hasOption( "variable" )){
	    			System.out.println(" -- Error. Variable Name missing. Parameter to add is \"-variable\"");
	    			ERRORINVARIABLE = true;
	    		}

	    		if (!ERRORINVARIABLE){
	    			VARNAME = line.getOptionValue("variable");
	    		}
	    	
	    	}
	    }

	    // We need to add a & in front of the variable name if it isnt provided..
	    VARNAME = VARNAME.toUpperCase();
	    if(!VARNAME.startsWith("&")){
	    	VARNAME = "&"+VARNAME;
	    }
	    if( line.hasOption( "priority" )) {
	    	if(isInteger(line.getOptionValue("priority"))){
    		JOBPRIORITY = Integer.parseInt(line.getOptionValue("priority"));}
    	else{
    		System.out.println(" -- Error. Priority is expected as a Number: "+line.getOptionValue("priority"));}
	    }
	    if( line.hasOption( "genatruntime" )) {JOBGENERATEATRUNTIME = true;}
	  //  if( line.hasOption( "maxparallelrun" )) {
	  //  	if(isInteger(line.getOptionValue("maxparallelrun"))){
	  //  		JOBMAXNUMBERRUN = Integer.parseInt(line.getOptionValue("maxparallelrun"));
	  //  	}else{
	  //  		System.out.println(" -- Error. Job Max Parallel Runs is expected as a Number: "+line.getOptionValue("maxparallelrun"));}
	  //  }
	    if( line.hasOption( "active" )) {JOBACTIVE = true;}
	    if( line.hasOption( "inactive" )) {JOBACTIVE = true;}
	    if( line.hasOption( "preprocess" )) {JOBPREPROCESS = line.getOptionValue("preprocess");}
	    if( line.hasOption( "postprocess" )) {JOBPOSTPROCESS = line.getOptionValue("postprocess");}
	    
	    // 3- keep the following one.. it will display help automatically
	    if( line.hasOption( "h" )) {
	    	System.out.println("Program: "+Version.getName() + " - " +Version.getDescription());
	    	System.out.println("Version: "+Version.getVersion()+"\n");
	    	HelpFormatter formatter = new HelpFormatter();
	    	formatter.printHelp(100,"java -jar Command.jar","\n++ Parameters Are: \n\n", options,"", true );
	    	System.exit(0);
	    }
	    
	    // 4- check only mandatory parameters!!
	    boolean ERROR_FREE=true;
	    if(JOBNAME.equals("")){System.out.println(" -- Error: Missing Jobname. Use -h for help.");ERROR_FREE=false;}
	   // if(JOBTEMPLATE.equals("")){System.out.println(" -- Error: Missing Job Template for job. Use -h for help.");ERROR_FREE=false;}
	    //if(JOBFOLDER.equals("")){System.out.println(" -- Error: Missing Folder name for job. Use -h for help.");ERROR_FREE=false;}
	    
	    if(!ERROR_FREE){
	    	System.out.println("\n -- Error(s) encountered. Please fix them and rerun.");
	    	System.exit(1);
	    }
		
	}
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

}