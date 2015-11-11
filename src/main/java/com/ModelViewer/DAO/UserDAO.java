package com.ModelViewer.DAO;

import com.ModelViewer.Model.UserModel;

public interface UserDAO {
	
	public UserModel GetUserByUserName(String UserName);
	public void CreateUserByModel(UserModel um);
	public void CreateUserByValues(String userName, String password, String email);
//	public UserModel UpdateByUserName(String name);
//	public UserModel DeleteByUserName(String name);
	public void DeleteByModel(UserModel name);
	

}
