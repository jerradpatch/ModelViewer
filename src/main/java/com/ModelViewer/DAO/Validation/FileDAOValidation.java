package com.ModelViewer.DAO.Validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;

import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;


public class FileDAOValidation implements FileDAO{
	
	final static ValidateUtil vu = new ValidateUtil();
	
	@Inject
	@Qualifier("FileDAOImpl")
	FileDAO fileDAO;
	
	
	private static final String NULL_INPUT = "\"null values were input\"";
	private static final String EMPTY_INPUT = "\"empty values were input\"";
	
	public InputStream GetFileAProjectFile(String userName,String projectName,String fileName, ReturnedObject ro){
		if(userName == null || projectName ==null || fileName == null){
			ro.setSuccess(false);
			ro.setMessage(NULL_INPUT);
			return null;
		}
		if(userName.isEmpty() || projectName.isEmpty() || fileName.isEmpty()){
			ro.setMessage(EMPTY_INPUT);
			ro.setSuccess(false);
			return null;
		}
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateFileName(fileName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		return fileDAO.GetFileAProjectFile(userName, projectName, fileName, ro);
	}
	public void UploadAProjectFile_streaming(String userName, String projectName, String fileName, InputStream fileBytes, ReturnedObject ro)
			throws IOException {
		
		if(userName == null || projectName ==null || fileBytes ==null){
			ro.setSuccess(false);
			ro.setMessage(NULL_INPUT);
			return;
		}
		if(userName.isEmpty() || projectName.isEmpty()){
			ro.setMessage(EMPTY_INPUT);
			ro.setSuccess(false);
			return;
		}
		
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return;
		}
		vu.validateFileName(fileName, ro);
		if(!ro.isSuccess()){
			return;
		}
		fileDAO.UploadAProjectFile_streaming(userName, projectName, fileName, fileBytes, ro);
	}

	public Integer GetProjectFileCount(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return fileDAO.GetProjectFileCount(userName, projectName, ro);
	}

	public Integer GetProjectImageCount(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return fileDAO.GetProjectImageCount(userName, projectName, ro);
	}

	public List<String> GetProjectImageNames(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return fileDAO.GetProjectImageNames(userName, projectName, ro);
	}

	public String GetProjectProjectName(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return fileDAO.GetProjectProjectName(userName, projectName, ro);
	}

	public List<String> GetFileListForAProject(String userName, String projectName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		return fileDAO.GetFileListForAProject(userName, projectName, ro);
	}

	public HashMap<Object, Object> GetFileListForAProjectAndMetaData(String userName, String projectName, ReturnedObject ro) {
		
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return null;
		}
		
		return fileDAO.GetFileListForAProjectAndMetaData(userName,projectName,ro);
	}

	public void DeleteFile(String userName, String projectName, String fileName, ReturnedObject ro) {
		vu.validateUserName(userName, ro);
		if(!ro.isSuccess()){
			return;
		}
		vu.validateProjectName(projectName, ro);
		if(!ro.isSuccess()){
			return;
		}
		vu.validateFileName(fileName, ro);
		if(!ro.isSuccess()){
			return;
		}		
		fileDAO.DeleteFile(userName, projectName, fileName, ro);
	}

}
