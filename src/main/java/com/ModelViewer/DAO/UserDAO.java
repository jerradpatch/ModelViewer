package com.ModelViewer.DAO;


import org.springframework.web.bind.annotation.RequestBody;

import com.ModelViewer.Model.UserModel;

public interface UserDAO {

	public UserModel createUser(@RequestBody(required = true) UserModel userModel) throws Exception;
	public UserModel readUser(@RequestBody(required = true) UserModel userModel) throws Exception;
	public void updateUser(@RequestBody(required = true) UserModel userModel) throws Exception;
	public UserModel readUser_userNamePassword(UserModel userModel);
}
