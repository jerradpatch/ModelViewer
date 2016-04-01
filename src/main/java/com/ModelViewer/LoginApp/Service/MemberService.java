package com.ModelViewer.LoginApp.Service;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.UserModel;


@Controller
@ResponseBody
@RequestMapping("/MemberService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class MemberService {

	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;
	
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/alive", method = RequestMethod.GET)
	public Object home() {

		return "hello world";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object readMember_userNamePassword(@RequestBody(required = true) Object obj) throws Exception {
		
		MemberModel memberModel = (MemberModel) obj;
		MemberModel memberModelRet =  memberDAO.readMember_userNamePassword(memberModel);
		if(memberModelRet == null){
			ReturnedObject.sThrowException("Access Denied");
		}
		return memberModelRet;
	}
	
	@RequestMapping(value = "/createMember", method = RequestMethod.POST)
	public void CreateAMember(@RequestBody(required = true) Object obj) throws Exception{
		MemberModel memberModel = (MemberModel) obj;
		memberDAO.createMember(memberModel);
	}	
	@RequestMapping(value = "/readMemberList", method = RequestMethod.POST)
	public Object readListOfMember(@RequestBody(required = true) Object obj) throws Exception {
		MemberModel memberModel = (MemberModel) obj;
		Set<MemberModel> members = memberDAO.readMemberList(memberModel);
		return members;
	}

	@RequestMapping(value = "/readMember", method = RequestMethod.POST)
	public Object readMember(@RequestBody(required = true) Object obj) throws Exception {	
		MemberModel memberModel = (MemberModel) obj;
		MemberModel memberRet = memberDAO.readMember(memberModel);
		return memberRet;
	}
//	@RequestMapping(value = "/readUserMembers", method = RequestMethod.POST)
//	public Object readUserMembers(@RequestBody(required = true) Object obj) throws Exception {
//		UserModel userModel = (UserModel) obj;
//		return userDAO.readUser(userModel).getMemberModels();
//	}
	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public void UpdateAMember(@RequestBody(required = true) Object obj) throws Exception{
		MemberModel memberModel = (MemberModel) obj;
		memberDAO.updateMember(memberModel);
	}	
	@RequestMapping(value = "/deleteAMember", method = RequestMethod.GET)
	public void DeleteAMember(@RequestBody(required = true) Object obj) throws Exception {	
		MemberModel memberModel = (MemberModel) obj;
		memberDAO.deleteMember(memberModel);//TODO see if relational mapping deletes all member in other table
	}
		
}



































