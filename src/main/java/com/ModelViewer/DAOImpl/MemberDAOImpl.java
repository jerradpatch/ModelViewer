package com.ModelViewer.DAOImpl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ModelViewer.DAO.MemberDAO;
import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.UserModel;

public class MemberDAOImpl implements MemberDAO{

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public List<String> GetListOfMember(String UserName) {
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
	
	public MemberModel GetMemberData(String userName, String member){
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(MemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("member",member));
        userQuery.setMaxResults(1);
        MemberModel memberModel = (MemberModel) userQuery.uniqueResult();
        session.close();
        return memberModel;
	}
	
	public void CreateAMember(String userName, String member, String password) {

		Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        MemberModel memberModel = new MemberModel();
        memberModel.setUserName(userName);
        memberModel.setMember(member);
        memberModel.setPassword(password);
        session.persist(memberModel);
        tx.commit();
        session.close();

	}
	
	public void CreateUpdateAMember(String userName, String member, String password) {
		if(GetMemberData(userName,member) != null){
			DeleteAMember(userName,member);			
		}
		CreateAMember(userName, member, password);
	}
	
	public void DeleteAMember(String userName, String member){
		Session session=this.sessionFactory.openSession(); 
		String hql = "delete from MemberModel mm where mm.userName= :userName and mm.member= :member"; 
		session.createQuery(hql).setString("userName", new String(userName))
			.setString("member", new String(member))
			.executeUpdate();
		session.close();
	}
}
