package com.ModelViewer.LoginApp.Service;

public class ReturnedObject {
	private boolean success;

	private String message;
	
	public ReturnedObject(){
		super();
	}

	public ReturnedObject(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String ToJSONString(){
		return "{ \"success\":"+success+", \"message\": \""+message+"\" }";
	}
}
