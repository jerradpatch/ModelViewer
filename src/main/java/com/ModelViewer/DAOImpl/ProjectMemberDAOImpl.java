package com.ModelViewer.DAOImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectMemberModel;
import com.ModelViewer.Model.ProjectMemberModelId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ProjectMemberDAOImpl implements ProjectMemberDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMemberDAOImpl.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public List<ProjectMemberModel> GetListOfProjectsAndMembers(String UserName, ReturnedObject ro) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
        userQuery.add(Restrictions.eq("userName",UserName));
        @SuppressWarnings("unchecked")
		List<ProjectMemberModel> projectMembersModelList =  userQuery.list();
        return projectMembersModelList;
	}
	
	public HashMap<String,List<String>> GetHashMapOfProjectAndMember(String userName, ReturnedObject ro){
		List<ProjectMemberModel> list = GetListOfProjectsAndMembers(userName,ro);
		
//		HashMap<String,List<String>> projectMembers = new HashMap<String,List<String>>();
//		for(ProjectMemberModel item : list){
//			if(projectMembers.containsKey(item.getProjectName())){
//				List<String> members = projectMembers.get(item.getProjectName());
//				members.add(item.getMember());
//				projectMembers.put(item.getProjectName(), members);
//			} else {
//				List<String> members = new ArrayList<String>();
//				if(!item.getMember().equals("")){
//					members.add(item.getMember());
//				}
//				projectMembers.put(item.getProjectName(), members);
//			}
//		}
//		return projectMembers;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> GetProjectsMemberIsAPartOf(String userName, String member, ReturnedObject ro) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("member",member));
        userQuery.setProjection(Projections.property("projectName"));
        List<String> projects = userQuery.list();
        return projects;
	}
	
	/**
	 * return members in a project
	 * @param userName
	 * @param member
	 * @param ro
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> GetListOfUserProject(String userName, String projectName, ReturnedObject ro) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("projectName",projectName));
        userQuery.setProjection(Projections.property("member"));
        List<String> projects = userQuery.list();
        return projects;
	}
	
	public ProjectMemberModel GetProjectMemberModel(String userName, String projectName, String member, ReturnedObject ro) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
        userQuery.add(Restrictions.eq("userName",userName));
        userQuery.add(Restrictions.eq("projectName",projectName));
        userQuery.add(Restrictions.eq("member",member));

        ProjectMemberModel projectMembersModelList =  (ProjectMemberModel) userQuery.uniqueResult();
        return projectMembersModelList;
	}
	public ProjectMemberModel GetProject(String userName, String projectName, ReturnedObject ro) {
        Session session = this.sessionFactory.getCurrentSession();
        
        ProjectMemberModelId pmmId = new ProjectMemberModelId("123456", "123456", "555555");
//        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("projectName",projectName));
        ProjectMemberModel pmm = (ProjectMemberModel) session.load(ProjectMemberModel.class,pmmId);

		logger.debug(pmm.getProjectName() + pmm.getUserName());
		try {
			logger.debug(mapper.writeValueAsString(pmm.getMembers()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        userQuery.setMaxResults(1);
//        
//        ProjectMemberModel projectMembersModelList =  (ProjectMemberModel) userQuery.uniqueResult();
        return null;
	}
	
	public void CreateANewProject(String userName, String projectName, ReturnedObject ro){
		
		ProjectMemberModel PMM = GetProject(userName,projectName,ro);
		if(PMM != null){
			ro.setSuccess(false);
			ro.setMessage("\"Project already exists\"");
			return;
		}
		if(!ro.isSuccess()){
			return;
		}	
		Session session = this.sessionFactory.getCurrentSession();				
		PMM = new ProjectMemberModel();
		PMM.setProjectName(projectName);
		PMM.setUserName(userName);		
		session.save(PMM);
	}
	public void CreateAMember(String userName, String projectName, String member, ReturnedObject ro){
		
		ProjectMemberModel PMM = GetProjectMemberModel(userName,projectName,member,ro);
		if(PMM != null){
			ro.setSuccess(false);
			ro.setMessage("\"Given member already exists in this project\"");
			return;
		}
		if(!ro.isSuccess()){
			return;
		}		
		Session session = this.sessionFactory.getCurrentSession();		
		PMM = new ProjectMemberModel();
		PMM.setProjectName(projectName);
		PMM.setUserName(userName);		
		session.save(PMM);		
	}
	
	public void DeleteAProject(String userName, String projectName, ReturnedObject ro){
		Session session=this.sessionFactory.getCurrentSession(); 
		String hql = "delete from ProjectMemberModel pmm where pmm.userName= :userName and pmm.projectName= :projectName"; 
		session.createQuery(hql).setString("userName", new String(userName))
			.setString("projectName", new String(projectName))
			.executeUpdate();
	}
	
	public void DeleteAMemberFromAllProject(String userName, String member, ReturnedObject ro){
		Session session=this.sessionFactory.getCurrentSession(); 
		String hql = " delete from ProjectMemberModel pmm where ( pmm.userName = :userName ) and ( pmm.member = :member ) "; 
		session.createQuery(hql).setString("userName", new String(userName))
			.setString("member", new String(member))
			.executeUpdate();
	}
	public void DeleteAMemberFromAProject(String userName,String projectName,String member, ReturnedObject ro){
		Session session=this.sessionFactory.getCurrentSession(); 
		String hql = " delete from ProjectMemberModel pmm where ( pmm.userName = :userName ) and "
				+ "( pmm.projectName = :projectName ) and ( pmm.member = :member ) "; 
		session.createQuery(hql)
			.setString("userName", new String(userName))
			.setString("projectName", new String(projectName))
			.setString("member", new String(member))
			.executeUpdate();	
	}


}
