package com.ModelViewer.LoginApp.Service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@ResponseBody
@RequestMapping("/MemberAndProjectMemberHybridService")
public class MemberAndProjectMemberHybridService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	//private ObjectMapper mapper = new ObjectMapper();
	
	@Inject
	MemberDAO memberDAO;
	
	@Inject
	ProjectMemberDAO projectMemberDAO;
	
	@RequestMapping(value = "/DeleteMember", method = RequestMethod.GET)
	public String DeleteMember(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "member", required = true) String member)
			throws JsonProcessingException {
		
		logger.info("DeleteMember request recieved: "+userName);
		
		memberDAO.DeleteAMember(userName, member);
		projectMemberDAO.DeleteAMemberFromAllProject(userName, member);
		
		return "sucess";
	}
	
}

