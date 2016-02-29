package com.ModelViewer.LoginApp.Service;


import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.ProjectInfoDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.ProjectInfoModel;
import com.ModelViewer.Model.ProjectMemberModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

	
@Controller
@ResponseBody
@RequestMapping("/ProjectMemberService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ProjectMemberService {
	
	private static final String EMPTY_STRING = "\"\"";
	private final static String NO_PROJECT_AND_MEMBER = "\"No project and members found\"";
	private final static String ACCESS_FORBIDDEN = "\"Access Forbbiden\"";
	private static final Logger logger = LoggerFactory.getLogger(ProjectMemberService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	@Qualifier("ProjectMemberDAO")
	ProjectMemberDAO projectMemberDAO;
	
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;
	
	@Inject
	@Qualifier("MemberDAO")
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
					throws JsonProcessingException, ReturnedObject {

		logger.info("GetListOfProjectsAndMembers request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;	
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){	
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;	
		}
		
		List<ProjectMemberModel> pandM = projectMemberDAO.GetListOfProjectsAndMembers(userName,ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else if(pandM == null){
    		ro.throwException(false,NO_PROJECT_AND_MEMBER);
    		return null;
		} else {
    		return ro.ToJSONString(true,mapper.writeValueAsString(pandM));
    	}
	}


	@RequestMapping(value = "/GetHashMapOfProjectAndMember", method = RequestMethod.GET)
	public String GetHashMapOfProjectAndMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
					throws JsonProcessingException, ReturnedObject {
		
		logger.info("GetHashMapOfProjectAndMember request recieved: "+userName);
		
		ReturnedObject ro = new ReturnedObject();
		
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;
		}
		
		HashMap<String, List<String>> hpm = projectMemberDAO.GetHashMapOfProjectAndMember(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	} else if(hpm == null){
    		ro.throwException(false,NO_PROJECT_AND_MEMBER);
    		return null;
		} else {
    		return ro.ToJSONString(true, mapper.writeValueAsString(hpm));
    	}
	}
	
	@RequestMapping(value = "/GetProjectsMemberIsAPartOf", method = RequestMethod.GET)
	public String GetProjectsMemberIsAPartOf(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String memberPassword)
					throws JsonProcessingException, ReturnedObject {
		
		logger.info("GetProjectsMemberIsAPartOf request recieved username: "+userName +" member:"+member);
		
		//look up if member is in this company and password given matches password in DB
		ReturnedObject ro = new ReturnedObject();

		String passwordFound = memberDAO.GetMemberPassword(userName,member,ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}else if(passwordFound == null || !passwordFound.equals(memberPassword)){
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;
		}
		
		List<String> projects = projectMemberDAO.GetProjectsMemberIsAPartOf(userName,member,ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	} else if(projects == null){
    		ro.throwException(false,NO_PROJECT_AND_MEMBER);
    		return null;
		} else {
    		return ro.ToJSONString(true,mapper.writeValueAsString(projects));
    	}		
	}
	
	@RequestMapping(value = "/CreateANewProject", method = RequestMethod.GET)
	public String CreateANewProject(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException, ReturnedObject {
		
		logger.info("CreateANewProject request recieved: "+userName+"projectNAme: "+projectName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;
		}
		
		projectMemberDAO.CreateANewProject(userName,projectName,ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	} 
    	
    	return ro.ToJSONString(true,EMPTY_STRING);
    	
	}
	
	@RequestMapping(value = "/CreateAMember", method = RequestMethod.GET)
	public String CreateAMemberInAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException, ReturnedObject {
		
		logger.info("CreateAMember request recieved: "+userName+" projectName:"+projectName+" member: "+member);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;
		}
		
		projectMemberDAO.CreateAMember(userName,projectName,member,ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}
		return ro.ToJSONString(true, EMPTY_STRING);
	}
	
	@RequestMapping(value = "/DeleteAProject", method = RequestMethod.GET)
	public String DeleteAProject(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException, ReturnedObject {
		
		logger.info("DeleteAProject request recieved: "+userName+"projectName: "+projectName);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;
		}
		
		projectMemberDAO.DeleteAProject(userName,projectName,ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}
		return ro.ToJSONString(true, EMPTY_STRING);
	}
	
	@RequestMapping(value = "/DeleteAMemberFromAllProjects", method = RequestMethod.GET)
	public String DeleteAMemberFromAllProjects(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException, ReturnedObject {
		
		logger.info("DeleteAMember request recieved: "+userName+"member: "+member);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;
		}
		
		projectMemberDAO.DeleteAMemberFromAllProject(userName,member,ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}

		return ro.ToJSONString(true,EMPTY_STRING);

	}
	
	//TODO To be used by the front end for displaying files in the directory
	@RequestMapping(value = "/DeleteAMemberFromAProject", method = RequestMethod.GET)
	public String DeleteAMemberFromAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = true) String member,
			@RequestParam(value = "companyP", required = true) String companyPassword)
			throws JsonProcessingException, ReturnedObject {
		
		logger.info("DeleteAMember request recieved: "+userName+"member: "+member);
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
    		ro.throwException(false,ACCESS_FORBIDDEN);
    		return null;
		}
		
		projectMemberDAO.DeleteAMemberFromAProject(userName,projectName,member, ro);
    	if(ro.isSuccess() == false){
    		ro.throwException();
    		return null;
    	} 
    	
		return ro.ToJSONString(true,EMPTY_STRING);
	}
}
