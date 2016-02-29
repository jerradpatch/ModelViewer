package com.ModelViewer.LoginApp.Service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@ResponseBody
@RequestMapping("/MemberAndProjectMemberHybridService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class MemberAndProjectMemberHybridService {

	private final static String EMPTY_STRING = "\"\"";
	private final static String ACCESS_FORBIDDEN = "\"Access Forbbiden\"";
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	//private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;
	
	@Inject
	@Qualifier("ProjectMemberDAO")
	ProjectMemberDAO projectMemberDAO;
	
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;
	
	@RequestMapping(value = "/DeleteMember", method = RequestMethod.GET)
	public String DeleteMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws Exception {
		
		logger.info("DeleteMember request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		
		//password check
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
		if(ro.isSuccess() == false){
			ro.throwException();
			return null;
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.throwException(false, ACCESS_FORBIDDEN);	
    		return null;
		}
		
		memberDAO.DeleteAMember(userName, member, ro);
		if(ro.isSuccess() == false){
			ro.throwException();
			return null;
    	}
		
		projectMemberDAO.DeleteAMemberFromAllProject(userName, member, ro);
		if(ro.isSuccess() == false){
			ro.throwException();
			return null;
    	} 
		
		//if global user, add user back
		if(member.equals("global")){
			UserModel um = new UserModel();
			um.setUserName(userName);
			memberDAO.CreateUpdateAMember(um, "global", "global", "global", "global", ro);
			logger.debug("created member global");
			if(ro.isSuccess() == false){
				ro.throwException();
	    	} 
			return ro.ToJSONString(true,"Global Member Must Always Exist");
		}

		return ro.ToJSONString(true,EMPTY_STRING);	
	}
	
}

