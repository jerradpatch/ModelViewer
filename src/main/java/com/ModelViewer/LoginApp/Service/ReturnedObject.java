package com.ModelViewer.LoginApp.Service;

public class ReturnedObject extends Exception{
    private static final long serialVersionUID = 1997753363232807009L;
	
    public final boolean FAILEDSTATUS = false;
    public final boolean PASSEDSTATUS = true;
    
    
    
	private boolean success;

	private String message;
	
	public ReturnedObject(){
		super();
	}
	public ReturnedObject(String message)
	{
		super(message);
	}
	public ReturnedObject(Throwable cause)
	{
		super(cause);
	}
	public ReturnedObject(String message, Throwable cause)
	{
		super(message, cause);
	}
	public ReturnedObject(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{	
		super(message, cause, enableSuppression, writableStackTrace);
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
	
	public void setSuccessMessage(boolean success, String message) {
		this.success = success;
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
	
	public String ToJSONString(boolean success, String message){
		this.success = success;
		this.message = message;	
		return this.ToJSONString();
	}
	
	public void throwException() throws ReturnedObject{
		throw new ReturnedObject(this.ToJSONString());
	}
	
	public void throwException(boolean success, String message) throws ReturnedObject{
		this.success = success;
		this.message = message;
		throw new ReturnedObject(this.ToJSONString());
	}
	
}
