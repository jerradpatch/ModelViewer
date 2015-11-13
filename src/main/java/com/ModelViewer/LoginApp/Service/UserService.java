package com.ModelViewer.LoginApp.Service;


import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
@RequestMapping("/UserService")
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	UserDAO userDAO;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/alive", method = RequestMethod.GET)
	public String home() {
		logger.info("alive request recieved");
		
		return "hello world";
	}
	
	@RequestMapping(value = "/GetUserByUserName", method = RequestMethod.GET)
	public String GetUserByUserName(@RequestParam(value = "userName", required = true) String userName) throws JsonProcessingException {
		logger.info("GetUserByUserName request recieved: "+userName);
		
		return mapper.writeValueAsString(userDAO.GetUserByUserName(userName));
	}
	
	@RequestMapping(value = "/CreateUser", method = RequestMethod.POST)
	public String CreateUser(@RequestBody(required = true) String jsonUserModel) throws IOException {
		
		UserModel userModel = mapper.readValue(jsonUserModel, UserModel.class);
		
		StringBuilder log = new StringBuilder("CreateUser request recieved: ");
		log.append(userModel.getUserName()).append(" email: ").append(userModel.getEmail());
		logger.info(log.toString());

		UserModel um = userDAO.GetUserByUserName(userModel.getUserName());
		if(um == null){
			userDAO.CreateUserByModel(userModel);
		} else {
			return "{ \"success\": false, \"message\":\"This company name already exists, please choose another\"}";
			
		}
		return "{ \"success\": true, \"message\": \"\" }";
	}
	
}














