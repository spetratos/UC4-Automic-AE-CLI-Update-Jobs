package com.automic.enums;

public enum ClientActionTypes {

	// Method names
	SET_VARIABLE("setVariable"),
	LIST_VARIABLE("listVariable"),
	ADD_VARIABLE("addVariable"),
	DELETE_VARIABLE("deleteVariable"),
	START("start"),
	STOP("stop");
	
	
	private String methodName;
	
    private ClientActionTypes(String methodName) {
        this.methodName = methodName;
}
	public static boolean contains(String str) {
	    for (ClientActionTypes c : ClientActionTypes.values()) {if (c.name().equals(str)) {return true;}}
	    return false;
	}
}
