package com.ModelViewer.LoginApp.Service;

public class ReturnedObject {
	public boolean success;
	public String message;
	
	

	public ReturnedObject(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}



	public String ToJSONString(){
		return "{ \"success\":"+success+", \"message\": \""+message+"\" }";
	}
}
