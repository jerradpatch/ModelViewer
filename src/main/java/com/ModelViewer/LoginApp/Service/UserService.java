package com.ModelViewer.LoginApp.Service;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Set;

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
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.ProjectMemberModel;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
@RequestMapping("/UserService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class UserService {

	private final static String USER_EXISTS = "\"This company name already exists, please choose another\"";
	private final static String ACCESS_FORBIDDEN = "\"Access Forbbiden\"";

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

		return "hello world";
	}
	

//	public boolean comparePasswordsForUser(@RequestBody(required = true) UserModel userModel) throws Exception{
//
//		String passwordFound = userDAO.readUser(userModel).getPassword();
//    	if(passwordFound == null || !passwordFound.equals(userModel.getPassword())){
//    		ReturnedObject.sThrowException(false,ACCESS_FORBIDDEN);
//		}
//    	return true;
//	}
	
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public void createUser(@RequestBody(required = true) UserModel userModel) throws Exception {

		UserModel um = userDAO.readUser(userModel);	
		if(um != null){
			ReturnedObject.sThrowException(USER_EXISTS);	
		}
		
		//create member, global		
		Long currentTime = System.currentTimeMillis();
		userModel.setDateCreated(new Timestamp(currentTime));
		userModel.setDateLastLoggedIn(new Timestamp(currentTime));
		userDAO.createUser(userModel);

		//add the global member to this users possible members to add to a project
		MemberModel memberModel = new MemberModel(null, userModel.getUserName(), "global", "global", null, null, null, null, null);	
		memberDAO.createMember(memberModel);	

	}
	@RequestMapping(value = "/readUser", method = RequestMethod.POST)
	public UserModel readUser(@RequestBody(required = true) UserModel userModel) throws Exception {
		 return userDAO.readUser(userModel);	
	}
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public void updateUser(@RequestBody(required = true) UserModel userModel) throws Exception {

		userDAO.updateUser(userModel);	
		Long currentTime = System.currentTimeMillis();
		userModel.setDateLastLoggedIn(new Timestamp(currentTime));
		userDAO.createUser(userModel);

	}
	
}









































