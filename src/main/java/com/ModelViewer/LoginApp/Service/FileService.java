package com.ModelViewer.LoginApp.Service;

import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.DAO.FileMetaDAO;
import com.ModelViewer.Model.FileMetaModel;


@Controller
@ResponseBody
@RequestMapping("/FileService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class FileService {
	
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
	
	@Inject
	@Qualifier("FileMetaDAO")
	FileMetaDAO fileMetaDAO;
	
	@RequestMapping(value = "/createFile", method = RequestMethod.POST)
	public @ResponseBody void createFile(@RequestParam(value = "file", required = true) MultipartFile fileMultiPart) throws Exception{
	
//		logger.debug("UploadFileAProjectFile: userName:"+userName+" projectName:"+projectName +" fileName:"+fileMultiPart.getName());
		
		String fileLocation = "10";

        if (fileMultiPart.isEmpty()) {
        	ReturnedObject.sThrowException(COULD_NOT_SAVE_FILE);
        }
        
    	//validate that this user, check if this project exists for this user

    	//test for the correct file type,//// tested in model
    	 String inFileType = fileMultiPart.getContentType();
    	 if(!inFileType.equals(PNG_MIME_TYPE) && !inFileType.equals(JPEG_MIME_TYPE) && !inFileType.equals(MODEL_MIME_TYPE)){
    		 ReturnedObject.sThrowException(File_TYPE_NOT_ALLOWED);
     	}
//        fileDAO	
    	String fileName = fileMultiPart.getOriginalFilename();
    	if(inFileType.equals(PNG_MIME_TYPE) || inFileType.equals(JPEG_MIME_TYPE)){ //if its a image being uploaded
    		//check that they havent already saved 5 files
    		Integer fileCount = fileDAO.readFileProjectImageCount(fileMetaModel);
    		if(fileCount >= MAXIMUM_IMAGE_COUNT){  //test if their image size not exceeded    			  
    			ReturnedObject.sThrowException(IMAGE_LIMIT_EXCEEDED);
    		} else {
    			fileLocation = "";
    			fileUploadRepository.uploadFile(fileLocation,fileMultiPart.getName(),fileMultiPart.getInputStream());	
    		}
    	} else if (inFileType.equals(PNG_MIME_TYPE)){
    		Integer fileCount = fileDAO.readFileProjectProjectCount(fileMetaModel);
    		if(fileCount >= MAXIMUM_PROJECT_COUNT){
    			ReturnedObject.sThrowException(PROJECT_LIMIT_EXCEEDED);
    		} else {
    			fileLocation = "";
    			fileUploadRepository.uploadFile(fileLocation,fileMultiPart.getName(),fileMultiPart.getInputStream());
    		}
    	}
    	
    	java.util.Date date= new java.util.Date();
    	Timestamp ts = new Timestamp(date.getTime()); 
    	
    	FileMetaModel fileModel = new FileMetaModel(fileMultiPart.getName(), fileLocation, fileMultiPart.getSize(), fileMultiPart.getContentType(), ts);
    	fileMetaDAO.createFileModel(fileMetaModel);
	}
	
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public @ResponseBody void deleteFile(@RequestParam(value = "file", required = true) FileMetaModel fileMetaModel) throws Exception{
		//delte from S3 and delete meta data
	}
	
	@RequestMapping(value = "/readFile", method = RequestMethod.POST)
	public @ResponseBody void readFile(@RequestParam(value = "file", required = true) FileMetaModel fileMetaModel) throws Exception{
		ServletOutputStream os = response.getOutputStream();
//		InputStream is = fileDAO.getFile(fileModel);

//TODO with S3
//		org.apache.commons.io.IOUtils.copy(is, os);
//		if(is!= null){
//			is.close();
//		}
    	return;
	}
	
	
	
	
}
