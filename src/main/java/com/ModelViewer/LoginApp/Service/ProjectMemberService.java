package com.ModelViewer.LoginApp.Service;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

	
@Controller
@ResponseBody
@RequestMapping("/ProjectMemberService")
public class ProjectMemberService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	ProjectMemberDAO projectMemberDAO;
	
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
	
	@RequestMapping(value = "/GetListOfProjectsAndMembers", method = RequestMethod.GET)
	public String GetListOfProjectsAndMembers(
			@RequestParam(value = "userName", required = true) String userName, 
			@RequestParam(value = "companyP", required = true) String companyPassword)
					throws JsonProcessingException {

		logger.info("GetListOfProjectsAndMembers request recieved: "+userName);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		return mapper.writeValueAsString(projectMemberDAO.GetListOfProjectsAndMembers(userName));
	}

	@RequestMapping(value = "/GetHashMapOfProjectAndMember", method = RequestMethod.GET)
	public String GetHashMapOfProjectAndMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
					throws JsonProcessingException {
		
		logger.info("GetHashMapOfProjectAndMember request recieved: "+userName);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		return mapper.writeValueAsString(projectMemberDAO.GetHashMapOfProjectAndMember(userName));
	}
	
	@RequestMapping(value = "/GetProjectsMemberIsAPartOf", method = RequestMethod.GET)
	public String GetProjectsMemberIsAPartOf(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
					throws JsonProcessingException {
		
		logger.info("GetProjectsMemberIsAPartOf request recieved username: "+userName +" member:"+member);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		return mapper.writeValueAsString(projectMemberDAO.GetProjectsMemberIsAPartOf(userName,member));
	}
	
//	@RequestMapping(value = "/GetAListOfMembers", method = RequestMethod.GET)
//	public String GetAListOfMembers(@RequestParam(value = "userName", required = true) String userName) throws JsonProcessingException {
//		logger.info("GetAListOfMembers request recieved: "+userName);
//		
//		return mapper.writeValueAsString(projectMemberDAO.GetAListOfMembers(userName));
//	}
	
	@RequestMapping(value = "/CreateANewProject", method = RequestMethod.GET)
	public String CreateANewProject(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("CreateANewProject request recieved: "+userName+"projectNAme: "+projectName);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		projectMemberDAO.CreateANewProject(userName,projectName);
		return mapper.writeValueAsString("success");
	}
	
	@RequestMapping(value = "/CreateAMember", method = RequestMethod.GET)
	public String CreateAMemberInAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("CreateAMember request recieved: "+userName+" projectName:"+projectName+" member: "+member);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		projectMemberDAO.CreateAMember(userName,projectName,member);
		return mapper.writeValueAsString("success");

	}
	
	@RequestMapping(value = "/DeleteAProject", method = RequestMethod.GET)
	public String DeleteAProject(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAProject request recieved: "+userName+"projectName: "+projectName);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		projectMemberDAO.DeleteAProject(userName,projectName);
		return mapper.writeValueAsString("success");
	}
	
	@RequestMapping(value = "/DeleteAMemberFromAllProjects", method = RequestMethod.GET)
	public String DeleteAMemberFromAllProjects(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAMember request recieved: "+userName+"member: "+member);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		projectMemberDAO.DeleteAMemberFromAllProject(userName,member);
		return mapper.writeValueAsString("success");
	}
	
	@RequestMapping(value = "/DeleteAMemberFromAProject", method = RequestMethod.GET)
	public String DeleteAMemberFromAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAMember request recieved: "+userName+"member: "+member);
		
		if(!userDAO.ComparePasswords(userName, companyPassword)){
			return new ReturnedObject(false,"Access Forbbiden").ToJSONString();
		}
		
		projectMemberDAO.DeleteAMemberFromAProject(userName,projectName,member);
		return mapper.writeValueAsString("success"); 
	}
}
