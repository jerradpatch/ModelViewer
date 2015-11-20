package com.ModelViewer.LoginApp.Service;


import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.ProjectMemberModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

	
@Controller
@ResponseBody
@RequestMapping("/ProjectMemberService")
public class ProjectMemberService {
	
	private static final String EMPTY_STRING = "";
	private final static String NO_PROJECT_AND_MEMBER = "No project and members found";
	private final static String ACCESS_FORBIDDEN = "Access Forbbiden";
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	ProjectMemberDAO projectMemberDAO;
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	MemberDAO memberDAO;
	
	
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
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		List<ProjectMemberModel> pandM = projectMemberDAO.GetListOfProjectsAndMembers(userName,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else if(pandM == null){
    		ro.setSuccess(false);
    		ro.setMessage(NO_PROJECT_AND_MEMBER);
    		return ro.ToJSONString();
		} else {
    		ro.setSuccess(true);
    		ro.setMessage(mapper.writeValueAsString(pandM));
    		return ro.ToJSONString();
    	}
	}


	@RequestMapping(value = "/GetHashMapOfProjectAndMember", method = RequestMethod.GET)
	public String GetHashMapOfProjectAndMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
					throws JsonProcessingException {
		
		logger.info("GetHashMapOfProjectAndMember request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		HashMap<String, List<String>> hpm = projectMemberDAO.GetHashMapOfProjectAndMember(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else if(hpm == null){
    		ro.setSuccess(false);
    		ro.setMessage(NO_PROJECT_AND_MEMBER);
    		return ro.ToJSONString();
		} else {
    		ro.setSuccess(true);
    		ro.setMessage(mapper.writeValueAsString(hpm));
    		return ro.ToJSONString();
    	}
	}
	
	@RequestMapping(value = "/GetProjectsMemberIsAPartOf", method = RequestMethod.GET)
	public String GetProjectsMemberIsAPartOf(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String memberPassword)
					throws JsonProcessingException {
		
		logger.info("GetProjectsMemberIsAPartOf request recieved username: "+userName +" member:"+member);
		
		//look up if member is in this company and password given matches password in DB
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = memberDAO.GetMemberPassword(userName,member,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(memberPassword)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		List<String> projects = projectMemberDAO.GetProjectsMemberIsAPartOf(userName,member,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else if(projects == null){
    		ro.setSuccess(false);
    		ro.setMessage(NO_PROJECT_AND_MEMBER);
    		return ro.ToJSONString();
		} else {
    		ro.setSuccess(true);
    		ro.setMessage(mapper.writeValueAsString(projects));
    		return ro.ToJSONString();
    	}		
	}
	
	@RequestMapping(value = "/CreateANewProject", method = RequestMethod.GET)
	public String CreateANewProject(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("CreateANewProject request recieved: "+userName+"projectNAme: "+projectName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		projectMemberDAO.CreateANewProject(userName,projectName,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}
	}
	
	@RequestMapping(value = "/CreateAMember", method = RequestMethod.GET)
	public String CreateAMemberInAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("CreateAMember request recieved: "+userName+" projectName:"+projectName+" member: "+member);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		projectMemberDAO.CreateAMember(userName,projectName,member,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}

	}
	
	@RequestMapping(value = "/DeleteAProject", method = RequestMethod.GET)
	public String DeleteAProject(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAProject request recieved: "+userName+"projectName: "+projectName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		projectMemberDAO.DeleteAProject(userName,projectName,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}
	}
	
	@RequestMapping(value = "/DeleteAMemberFromAllProjects", method = RequestMethod.GET)
	public String DeleteAMemberFromAllProjects(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAMember request recieved: "+userName+"member: "+member);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		projectMemberDAO.DeleteAMemberFromAllProject(userName,member,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}
	}
	
	@RequestMapping(value = "/DeleteAMemberFromAProject", method = RequestMethod.GET)
	public String DeleteAMemberFromAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException {
		
		logger.info("DeleteAMember request recieved: "+userName+"member: "+member);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
		
		projectMemberDAO.DeleteAMemberFromAProject(userName,projectName,member, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else {
    		ro.setSuccess(true);
    		ro.setMessage(EMPTY_STRING);
    		return ro.ToJSONString();
    	}
	}
}
