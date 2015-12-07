package com.ModelViewer.DAOImpl;

import java.sql.Timestamp;

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
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public UserModel GetUserByUserName(String userName, ReturnedObject ro) {
		logger.debug("GetUserByUserName: username "+userName);
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(UserModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.setMaxResults(1);
        UserModel userModel = (UserModel) userQuery.uniqueResult();
        session.close();
        return userModel;
	}
	
	public String GetUserPasswordByUserName(String userName, ReturnedObject ro){
		Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(UserModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.setProjection(Projections.property("password"));
        String password = (String) userQuery.uniqueResult();
        session.close();
        
        return password;
	}

	public void CreateUserByModel(UserModel user, ReturnedObject ro) {
		
		UserModel user2 = GetUserByUserName(user.getUserName(), ro);
		if(user2 != null){
			ro.setSuccess(false);
			ro.setMessage("\"User already exists\"");
			return;
		}
		if(!ro.isSuccess()){
			return;
		}

		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
	}
	public void CreateUserByValues(String userName, String password, String email, ReturnedObject ro) {
		
		UserModel um = GetUserByUserName(userName, ro);
		if(um != null){
			ro.setSuccess(false);
			ro.setMessage("\"User already exists\"");
			return;
		}
		if(!ro.isSuccess()){
			return;
		}
		
		um = new UserModel();
		um.setUserName(userName);
		um.setPassword(password);
		um.setEmail(email);
		
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(um);
        tx.commit();
        session.close();
        
        return;
	}
	public void UpdateUserLoginToCurrentTime(String userName, ReturnedObject ro) {
		
		Session session = this.sessionFactory.openSession();
        UserModel um = GetUserByUserName(userName, ro);
        Transaction tx = session.beginTransaction();
        session.update(um);
        um.setDateLastLoggedIn(new Timestamp(System.currentTimeMillis()));
        tx.commit();
        session.close();
             
        return;
	}
//	public UserModel UpdateByUserName(String UserName, ReturnedObject ro) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public void DeleteByModel(UserModel UserName, ReturnedObject ro) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(UserName);
        tx.commit();
        session.close();
        return;
	}
	
	/* if passwords for use = return true, else false */
//	public boolean ComparePasswords(String userName, String password){
//		if(userName == null || userName.isEmpty() || password == null || password.isEmpty()){
//			return false;
//		}
//		
//		String passwordDB = GetUserPasswordByUserName(userName);
//		if(password.equals(passwordDB)){
//			return true;
//		}
//		return false;
//	}

}
