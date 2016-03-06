package com.ModelViewer.LoginApp.Service.Support;

import java.util.Calendar;
import java.util.HashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ModelViewer.LoginApp.Service.FileMetaService;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
public class Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(FileMetaService.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	//inplace point-cut expression, aspect oriented programming
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object aroundEachServiceCall(ProceedingJoinPoint pjp) throws Throwable {
		// start stopwatch
		Object retVal = null;
		HashMap<String,Object> retJson = new HashMap<String,Object>();
		int startTime = Calendar.getInstance().get(Calendar.MILLISECOND);
		try {
			
			retVal = pjp.proceed();
		} catch(ReturnedObject re){
			int stopTime = Calendar.getInstance().get(Calendar.MILLISECOND);
			
			retJson.put("calledMethod", pjp.getSourceLocation());
			retJson.put("timeTaken", startTime-stopTime);
			retJson.put("status", false);
			retJson.put("message", re.getMessage());
			
			logger.warn(mapper.writeValueAsString(retJson));
			return re.ToJSONString();
		} catch (Exception e){
			
			int stopTime = Calendar.getInstance().get(Calendar.MILLISECOND);
			retJson.put("calledMethod", pjp.getSourceLocation());
			retJson.put("timeTaken", startTime-stopTime);
			retJson.put("status", false);
			retJson.put("message", e.getMessage());
			
			logger.error(mapper.writeValueAsString(retJson), e);
			return mapper.writeValueAsString(retJson);
		}
		
		int stopTime = Calendar.getInstance().get(Calendar.MILLISECOND);
		retJson.put("calledMethod", pjp.getSourceLocation());
		retJson.put("timeTaken", startTime-stopTime);
		retJson.put("status", true);
		retJson.put("message", retVal);

		String json = mapper.writeValueAsString(retJson);
        // stop stopwatch
		logger.info(json);
        return json;
	}
}
