package com.automic.specifics;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.automic.std.ExtendedParser;
import com.automic.std.HelpLabels;
import com.automic.std.ProcessSpecCLI;
import com.automic.std.consistencyUtils;

public class ProcessSpecificCLI extends ProcessSpecCLI{
	
	public ProcessSpecificCLI(){
		super();
	}

	static boolean ERROR_FREE = true;

	public boolean processSpecificParameters(String[] args) throws ParseException {
		
		Options options=LoadDefaultOptions();
		options.addOption( L_NAME, true, HelpLabels.MANDATORY+HelpLabels.META_FILTER+"Name.\n" );
		options.addOption( L_F_NAME, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Name.");
		options.addOption( L_F_HOST, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"HOST." );
		options.addOption( L_F_LOGIN, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"LOGIN." );
		options.addOption( L_F_QUEUE, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"QUEUE." );
		options.addOption( L_F_TITLE, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"TITLE." );
		options.addOption( L_F_ACTIVE, false, HelpLabels.OPTIONAL+HelpLabels.BOOLEAN_FILTER+"Active or Inactive State." );
		options.addOption( L_F_ARCHIVE1, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Archive key 1." );
		options.addOption( L_F_ARCHIVE2, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Archive Key 2." );
		options.addOption( L_F_VARIABLE_NAME, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Variable Name." );
		options.addOption( L_F_VARIABLE_VALUE, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Variable Value." + "\n" );
		options.addOption( L_F_METADATA_NAME, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Metadata Name." );
		options.addOption( L_F_METADATA_VALUE, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"Metadata Value." );
		options.addOption( L_F_PROCESS_KEYWORD, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"keyword in Process." );
		options.addOption( L_F_PREPROCESS_KEYWORD, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"keyword in PreProcess." );
		options.addOption( L_F_POSTPROCESS_KEYWORD, true, HelpLabels.OPTIONAL+HelpLabels.REGEX_FILTER+"keyword in PostProcess.");
		
		options.addOption( L_U_LOGIN, true,  HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update LOGIN value." );
		options.addOption( L_U_HOST, true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update HOST value.");
		options.addOption( L_U_PROCESS, true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update PROCESS.");
		options.addOption( L_U_TITLE, true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update TITLE value.");
		options.addOption( L_U_QUEUE, true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update QUEUE value.");
		options.addOption( L_U_TZ, true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update TIMEZONE value." );
		options.addOption( L_U_ADD_VARIABLE, true, HelpLabels.OPTIONAL+HelpLabels.VARIABLE_UPDATE+ "Add Variable.");
		options.addOption( L_U_UPD_VARIABLE, true, HelpLabels.OPTIONAL+HelpLabels.VARIABLE_UPDATE+ "Update Variable Value.");
		options.addOption( L_U_DEL_VARIABLE, true, HelpLabels.OPTIONAL+HelpLabels.STD_UPDATE+ "Delete Variable.");
		options.addOption( L_U_PRIORITY, true,  HelpLabels.OPTIONAL+HelpLabels.INT_UPDATE+ "Update Priority.");
		options.addOption( L_U_GENERATEATRUNTIME, true,  HelpLabels.OPTIONAL+HelpLabels.BOOLEAN_UPDATE+ "Generate At Runtime.");
		options.addOption( L_U_ACTIVE, true, HelpLabels.OPTIONAL+HelpLabels.BOOLEAN_UPDATE+ "Activate / Deactivate.");
		options.addOption( L_U_POSTPROCESS, true,  HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update PostProcess.");
		options.addOption( L_U_PREPROCESS, true,  HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update PreProcess.");
		options.addOption( L_U_MAXNUMBERRUN, true,  HelpLabels.OPTIONAL+HelpLabels.INT_UPDATE+ "Max Parallel Runs.");
		options.addOption( L_U_ARCH1, true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update Archive Key 1 value.");
		options.addOption( L_U_ARCH2, true, HelpLabels.OPTIONAL+HelpLabels.PATTERN_UPDATE+ "Update Archive Key 2 value.");
		options.addOption( L_U_ADD_MDATA, true,  HelpLabels.OPTIONAL+HelpLabels.VARIABLE_UPDATE+ "Add Metadata Tag.");
		options.addOption( L_U_DEL_MDATA, true,  HelpLabels.OPTIONAL+HelpLabels.STD_UPDATE+ "Delete Metadata Tag.");
		
		// parse the command line arguments
				CommandLine line;
				try{line = parser.parse( options, args );}
				catch(MissingArgumentException m){
					System.out.println(" -- Error: " + m.getMessage());
					return false;
				}
	 
		if( line.hasOption( L_NAME )) {NAME = line.getOptionValue(L_NAME);}
		if( line.hasOption( L_F_NAME )) {F_NAME = line.getOptionValue(L_F_NAME);}
		if( line.hasOption( L_F_HOST )) {F_HOST = line.getOptionValue(L_F_HOST);}
		if( line.hasOption( L_F_LOGIN )) {F_LOGIN = line.getOptionValue(L_F_LOGIN);}
		if( line.hasOption( L_F_QUEUE )) {F_QUEUE = line.getOptionValue(L_F_QUEUE);}
		if( line.hasOption( L_F_TITLE )) {F_TITLE = line.getOptionValue(L_F_TITLE);}
		if( line.hasOption( L_F_ACTIVE )) {F_ACTIVE = line.getOptionValue(L_F_ACTIVE);}
		if( line.hasOption( L_F_ARCHIVE1 )) {F_ARCHIVE1 = line.getOptionValue(L_F_ARCHIVE1);}
		if( line.hasOption( L_F_ARCHIVE2 )) {F_ARCHIVE2 = line.getOptionValue(L_F_ARCHIVE2);}
		if( line.hasOption( L_F_VARIABLE_NAME )) {F_VARIABLE_NAME = line.getOptionValue(L_F_VARIABLE_NAME);}
		if( line.hasOption( L_F_VARIABLE_VALUE )) {F_VARIABLE_VALUE = line.getOptionValue(L_F_VARIABLE_VALUE);}
		if( line.hasOption( L_F_PROCESS_KEYWORD )) {F_PROCESS_KEYWORD = line.getOptionValue(L_F_PROCESS_KEYWORD);}
		if( line.hasOption( L_F_PREPROCESS_KEYWORD )) {F_PREPROCESS_KEYWORD = line.getOptionValue(L_F_PREPROCESS_KEYWORD);}
		if( line.hasOption( L_F_POSTPROCESS_KEYWORD )) {F_POSTPROCESS_KEYWORD = line.getOptionValue(L_F_POSTPROCESS_KEYWORD);}
		if( line.hasOption( L_F_METADATA_NAME )) {F_METADATA_NAME = line.getOptionValue(L_F_METADATA_NAME);}
		if( line.hasOption( L_F_METADATA_VALUE )) {F_METADATA_VALUE = line.getOptionValue(L_F_METADATA_VALUE);}
		// processing update parameters

		if( line.hasOption( L_U_HOST )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_HOST))){U_HOST = line.getOptionValue(L_U_HOST);}else{System.out.println(" -- Error in Value for -u_host_to. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_LOGIN )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_LOGIN))){U_LOGIN = line.getOptionValue(L_U_LOGIN);}else{System.out.println(" -- Error in Value for -u_login_to. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_PROCESS )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_PROCESS))){U_PROCESS = line.getOptionValue(L_U_PROCESS);}else{System.out.println(" -- Error in Value for u_process. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_TITLE )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_TITLE))){U_TITLE = line.getOptionValue(L_U_TITLE);}else{System.out.println(" -- Error in Value for u_title. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_QUEUE )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_QUEUE))){U_QUEUE = line.getOptionValue(L_U_QUEUE);}else{System.out.println(" -- Error in Value for u_queue. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_TZ )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_TZ))){U_TZ = line.getOptionValue(L_U_TZ);}else{System.out.println(" -- Error in Value for u_timezone. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_ADD_VARIABLE )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("L_U_ADD_VARIABLE"))){U_ADD_VARIABLE = line.getOptionValue("L_U_ADD_VARIABLE");}else{System.out.println(" -- Error in Value for u_addvar. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_UPD_VARIABLE )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("L_U_UPD_VARIABLE"))){U_UPD_VARIABLE = line.getOptionValue("L_U_UPD_VARIABLE");}else{System.out.println(" -- Error in Value for u_updvar. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_DEL_VARIABLE )) {if(consistencyUtils.checkValueStructure(line.getOptionValue("L_U_DEL_VARIABLE"))){U_DEL_VARIABLE = line.getOptionValue("L_U_DEL_VARIABLE");}else{System.out.println(" -- Error in Value for u_delvar. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}

		if( line.hasOption( L_U_PRIORITY )) {
			if(consistencyUtils.isInteger(line.getOptionValue(L_U_PRIORITY))){U_PRIORITY = Integer.parseInt((line.getOptionValue(L_U_PRIORITY)));
			}else{System.out.println(" -- Error: Cannot Update Priority to non Integer value..");ERROR_FREE=false;}}	
		if( line.hasOption( L_U_MAXNUMBERRUN )) {
			if(consistencyUtils.isInteger(line.getOptionValue(L_U_MAXNUMBERRUN))){U_MAXNUMBERRUN = Integer.parseInt((line.getOptionValue(L_U_MAXNUMBERRUN)));
			}else{System.out.println(" -- Error: Cannot Update Max Parallel Runs to non Integer value..");ERROR_FREE=false;}}	
		if( line.hasOption( L_U_GENERATEATRUNTIME )) {
			if(consistencyUtils.checkStringIsYorN(line.getOptionValue(L_U_GENERATEATRUNTIME))){U_GENERATEATRUNTIME = line.getOptionValue(L_U_GENERATEATRUNTIME);
			}else{System.out.println(" -- Error: genatruntime needs to be set to Y or N..");ERROR_FREE=false;}}
		if( line.hasOption( L_U_ACTIVE )) {
			if(consistencyUtils.checkStringIsYorN(line.getOptionValue(L_U_ACTIVE))){U_ACTIVE = line.getOptionValue(L_U_ACTIVE);
			}else{System.out.println(" -- Error: active needs to be set to Y or N..");ERROR_FREE=false;}}
		
		if( line.hasOption( L_U_POSTPROCESS )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_POSTPROCESS))){U_POSTPROCESS = line.getOptionValue(L_U_POSTPROCESS);}else{System.out.println(" -- Error in Value for u_postprocess. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_PREPROCESS )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_PREPROCESS))){U_PREPROCESS = line.getOptionValue(L_U_PREPROCESS);}else{System.out.println(" -- Error in Value for u_postprocess. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_ARCH1 )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_ARCH1))){U_ARCH1 = line.getOptionValue(L_U_ARCH1);}else{System.out.println(" -- Error in Value for u_arch1. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_ARCH2 )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_ARCH2))){U_ARCH2 = line.getOptionValue(L_U_ARCH2);}else{System.out.println(" -- Error in Value for u_arch2. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_ADD_MDATA )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_ADD_MDATA))){U_ADD_MDATA = line.getOptionValue(L_U_ADD_MDATA);}else{System.out.println(" -- Error in Value for u_addmdata. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}
		if( line.hasOption( L_U_DEL_MDATA )) {if(consistencyUtils.checkValueStructure(line.getOptionValue(L_U_DEL_MDATA))){U_DEL_MDATA = line.getOptionValue(L_U_DEL_MDATA);}else{System.out.println(" -- Error in Value for u_delmdata. Expected: [\"OLD.NAME\",\"NEW.NAME\"] or [\"OLD*\",\"NEW\"]");ERROR_FREE=false;};}	
		
	    if( line.hasOption( "commit" )) {SIMULATE = false;}

	    if(NAME.equals("")){System.out.println(" -- Error: Missing Mandatory Parameter: -name. Use -h for help.");ERROR_FREE=false;}

	    // 3- keep the following one.. it will display help automatically
	    if( line.hasOption( "h" )) {
	    	System.out.println("Program: "+Version.getName() + " - " +Version.getDescription());
	    	System.out.println("Version: "+Version.getVersion()+"\n");
	    	HelpFormatter formatter = new HelpFormatter();
	    	formatter.printHelp(300,"java -jar Command.jar","\n++ Parameters Are: \n\n", options,"", true );
	    	
	    	System.out.println("\n === Examples: ===");
	    	System.out.println("\t Update Process Tab (replace an IP adr by another one) for all JOBS objects with a name matching *JOBS.ABC* with a LOGIN containing *GENERAL");
	    	System.out.println(" \t => java -jar command.jar -name \"*JOBS.ABC*\" -f_login \".*GENERAL\" -u_process [\"192.168.1.123\",\"192.861.1.321\"] \n"  );
	    	System.out.println("\t list all JOBS objects running on a Queue matching *SYS* ");
	    	System.out.println(" \t => java -jar command.jar -name \"*\" -f_queue \".*SYS.*\" \n"  );
	    	return false;
	    }
	    
	    if(!ERROR_FREE){
	    	System.out.println("\n -- Error(s) encountered. Please fix them and rerun.");
	    	return false;
	    }
		return true;
	}
}