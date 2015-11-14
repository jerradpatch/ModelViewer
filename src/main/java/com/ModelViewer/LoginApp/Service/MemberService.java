package com.ModelViewer.LoginApp.Service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	MemberDAO memberDAO;
	
	@Inject
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
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		return mapper.writeValueAsString(memberDAO.GetListOfMember(userName));
	}
	
	@RequestMapping(value = "/GetMemberData", method = RequestMethod.GET)
	public String GetMemberData(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("GetMemberData request recieved: "+userName+" member: "+member);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		return mapper.writeValueAsString(memberDAO.GetMemberData(userName, member));
	}
	
	@RequestMapping(value = "/CreateUpdateAMember", method = RequestMethod.GET)
	public String CreateUpdateAMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "companyP", required = true) String companyPassword)
					throws JsonProcessingException {
		
		logger.info("CreateUpdateAMember request recieved: "+userName);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		memberDAO.CreateUpdateAMember(userName,member,password);
		return new ReturnedObject(true,"").ToJSONString();
	}
	
	@RequestMapping(value = "/DeleteAMember", method = RequestMethod.GET)
	public String DeleteAMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAMember request recieved: "+userName);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		memberDAO.DeleteAMember(userName,member);
		return new ReturnedObject(true,"").ToJSONString();
	}
		
}



































