package com.ModelViewer.DAOImpl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.MemberModel;

public class MemberDAOImpl implements MemberDAO{

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public List<String> GetListOfMember(String UserName, ReturnedObject ro) {
        Session session = this.sessionFactory.openSession();
        Criteria memberQuery = session.createCriteria(MemberModel.class);
        memberQuery.add(Restrictions.eq("userName",UserName));
        @SuppressWarnings("unchecked")
		List<MemberModel> membersModelList =  memberQuery.list();
        session.close();
        
        List<String> members = new ArrayList<String>();
        for(MemberModel memberModel : membersModelList){
        	members.add(memberModel.getMember());
        }
        return members;
	}
	
	public MemberModel GetMemberData(String userName, String member, ReturnedObject ro){
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(MemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("member",member));
        userQuery.setMaxResults(1);
        MemberModel memberModel = (MemberModel) userQuery.uniqueResult();
        session.close();
        return memberModel;
	}
	
	public String GetMemberPassword(String userName, String member, ReturnedObject ro){
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(MemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("member",member));
        userQuery.setProjection(Projections.property("password"));
        userQuery.setMaxResults(1);
        String password = (String) userQuery.uniqueResult();
        session.close();
        return password;
	}
	
	public void CreateAMember(String userName, String member, String password, ReturnedObject ro) {
		
		//check if the member exists or not
		MemberModel memberModel = GetMemberData(userName, member, ro);
		if(memberModel != null){
			ro.setSuccess(false);
			ro.setMessage("\"Member already exists\"");
			return;
		}
		if(!ro.isSuccess()){
			return;
		}
		
		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        memberModel = new MemberModel();
        memberModel.setUserName(userName);
        memberModel.setMember(member);
        memberModel.setPassword(password);
        session.persist(memberModel);
        tx.commit();
        session.close();

	}
	
	public void CreateUpdateAMember(String userName, String member, String password, ReturnedObject ro) {
		if(GetMemberData(userName,member,ro) != null){
			DeleteAMember(userName,member,ro);			
		}
		CreateAMember(userName, member, password, ro);
	}
	
	public void DeleteAMember(String userName, String member, ReturnedObject ro){
		Session session=this.sessionFactory.openSession(); 
		String hql = "delete from MemberModel mm where mm.userName= :userName and mm.member= :member"; 
		session.createQuery(hql).setString("userName", new String(userName))
			.setString("member", new String(member))
			.executeUpdate();
		session.close();
	}
	
	/* if passwords for use = return true, else false */
//	public boolean ComparePasswords(String userName, String member, String password){
//		
//		String passwordDB = GetMemberPassword(userName,member);
//		if(password.equals(passwordDB)){
//			return true;
//		}
//		return false;
//	}
}
