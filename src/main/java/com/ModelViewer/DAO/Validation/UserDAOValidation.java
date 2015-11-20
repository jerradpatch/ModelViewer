package com.ModelViewer.DAO.Validation;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.DAOImpl.UserDAOImpl;

import javax.inject.Inject;

import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.UserModel;

public class UserDAOValidation implements UserDAO{
	
	private final ValidateUtil vu = new ValidateUtil();
	
	@Inject
	UserDAOImpl userDAOImpl;
	
	public UserModel GetUserByUserName(String userName, ReturnedObject ro) {
		
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}		
		return userDAOImpl.GetUserByUserName(userName, ro);
	}

	public String GetUserPasswordByUserName(String password, ReturnedObject ro) {
		return userDAOImpl.GetUserPasswordByUserName(password, ro);
	}

	public void CreateUserByModel(UserModel um, ReturnedObject ro) {
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
		userDAOImpl.CreateUserByModel(um, ro);
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
		userDAOImpl.CreateUserByValues(userName, password, email, ro);
	}

	public void DeleteByModel(UserModel um, ReturnedObject ro) {
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
		userDAOImpl.DeleteByModel(um, ro);
	}

	public void UpdateUserLoginToCurrentTime(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}	
		userDAOImpl.UpdateUserLoginToCurrentTime(userName, ro);		
	}
	
	

}
