package com.ModelViewer.DAOImpl;

import java.sql.Timestamp;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.UserModel;

public class UserDAOImpl implements UserDAO{

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public void createUser(UserModel userModel) throws Exception {
		this.sessionFactory.getCurrentSession().persist(userModel);
	}

	public UserModel readUser(UserModel userModel) throws Exception {
		return this.sessionFactory.getCurrentSession().get(UserModel.class,userModel.getUuid());
	}

	public void updateUser(UserModel userModel) throws Exception {
		this.sessionFactory.getCurrentSession().update(userModel);
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
