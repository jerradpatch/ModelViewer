package com.ModelViewer.LoginApp.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;

@Controller
@ResponseBody
@RequestMapping("/FileService")
public class FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Inject
	@Qualifier("ProjectMemberDAO")
	ProjectMemberDAO projectMemberDAO;

	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;
	
	@Inject
	@Qualifier("FileDAO")
	FileDAO fileDAO;
	
	private static final String EMPTY_STRING = "";
	private static final String FILE_BASE_LOCATION ="/userData/userProjects/";
	private static final String MODEL_NAME = "model.unity3d";
	private static final String IMAGE_LIMIT_EXCEEDED = "Image files exceed allowed amount (5). Please delete some image files before adding more.";
	private static final String PROJECT_LIMIT_EXCEEDED = "Project files exceed allowed amount (1). Please delete some project files before adding more.";
	private static final String File_TYPE_NOT_ALLOWED = "This file type is not allowed. Only \".unity3d\", \".jpg\\.jpeg\", and \".png\" file types are allowed.";
	private static final String COULD_NOT_SAVE_FILE = "Could not save file, file empty.";
	private final static String ACCESS_FORBIDDEN = "Access Forbbiden";
	
	private static final String UNITY_MIME_TYPE = "application/vnd.unity";
	private static final String JPEG_MIME_TYPE = "image/png";
	private static final String PNG_MIME_TYPE = "image/jpeg";
	
	private static final int MAXIMUM_IMAGE_COUNT = 5;
	private static final int MAXIMUM_PROJECT_COUNT = 1;
	
	@RequestMapping(value = "/UploadFileAProjectFile", method = RequestMethod.POST)
	public @ResponseBody String UploadFileAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "userP", required = true) String password,
			@RequestParam(value = "file", required = true) MultipartFile fileMultiPart
			) throws IOException{
	
		logger.info("uploading: "+userName+" "+projectName);
		
		ReturnedObject ro = new ReturnedObject();
		
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro); need to test this
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	} else if(passwordFound == null || !passwordFound.equals(password)){
    		ro.setSuccess(false);
    		ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}
    	
        if (fileMultiPart.isEmpty()) {
        	ro.setMessage(COULD_NOT_SAVE_FILE);
            ro.setSuccess(false);
        	return ro.ToJSONString();
        }
        
    	//validate that this user, check if this project exists for this user
    	projectMemberDAO.GetProjectMemberModel(userName, projectName, "", ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}
    	
    	//test for the correct file type
    	 String inFileType = fileMultiPart.getContentType();
    	 if(!inFileType.equals(PNG_MIME_TYPE) && !inFileType.equals(JPEG_MIME_TYPE) && !inFileType.equals(UNITY_MIME_TYPE)){
     		
             ro.setMessage(File_TYPE_NOT_ALLOWED);
             ro.setSuccess(false);
             return ro.ToJSONString();
     	}
        	
       
        	
    	if(inFileType.equals(PNG_MIME_TYPE) || inFileType.equals(JPEG_MIME_TYPE)){ //if its a image being uploaded
    		//check that they havent already saved 5 files
    		int fileCount = fileDAO.GetProjectImageCount(userName, projectName, ro);
        	if(ro.isSuccess() == false){
        		return ro.ToJSONString();
        	}
    		
    		if(fileCount <= MAXIMUM_IMAGE_COUNT){  //test if their image size not exceeded
    			
    			fileDAO.UploadAProjectFile_whole(userName,projectName,fileMultiPart.getBytes(), ro);
            	if(ro.isSuccess() == false){
            		return ro.ToJSONString();
            	}
            	
    			ro.setMessage(EMPTY_STRING);
                ro.setSuccess(true);   
                
    		} else {
    			
                ro.setMessage(IMAGE_LIMIT_EXCEEDED);
                ro.setSuccess(false);        			
    		}
    		
    		return ro.ToJSONString();
    	} 
    	
    	//if(inFileType.equals(UNITY_MIME_TYPE)){ //if project being uploaded
    		
    		int fileCount = fileDAO.GetProjectFileCount(userName, projectName, ro);
        	if(ro.isSuccess() == false){
        		return ro.ToJSONString();
        	}
    		
    		if(fileCount <= MAXIMUM_PROJECT_COUNT){  //if amount of project limit not exceeded
    			
    			fileDAO.UploadAProjectFile_whole(userName,projectName,fileMultiPart.getBytes(), ro);
            	if(ro.isSuccess() == false){
            		return ro.ToJSONString();
            	}
            	
    			ro.setMessage(EMPTY_STRING);
                ro.setSuccess(true);     		
                
                
    		}  else {
    			
                ro.setMessage(PROJECT_LIMIT_EXCEEDED);
                ro.setSuccess(false);        			
    		}
    		
    		return ro.ToJSONString();
    	//}

	}
	
	@RequestMapping(value = "/GetFileAProjectFile", method = RequestMethod.GET)
	public void GetFileAProjectFile(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = true) String member,
			HttpServletResponse response
			) throws IOException{
		
		StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
    	path.append(userName).append("/").append(projectName).append("/").append(MODEL_NAME);
    	
    	InputStream is = null;
	    try {
	    	File file = new File(path.toString());
	    	logger.info("get a file path: "+path);
	        // get your file as InputStream
	        is = new BufferedInputStream(new FileInputStream(file));
	        // copy it to response's OutputStream
	        org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
	        response.flushBuffer();
	        is.close();
	      } catch (IOException ex) {
	        logger.error("Error writing file to output stream. Filename was '{}'");

	      } finally{
	    	  if(is != null){
	    		  is.close();
	    	  }
	    	  
	      }
	    		  
	}

}
