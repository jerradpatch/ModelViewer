package com.ModelViewer.DAO;

import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.UserModel;

public interface UserDAO {
	
	public UserModel GetUserByUserName(String userName, ReturnedObject ro);
	public String GetUserPasswordByUserName(String userName, ReturnedObject ro);
	public void CreateUserByModel(UserModel um, ReturnedObject ro);
	public void CreateUserByValues(String userName, String password, String email, ReturnedObject ro);
//	public UserModel UpdateByUserName(String name);
//	public UserModel DeleteByUserName(String name);
	public void DeleteByModel(UserModel um, ReturnedObject ro);
	public void UpdateUserLoginToCurrentTime(String userName, ReturnedObject ro);
	
	

}
