package com.automic.specifics;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.automic.std.ExtendedParser;
import com.automic.std.HelpLabels;
import com.automic.std.consistencyUtils;

public class ProcessSpecificCLI {

	// Items For Selection
		public static String NAME = "";
		public static String FILTER_NAME = "";
		public static String FILTER_HOST = "";
		public static String FILTER_LOGIN = "";
		public static String FILTER_QUEUE = "";
		public static String FILTER_TITLE = "";
		public static String FILTER_ACTIVE = "";
		public static String FILTER_INACTIVE = "";
		public static String FILTER_ARCHIVE1 = "";
		public static String FILTER_ARCHIVE2 = "";
		public static String FILTER_VARIABLE_NAME = "";
		public static String FILTER_VARIABLE_VALUE = "";
		public static String FILTER_PROCESS_KEYWORD = "";
		public static String FILTER_PREPROCESS_KEYWORD = "";
		public static String FILTER_POSTPROCESS_KEYWORD = "";
		public static String FILTER_METADATA_NAME = "";
		public static String FILTER_METADATA_VALUE = "";
	
	// Items For Modification
		public static String U_LOGIN = ""; 						//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_HOST = ""; 						//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_PROCESS = ""; 					//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_TITLE = ""; 						//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_QUEUE = ""; 						//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_TZ = ""; 						//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static int U_PRIORITY = -1; 						// "123"
		public static String U_GENERATEATRUNTIME = ""; 			// "Y" or "N"
		public static int U_MAXNUMBERRUN = -1; 					// "123"
		public static String U_ACTIVE = "";  					// "Y" or "N"
		public static String U_INACTIVE = "";  					// "Y" or "N"
		public static String U_PREPROCESS = ""; 				//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_POSTPROCESS = ""; 				//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_ADD_VARIABLE = ""; 				//["&TEST#","1234"]
		public static String U_UPD_VARIABLE = ""; 				//["&TEST#","1234"]	
		public static String U_DEL_VARIABLE = ""; 				//"&VARNAME"
		public static String U_ARCH1 = ""; 						//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_ARCH2 = ""; 						//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		public static String U_ADD_MDATA = "";					//["DEF","GENERAL"] or [".*","LOGIN.GENERAL"]
		//public static String U_UPD_MDATA = ""; 				//  ----  cannot be done directly
		public static String U_DEL_MDATA = ""; 					//"GEOGRAPHY"

	// Other things
		public static boolean SIMULATE = true;
		public static boolean COMMIT = false;
	
		
	public static void processSpecificParameters(String[] args) throws ParseException {
		
		CommandLineParser parser = new ExtendedParser(true);
		Options options = new Options();

		// 1- add your options below this line
		options.addOption( "name", true, HelpLabels.MANDATORY+HelpLabels.META_FILTER+"Name.\n" );
		options.addOption( "f_name", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Name.");
		options.addOption( "f_host", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"HOST." );
		options.addOption( "f_login", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"LOGIN." );
		options.addOption( "f_queue", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"QUEUE." );
		options.addOption( "f_title", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"TITLE." );
		options.addOption( "f_active", false, HelpLabels.OPTIONAL+HelpLabels.BOOLEAN_FILTER+"Active or Inactive State." );
		options.addOption( "f_arch1", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Archive key 1." );
		options.addOption( "f_arch2", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Archive Key 2." );
		options.addOption( "f_varname", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Variable Name." );
		options.addOption( "f_varvalue", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Variable Value." + "\n" );
		options.addOption( "f_mdataname", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Metadata Name." );
		options.addOption( "f_mdatavalue", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Metadata Value." );
		options.addOption( "f_process", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"keyword in Process." );
		options.addOption( "f_postprocess", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"keyword in PostProcess.");
		options.addOption( "f_preprocess", true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"keyword in PreProcess.");
		
		options.addOption( "u_login", true,  HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update LOGIN FROM value." );
		options.addOption( "u_host", true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update HOST FROM value.");
		options.addOption( "u_process", true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update PROCESS.");
		options.addOption( "u_title", true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update TITLE value.");
		options.addOption( "u_queue", true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update QUEUE value.");
		options.addOption( "u_timezone", true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update TIMEZONE value." );
		options.addOption( "u_addvar", true, HelpLabels.OPTIONAL+HelpLabels.VARIABLE_UPDATE+ "Add Variable.");
		options.addOption( "u_updvar", true, HelpLabels.OPTIONAL+HelpLabels.VARIABLE_UPDATE+ "Update Variable Value.");
		options.addOption( "u_delvar", true, HelpLabels.OPTIONAL+HelpLabels.STD_UPDATE+ "Delete Variable.");
		options.addOption( "u_priority", true,  HelpLabels.OPTIONAL+HelpLabels.INT_UPDATE+ "Update Priority.");
		options.addOption( "u_genatruntime", true,  HelpLabels.OPTIONAL+HelpLabels.BOOLEAN_UPDATE+ "Generate At Runtime.");
		options.addOption( "u_active", true, HelpLabels.OPTIONAL+HelpLabels.BOOLEAN_UPDATE+ "Activate / Deactivate.");
		options.addOption( "u_postprocess", true,  HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update PostProcess.");
		options.addOption( "u_preprocess", true,  HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update PreProcess.");
		options.addOption( "u_maxruns", true,  HelpLabels.OPTIONAL+HelpLabels.INT_UPDATE+ "Max Parallel Runs.");
		options.addOption( "u_arch1", true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update Archive Key 1 value.");
		options.addOption( "u_arch2", true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update Archive Key 2 value.");
		options.addOption( "u_addmdata", true,  HelpLabels.OPTIONAL+HelpLabels.VARIABLE_UPDATE+ "Add Metadata Tag.");
		options.addOption( "u_delmdata", true,  HelpLabels.OPTIONAL+HelpLabels.STD_UPDATE+ "Delete Metadata Tag.");

		options.addOption( "commit", false, "[OPTIONAL] Commit Update (By Default, only a simulation is ran)"  + "\n" );
		//options.addOption( "simulatelist", false, "[OPTIONAL] Simulate Update (does not update anything)" );
		
		//options.addOption( "varadd", false, "[OPTIONAL] Add Variable and Value to Job \n"
		//		+ "	  => Requires -variable option (represents Variable name)\n"
		//		+ "	  => Requires -value option (represents Variable value)\n");
	
		options.addOption( "h", "help", false, "display help\n");
		
		// parse the command line arguments
		CommandLine line = parser.parse( options, args );
	
		// 2- retrieve the value of required parameters
	 
		// processing filter parameters
		if( line.hasOption( "name" )) {NAME = line.getOptionValue("name");}
		if( line.hasOption( "f_name" )) {FILTER_NAME = line.getOptionValue("f_name");}
		if( line.hasOption( "f_host" )) {FILTER_HOST = line.getOptionValue("f_host");}
		if( line.hasOption( "f_login" )) {FILTER_LOGIN = line.getOptionValue("f_login");}
		if( line.hasOption( "f_queue" )) {FILTER_QUEUE = line.getOptionValue("f_queue");}
		if( line.hasOption( "f_title" )) {FILTER_TITLE = line.getOptionValue("f_title");}
		if( line.hasOption( "f_active" )) {FILTER_ACTIVE = line.getOptionValue("f_active");}
		if( line.hasOption( "f_arch1" )) {FILTER_ARCHIVE1 = line.getOptionValue("f_arch1");}
		if( line.hasOption( "f_arch2" )) {FILTER_ARCHIVE2 = line.getOptionValue("f_arch2");}
		if( line.hasOption( "f_varname" )) {FILTER_VARIABLE_NAME = line.getOptionValue("f_varname");}
		if( line.hasOption( "f_varvalue" )) {FILTER_VARIABLE_VALUE = line.getOptionValue("f_varvalue");}
		if( line.hasOption( "f_process" )) {FILTER_PROCESS_KEYWORD = line.getOptionValue("f_process");}
		if( line.hasOption( "f_preprocess" )) {FILTER_PREPROCESS_KEYWORD = line.getOptionValue("f_preprocess");}
		if( line.hasOption( "f_postprocess" )) {FILTER_POSTPROCESS_KEYWORD = line.getOptionValue("f_postprocess");}
		if( line.hasOption( "f_mdataname" )) {FILTER_METADATA_NAME = line.getOptionValue("f_mdataname");}
		if( line.hasOption( "f_mdatavalue" )) {FILTER_METADATA_VALUE = line.getOptionValue("f_mdatavalue");}
		// processing update parameters

	    // 4- check only mandatory parameters!!
	    boolean ERROR_FREE=true;
		
		if( line.hasOption( "u_host" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_host"))){U_HOST = line.getOptionValue("u_host");}else{System.out.println(" -- Error in Value for u_host. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_login" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_login"))){U_LOGIN = line.getOptionValue("u_login");}else{System.out.println(" -- Error in Value for u_login. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_process" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_process"))){U_PROCESS = line.getOptionValue("u_process");}else{System.out.println(" -- Error in Value for u_process. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_title" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_title"))){U_TITLE = line.getOptionValue("u_title");}else{System.out.println(" -- Error in Value for u_title. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_queue" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_queue"))){U_QUEUE = line.getOptionValue("u_queue");}else{System.out.println(" -- Error in Value for u_queue. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_timezone" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_timezone"))){U_TZ = line.getOptionValue("u_timezone");}else{System.out.println(" -- Error in Value for u_timezone. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_addvar" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_addvar"))){U_ADD_VARIABLE = line.getOptionValue("u_addvar");}else{System.out.println(" -- Error in Value for u_addvar. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_updvar" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_updvar"))){U_UPD_VARIABLE = line.getOptionValue("u_updvar");}else{System.out.println(" -- Error in Value for u_updvar. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_delvar" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_delvar"))){U_DEL_VARIABLE = line.getOptionValue("u_delvar");}else{System.out.println(" -- Error in Value for u_delvar. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		
		if( line.hasOption( "u_priority" )) {
			if(consistencyUtils.isInteger(line.getOptionValue("u_priority"))){U_PRIORITY = Integer.parseInt((line.getOptionValue("u_priority")));
			}else{System.out.println(" -- Error: Cannot Update Priority to non Integer value..");ERROR_FREE=false;}}	
		if( line.hasOption( "u_maxruns" )) {
			if(consistencyUtils.isInteger(line.getOptionValue("u_maxruns"))){U_MAXNUMBERRUN = Integer.parseInt((line.getOptionValue("u_maxruns")));
			}else{System.out.println(" -- Error: Cannot Update Max Parallel Runs to non Integer value..");ERROR_FREE=false;}}	
		if( line.hasOption( "u_genatruntime" )) {
			if(consistencyUtils.checkStringIsYorN(line.getOptionValue("u_genatruntime"))){U_GENERATEATRUNTIME = line.getOptionValue("u_genatruntime");
			}else{System.out.println(" -- Error: genatruntime needs to be set to Y or N..");ERROR_FREE=false;}}
		if( line.hasOption( "u_active" )) {
			if(consistencyUtils.checkStringIsYorN(line.getOptionValue("u_active"))){U_ACTIVE = line.getOptionValue("u_active");
			}else{System.out.println(" -- Error: active needs to be set to Y or N..");ERROR_FREE=false;}}
		
		if( line.hasOption( "u_preprocess" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_preprocess"))){U_PREPROCESS = line.getOptionValue("u_preprocess");}else{System.out.println(" -- Error in Value for u_preprocess. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_postprocess" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_postprocess"))){U_POSTPROCESS = line.getOptionValue("u_postprocess");}else{System.out.println(" -- Error in Value for u_postprocess. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_arch1" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_arch1"))){U_ARCH1 = line.getOptionValue("u_arch1");}else{System.out.println(" -- Error in Value for u_arch1. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_arch2" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_arch2"))){U_ARCH2 = line.getOptionValue("u_arch2");}else{System.out.println(" -- Error in Value for u_arch2. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_addmdata" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_addmdata"))){U_ADD_MDATA = line.getOptionValue("u_addmdata");}else{System.out.println(" -- Error in Value for u_addmdata. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		//if( line.hasOption( "u_updmdata" )) {if(checkValueStructure(line.getOptionValue("u_updmdata"))){U_UPD_MDATA = line.getOptionValue("u_updmdata");}else{System.out.println(" -- Error in Value for u_updmdata. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( "u_delmdata" )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("u_delmdata"))){U_DEL_MDATA = line.getOptionValue("u_delmdata");}else{System.out.println(" -- Error in Value for u_delmdata. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}	
		
	    if( line.hasOption( "commit" )) {SIMULATE = false;}

	    if(NAME.equals("")){System.out.println(" -- Error: Missing Mandatory Parameter: -name. Use -h for help.");ERROR_FREE=false;}

	    // We need to add a & in front of the variable name if it isnt provided..
	  //  VARNAME = VARNAME.toUpperCase();
	  //  if(!VARNAME.startsWith("&")){
	  //  	VARNAME = "&"+VARNAME;
	   // }
	  
	    
	    // 3- keep the following one.. it will display help automatically
	    if( line.hasOption( "h" )) {
	    	System.out.println("Program: "+Version.getName() + " - " +Version.getDescription());
	    	System.out.println("Version: "+Version.getVersion()+"\n");
	    	HelpFormatter formatter = new HelpFormatter();
	    	formatter.printHelp(300,"java -jar Command.jar","\n++ Parameters Are: \n\n", options,"", true );
	    	
	    	System.out.println(" === Examples: ===");
	    	System.out.println("\t Update Process Tab (replace an IP adr by another one) for all JOBS objects with a name matching *JOBS.ABC* with a LOGIN containing *GENERAL");
	    	System.out.println(" \t => java -jar command.jar -name \"*JOBS.ABC*\" -f_login \".*GENERAL\" -u_process [\"192.168.1.123\",\"192.861.1.321\"] \n"  );
	    	System.out.println("\t list all JOBS objects running on a Queue matching *SYS* ");
	    	System.out.println(" \t => java -jar command.jar -name \"*\" -f_queue \".*SYS.*\" \n"  );
	    	System.exit(0);
	    }
	    
	    if(!ERROR_FREE){
	    	System.out.println("\n -- Error(s) encountered. Please fix them and rerun.");
	    	System.exit(1);
	    }
		
	}
}