package com.ModelViewer.DAOImpl;


import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ModelViewer.DAO.MemberDAO;

import com.ModelViewer.Model.MemberModel;

public class MemberDAOImpl implements MemberDAO{

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	
	public void createMember(MemberModel memberModel){
		this.sessionFactory.getCurrentSession().persist(memberModel);
	}
	public MemberModel readMember(MemberModel memberModel) throws Exception {
		return this.sessionFactory.getCurrentSession().get(MemberModel.class,memberModel.getUuid());
	}
	public MemberModel readMember_userNamePassword(MemberModel memberModel) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(MemberModel.class);
		criteria.setMaxResults(1);
		criteria.add(Restrictions.eq("memberName", memberModel.getmemberName()));
		criteria.add(Restrictions.eq("password", memberModel.getPassword()));
		return (MemberModel) criteria.uniqueResult();
	}
	public Set<MemberModel> readMemberList(MemberModel memberModel) throws Exception {
		return readMember(memberModel).getUserModel().getMembers();
	}
	public void updateMember(MemberModel memberModel) throws Exception {
		this.sessionFactory.getCurrentSession().update(memberModel);
	}
	public void deleteMember(MemberModel memberModel) throws Exception {
		this.sessionFactory.getCurrentSession().delete(memberModel);
	}
//	public List<String> GetListOfMember(String UserName, ReturnedObject ro) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria memberQuery = session.createCriteria(MemberModel.class);
//        memberQuery.add(Restrictions.eq("userName",UserName));
//        @SuppressWarnings("unchecked")
//		List<MemberModel> membersModelList =  memberQuery.list();
//        //session.close();
//        
//        List<String> members = new ArrayList<String>();
//        for(MemberModel memberModel : membersModelList){
//        	members.add(memberModel.getMember());
//        }
//        return members;
//	}
//	
//	public MemberModel GetMemberData(String userName, String member, ReturnedObject ro){
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(MemberModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("member",member));
//        userQuery.setMaxResults(1);
//        MemberModel memberModel = (MemberModel) userQuery.uniqueResult();
//        return memberModel;
//	}
//	
//	public String GetMemberPassword(String userName, String member, ReturnedObject ro){
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(MemberModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("member",member));
//        userQuery.setProjection(Projections.property("password"));
//        userQuery.setMaxResults(1);
//        String password = (String) userQuery.uniqueResult();
//        return password;
//	}
//	
//	public void CreateAMember(UserModel um, String member, String password, ReturnedObject ro) {
//		
//		//check if the member exists or not
//		MemberModel memberModel = GetMemberData(um.getUserName(), member, ro);
//		if(memberModel != null){
//			ro.setSuccess(false);
//			ro.setMessage("\"Member already exists\"");
//			return;
//		}
//		if(!ro.isSuccess()){
//			return;
//		}
//		memberModel = new MemberModel();
//		
//		Session session = this.sessionFactory.getCurrentSession();
//        memberModel.setUserModel(um);
//        memberModel.setUserName(um.getUserName());
//        memberModel.setMember(member);
//        memberModel.setPassword(password);
//        session.save(memberModel);
//	}
//	
//	public void CreateUpdateAMember(UserModel um, String memberNameOld,String memberPasswordOld,String member,String password, ReturnedObject ro) {
//		if(GetMemberData(um.getUserName(),memberNameOld,ro) != null){ //delete old and create new
//			DeleteAMember(um.getUserName(),memberNameOld,ro);
//			logger.debug("user: "+um.getUserName()+" member deleted: "+memberNameOld);
//		}
//		CreateAMember(um, member, password, ro);
//		logger.debug("user: "+um.getUserName()+" member created: "+member);
//	}
//	
//	public void DeleteAMember(String userName, String member, ReturnedObject ro){
//		Session session=this.sessionFactory.getCurrentSession();
//		String hql = "delete from MemberModel mm where mm.userName= :userName and mm.member= :member"; 
//		session.createQuery(hql).setString("userName", new String(userName))
//			.setString("member", new String(member))
//			.executeUpdate();
//	}
//	



}
