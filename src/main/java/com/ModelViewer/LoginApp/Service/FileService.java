package com.ModelViewer.LoginApp.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
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
import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.ProjectInfoDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.ProjectInfoModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
@RequestMapping("/FileService")
public class FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	@Qualifier("ProjectMemberDAO")
	ProjectMemberDAO projectMemberDAO;
	
	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;
	
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;
	
	@Inject
	@Qualifier("ProjectInfoDAO")
	ProjectInfoDAO projectInfoDAO;
	
	@Inject
	@Qualifier("FileDAO")
	FileDAO fileDAO;
	
	private static final String EMPTY_STRING = "\"\"";
	private static final String IMAGE_LIMIT_EXCEEDED = "\"Image files exceed allowed amount (5). Please delete some image files before adding more.\"";
	private static final String PROJECT_LIMIT_EXCEEDED = "\"Project files exceed allowed amount (1). Please delete some project files before adding more.\"";
	private static final String File_TYPE_NOT_ALLOWED = "\"This file type is not allowed. Only \".zip\", \".jpg\\.jpeg\", and \".png\" file types are allowed.\"";
	private static final String COULD_NOT_SAVE_FILE = "\"Could not save file, file empty.\"";
	private final static String ACCESS_FORBIDDEN = "\"Access Forbbiden\"";
	
	private static final String FILE_BASE_LOCATION ="/userData/userProjects/";
	private static final String MODEL_NAME = "model.unity3d";
	private static final String MODEL_MIME_TYPE = "application/zip";
	private static final String JPEG_MIME_TYPE = "image/jpeg";
	private static final String PNG_MIME_TYPE = "image/png";
	
	private static final String MODEL_NAME_PROJECT_TYPE = "folder";
	private static final String MODEL_NAME_JPG_TYPE = ".jpg";
	private static final String MODEL_NAME_JPEG_TYPE = ".jpeg";
	private static final String MODEL_NAME_PNG_TYPE = ".png";
	
	private static final int MAXIMUM_IMAGE_COUNT = 5;
	private static final int MAXIMUM_PROJECT_COUNT = 5;
	
	private static final String BASE_URL = "/ModelViewer/FileService";
	
	@RequestMapping(value = "/UploadFileAProjectFile", method = RequestMethod.POST)
	public @ResponseBody String UploadFileAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "userP", required = true) String password,
			@RequestParam(value = "file", required = true) MultipartFile fileMultiPart
			) throws IOException{
	
		logger.debug("UploadFileAProjectFile: userName:"+userName+" projectName:"+projectName +" fileName:"+fileMultiPart.getName());
		
		ReturnedObject ro = new ReturnedObject();
		
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro); 
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
    	projectMemberDAO.GetProject(userName, projectName, ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}
    	//test for the correct file type
    	 String inFileType = fileMultiPart.getContentType();
    	 if(!inFileType.equals(PNG_MIME_TYPE) && !inFileType.equals(JPEG_MIME_TYPE) && !inFileType.equals(MODEL_MIME_TYPE)){
     		
             ro.setMessage(File_TYPE_NOT_ALLOWED);
             ro.setSuccess(false);
             return ro.ToJSONString();
     	}
        	
    	String fileName = fileMultiPart.getOriginalFilename();
    	if(inFileType.equals(PNG_MIME_TYPE) || inFileType.equals(JPEG_MIME_TYPE)){ //if its a image being uploaded
    		//check that they havent already saved 5 files
    		Integer fileCount = fileDAO.GetProjectImageCount(userName, projectName, ro);
        	if(ro.isSuccess() == false){
        		return ro.ToJSONString();
        	}
    		
    		if(fileCount <= MAXIMUM_IMAGE_COUNT){  //test if their image size not exceeded
    			
    			fileDAO.UploadAProjectFile_streaming(userName,projectName,fileName,fileMultiPart.getInputStream(), ro);
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
    	
		fileDAO.UploadAProjectFile_streaming(userName,projectName,fileName,fileMultiPart.getInputStream(), ro);
    	if(ro.isSuccess() == false){
    		return ro.ToJSONString();
    	}
    	
		ro.setMessage(EMPTY_STRING);
        ro.setSuccess(true);   
    	
    	
//		int fileCount = fileDAO.GetProjectFileCount(userName, projectName, ro);
//    	if(ro.isSuccess() == false){
//    		return ro.ToJSONString();
//    	}
		
//		file will be unzipped and contain many different types
//		if(fileCount <= MAXIMUM_PROJECT_COUNT){  //if amount of project limit not exceeded
//			
//			fileDAO.UploadAProjectFile_whole(userName,projectName,fileName,fileMultiPart.getInputStream(), ro);
//        	if(ro.isSuccess() == false){
//        		return ro.ToJSONString();
//        	}
//        	
//			ro.setMessage(EMPTY_STRING);
//            ro.setSuccess(true);     		
//            
//            
//		}  else {
//			
//            ro.setMessage(PROJECT_LIMIT_EXCEEDED);
//            ro.setSuccess(false);        			
//		}
		
		return ro.ToJSONString();	

	}
	
	@RequestMapping(value = "/GetFileAProjectFile", method = RequestMethod.GET)
	public void GetFileAProjectFile(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "userP", required = false) String userP,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = false) String member,
			@RequestParam(value = "memberP", required = false) String memberP,
			@RequestParam(value = "fileName", required = true) String fileName,
			HttpServletResponse response
			) throws IOException{
		
		logger.debug("file request recieved: userName"+userName+" userP:"+userP+" projectName:"+projectName+" memeber:"+member+" memberP:"+memberP+" fileName:"+fileName);
		if(userName != null && userP != null){
			ReturnedObject ro = new ReturnedObject();
			String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro);
	    	if(ro.isSuccess() == false){
	    		response.setStatus(404);
	    		logger.debug("ro not successful: userName and password rejected");
	    		return;
	    	}else if(passwordFound == null || !passwordFound.equals(userP)){
				response.setStatus(404);
				logger.debug(" userName and password rejected");
				return;
			}
		} else if (member != null && memberP != null) {
			ReturnedObject ro = new ReturnedObject();
			String passwordFound = memberDAO.GetMemberPassword(userName, member, ro);
	    	if(ro.isSuccess() == false){
	    		logger.debug("ro rejected member");
	    		response.setStatus(404);
	    		return;
	    	}else if(passwordFound == null || !passwordFound.equals(memberP)){
	    		logger.debug("member password rejected");
				response.setStatus(404);
				return;
			}
		} else {
			response.setStatus(404);
			logger.debug("emember and user were both null");
			return;			
		}
		
		logger.debug("retriving file");
		ReturnedObject ro = new ReturnedObject();
		
		ServletOutputStream os = response.getOutputStream();
		InputStream is = fileDAO.GetFileAProjectFile(userName, projectName, fileName, ro);
		if(!ro.isSuccess()){
			if(is!= null){
				is.close();
			}
			response.setStatus(404);
			return;
		}

		org.apache.commons.io.IOUtils.copy(is, os);
		if(is!= null){
			is.close();
		}
    	return;
		
	    		  
	}

	//return lists/hashmaps/strings
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/GetAllFileMetaData", method = RequestMethod.GET)
	public String GetAllFileMetaData(	
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "member", required = false) String member,
			@RequestParam(value = "uPass", required = false) String uPass,
			@RequestParam(value = "memberP", required = false) String memberP
			) throws IOException{
		
		ReturnedObject ro = new ReturnedObject();
		
		if(userName != null && uPass != null){
			String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro); 
			if(ro.isSuccess() == false){
				return ro.ToJSONString();
			} else if(passwordFound == null || !passwordFound.equals(uPass)){
				ro.setSuccess(false);
				ro.setMessage(ACCESS_FORBIDDEN);
				return ro.ToJSONString();	
			}
		} else if (member != null && memberP != null){
			String passwordFound = memberDAO.GetMemberPassword(userName, memberP, ro); 
			if(ro.isSuccess() == false){
				return ro.ToJSONString();
			} else if(passwordFound == null || !passwordFound.equals(memberP)){
				ro.setSuccess(false);
				ro.setMessage(ACCESS_FORBIDDEN);
				return ro.ToJSONString();	
			}			
		} else {
			ro.setSuccess(false);
			ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();				
		}
		
		logger.debug("GetAllFileMetaData: userName: "+userName+" projectName: "+projectName+" memberName: "+member);
		HashMap<Object, Object> fileMeta = fileDAO.GetFileListForAProjectAndMetaData(userName, projectName, ro);
    	if(ro.isSuccess() == false || fileMeta == null){
    		return ro.ToJSONString();
    	}
    	
    	//make json object {images:<all images>,projects:<all projects>}
    	List<HashMap<String,String>> rootFileList = null;
    	try{
    		rootFileList = (List<HashMap<String,String>>) fileMeta.get("list");
    	} catch (Exception e){
			ro.setSuccess(false);
			ro.setMessage("error getting files");
			return ro.ToJSONString();    		
    	}
    	HashMap<Object, Object> retJson = new HashMap<Object, Object>();
    	
		//images
    	List<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
    	for(HashMap<String,String> file : rootFileList){
    		if(file.get("type").equals(MODEL_NAME_JPG_TYPE) || file.get("type").equals(MODEL_NAME_JPEG_TYPE) || file.get("type").equals(MODEL_NAME_PNG_TYPE)){
    			StringBuilder SB = new StringBuilder(BASE_URL);
    			file.put("url", SB
    					.append("/GetFileAProjectFile?userName=").append(userName)
    					.append("&projectName=").append(projectName)
    					.append("&member=").append(member)
    					.append("&memberP=").append(memberP)
    					.append("&fileName=").append(file.get("name")).toString());
    			imageList.add(file);
    		}
    	}
		retJson.put("Images", imageList);
		
    	//models
    	List<HashMap<String,String>> modelList = new ArrayList<HashMap<String,String>>();
    	for(HashMap<String,String> file : rootFileList){
    		if(file.get("type").equals(MODEL_NAME_PROJECT_TYPE)){
    			StringBuilder SB = new StringBuilder(BASE_URL);
    			file.put("url", SB
    					.append("/GetFileAProjectFile?userName=").append(userName)
    					.append("&projectName=").append(projectName)
    					.append("&member=").append(member)
    					.append("&memberP=").append(memberP)
    					.append("&fileName=").append(file.get("name")).toString());
    			modelList.add(file);
    		}
    	}
    	retJson.put("Models", modelList);
    	
//    	ProjectInfoModel story = projectInfoDAO.GetProjectInfo(userName, projectName, ro);   	
//    	if(!ro.isSuccess()){
//    		return ro.ToJSONString();
//    	}
//    	if(story == null || story.getStory() == null){
//    		retJson.put("Story", "");	    		
//    	} else {
//    	   	retJson.put("Story", story.getStory());	
//    	}
    	
    	ro.setSuccess(true);
    	ro.setMessage(mapper.writeValueAsString(retJson));
    	return ro.ToJSONString();
	}
	
	@RequestMapping(value = "/DeleteFile", method = RequestMethod.GET)
	public String DeleteFile(	
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "fileName", required = true) String fileName,
			@RequestParam(value = "uPass", required = true) String uPass
			) throws IOException{
		
		ReturnedObject ro = new ReturnedObject();
		String passwordFound = userDAO.GetUserPasswordByUserName(userName, ro); 
		if(ro.isSuccess() == false){
			return ro.ToJSONString();
		} else if(passwordFound == null || !passwordFound.equals(uPass)){
			ro.setSuccess(false);
			ro.setMessage(ACCESS_FORBIDDEN);
			return ro.ToJSONString();	
		}		
		
		fileDAO.DeleteFile(userName,projectName,fileName,ro);
		return ro.ToJSONString();
	}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}
