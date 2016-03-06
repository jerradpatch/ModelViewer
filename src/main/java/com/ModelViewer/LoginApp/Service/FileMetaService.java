package com.ModelViewer.LoginApp.Service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ModelViewer.DAO.FileDAO;
import com.ModelViewer.DAO.FileMetaDAO;
import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.FileMetaModel;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ResponseBody
@RequestMapping("/FileMetaService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class FileMetaService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileMetaService.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	@Qualifier("ProjectMemberDAO")
	ProjectMemberDAO projectMemberDAO;
	
	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;
	
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;

	@Inject
	@Qualifier("FileMetaDAO")
	FileMetaDAO fileMetaDAO;
	
	@RequestMapping(value = "/readFileMetaList", method = RequestMethod.POST)
	public Set<FileMetaModel> readFileMetaList(@RequestBody(required = true) FileMetaModel fileMetaModel) throws Exception {
		return fileMetaDAO.readFileMetaList(fileMetaModel);
	}


			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}
