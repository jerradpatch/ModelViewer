package com.ModelViewer.DAO.Validation;

import java.util.List;

import javax.inject.Inject;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAOImpl.MemberDAOImpl;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.MemberModel;

public class MemberDAOValidation implements MemberDAO{

	private final ValidateUtil vu = new ValidateUtil();
	
	@Inject
	MemberDAOImpl memberDAOImpl;
	
	public List<String> GetListOfMember(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return memberDAOImpl.GetListOfMember(userName, ro);
	}

	public String GetMemberPassword(String userName, String member, ReturnedObject ro) {
		return memberDAOImpl.GetMemberPassword(userName, member, ro);
	}
	
	public MemberModel GetMemberData(String userName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return null;
		}	
		return memberDAOImpl.GetMemberData(userName, member, ro);
	}

	public void CreateUpdateAMember(String userName, String member, String password, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validatePassword(password, ro);
		if(!ro.isSuccess()){
			return;
		}
		memberDAOImpl.CreateUpdateAMember(userName, member, password, ro);
	}

	public void DeleteAMember(String userName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return;
		}	
		memberDAOImpl.DeleteAMember(userName, member, ro);
	}


}
