package com.ModelViewer.LoginApp.Service.Support;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandeling extends ResponseEntityExceptionHandler{

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandeling.class);
	private ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(value = { Exception.class})
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {

    	String bodyOfResponse = ex.getMessage();
    	
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
