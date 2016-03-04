package com.ModelViewer.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public interface FileDAO {

	public void UploadAProjectFile_streaming(String userName, String projectName, String fileName, InputStream in) throws IOException;
	InputStream GetFileAProjectFile(String userName,String projectName,String fileName);
	Integer GetProjectFileCount(String userName, String projectName);
	Integer GetProjectImageCount(String userName, String projectName);
	List<String> GetProjectImageNames(String userName, String projectName);
	String GetProjectProjectName(String userName, String projectName);
	List<String> GetFileListForAProject(String userName, String projectName);
	HashMap<Object, Object> GetFileListForAProjectAndMetaData(String userName, String projectName);
	public void DeleteFile(String userName, String projectName, String fileName);
}
