package com.ModelViewer.DAO.Validation;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAOImpl.ProjectMemberDAOImpl;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectMemberModel;

public class ProjectMemberDAOValidation implements ProjectMemberDAO{

	private final ValidateUtil vu = new ValidateUtil();
	
	@Inject
	ProjectMemberDAOImpl projectMemberDAOImpl;
	
	public List<ProjectMemberModel> GetListOfProjectsAndMembers(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}	
		return projectMemberDAOImpl.GetListOfProjectsAndMembers(userName, ro);
	}

	public HashMap<String, List<String>> GetHashMapOfProjectAndMember(String userName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}	
		return projectMemberDAOImpl.GetHashMapOfProjectAndMember(userName, ro);
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
		return projectMemberDAOImpl.GetProjectsMemberIsAPartOf(userName, member, ro);
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
		
		return projectMemberDAOImpl.GetProjectMemberModel(userName, projectName, member, ro);
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
		projectMemberDAOImpl.CreateANewProject(userName, projectName, ro); 		
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
		projectMemberDAOImpl.CreateAMember(userName, projectName, member, ro);
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
		projectMemberDAOImpl.DeleteAProject(userName, projectName, ro);
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
		projectMemberDAOImpl.DeleteAMemberFromAllProject(userName, member, ro);
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
		projectMemberDAOImpl.DeleteAMemberFromAProject(userName, projectName, member, ro);
	}

}
