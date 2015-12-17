package com.ModelViewer.DAOImpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.LoginApp.Service.FileService;
import com.ModelViewer.LoginApp.Service.ReturnedObject;

public class FileDAOImpl implements FileDAO{
	
	private static final String EMPTY_STRING = "\"\"";
	private static final String FILE_BASE_LOCATION ="/userData/userProjects/";
	private static final String MODEL_NAME = "model.unity3d";
	private static final String MODEL_NAME_PROJECT_TYPE = "unity3d";
	private static final String MODEL_NAME_JPG_TYPE = "jpg";
	private static final String MODEL_NAME_JPEG_TYPE = "jpeg";
	private static final String MODEL_NAME_PNG_TYPE = "png";
	
	private static final Logger logger = LoggerFactory.getLogger(FileDAOImpl.class);
	
	InputStream GetFileStream(String userName, String projectName, String memberName){
		return null;
		
	}
	
	/**
	 * @return null and ro set if error, else value
	 */
	public List<String> GetFileListForAProject(String userName, String projectName, ReturnedObject ro){
		
		String location = makeFolderPath(userName, projectName);
    	if(location == null){
    		ro.setSuccess(false);
    		ro.setMessage("path is empty");
    		return null;
    	}
    	File folder = CreateFolderIfNotExist(location, ro);
    	
    	if(folder.listFiles() == null){
    		ro.setSuccess(false);
    		ro.setMessage("not a folder");
    		return null;
    	}
    	
    	List<String> filesNames = new ArrayList<String>();
    	File[] files = folder.listFiles();
    	
    	for(File F : files){
    		filesNames.add(F.getName());
    	}
    	return filesNames;
	}
	
	/**
	 * @return null if path is unable to be made, empty list if no files, otherwise files list< name and type>; type, is empty if there is no extension
	 */
	public List<HashMap<String,String>> GetFileListForAProjectAndMetaData(String userName, String projectName, ReturnedObject ro){
		
		String location = makeFolderPath(userName, projectName);
    	if(location == null){
    		ro.setSuccess(false);
    		ro.setMessage("Couldnt make the path");
    		return null;
    	}
    	
    	List<HashMap<String,String>> metaData = new ArrayList<HashMap<String,String>>();
    	File[] files = new File(location).listFiles();
    	if(files == null){
    		ro.setSuccess(true);
    		ro.setMessage("");
    		return metaData;  		
    	}
    	
    	for(File F : files){
    		HashMap<String,String> hm = new HashMap<String,String>();
    		String fileName = F.getName();
    		
    		hm.put("name", fileName);
    		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") < fileName.length()){
    			int typeIndex = fileName.lastIndexOf(".") + 1;
    			String type = fileName.substring(typeIndex, fileName.length());
    			hm.put("type", type);
    		} else {
    			hm.put("type", "");
    			logger.info("GetFileListForAProjectAndMetaData: userName: "+userName+" projectName: "+projectName+"; projectMeta dont have .extensions" );
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
			if(periodLocation >= 0){
				String type = fName.substring(periodLocation, fName.length());
				if(type.equals(MODEL_NAME_PROJECT_TYPE)){
					finalCount++;
				}
			} else {
				logger.info("GetProjectFileCount: userName: "+userName+" projectName: "+projectName+"; images dont have .extensions" );
			}	
		}
    	return finalCount;
	}
	
	/**
	 * @return returns null and ro set if error, else returns value
	 */
	public Integer GetProjectImageCount(String userName, String projectName, ReturnedObject ro){
		
		List<String> files = GetFileListForAProject(userName, projectName, ro);
		if(files == null || !ro.isSuccess()){
			return null;
		}			
		int finalCount = 0;
		for(String fName : files){
			int periodLocation = fName.lastIndexOf('.');
			if(periodLocation >= 0){
				String type = fName.substring(periodLocation, fName.length());
				if(type.equals(MODEL_NAME_JPG_TYPE) || type.equals(MODEL_NAME_JPEG_TYPE) || type.equals(MODEL_NAME_PNG_TYPE)){
					finalCount++;
				}
			} else {
				logger.info("GetProjectImageCount: userName: "+userName+" projectName: "+projectName+"; images dont have .extensions" );
			}
		}
    	return finalCount;
	}
	
	public List<String> GetProjectImageNames(String userName, String projectName, ReturnedObject ro){
		
		List<String> files = GetFileListForAProject(userName, projectName, ro);
		List<String> finalImg = new ArrayList<String>();
		for(String fName : files){
			int periodLocation = fName.lastIndexOf('.');
			if(periodLocation >= 0){
				String type = fName.substring(periodLocation, fName.length());
				if(type.equals(MODEL_NAME_JPG_TYPE) || type.equals(MODEL_NAME_JPEG_TYPE) || type.equals(MODEL_NAME_PNG_TYPE)){
					finalImg.add(fName);
				}
			} else {
				logger.info("GetProjectImageNames: userName: "+userName+" projectName: "+projectName+"; images dont have .extensions" );
			}
		}
    	return finalImg;
	}
	
	public String GetProjectProjectName(String userName, String projectName, ReturnedObject ro){
		
		List<String> files = GetFileListForAProject(userName, projectName, ro);
		for(String fName : files){
			int periodLocation = fName.lastIndexOf('.');
			if(periodLocation >= 0){
				String type = fName.substring(periodLocation, fName.length());
				if(type.equals(MODEL_NAME_PROJECT_TYPE)){
					return fName;  
				}
			} else {
				logger.info("GetProjectProjectName: userName: "+userName+" projectName: "+projectName+"; projects dont have .extensions" );
			}
		}
    	return "";
	}
	
	public void UploadAProjectFile_whole(String userName, String projectName, String fileName, InputStream in, ReturnedObject ro) throws IOException{
		
    	BufferedOutputStream out = null;
    	//upload the file
        try {
        	
        	CreateFolderIfNotExist(makeFolderPath(userName,projectName), ro);
        	if(ro.isSuccess() == false){
        		return;
        	}
        	
        	String location = makeFilePath(userName, projectName, fileName);
        	
            
            File file = new File(location);
            if(file.exists()){
            	file.delete();
            }
            
            out = new BufferedOutputStream(new FileOutputStream(file));
            IOUtils.copy(in,out);
            
            ro.setSuccess(true);
            ro.setMessage(EMPTY_STRING);
        } catch (Exception e) {
            ro.setSuccess(false);
            ro.setMessage(e.getMessage());         
        } finally {
        	if(out != null){
        		out.close();
        	}
        }
	}

	public void DeleteFile(String userName, String projectName, String fileName, ReturnedObject ro) {

		String path = this.makeFilePath(userName, projectName, fileName);
		File deleteFile = new File(path);
		if(deleteFile.exists()){
			deleteFile.delete();
			ro.setSuccess(true);
			ro.setMessage("");
		} else {
			ro.setSuccess(false);
			ro.setMessage("DeleteFile, fileName not found");		
		}
		return;	
	}
	
	
	
	
	
	
	
	////--------------------util---------------------
	private String makeFilePath(String userName, String projectName, String fileName){
    	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
    	path.append(userName).append("/").append(projectName).append("/").append(fileName);
		return path.toString();
	}
	private String makeFolderPath(String userName, String projectName){
    	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
    	path.append(userName).append("/").append(projectName);
		return path.toString();
	}
	
	File CreateFolderIfNotExist(String path, ReturnedObject ro){
    	File folder = new File(path);
    	logger.debug("CreateFolderIfNotExist path: "+ path);
    	if (!folder.exists()) {
    	    try{
    	    	folder.mkdirs();
    	    } 
    	    catch(SecurityException se){
        		ro.setSuccess(false);
        		ro.setMessage("couldnt make the folder");
        		return null;
    	    }        
    	}
    	
    	return folder;
	}

}



















