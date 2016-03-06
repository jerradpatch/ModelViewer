package com.ModelViewer.DAOImpl;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;

import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.Model.FileMetaModel;
import com.amazonaws.services.s3.AmazonS3;

 

public class FileS3Repository implements FileDAO {

	private ResourceLoader resourceLoader;
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}	
	private String baseBucket;
	public void setBaseBucket(String baseBucket) {
		this.baseBucket = baseBucket;
	}	
	private AmazonS3 amazonS3;
	
	
	public void createFile(String userName, String projectName, String fileName,InputStream fileIn) throws Exception {
		String s3Location = makeS3FileLocation(userName,projectName,fileName);
		Resource resource = this.resourceLoader.getResource(s3Location);
		WritableResource writableResource = (WritableResource) resource;
		OutputStream outputStream = writableResource.getOutputStream();
		IOUtils.copy(fileIn, outputStream);
	}

	public InputStream readFile(String userName, String projectName, String fileName) throws Exception {
		String s3Location = makeS3FileLocation(userName,projectName,fileName);
		Resource resource = this.resourceLoader.getResource(s3Location);
		return resource.getInputStream();
	}

	public void deleteFile(String userName, String projectName, String fileName) throws Exception {
		String s3Location = makeS3FileLocation(userName,projectName,fileName);
		//TODO not sure if this is correct
		this.amazonS3.deleteObject(baseBucket,s3Location);		
	}

	
	private String makeS3FileLocation(String userName, String projectName, String fileName) throws Exception{
		if(baseBucket == null){
			throw new Exception("s3 baseBucketURL = null");
		}
		StringBuilder sb = new StringBuilder(baseBucket);		
		return sb.append("/").append(userName).append("/").append(projectName).append("/").append(fileName).toString();	
	}

}
