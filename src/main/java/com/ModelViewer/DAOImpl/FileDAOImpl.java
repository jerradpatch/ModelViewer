package com.ModelViewer.DAOImpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.LoginApp.Service.FileService;
import com.ModelViewer.LoginApp.Service.ReturnedObject;

public class FileDAOImpl implements FileDAO{
	
	private static final String EMPTY_STRING = "\"\"";
	private static final String FILE_BASE_LOCATION ="/userData/userProjects/";
	private static final String MODEL_NAME = "model.unity3d";
	private static final String MODEL_NAME_PROJECT_TYPE = ".unity3d";
	private static final String MODEL_NAME_JPG_TYPE = ".jpg";
	private static final String MODEL_NAME_JPEG_TYPE = ".jpeg";
	private static final String MODEL_NAME_PNG_TYPE = ".png";
	
	private static final Logger logger = LoggerFactory.getLogger(FileDAOImpl.class);
	
	InputStream GetFileStream(String userName, String projectName, String memberName){
		return null;
		
	}
	
	public List<String> GetFileListForAProject(String userName, String projectName, ReturnedObject ro){
		
    	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
    	path.append(userName).append("/").append(projectName).append("/");
    	
    	List<String> filesNames = new ArrayList<String>();
    	File[] files = new File(path.toString()).listFiles();
    	for(File F : files){
    		filesNames.add(F.getName());
    	}
    	return filesNames;
	}
	
	public List<HashMap<String,String>> GetFileListForAProjectAndMetaData(String userName, String projectName, ReturnedObject ro){
		
    	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
    	path.append(userName).append("/").append(projectName).append("/");
    	
    	List<HashMap<String,String>> metaData = new ArrayList<HashMap<String,String>>();
    	File[] files = new File(path.toString()).listFiles();
    	for(File F : files){
    		HashMap<String,String> hm = new HashMap<String,String>();
    		String fileName = F.getName();
    		hm.put("name", fileName);
    		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") < fileName.length()){
    			int typeIndex = fileName.lastIndexOf(".") + 1;
    			String type = fileName.substring(typeIndex, fileName.length());
    			hm.put("type", type);
    		} else {
    			hm.put("type", null);
    		}

    		metaData.add(hm);
    	}
    	return metaData;
	}
	
	public Integer GetProjectFileCount(String userName, String projectName, ReturnedObject ro){
		
		List<String> files = GetFileListForAProject(userName, projectName, ro);
		int finalCount = 0;
		for(String fName : files){
			int periodLocation = fName.lastIndexOf('.');
			String type = fName.substring(periodLocation, fName.length());
			if(type.equals(MODEL_NAME_PROJECT_TYPE)){
				finalCount++;
			}
		}
    	return finalCount;
	}
	
	public Integer GetProjectImageCount(String userName, String projectName, ReturnedObject ro){
		
		List<String> files = GetFileListForAProject(userName, projectName, ro);
		int finalCount = 0;
		for(String fName : files){
			int periodLocation = fName.lastIndexOf('.');
			String type = fName.substring(periodLocation, fName.length());
			if(type.equals(MODEL_NAME_JPG_TYPE) || type.equals(MODEL_NAME_JPEG_TYPE) || type.equals(MODEL_NAME_PNG_TYPE)){
				finalCount++;
			}
		}
    	return finalCount;
	}
	
	public List<String> GetProjectImageNames(String userName, String projectName, ReturnedObject ro){
		
		List<String> files = GetFileListForAProject(userName, projectName, ro);
		List<String> finalImg = new ArrayList<String>();
		for(String fName : files){
			int periodLocation = fName.lastIndexOf('.');
			String type = fName.substring(periodLocation, fName.length());
			if(type.equals(MODEL_NAME_JPG_TYPE) || type.equals(MODEL_NAME_JPEG_TYPE) || type.equals(MODEL_NAME_PNG_TYPE)){
				finalImg.add(fName);
			}
		}
    	return finalImg;
	}
	
	public String GetProjectProjectName(String userName, String projectName, ReturnedObject ro){
		
		List<String> files = GetFileListForAProject(userName, projectName, ro);
		for(String fName : files){
			int periodLocation = fName.lastIndexOf('.');
			String type = fName.substring(periodLocation, fName.length());
			if(type.equals(MODEL_NAME_PROJECT_TYPE)){
				return fName;  
			}
		}
    	return "";
	}
	
	public void UploadAProjectFile_whole(String userName, String projectName, byte[] fileBytes, ReturnedObject ro) throws IOException{
		
		//@TODO if file exists delete it, or overwrite it by code below
		
		
    	BufferedOutputStream stream = null;
    	//upload the file
        try {
        	//@TODO change this to stream instead of completely save to memory before writing to file.
        	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
        	path.append(userName).append("/").append(projectName).append("/").append(MODEL_NAME);
        	
            byte[] bytes = fileBytes;
            File file = new File(path.toString());
            if(file.exists()){
            	file.delete();
            }
            
            file.getParentFile().mkdirs();
            stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            
            ro.setSuccess(true);
            ro.setMessage(EMPTY_STRING);
        } catch (Exception e) {
            ro.setSuccess(false);
            ro.setMessage(e.getMessage());
            logger.error(e.getMessage());         
        } finally {
        	if(stream != null){
        		stream.close();
        	}
        }
	}
}
