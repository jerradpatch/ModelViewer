package com.ModelViewer.DAO;

import java.io.InputStream;

public interface FileDAO {
	
	public void createFile(String userName, String projectName, String fileName,InputStream fileIn) throws Exception;
	public InputStream readFile(String userName, String projectName, String fileName) throws Exception;
	public void deleteFile(String userName, String projectName, String fileName) throws Exception;
}
