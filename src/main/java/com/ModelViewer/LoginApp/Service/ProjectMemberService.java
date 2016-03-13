package com.ModelViewer.LoginApp.Service;


import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.ProjectMemberModel;
import com.ModelViewer.Model.UserModel;

	
@Controller
@ResponseBody
@RequestMapping("/ProjectMemberService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ProjectMemberService {
	

	@Inject
	@Qualifier("ProjectMemberDAO")
	ProjectMemberDAO projectMemberDAO;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/alive", method = RequestMethod.GET)
	public Object home() {		
		return "hello world";
	}
	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	public Object createProject(@RequestBody(required = true) ProjectMemberModel projectMemberModel) throws Exception{
		projectMemberDAO.createProject(projectMemberModel);   	
    	return true; 	
	}
	@RequestMapping(value = "/readMemberProjects", method = RequestMethod.POST)
	public Object readMemberProjects(@RequestBody(required = true) MemberModel memberModel) throws Exception{	
		return memberModel.getProjectMemberModel();
	}
	@RequestMapping(value = "/readUserProjects", method = RequestMethod.POST)
	public Object readUserProjects(@RequestBody(required = true) UserModel userModel) throws Exception {
		return userModel.getProjectMemberModels();
	}
	@RequestMapping(value = "/readUserProjectsAndMembers", method = RequestMethod.POST)
	public Object readUserProjectsAndMembers(@RequestBody(required = true) UserModel userModel) throws Exception {
		
		HashMap<ProjectMemberModel,Set<MemberModel>> pm = new HashMap<ProjectMemberModel,Set<MemberModel>>();
		
		for(ProjectMemberModel pmm : userModel.getProjectMemberModels()){
			pm.put(pmm, pmm.getMembers());
		}
		return pm;
	}
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public Object updateProject(@RequestBody(required = true) ProjectMemberModel updateProject) throws Exception{
		projectMemberDAO.updateProject(updateProject);
		return true;
	}
	
	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public Object deleteProject(@RequestBody(required = true) ProjectMemberModel projectMemberModel) throws Exception{
		projectMemberDAO.deleteProject(projectMemberModel);
		return true;
	}
	//TODO moved to user
//	@RequestMapping(value = "/GetListOfProjectsAndMembers", method = RequestMethod.GET)
//	public String GetListOfProjectsAndMembers(
//			@RequestParam(value = "userName", required = true) String userName, 
//			@RequestParam(value = "companyP", required = true) String companyPassword)
//					throws JsonProcessingException, ReturnedObject {
//
//		logger.info("GetListOfProjectsAndMembers request recieved: "+userName);
//		
//		ReturnedObject ro = new ReturnedObject();
//		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
//    	if(ro.isSuccess() == false){
//    		ro.throwException();
//    		return null;	
//    	}else if(passwordFound == null || !passwordFound.equals(companyPassword)){	
//    		ro.throwException(false,ACCESS_FORBIDDEN);
//    		return null;	
//		}
//		
//		List<ProjectMemberModel> pandM = projectMemberDAO.GetListOfProjectsAndMembers(userName,ro);
//    	if(ro.isSuccess() == false){
//    		return ro.ToJSONString();
//    	} else if(pandM == null){
//    		ro.throwException(false,NO_PROJECT_AND_MEMBER);
//    		return null;
//		} else {
//    		return ro.ToJSONString(true,mapper.writeValueAsString(pandM));
//    	}
//	}

//	TODO move to member model
//	@RequestMapping(value = "/readMemberProjects", method = RequestMethod.GET) //moved to user
//	public boolean GetHashMapOfProjectAndMember(
//			@RequestParam(value = "userName", required = true) String userName,
//			@RequestParam(value = "companyP", required = true) String companyPassword)
//					throws JsonProcessingException, ReturnedObject {
//
//		HashMap<String, Set<MemberModel>> hpm = projectMemberDAO.GetHashMapOfProjectAndMember(userName, ro);
//    	return true;
//	}
//	
	
	//TODO //this is part of Delete memeber
//	@RequestMapping(value = "/DeleteAMemberFromAllProjects", method = RequestMethod.GET)
//	public String deleteProject(@RequestBody(required = true) ProjectMemberModel projectMemberModel) throws Exception{
//		
//		logger.info("DeleteAMember request recieved: "+userName+"member: "+member);
//		
//		ReturnedObject ro = new ReturnedObject();
//		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
//    	if(ro.isSuccess() == false){
//    		ro.throwException();
//    		return null;
//    	}else if(passwordFound == null || !passwordFound.equals(passwordFound)){
//    		ro.throwException(false,ACCESS_FORBIDDEN);
//    		return null;
//		}
//		
//		projectMemberDAO.DeleteAMemberFromAllProject(userName,member,ro);
//    	if(ro.isSuccess() == false){
//    		ro.throwException();
//    		return null;
//    	}
//
//		return ro.ToJSONString(true,EMPTY_STRING);
//
//	}
	
	//update a project with one less memeber?
//	@RequestMapping(value = "/deleteProjectMember", method = RequestMethod.POST)
//	public String deleteProjectMember(@RequestBody(required = true) MemberModel memberModel) throws Exception{
//		memberModel.deleteMember(memberModel);
//		return true;
//	}
}
