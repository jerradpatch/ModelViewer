package com.ModelViewer.LoginApp.Service;

import java.util.List;

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
import com.ModelViewer.DAO.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@ResponseBody
@RequestMapping("/MemberService")
public class MemberService {

	private static final String EMPTY_STRING = "";
	private final static String NO_MEMBER = "No members found";
	private final static String ACCESS_FORBIDDEN = "Access Forbbiden";
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
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
	public String home() {
		logger.info("alive request recieved");
		
		return "hello world";
	}
	
	@RequestMapping(value = "/GetAListOfMembers", method = RequestMethod.GET)
	public String GetListOfMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			
			throws JsonProcessingException {
		
		logger.info("GetListOfMember request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		List<String> members = memberDAO.GetListOfMember(userName,ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else if(members == null){
    		ro.setSuccess(false);
    		ro.setMessage(NO_MEMBER);
    		return ro.ToJSONString();
		} else {
    		ro.setSuccess(true);
    		ro.setMessage(mapper.writeValueAsString(members));
    		return ro.ToJSONString();
    	}
	}
	
	@RequestMapping(value = "/ComparePasswordsForMember", method = RequestMethod.GET)
	public String ComparePasswordsForMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "memberPassword", required = true) String memberPassword)
			throws JsonProcessingException {
		
		logger.info("GetMemberData request recieved: "+userName+" member: "+member);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = memberDAO.GetMemberPassword(userName,member,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(memberPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
    	
    	ro.setSuccess(true);
    	ro.setMessage(EMPTY_STRING);
	
		return ro.ToJSONString();
	
	}
	
	@RequestMapping(value = "/CreateUpdateAMember", method = RequestMethod.GET)
	public String CreateUpdateAMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "companyP", required = true) String companyPassword)
					throws JsonProcessingException {
		
		logger.info("CreateUpdateAMember request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		memberDAO.CreateUpdateAMember(userName,member,password,ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}
	}
	
	@RequestMapping(value = "/DeleteAMember", method = RequestMethod.GET)
	public String DeleteAMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAMember request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		memberDAO.DeleteAMember(userName,member,ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}
	}
		
}



































