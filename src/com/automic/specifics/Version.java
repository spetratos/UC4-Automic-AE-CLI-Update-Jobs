package com.automic.specifics;

public class Version {

	public static String MajorVersion = "1";
	public static String MinorVersion = "0";
	public static String ReleaseNum = "0";
	public static String ProgramName = "CreateJobs";
	public static String Description = "Create Job Objects in AE";
	
	public static String getVersion(){
		return MajorVersion+"."+MinorVersion+"."+ReleaseNum;
	}
	
	public static String getMajorVersion(){
		return MajorVersion;
	}
	
	public static String getMinorVersion(){
		return MinorVersion;
	}
	
	public static String getReleaseNum(){
		return ReleaseNum;
	}
	
	public static String getName(){
		return ProgramName;
	}
	
	public static String getDescription(){
		return Description;
	}
}
