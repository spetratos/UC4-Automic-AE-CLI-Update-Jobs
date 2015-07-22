package com.automic.std;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import com.automic.AECredentials;

public class ProcessStandardCLI {

	private static String HOSTNAME="";
	private static int PORT = -1;
	private static int CLIENT = -1;
	private static String LOGIN="";
	private static String DEPT = "";
	private static String PASSWORD="";
	
	public AECredentials processStdCredentials(String[] args) throws IOException, InstantiationException, IllegalAccessException, ParseException, ParserConfigurationException, SAXException, XPathExpressionException{


		String CONFIG_FILENAME = "./connection.config";
		File f = new File(CONFIG_FILENAME);
		boolean CONFIG_FILE_EXISTS = f.exists();
		if(CONFIG_FILE_EXISTS){
			FileInputStream file = new FileInputStream(CONFIG_FILENAME);
			Properties configFile = new java.util.Properties();
			configFile.load(file);
			file.close();
			HOSTNAME = configFile.getProperty("AE_IP_ADR");
			PORT = Integer.parseInt(configFile.getProperty("AE_PRIMARY_PORT")); 
			CLIENT = Integer.parseInt(configFile.getProperty("AE_CLIENT"));
			LOGIN = configFile.getProperty("AE_LOGIN"); 
			DEPT = configFile.getProperty("AE_DEPT"); 
			PASSWORD = configFile.getProperty("AE_PASSWORD"); 
			
			// if password is encrypted in config file then we decrypt before executing
			if (PASSWORD.subSequence(0, 2).equals("--")){
				String pwd = CryptoUtils.decode(PASSWORD.substring(2));
				PASSWORD = pwd;
			}
			}
		
		// create the command line parser
		CommandLineParser parser = new ExtendedParser(true);

		/*
		 *  cli --Hostname HOSTNAME --Port 2217 --Client 2 --Login BSP --Dept AUTOMIC --Password Un1ver$e
		 * 
		 */
		
		Options CredentialOptions = new Options();
		CredentialOptions.addOption( "H", "hostname", true, "AE Hostname or IP" );
		CredentialOptions.addOption( "P", "port", true, "AE Port Number" );
		CredentialOptions.addOption( "C", "client", true, "Client Number");
		CredentialOptions.addOption( "L", "login", true, "Login");
		CredentialOptions.addOption( "D", "dept", true, "Department");
		CredentialOptions.addOption( "W", "password", true, "Password");
		CredentialOptions.addOption( "help", "help", false, "display help");
		
		    // parse the command line arguments
		    CommandLine line = parser.parse( CredentialOptions, args );
		    
		    if(line.hasOption("help")) {
		    	HelpFormatter formatter = new HelpFormatter();
		    	formatter.printHelp( "java -jar Command.jar", CredentialOptions, true );
		    	System.exit(0);
		    }
		    
		    if( line.hasOption( "H" )) {HOSTNAME = line.getOptionValue("H");}
		    if( line.hasOption( "P" )) {
		    	if (isInteger(line.getOptionValue("P"))){
		    		PORT = Integer.parseInt(line.getOptionValue("P"));}
		    	else{System.out.println(" -- Error. Port is expected as a Number: "+line.getOptionValue("P"));}
		    }
		    if( line.hasOption( "C" )) {
		    	if (isInteger(line.getOptionValue("C"))){
		    		CLIENT = Integer.parseInt(line.getOptionValue("C"));}
		    	else{System.out.println(" -- Error. Client is expected as a Number: "+line.getOptionValue("C"));}
		    }
		    if( line.hasOption( "L" )) {LOGIN = line.getOptionValue("L");} 
		    if( line.hasOption( "D" )) {DEPT = line.getOptionValue("D");}
		    if( line.hasOption( "W" )) {PASSWORD = line.getOptionValue("W");}
		    
		    boolean ERROR_FREE=true;
		    if(HOSTNAME.equals("")){System.out.println(" -- Error: Missing Hostname for AE. It must be provided either in connection.config file or as a parameter. Use -help for help.");ERROR_FREE=false;}
		    if(PORT == -1){System.out.println(" %% Warning: Missing Port for AE. Using Default: 2217.");PORT=2217;}
		    if(CLIENT == -1){System.out.println(" %% Warning: Missing Client for AE. Using Default: 0.");CLIENT=0;}
		    if(LOGIN.equals("")){System.out.println(" -- Error: Missing Login for AE. It must be provided either in connection.config file or as a parameter. Use -help for help.");ERROR_FREE=false;}
		    if(DEPT.equals("")){System.out.println(" -- Error: Missing Department for AE. It must be provided either in connection.config file or as a parameter. Use -help for help.");ERROR_FREE=false;}
		    if(PASSWORD.equals("")){System.out.println(" -- Error: Missing Password for AE. It must be provided either in connection.config file or as a parameter. Use -help for help.");ERROR_FREE=false;}
		    
		    if(!ERROR_FREE){
		    	System.out.println("\n -- Error(s) encountered. Please fix them and rerun.");
		    	System.exit(1);
		    }
		    
		    AECredentials myCreds = new AECredentials(HOSTNAME,PORT,CLIENT,LOGIN,DEPT,PASSWORD,'E');
		    return myCreds;
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


