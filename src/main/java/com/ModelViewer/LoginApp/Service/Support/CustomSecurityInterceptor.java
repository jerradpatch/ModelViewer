package com.ModelViewer.LoginApp.Service.Support;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.FileMetaModel;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.ProjectMemberModel;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomSecurityInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = Logger.getLogger(CustomSecurityInterceptor.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Inject
	@Qualifier("UserDAO")
	UserDAO userDAO;

	@Inject
	@Qualifier("MemberDAO")
	MemberDAO memberDAO;
	
	/**
	 * (/login) and (/createUser) do no use this intercepter b/c they do not have an uuid at that time
	 */
	@Override 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("made it to the interceptor");

		JsonNode inputNode = mapper.readTree(request.getReader());
		JsonNode inputAuth = inputNode.get("auth");
		
		logger.info(mapper.writeValueAsString(inputNode));
		if(inputAuth == null){
			ReturnedObject.sThrowException("auth not availible");
		}

		JsonNode memberModelN = inputAuth.get("memberModel");
		JsonNode userModelN = inputAuth.get("userModel");
		
		if(userModelN != null){			
			UserModel userModel = mapper.treeToValue(userModelN, UserModel.class);
			String uuid = userModel.getUuid();
			if(uuid != null){
				UserModel readUserModel = (UserModel) getTransaction(UserModel.class, uuid);
				logger.debug(readUserModel);
				if(readUserModel != null){
					logger.debug("password userModel "+userModel.getPassword());
					logger.debug("password readUserModel "+readUserModel.getPassword());
					if(userModel.getPassword().equals(readUserModel.getPassword())){
						JsonNode inputData = inputNode.get("data");
						Object obj = nodeToObject(inputData);
						request.setAttribute("obj", obj);
						logger.debug(mapper.writeValueAsString(obj));
						return true;
					}	
				}
			}
			
		} else if(memberModelN != null){
			MemberModel memberModel = mapper.treeToValue(memberModelN, MemberModel.class);
			String uuid = memberModel.getUuid();
			if(uuid != null){
				MemberModel readMemberModel = (MemberModel) getTransaction(MemberModel.class, uuid);
				logger.debug(readMemberModel);
				if(readMemberModel != null){
					logger.debug("password memberModel "+memberModel.getPassword());
					logger.debug("password readMemberModel "+readMemberModel.getPassword());
					if(memberModel.getPassword().equals(readMemberModel.getPassword())){
						JsonNode inputData = inputNode.get("data");
						Object obj = nodeToObject(inputData);
						request.setAttribute("obj", obj);
						return true;
					}
				}
			}
		} 
			
		HashMap<String,Object> ret = new HashMap<String,Object>();
		ret.put("status_boolean", false);
		ret.put("message_string", "Access Denied");

		response.getOutputStream().print(mapper.writeValueAsString(ret));
		return false;
	
	}
	
	private Object nodeToObject(JsonNode jn) throws ReturnedObject, JsonProcessingException{
		if(jn.get("userModel") != null){
			return mapper.treeToValue(jn.get("userModel"), UserModel.class);
		} else if(jn.get("memberModel") != null) {
			return mapper.treeToValue(jn.get("memberModel"), MemberModel.class);
		} else if(jn.get("projectMemberModel") != null){
			return mapper.treeToValue(jn.get("projectMemberModel"), ProjectMemberModel.class);
		} else if(jn.get("fileMetaModel") != null){
			return mapper.treeToValue(jn.get("fileMetaModel"), FileMetaModel.class);
		} else {
			ReturnedObject.sThrowException("not a valid model type");
					}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Object getTransaction (Class classType, String uuid){
		
	
		Session session = sessionFactory.openSession();
		Object obj = null;
		try {
			obj = session.get(classType, uuid);
		}
		catch (Exception e) {
		   e.printStackTrace(); 
		}finally {
		   session.close();
		}
		return obj;
	}
}





