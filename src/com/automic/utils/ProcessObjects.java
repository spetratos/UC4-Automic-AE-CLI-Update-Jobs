package com.automic.utils;

public class ProcessObjects {

	private String Object;
	private String ActionTypeClass;

	public ProcessObjects(String ObjName, String ActionTypeClass){
		this.Object = ObjName;
		this.ActionTypeClass = ActionTypeClass;
	}
	public Class getClassFromPath(String path) throws ClassNotFoundException{
		Class myClass = Class.forName(path);
		return myClass;
	}
	
}
