package com.automic.enums;

public enum StdActionTypes {

		LIST(""),
	  SHOW(""),
	  UPDATE(""),
	  DELETE(""),
	  CREATE(""),
	  DUPLICATE(""),
	  RENAME(""),
	  MOVE(""),
	  REPLACE(""),
	  EXECUTE(""),
	  CANCEL(""),
	  DEACTIVATE("");

	private String className;
	
    private StdActionTypes(String className) {
        this.className = className;
}
	public static boolean contains(String str) {
	    for (StdActionTypes c : StdActionTypes.values()) {if (c.name().equals(str)) {return true;}}
	    return false;
	}
}
