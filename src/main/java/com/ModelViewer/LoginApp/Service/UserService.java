package com.ModelViewer.LoginApp.Service;


import java.io.IOException;
import java.sql.Timestamp;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
@RequestMapping("/UserService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class UserService {
	private static final String EMPTY_STRING = "\"\"";
	private final static String USER_EXISTS = "\"This company name already exists, please choose another\"";
	private final static String ACCESS_FORBIDDEN = "\"Access Forbbiden\"";
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;

	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/alive", method = RequestMethod.GET)
	public String home() {
		logger.info("alive request recieved");
		
		return "hello world";
	}
	
	@RequestMapping(value = "/ComparePasswordsForUser", method = RequestMethod.GET)
	public String ComparePasswordsForUser(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) 
					throws JsonProcessingException {
		
		logger.info("GetUserByUserName request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		
		
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(password)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
    	userDAO.UpdateUserLoginToCurrentTime(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}
	}
	
	
	@RequestMapping(value = "/CreateUser", method = RequestMethod.POST)
	//@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
	public String CreateUser(@RequestBody(required = true) String jsonUserModel) throws Exception {
		
		UserModel userModel = mapper.readValue(jsonUserModel, UserModel.class);
		
		StringBuilder log = new StringBuilder("CreateUser request recieved: ");
		log.append(userModel.getUserName()).append(" email: ").append(userModel.getEmail());		
		logger.debug(log.toString());

		ReturnedObject ro = new ReturnedObject();
		
		UserModel um = userDAO.GetUserByUserName(userModel.getUserName(),ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    	}   	
		if(um != null){
    		ro.throwException(false,USER_EXISTS);			
		}
		
		//create member, global		
		Long currentTime = System.currentTimeMillis();
		userModel.setDateCreated(new Timestamp(currentTime));
		userModel.setDateLastLoggedIn(new Timestamp(currentTime));
		userDAO.CreateUserByModel(userModel,ro);
		if(!ro.isSuccess()){
			ro.throwException();
		}
		
		//add the global member to this users possible members to add to a project
		memberDAO.CreateUpdateAMember(userModel, "global", "global", "global", "global", ro);	
		if(!ro.isSuccess()){
			ro.throwException();
		}

		return ro.ToJSONString(true, EMPTY_STRING);
	}
	
}














