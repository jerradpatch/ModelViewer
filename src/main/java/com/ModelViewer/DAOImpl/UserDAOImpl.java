package com.ModelViewer.DAOImpl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDAOImpl implements UserDAO{
	
	private ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public UserModel createUser(UserModel userModel) throws Exception {
		this.sessionFactory.getCurrentSession().persist(userModel);
		return userModel;
	}

	public UserModel readUser(UserModel userModel) throws Exception {
		logger.debug(this.sessionFactory.getCurrentSession());
		logger.debug("userModel "+userModel.getUuid());
		UserModel userReturned = this.sessionFactory.getCurrentSession().get(UserModel.class,userModel.getUuid());
		logger.debug("userReturned "+mapper.writeValueAsString(userReturned));
		return userReturned;
	}

	public UserModel readUser_userNamePassword(UserModel userModel) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(UserModel.class);
		criteria.setMaxResults(1);
		criteria.add(Restrictions.eq("userName", userModel.getUserName()));
		criteria.add(Restrictions.eq("password", userModel.getPassword()));
		return (UserModel) criteria.uniqueResult();
	}
	
	public void updateUser(UserModel userModel) throws Exception {
		this.sessionFactory.getCurrentSession().update(userModel);
	}

	public Set<MemberModel> readMemberList(UserModel userModel) throws Exception {
		UserModel readUser = readUser(userModel);
		logger.debug(mapper.writeValueAsString(readUser));
		return readUser.getMemberModels();
	}

//	public void CreateUserByModel(UserModel user, ReturnedObject ro) {
//		
//		UserModel user2 = GetUserByUserName(user.getUserName(), ro);
//		if(user2 != null){
//			ro.setSuccess(false);
//			ro.setMessage("\"User already exists\"");
//			return;
//		}
//		if(!ro.isSuccess()){
//			return;
//		}		
//		this.sessionFactory.getCurrentSession().persist(user);
//	}
//	
//	public UserModel GetUserByUserName(String userName, ReturnedObject ro) {
//		logger.debug("GetUserByUserName: username "+userName);
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(UserModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.setMaxResults(1);
//        UserModel userModel = (UserModel) userQuery.uniqueResult();
//        return userModel;
//	}
//	
//	public String GetUserPasswordByUserName(String userName, ReturnedObject ro){
//		Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(UserModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.setProjection(Projections.property("password"));
//        String password = (String) userQuery.uniqueResult();
//        return password;
//	}

//	public void CreateUserByValues(String userName, String password, String email, ReturnedObject ro) {
//		
//		UserModel um = GetUserByUserName(userName, ro);
//		if(um != null){
//			ro.setSuccess(false);
//			ro.setMessage("\"User already exists\"");
//			return;
//		}
//		if(!ro.isSuccess()){
//			return;
//		}		
//		um = new UserModel();
//		um.setUserName(userName);
//		um.setPassword(password);
//		um.setEmail(email);		
//		this.sessionFactory.getCurrentSession().persist(um);
//	}
//	public void UpdateUserLoginToCurrentTime(String userName, ReturnedObject ro) {		
//		Session session = this.sessionFactory.getCurrentSession();
//        UserModel um = GetUserByUserName(userName, ro);
//        um.setDateLastLoggedIn(new Timestamp(System.currentTimeMillis()));
//        session.update(um);
//	}
//
//	public void DeleteByModel(UserModel UserName, ReturnedObject ro) {
//		this.sessionFactory.getCurrentSession().delete(UserName);
//	}

}
