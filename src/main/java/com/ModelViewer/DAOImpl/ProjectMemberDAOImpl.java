package com.ModelViewer.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectMemberModel;



public class ProjectMemberDAOImpl implements ProjectMemberDAO{
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public List<ProjectMemberModel> GetListOfProjectsAndMembers(String UserName, ReturnedObject ro) {
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
        userQuery.add(Restrictions.eq("userName",UserName));
        @SuppressWarnings("unchecked")
		List<ProjectMemberModel> projectMembersModelList =  userQuery.list();
        session.close();
        return projectMembersModelList;
	}
	
	public HashMap<String,List<String>> GetHashMapOfProjectAndMember(String userName, ReturnedObject ro){
		List<ProjectMemberModel> list = GetListOfProjectsAndMembers(userName,ro);
		
		HashMap<String,List<String>> projectMembers = new HashMap<String,List<String>>();
		for(ProjectMemberModel item : list){
			if(projectMembers.containsKey(item.getProjectName())){
				List<String> members = projectMembers.get(item.getProjectName());
				members.add(item.getMember());
				projectMembers.put(item.getProjectName(), members);
			} else {
				List<String> members = new ArrayList<String>();
				if(!item.getMember().equals("")){
					members.add(item.getMember());
				}
				projectMembers.put(item.getProjectName(), members);
			}
		}
		return projectMembers;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> GetProjectsMemberIsAPartOf(String userName, String member, ReturnedObject ro) {
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("member",member));
        userQuery.setProjection(Projections.property("projectName"));
        List<String> projects = userQuery.list();
        session.close();
        
        return projects;
	}
	
	public ProjectMemberModel GetProjectMemberModel(String userName, String projectName, String member, ReturnedObject ro) {
        Session session = this.sessionFactory.openSession();
        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("projectName",projectName));
        userQuery.add(Restrictions.eq("member",member));

        ProjectMemberModel projectMembersModelList =  (ProjectMemberModel) userQuery.uniqueResult();
        session.close();
        return projectMembersModelList;
	}
	
	public void CreateANewProject(String userName, String projectName, ReturnedObject ro){
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
				
		ProjectMemberModel PMM = new ProjectMemberModel();
		PMM.setMember("");
		PMM.setProjectName(projectName);
		PMM.setUserName(userName);
		
		session.save(PMM);
		session.getTransaction().commit();
		session.close();		
	}
	public void CreateAMember(String userName, String projectName, String member, ReturnedObject ro){
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
				
		ProjectMemberModel PMM = new ProjectMemberModel();
		PMM.setMember(member);
		PMM.setProjectName(projectName);
		PMM.setUserName(userName);
		
		session.save(PMM);
		session.getTransaction().commit();
		session.close();		
	}
	
	public void DeleteAProject(String userName, String projectName, ReturnedObject ro){
		Session session=this.sessionFactory.openSession(); 
		String hql = "delete from ProjectMemberModel pmm where pmm.userName= :userName and pmm.projectName= :projectName"; 
		session.createQuery(hql).setString("userName", new String(userName))
			.setString("projectName", new String(projectName))
			.executeUpdate();
		session.close();
	}
	
	public void DeleteAMemberFromAllProject(String userName, String member, ReturnedObject ro){
		Session session=this.sessionFactory.openSession(); 
		String hql = " delete from ProjectMemberModel pmm where ( pmm.userName = :userName ) and ( pmm.member = :member ) "; 
		session.createQuery(hql).setString("userName", new String(userName))
			.setString("member", new String(member))
			.executeUpdate();
		session.close();
	}
	public void DeleteAMemberFromAProject(String userName,String projectName,String member, ReturnedObject ro){
		Session session=this.sessionFactory.openSession(); 
		String hql = " delete from ProjectMemberModel pmm where ( pmm.userName = :userName ) and "
				+ "( pmm.projectName = :projectName ) and ( pmm.member = :member ) "; 
		session.createQuery(hql)
			.setString("userName", new String(userName))
			.setString("projectName", new String(projectName))
			.setString("member", new String(member))
			.executeUpdate();
		session.close();		
	}


}
