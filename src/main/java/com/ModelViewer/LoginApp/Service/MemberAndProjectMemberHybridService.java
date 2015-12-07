package com.ModelViewer.LoginApp.Service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@ResponseBody
@RequestMapping("/MemberAndProjectMemberHybridService")
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
			throws JsonProcessingException {
		
		logger.info("DeleteMember request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		memberDAO.DeleteAMember(userName, member, ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}
		
		projectMemberDAO.DeleteAMemberFromAllProject(userName, member, ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}
		
		ro.setSuccess(true);
		ro.setMessage(EMPTY_STRING);
		return ro.ToJSONString();	
	}
	
}

