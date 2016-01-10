package com.ModelViewer.LoginApp.Service.Filters;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import com.ModelViewer.LoginApp.Service.FileService;

/**
 * this receives the modified-since from a request
 * modified-since, holds data from when the object was last updated and other information
 * we use this to
 * 1) query given api to see when object was last updated in db
 * if BD time > client time, return new object to client
 * else return HTTP response of "Not Modified 304"
 * @author jerrad
 *
 */
public class IfModifiedSinceFilter extends GenericFilterBean{
			   
	@Override
	protected void initFilterBean() {
		logger.debug("IfModifiedSinceFilter initialized");
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String sDate = ((HttpServletRequest) request).getHeader("If-Modified-Since"); //Weekday, DD-Mon-YY HH:MM:SS TIMEZONE
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			dateFormat.parse(sDate);
		} catch (ParseException e) {
			//if cant do it just skip
			return;
		}
		
		//got stuck on how to call non-specific bean functions that would tell if their there specific data has changed or not.
		//https://docs.spring.io/spring-security/site/docs/3.0.x/reference/security-filter-chain.html
		//http://stackoverflow.com/questions/11928637/how-to-write-a-custom-filter-in-spring-security
		//http://stackoverflow.com/questions/7882042/how-can-i-get-a-spring-bean-in-a-servlet-filter
//		request.
		
		
	}
}
