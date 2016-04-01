package com.ModelViewer.LoginApp.Service;

import java.io.InputStream;
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
import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.DAO.FileMetaDAO;
import com.ModelViewer.Model.FileMetaModel;


@Controller
@ResponseBody
@RequestMapping("/FileService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class FileService {

	
	@Inject
	@Qualifier("FileMetaDAO")
	FileMetaDAO fileMetaDAO;
	
	@Inject
	@Qualifier("FileDAO")
	FileDAO fileDAO;
	
	@RequestMapping(value = "/createFile", method = RequestMethod.POST)
	public void createFile(@RequestParam(value = "fileMeta", required = true) Object obj) throws Exception{
		
		FileMetaModel fileMetaModel = (FileMetaModel) obj;
		String userName = fileMetaModel.getUserModel().getUserName();
		String projectName = fileMetaModel.getProjectMemberModel().getProjectName();
		String fileName = fileMetaModel.getFileName();
		fileDAO.createFile(userName, projectName, fileName, fileMetaModel.getInputStream());
		fileMetaDAO.createFileMeta(fileMetaModel);	
	}
	
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public void deleteFile(@RequestParam(value = "file", required = true) Object obj) throws Exception{
		FileMetaModel fileMetaModel = (FileMetaModel) obj;
		fileMetaDAO.deleteFileMeta(fileMetaModel);
	}
	
	@RequestMapping(value = "/readFile", method = RequestMethod.POST)
	public Object readFile(@RequestParam(value = "file", required = true) Object obj) throws Exception{
		FileMetaModel fileMetaModel = (FileMetaModel) obj;
		String userName = fileMetaModel.getUserModel().getUserName();
		String projectName = fileMetaModel.getProjectMemberModel().getProjectName();
		String fileName = fileMetaModel.getFileName();
		InputStream inputStream = fileDAO.readFile(userName, projectName, fileName);
		FileMetaModel fileMetaModelRet = fileMetaDAO.readFileMeta(fileMetaModel);
		fileMetaModelRet.setInputStream(inputStream);
		return fileMetaModelRet;
	}
	
	
	
	
}
