package com.ModelViewer.DAO.Validation;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectMemberModel;

public class ProjectMemberDAOValidation implements ProjectMemberDAO{

	private final ValidateUtil vu = new ValidateUtil();
	
	@Inject
	@Qualifier("ProjectMemberDAOImpl")
	ProjectMemberDAO projectMemberDAO;
	
	public List<ProjectMemberModel> GetListOfProjectsAndMembers(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}	
		return projectMemberDAO.GetListOfProjectsAndMembers(userName, ro);
	}

	public HashMap<String, List<String>> GetHashMapOfProjectAndMember(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}	
		return projectMemberDAO.GetHashMapOfProjectAndMember(userName, ro);
	}

	public List<String> GetProjectsMemberIsAPartOf(String userName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		} 
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return projectMemberDAO.GetProjectsMemberIsAPartOf(userName, member, ro);
	}

	public ProjectMemberModel GetProjectMemberModel(String userName, String projectName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		return projectMemberDAO.GetProjectMemberModel(userName, projectName, member, ro);
	}

	public void CreateANewProject(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return;
		}		
		projectMemberDAO.CreateANewProject(userName, projectName, ro); 		
	}

	public void CreateAMember(String userName, String projectName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return;
		}
		projectMemberDAO.CreateAMember(userName, projectName, member, ro);
	}

	public void DeleteAProject(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return;
		}
		projectMemberDAO.DeleteAProject(userName, projectName, ro);
	}

	public void DeleteAMemberFromAllProject(String userName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return;
		}	
		projectMemberDAO.DeleteAMemberFromAllProject(userName, member, ro);
	}

	public void DeleteAMemberFromAProject(String userName, String projectName, String member, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return;
		}
		
		vu.validateMemberName(member, ro);
		if(!ro.isSuccess()){
			return;
		}
		projectMemberDAO.DeleteAMemberFromAProject(userName, projectName, member, ro);
	}

}
