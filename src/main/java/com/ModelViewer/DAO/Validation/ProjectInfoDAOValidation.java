package com.ModelViewer.DAO.Validation;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.ModelViewer.DAO.ProjectInfoDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectInfoModel;

public class ProjectInfoDAOValidation implements ProjectInfoDAO{

	private final ValidateUtil vu = new ValidateUtil();
	
	@Inject
	@Qualifier("ProjectInfoDAOImpl")
	ProjectInfoDAO projectInfoDAO;
	
	public ProjectInfoModel GetProjectInfo(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		return projectInfoDAO.GetProjectInfo(userName, projectName, ro);
	}

	public void CreateProjectInfo(ProjectInfoModel pim, ReturnedObject ro) {
		
		if(!validatedProjectInfoModel(pim,ro)){
			ro.setSuccess(false);
			ro.setMessage("\"SetProjectInfo: could not set text\"");
		}
		
		projectInfoDAO.CreateProjectInfo(pim, ro);
		
	}
	
	private boolean validatedProjectInfoModel(ProjectInfoModel pim, ReturnedObject ro){
		String un = pim.getUserName();
		String pn = pim.getProjectName();
		String s = pim.getStory();
	
		vu.validateUserName(un, ro);
		if(!ro.isSuccess()){
			return false;
		}
		
		vu.validateProjectName(pn, ro);
		if(!ro.isSuccess()){
			return false;
		}
		
		vu.validateTextField(s, ro);
		if(!ro.isSuccess()){
			return false;
		}
		
		return true;

	}
}






