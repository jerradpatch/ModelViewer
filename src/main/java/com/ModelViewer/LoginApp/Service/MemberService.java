package com.ModelViewer.LoginApp.Service;

import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@ResponseBody
@RequestMapping("/MemberService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class MemberService {

	private ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = Logger.getLogger(MemberService.class);
	
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
	public Object CreateAMember(HttpServletRequest request) throws Exception{
		logger.debug("CreateAMember");
		Object obj = request.getAttribute("obj");
		MemberModel memberModel = (MemberModel) obj;
		logger.debug("CreateAMember Model "+mapper.writeValueAsString(memberModel));
		
		MemberModel memberModelRet = memberDAO.createMember(memberModel);
		return memberModelRet;
	}	
	@RequestMapping(value = "/readMemberList", method = RequestMethod.POST)
	public Object readListOfMember(HttpServletRequest request) throws Exception {
		logger.debug("readMemberList");
		Object obj = request.getAttribute("obj");
		MemberModel memberModel = (MemberModel) obj;
		logger.debug("readMemberList Model "+mapper.writeValueAsString(memberModel));
		
		Set<MemberModel> members = memberDAO.readMemberList(memberModel);
		return members;
	}

	@RequestMapping(value = "/readMember", method = RequestMethod.POST)
	public Object readMember(HttpServletRequest request) throws Exception {	
		logger.debug("readMember");
		Object obj = request.getAttribute("obj");
		MemberModel memberModel = (MemberModel) obj;
		MemberModel memberRet = memberDAO.readMember(memberModel);
		return memberRet;
	}
	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public void UpdateAMember(HttpServletRequest request) throws Exception{
		Object obj = request.getAttribute("obj");
		MemberModel memberModel = (MemberModel) obj;
		memberDAO.updateMember(memberModel);
	}	
	@RequestMapping(value = "/deleteAMember", method = RequestMethod.GET)
	public void DeleteAMember(HttpServletRequest request) throws Exception {
		Object obj = request.getAttribute("obj");
		MemberModel memberModel = (MemberModel) obj;
		memberDAO.deleteMember(memberModel);//TODO see if relational mapping deletes all member in other table
	}
		
}



































