package com.automic.utils;

import java.util.ArrayList;

public class Parameters {

	private String[] ParamTable;
	public int NumberOfParams;
	
	public Parameters(String FullParams){
		if(!FullParams.isEmpty()){
			this.ParamTable = FullParams.split(";");
			this.NumberOfParams = this.ParamTable.length;
		}
	}
	
	public boolean areAllParamsOK(){
		for(String Param : this.ParamTable){
			if(!checkParamFormat(Param)){return false;}
		}
		return true;
	}
	
	private boolean checkParamFormat(String FullParam){
		if(!FullParam.contains("=")){
			System.out.println(" -- Error, Parameter " + FullParam + "Should be in format NAME=VALUE");return false;}
		String[] stuff = FullParam.split("=");
		if(stuff.length>2){System.out.println(" -- Error, Parameter " + FullParam + "Should be in format NAME=VALUE");return false;}
		return true;
	}
	public String[] getSanitizedParameters(){
		return this.ParamTable;
	}
}
