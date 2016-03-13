package com.ModelViewer.LoginApp.Service;

import java.util.Set;

import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ModelViewer.DAO.FileMetaDAO;
import com.ModelViewer.Model.FileMetaModel;

@Controller
@ResponseBody
@RequestMapping("/FileMetaService")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class FileMetaService {


	@Inject
	@Qualifier("FileMetaDAO")
	FileMetaDAO fileMetaDAO;
	
	@RequestMapping(value = "/readFileMetaList", method = RequestMethod.POST)
	public Object readFileMetaList(@RequestBody(required = true) FileMetaModel fileMetaModel) throws Exception {
		return fileMetaDAO.readFileMetaList(fileMetaModel);
	}
}

