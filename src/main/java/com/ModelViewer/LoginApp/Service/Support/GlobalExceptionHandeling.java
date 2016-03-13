package com.ModelViewer.LoginApp.Service.Support;


import java.util.Calendar;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//TODO not used, code saved, this was Deprecated by Around Advice AOP, purpose was to global error handle
public class GlobalExceptionHandeling extends ResponseEntityExceptionHandler{

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandeling.class);
	private ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(value = { Exception.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {

    	String bodyOfResponse = ex.getMessage();
    	
		int startTime = Calendar.getInstance().get(Calendar.MILLISECOND);
		HashMap<String,Object> whole = new HashMap<String,Object>();
		HashMap<String,Object> ret = new HashMap<String,Object>();
		HashMap<String,Object> meta = new HashMap<String,Object>();
		
		
    	
    	if(ex instanceof ReturnedObject){
    		
    		return handleExceptionInternal(ex, bodyOfResponse, 
    		          new HttpHeaders(), HttpStatus.valueOf(200), request);
    	}
    	
    	try {
    		mapper.readValue(bodyOfResponse, JsonNode.class);
    	} catch (Exception e) {
    		//if not valid json then is a real error
    		logger.error("for real exception", ex);
    		
    		String retObj = "{\"status\":false,\"message\":\""+bodyOfResponse+"\"}";
            return handleExceptionInternal(ex, retObj, 
                    new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    	}
    	
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.valueOf(200), request);
    }
	
	
}
