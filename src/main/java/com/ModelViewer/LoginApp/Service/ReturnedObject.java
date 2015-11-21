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
		StringBuilder sb = new StringBuilder("{\"success\":");
		sb.append(success).append(",\"message\":");
		if(message.isEmpty()){
			sb.append("\"\"}");
		} else {
			sb.append(message).append("}");
		}
		return sb.toString();
	}
}
