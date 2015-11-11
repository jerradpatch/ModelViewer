package com.ModelViewer.DAOImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ModelViewer.DAO.UserDAO;
import com.ModelViewer.Model.UserModel;

public class UserDAOImpl implements UserDAO{

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public UserModel GetUserByUserName(String UserName) {
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(UserModel.class);
        userQuery.add(Restrictions.eq("userName",UserName));
        userQuery.setMaxResults(1);
        UserModel userModel = (UserModel) userQuery.uniqueResult();
        session.close();
        return userModel;
	}

	public void CreateUserByModel(UserModel user) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
	}
	public void CreateUserByValues(String userName, String password, String email) {
		
		UserModel um = new UserModel();
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

	public UserModel UpdateByUserName(String UserName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void DeleteByModel(UserModel UserName) {
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(UserName);
        tx.commit();
        session.close();
        return;
	}

}
