package com.ModelViewer.DAO.Validation;

import com.ModelViewer.DAO.Validation.ValidateUtil;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.UserModel;

public class UserDAOValidation implements UserDAO{
	private static final Logger logger = LoggerFactory.getLogger(UserDAOValidation.class);
	private final ValidateUtil vu = new ValidateUtil();
	
	@Inject
	@Qualifier("UserDAOImpl")
	UserDAO userDAO;
	
	public UserModel GetUserByUserName(String userName, ReturnedObject ro) {
		
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		logger.debug("GetUserByUserName userDAO: "+userDAO);
		logger.debug("GetUserByUserName: userName "+userName);
		return userDAO.GetUserByUserName(userName, ro);
	}

	public String GetUserPasswordByUserName(String password, ReturnedObject ro) {
		vu.validatePassword(password, ro);
		if(!ro.isSuccess()){
			return null;
		}	
		return userDAO.GetUserPasswordByUserName(password, ro);
	}

	public void CreateUserByModel(UserModel um, ReturnedObject ro) {
		if(um == null){
			ro.setSuccess(false);
			ro.setMessage("User was null");
			return;
		}
		String userName = um.getUserName();
		String password = um.getPassword();
		String email = um.getEmail();
		
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}	
		
		vu.validatePassword(password, ro);
		if(!ro.isSuccess()){
			return;
		}	
		
		vu.validateEmail(email, ro);
		if(!ro.isSuccess()){
			return;
		}		
		userDAO.CreateUserByModel(um, ro);
	}

	public void CreateUserByValues(String userName, String password, String email, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}	
		
		vu.validatePassword(password, ro);
		if(!ro.isSuccess()){
			return;
		}	
		
		vu.validateEmail(email, ro);
		if(!ro.isSuccess()){
			return;
		}
		userDAO.CreateUserByValues(userName, password, email, ro);
	}

	public void DeleteByModel(UserModel um, ReturnedObject ro) {
		if(um == null){
			ro.setSuccess(false);
			ro.setMessage("User was null");
			return;
		}
		String userName = um.getUserName();
		String password = um.getPassword();
		String email = um.getEmail();
		
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}	
		
		vu.validatePassword(password, ro);
		if(!ro.isSuccess()){
			return;
		}	
		
		vu.validateEmail(email, ro);
		if(!ro.isSuccess()){
			return;
		}
		userDAO.DeleteByModel(um, ro);
	}

	public void UpdateUserLoginToCurrentTime(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}	
		userDAO.UpdateUserLoginToCurrentTime(userName, ro);		
	}
	
	

}
