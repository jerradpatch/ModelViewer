package com.ModelViewer.LoginApp.Service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.LoginApp.Service.Support.CustomSecurityInterceptor;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/UserService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class UserService {

	private final static String USER_EXISTS = "This company name already exists, please choose another";
	private ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = Logger.getLogger(UserService.class);
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;

	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;

	@RequestMapping(value = "/alive", method = RequestMethod.GET)
	public Object home(@RequestParam(required = true) String test) {
		logger.debug(test);
		return "hello world";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Object readUser_userNamePassword(@RequestBody(required = true) UserModel userModel) throws Exception {
		logger.debug(mapper.writeValueAsString(userModel));
//		UserModel userModel = (UserModel) obj;
		UserModel userModelRet = userDAO.readUser_userNamePassword(userModel);
		if(userModelRet == null){
			ReturnedObject.sThrowException("Access Denied");
		}
		return userModelRet;
	}
	
	//TODO look into persistanct types with hibernate, and remove the flush() on create if hibernate can manage this
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public Object createUser(@RequestBody(required = true) UserModel userModel) throws Exception {
		logger.debug(mapper.writeValueAsString(userModel));
		logger.debug(userModel.getPassword());
		UserModel um = userDAO.readUser_userNamePassword(userModel);	
		if(um != null){
			ReturnedObject.sThrowException(USER_EXISTS);	
		}
		
		userModel.setMaxDepthLimit(0);
		//create member, global		
		Long currentTime = System.currentTimeMillis();
		userModel.setDateCreated(new Timestamp(currentTime));
		userModel.setDateLastLoggedIn(new Timestamp(currentTime));
		MemberModel memberModel = new MemberModel("global", "global", "global", "global",userModel);	
		userModel.addMemberModel(memberModel);
		
		userDAO.createUser(userModel);
		return userModel;
	}
	
	@RequestMapping(value = "/readUser", method = RequestMethod.POST)
	public Object readUser(HttpServletRequest request) throws Exception {
		Object obj = request.getAttribute("obj");
		UserModel userModel = (UserModel) obj;
		 return userDAO.readUser(userModel);	
	}
	
	@RequestMapping(value = "/readMemberList", method = RequestMethod.POST)
	public Object readListOfMember(HttpServletRequest request) throws Exception {
		Object obj = request.getAttribute("obj");
		UserModel userModel = (UserModel) obj;
		logger.debug(mapper.writeValueAsString(userModel));
		
		Set<MemberModel> members = userDAO.readMemberList(userModel);
		logger.debug(mapper.writeValueAsString(members));
		return members;
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public void updateUser(HttpServletRequest request) throws Exception {
		Object obj = request.getAttribute("obj");
		UserModel userModel = (UserModel) obj;
		Long currentTime = System.currentTimeMillis();
		userModel.setDateLastLoggedIn(new Timestamp(currentTime));
		userDAO.updateUser(userModel);	
	}	
	
	
}









































