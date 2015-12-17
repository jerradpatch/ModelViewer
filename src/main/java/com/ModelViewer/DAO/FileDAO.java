package com.ModelViewer.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.ModelViewer.LoginApp.Service.ReturnedObject;

public interface FileDAO {

	public void UploadAProjectFile_whole(String userName, String projectName, String fileName, InputStream in, ReturnedObject ro) throws IOException;
	Integer GetProjectFileCount(String userName, String projectName, ReturnedObject ro);
	Integer GetProjectImageCount(String userName, String projectName, ReturnedObject ro);
	List<String> GetProjectImageNames(String userName, String projectName, ReturnedObject ro);
	String GetProjectProjectName(String userName, String projectName, ReturnedObject ro);
	List<String> GetFileListForAProject(String userName, String projectName, ReturnedObject ro);
	List<HashMap<String,String>> GetFileListForAProjectAndMetaData(String userName, String projectName, ReturnedObject ro);
	public void DeleteFile(String userName, String projectName, String fileName, ReturnedObject ro);
}
