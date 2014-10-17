package com.automic.enums;

public enum DoTypes {


		SEARCH(""),
		IMPORT(""),
		EXPORT("");

		private String className;
		
	    private DoTypes(String className) {
	        this.className = className;
	}
		public static boolean contains(String str) {
		    for (DoTypes c : DoTypes.values()) {if (c.name().equals(str)) {return true;}}
		    return false;
		}
	
}
