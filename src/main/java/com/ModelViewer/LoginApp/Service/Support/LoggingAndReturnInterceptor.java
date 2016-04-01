package com.ModelViewer.LoginApp.Service.Support;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.ModelViewer.LoginApp.Service.FileMetaService;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component 
@Aspect //AOP
@EnableAspectJAutoProxy
public class LoggingAndReturnInterceptor {

	private static final Logger logger = Logger.getLogger(LoggingAndReturnInterceptor.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	//inplace point-cut expression, aspect oriented programming
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)") 
	//aop is not passing parameters to service... in addition json "password" is not be deserialized due to jackson annotation on object
	public Object aroundEachServiceCall(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("begining AOP around");
		int startTime = Calendar.getInstance().get(Calendar.MILLISECOND);
		HashMap<String,Object> whole = new HashMap<String,Object>();
		HashMap<String,Object> ret = new HashMap<String,Object>();
		HashMap<String,Object> meta = new HashMap<String,Object>();
		
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
		String className = method.getDeclaringClass().getCanonicalName();
		meta.put("methodName_string", methodName);
		meta.put("className_string", className);
		
		// start stopwatch
		Object retVal = null;
		startTime = Calendar.getInstance().get(Calendar.MILLISECOND);
		try {		
			logger.debug("AOP service request made");
			retVal = pjp.proceed();
		} catch(ReturnedObject re){
			int stopTime = Calendar.getInstance().get(Calendar.MILLISECOND);
			meta.put("timeTakenMs_long", stopTime - startTime);
			whole.put("metaData", meta);
			
			ret.put("status_boolean", false);
			ret.put("message_string", re.getMessage());
			whole.put("data",ret);
			
			logger.warn(mapper.writeValueAsString(whole));
			return ret;
		} catch (Exception e){
			int stopTime = Calendar.getInstance().get(Calendar.MILLISECOND);
			meta.put("timeTakenMs_long", stopTime - startTime);
			whole.put("metaData", meta);
			
			ret.put("status_boolean", false);
			ret.put("message_string", e.getMessage());
			whole.put("data",ret);
			
			logger.error(mapper.writeValueAsString(whole), e);
			return whole;
		}

		int stopTime = Calendar.getInstance().get(Calendar.MILLISECOND);
		meta.put("timeTakenMs_long", stopTime - startTime);
		whole.put("metaData", meta);
		
		ret.put("status_boolean", true);
		ret.put("message_string", retVal);
		whole.put("data",ret);
				
		whole.put("data","");
		logger.info( mapper.writeValueAsString(meta));//dont need success data in logs
		logger.debug("AOP service request complete");
        return ret;
	}
}
