package com.automic.specifics;

public class Version {

	public static String MajorVersion = "2";
	public static String MinorVersion = "4";
	public static String ReleaseNum = "5";
	public static String ProgramName = "UpdateJobs";
	public static String Description = "Update Jobs in AE";
	
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
