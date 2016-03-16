package com.automic.specifics;

public class Version {

	public static String ProgramName = "Update JOBS";
	public static String Description = "Update JOBS Objects";
	static String MinimumAEVersion = "9.0.0";
static String VERSION = GoRunCommand.class.getPackage().getImplementationVersion();
	
	public static String getVersion(){
		return VERSION; //MajorVersion+"."+MinorVersion+"."+ReleaseNum; 
	}
	
	public static String getMajorVersion(){
		return VERSION;
	}
	
	public static String getMinorVersion(){
		return VERSION;
	}
	
	public static String getReleaseNum(){
		return VERSION;
	}
	
	public static String getName(){
		return ProgramName; 
	}
	
	public static String getDescription(){
		return Description;
	}
}
