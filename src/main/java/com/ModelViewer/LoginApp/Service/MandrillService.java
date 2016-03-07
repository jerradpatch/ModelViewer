package com.ModelViewer.LoginApp.Service;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microtripit.mandrillapp.lutung.MandrillApi;

@Controller
@ResponseBody
@RequestMapping("/Mandrill")
@EnableTransactionManagement
@Transactional(readOnly = false, rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class MandrillService {


	@Inject
	@Qualifier("Mandrill")
	MandrillApi mandrillApi;
	
	@Inject
	@Qualifier("ProjectMemberDAO")
	ProjectMemberDAO projectMemberDAO;
	
	@RequestMapping(value = "/ProjectMemberService/sendNotification", method = RequestMethod.GET)
	public @ResponseBody String sendNotification(
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "userP", required = true) String password
			) throws IOException, ReturnedObject{
		
			ReturnedObject ro = new ReturnedObject();
		//	ProjectMemberModel projectMember = projectMemberDAO.GetProject(userName, projectName, ro);
			
	//		logger.debug(mapper.writeValueAsString(projectMember));
			
	//		logger.debug(mapper.writeValueAsString(projectMember.getMembers()));
			
			return null;	
				
//				MandrillMessage message = new MandrillMessage();
//			message.setSubject("Hello World!");
//			message.setHtml("<h1>Hi pal!</h1><br />Really, I'm just saying hi!");
//			message.setAutoText(true);
//			message.setFromEmail("kitty@yourdomain.com");
//			message.setFromName("Kitty Katz");
//			// add recipients
//			ArrayList<Recipient> recipients = new ArrayList<Recipient>();
//			Recipient recipient = new Recipient();
//			recipient.setEmail("claireannette@someotherdomain.com");
//			recipient.setName("Claire Annette");
//			recipients.add(recipient);
//			recipient = new Recipient();
//			recipient.setEmail("terrybull@yetanotherdomain.com");
//			recipients.add(recipient);
//			message.setTo(recipients);
//			message.setPreserveRecipients(true);
//			ArrayList<String> tags = new ArrayList<String>();
//			tags.add("test");
//			tags.add("helloworld");
//			message.setTags(tags);
	}
}






















