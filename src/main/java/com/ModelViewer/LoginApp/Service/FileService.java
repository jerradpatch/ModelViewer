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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ModelViewer.DAO.ProjectMemberDAO;

@Controller
@ResponseBody
@RequestMapping("/FileService")
public class FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Inject
	ProjectMemberDAO projectMemberDAO;
	private static final String EMPTY_STRING = "";
	private static final String FILE_BASE_LOCATION ="/userData/userProjects/";
	private static final String MODEL_NAME = "model.unity3d";
	
	@RequestMapping(value = "/UploadFileAProjectFile", method = RequestMethod.POST)
	public @ResponseBody String UploadFileAProject(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "file", required = true) MultipartFile fileMultiPart
			) throws IOException{

		String status = null;
        if (!fileMultiPart.isEmpty()) {
        	
        	logger.info("uploading: "+userName+" "+projectName);
        	//validate that this user, check if this project exists for this user
        	ReturnedObject ro = new ReturnedObject();
        	projectMemberDAO.GetProjectMemberModel(userName, projectName, "", ro);
        	if(ro.isSuccess() == false){
        		return ro.ToJSONString();
        	}
        	
        	BufferedOutputStream stream = null;
        	//upload the file
            try {
            	//@TODO change this to stream instead of completely save to memory before writing to file.
            	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
            	path.append(userName).append("/").append(projectName).append("/").append(MODEL_NAME);
            	
                byte[] bytes = fileMultiPart.getBytes();
                File file = new File(path.toString());
                file.getParentFile().mkdirs();
                stream = new BufferedOutputStream(new FileOutputStream(file));
                stream.write(bytes);
                
                ro.setSuccess(true);
                ro.setMessage(EMPTY_STRING);
                return ro.ToJSONString();
            } catch (Exception e) {
                ro.setSuccess(false);
                ro.setMessage(e.getMessage());
                logger.error(e.getMessage());
                return ro.ToJSONString();               
            } finally {
            	if(stream != null){
            		stream.close();
            	}
            }
            
        } else {
            status = "{\"status\":\"error\",\"message\":\"file is empty\"}";
        }
        
        return status;
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
