package com.automic.utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ProcessSpecificCLI {

	public static String JOBNAME = "";
	public static String JOBLOGIN = "";
	public static String JOBHOST = "";
	public static String JOBPROCESS = "";
	public static String JOBTEMPLATE = "";
	public static String JOBFOLDER = "";
	
	public static void processSpecificParameters(String[] args) throws ParseException {
		
		CommandLineParser parser = new ExtendedParser(true);
		Options options = new Options();
		
		/*
		 *  cli --Jobname MY.JOB --Joblogin LOGIN.GENERAL --Jobhost WIN03V11 --Jobprocess "echo my job is running"
		 * 
		 * DO NOT USE THE FOLLOWING OPTIONS (they are already taken), basically use anything lowercase:
		 * H: AE Hostname or IP
		 * P: Port number
		 * C: Client number
		 * L: Login
		 * D: Department
		 * W: Password
		 * help: display help
		 * 
		 */

		// 1- add your options below this line
		options.addOption( "jobname", "jobname", true, "[MANDATORY] Job Name To Create" );
		options.addOption( "login", "login", true, "[OPTIONAL] Login used in Job" );
		options.addOption( "host", "host", true, "[OPTIONAL] Host used in Job");
		options.addOption( "process", "process", true, "[OPTIONAL] Process for Job");
		options.addOption( "template", "template", true, "[MANDATORY] Template for Job [JOBS.WIN, JOBS.UNX, etc.]");
		options.addOption( "folder", "folder", true, "[MANDATORY] Existing folder where Job is to be created");
		options.addOption( "h", "help", false, "display help");
		
		// parse the command line arguments
		CommandLine line = parser.parse( options, args );
	
		// 2- retrieve the value pf required parameters
	    if( line.hasOption( "jobname" )) {JOBNAME = line.getOptionValue("jobname");}
	    if( line.hasOption( "login" )) {JOBLOGIN = line.getOptionValue("login");}
	    if( line.hasOption( "host" )) {JOBHOST = line.getOptionValue("host");}
	    if( line.hasOption( "process" )) {JOBPROCESS = line.getOptionValue("process");}
	    if( line.hasOption( "template" )) {JOBTEMPLATE = line.getOptionValue("template");}
	    if( line.hasOption( "folder" )) {JOBFOLDER = line.getOptionValue("folder");}
	    // 3- keep the following one.. it will display help automatically
	    if( line.hasOption( "h" )) {
	    	HelpFormatter formatter = new HelpFormatter();
	    	formatter.printHelp(100,"java -jar Command.jar","\n++ Parameters Are: \n\n", options,"", true );
	    	System.exit(0);
	    }
	    
	    // 4- check only mandatory parameters!!
	    boolean ERROR_FREE=true;
	    if(JOBNAME.equals("")){System.out.println(" -- Error: Missing Jobname. Use -h for help.");ERROR_FREE=false;}
	    if(JOBTEMPLATE.equals("")){System.out.println(" -- Error: Missing Job Template for job. Use -h for help.");ERROR_FREE=false;}
	    if(JOBFOLDER.equals("")){System.out.println(" -- Error: Missing Folder name for job. Use -h for help.");ERROR_FREE=false;}
	    
	    if(!ERROR_FREE){
	    	System.out.println("\n -- Error(s) encountered. Please fix them and rerun.");
	    	System.exit(1);
	    }
	}
}