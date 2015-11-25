package com.ModelViewer.DAO;

import java.io.IOException;
import java.util.List;

import com.ModelViewer.LoginApp.Service.ReturnedObject;

public interface FileDAO {

	void UploadAProjectFile_whole(String userName, String projectName, byte[] fileBytes, ReturnedObject ro) throws IOException;
	int GetProjectFileCount(String userName, String projectName, ReturnedObject ro);
	int GetProjectImageCount(String userName, String projectName, ReturnedObject ro);
	List<String> GetProjectImageNames(String userName, String projectName, ReturnedObject ro);
	String GetProjectProjectName(String userName, String projectName, ReturnedObject ro);
	List<String> GetFileListForAProject(String userName, String projectName, ReturnedObject ro);
}
