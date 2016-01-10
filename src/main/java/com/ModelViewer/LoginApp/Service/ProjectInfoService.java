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
import com.ModelViewer.DAO.ProjectInfoDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.ProjectInfoModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
@RequestMapping("/ProjectInfoService")
public class ProjectInfoService {
	
	private static final String EMPTY_STRING = "\"\"";
	private final static String NO_PROJECT_AND_MEMBER = "\"No project and members found\"";
	private final static String ACCESS_FORBIDDEN = "\"Access Forbbiden\"";
	private static final Logger logger = LoggerFactory.getLogger(ProjectInfoService.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;
	
	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;
	
	@Inject
	@Qualifier("ProjectInfoDAO")
	ProjectInfoDAO projectInfoDAO;
	

	@RequestMapping(value = "/ReadProjectInfo", method = RequestMethod.GET)
	public String GetProjectStory(
		@RequestParam(value = "userName", required = true) String userName, 
		@RequestParam(value = "userPass", required = false) String userPass, 
		@RequestParam(value = "projectName", required = true) String projectName,
		@RequestParam(value = "memberName", required = false) String memberName, 
		@RequestParam(value = "memberPass", required = false) String memberPass)
				throws JsonProcessingException {
		
		logger.info("GetProjectStory request recieved userName: "+userName+" projectName: "+projectName +" memberName: "+memberName);
		
		ReturnedObject ro = new ReturnedObject();
		
		if(userName != null && userPass != null){
			String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
	    	if(ro.isSuccess() == false){
	    		return ro.ToJSONString();
	    	}else if(passwordFound == null || !passwordFound.equals(userPass)){
	    		ro.setSuccess(false);
	    		ro.setMessage(ACCESS_FORBIDDEN);
				return ro.ToJSONString();	
			}
		} else if(memberName != null && memberPass != null){
			String passwordFound = memberDAO.GetMemberPassword(userName, memberName, ro);
	    	if(ro.isSuccess() == false){
	    		return ro.ToJSONString();
	    	}else if(passwordFound == null || !passwordFound.equals(memberPass)){
	    		ro.setSuccess(false);
	    		ro.setMessage(ACCESS_FORBIDDEN);
				return ro.ToJSONString();	
			}			
		} else {
			ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();
		}
		
		ProjectInfoModel projectInfo = projectInfoDAO.GetProjectInfo(userName, projectName, ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}
		
		ro.setSuccess(true);
		ro.setMessage("\""+projectInfo.getStory()+"\"");
		return ro.ToJSONString();
	}
	
	@RequestMapping(value = "/CreateUpdateProjectInfo", method = RequestMethod.GET)
	public String CreateUpdateStory(
		@RequestParam(value = "userName", required = true) String userName, 
		@RequestParam(value = "userPass", required = false) String userPass, 
		@RequestParam(value = "projectName", required = true) String projectName,
		@RequestParam(value = "memberName", required = false) String memberName, 
		@RequestParam(value = "memberPass", required = false) String memberPass,
		@RequestParam(value = "story", required = false) String story)
				throws JsonProcessingException {
		
		logger.info("GetProjectStory request recieved: "+userName+" projectName:"+projectName +" story:"+story);
		
		ReturnedObject ro = new ReturnedObject();
		
		if(userName != null && userPass != null){
			String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
	    	if(ro.isSuccess() == false){
	    		return ro.ToJSONString();
	    	}else if(passwordFound == null || !passwordFound.equals(userPass)){
	    		ro.setSuccess(false);
	    		ro.setMessage(ACCESS_FORBIDDEN);
				return ro.ToJSONString();	
			}
		} else if(memberName != null && memberPass != null){
			String passwordFound = memberDAO.GetMemberPassword(userName, memberName, ro);
	    	if(ro.isSuccess() == false){
	    		return ro.ToJSONString();
	    	}else if(passwordFound == null || !passwordFound.equals(userPass)){
	    		ro.setSuccess(false);
	    		ro.setMessage(ACCESS_FORBIDDEN);
				return ro.ToJSONString();	
			}			
		} else {
			ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();
		}
		ProjectInfoModel pim = new ProjectInfoModel();
		pim.setUserName(userName);
		pim.setProjectName(projectName);
		pim.setStory(story);
		
		projectInfoDAO.CreateProjectInfo(pim, ro);
		if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}
		
		ro.setSuccess(true);
		return ro.ToJSONString();
	}
}
