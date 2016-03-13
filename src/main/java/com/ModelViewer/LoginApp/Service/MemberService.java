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
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/alive", method = RequestMethod.GET)
	public Object home() {

		return "hello world";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object readMember_userNamePassword(@RequestBody(required = true) MemberModel memberModel) throws Exception {
		MemberModel memberModelRet =  memberDAO.readMember_userNamePassword(memberModel);
		if(memberModelRet == null){
			ReturnedObject.sThrowException("Access Denied");
		}
		return memberModelRet;
	}
	
	@RequestMapping(value = "/createMember", method = RequestMethod.POST)
	public void CreateAMember(@RequestBody(required = true) MemberModel memberModel) throws Exception{
		memberDAO.createMember(memberModel);
	}	
	@RequestMapping(value = "/readMemberList", method = RequestMethod.POST)
	public Object readListOfMember(@RequestBody(required = true) MemberModel memberModel) throws Exception {
		Set<MemberModel> members = memberDAO.readMemberList(memberModel);
		return members;
	}

	@RequestMapping(value = "/readMember", method = RequestMethod.POST)
	public Object readMember(@RequestBody(required = true) MemberModel memberModel) throws Exception {	
		MemberModel memberRet = memberDAO.readMember(memberModel);
		return memberRet;
	}
	@RequestMapping(value = "/readUserMembers", method = RequestMethod.POST)
	public Object readUserMembers(@RequestBody(required = true) UserModel userModel) throws Exception {
		return userModel.getMembers();
	}
	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public void UpdateAMember(@RequestBody(required = true) MemberModel memberModel) throws Exception{
		memberDAO.updateMember(memberModel);
	}	
	@RequestMapping(value = "/DeleteAMember", method = RequestMethod.GET)
	public void DeleteAMember(@RequestBody(required = true) MemberModel memberModel) throws Exception {		
		memberDAO.deleteMember(memberModel);//TODO see if relational mapping deletes all member in other table
	}
		
}



































