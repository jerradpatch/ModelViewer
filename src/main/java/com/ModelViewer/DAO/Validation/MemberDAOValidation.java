package com.ModelViewer.DAO.Validation;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.MemberModel;

public class MemberDAOValidation implements MemberDAO{

	private final ValidateUtil vu = new ValidateUtil();
	
	@Inject
	@Qualifier("MemberDAOImpl")
	MemberDAO memberDAO;
	
	public List<String> GetListOfMember(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return memberDAO.GetListOfMember(userName, ro);
	}

	public String GetMemberPassword(String userName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return null;
		}	
		return memberDAO.GetMemberPassword(userName, member, ro);
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
		return memberDAO.GetMemberData(userName, member, ro);
	}

	public void CreateUpdateAMember(String userName, String memberNameOld,String memberPasswordOld,String member,String password, ReturnedObject ro) {
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
		
		vu.validateMemberName(memberNameOld, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validatePassword(memberPasswordOld, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		memberDAO.CreateUpdateAMember(userName, memberNameOld, memberPasswordOld, member, password, ro);
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
		memberDAO.DeleteAMember(userName, member, ro);
	}


}
