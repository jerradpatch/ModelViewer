package com.ModelViewer.DAOImpl;


import java.util.Set;

import org.hibernate.SessionFactory;
import com.ModelViewer.DAO.ProjectMemberDAO;
import com.ModelViewer.Model.ProjectMemberModel;



public class ProjectMemberDAOImpl implements ProjectMemberDAO{

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	public void createProject(ProjectMemberModel projectMemberModel) throws Exception {
		this.sessionFactory.getCurrentSession().persist(projectMemberModel);
	}
	public void updateProject(ProjectMemberModel projectMemberModel) throws Exception {
		this.sessionFactory.getCurrentSession().update(projectMemberModel);
	}
	public void deleteProject(ProjectMemberModel projectMemberModel) throws Exception {
		this.sessionFactory.getCurrentSession().delete(projectMemberModel);
	}
	public Set<ProjectMemberModel> readMemberProjects(ProjectMemberModel projectMemberModel) throws Exception {
		ProjectMemberModel pmm = this.sessionFactory.getCurrentSession().get(ProjectMemberModel.class, projectMemberModel.getUuid());
		return pmm.getUserModel().getProjectMemberModels();
	}
	
	
//	
//	public List<ProjectMemberModel> GetListOfProjectsAndMembers(String UserName, ReturnedObject ro) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
//        userQuery.add(Restrictions.eq("userName",UserName));
//        @SuppressWarnings("unchecked")
//		List<ProjectMemberModel> projectMembersModelList =  userQuery.list();
//        return projectMembersModelList;
//	}
//	
//	public HashMap<String, Set<MemberModel>> GetHashMapOfProjectAndMember(String userName, ReturnedObject ro){
//		List<ProjectMemberModel> list = GetListOfProjectsAndMembers(userName,ro);
//		HashMap<String, Set<MemberModel>> projectMembers = new HashMap<String,Set<MemberModel>>();
//		
//		for(ProjectMemberModel item : list){	
//			projectMembers.put(item.getProjectName(), item.getMembers());
//		}
//		return projectMembers;
//		
////		HashMap<String,List<String>> projectMembers = new HashMap<String,List<String>>();
////		for(ProjectMemberModel item : list){
////			if(projectMembers.containsKey(item.getProjectName())){
////				List<String> members = projectMembers.get(item.getProjectName());
////				members.add(item.getMember());
////				projectMembers.put(item.getProjectName(), members);
////			} else {
////				List<String> members = new ArrayList<String>();
////				if(!item.getMember().equals("")){
////					members.add(item.getMember());
////				}
////				projectMembers.put(item.getProjectName(), members);
////			}
////		}
////		return projectMembers;
//
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<String> GetProjectsMemberIsAPartOf(String userName, String member, ReturnedObject ro) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("member",member));
//        userQuery.setProjection(Projections.property("projectName"));
//        List<String> projects = userQuery.list();
//        return projects;
//	}
//	
//	/**
//	 * return members in a project
//	 * @param userName
//	 * @param member
//	 * @param ro
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public List<String> GetListOfUserProject(String userName, String projectName, ReturnedObject ro) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("projectName",projectName));
//        userQuery.setProjection(Projections.property("member"));
//        List<String> projects = userQuery.list();
//        return projects;
//	}
//	
//	public ProjectMemberModel GetProjectMemberModel(String userName, String projectName, String member, ReturnedObject ro) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("projectName",projectName));
//        userQuery.add(Restrictions.eq("member",member));
//
//        ProjectMemberModel projectMembersModelList =  (ProjectMemberModel) userQuery.uniqueResult();
//        return projectMembersModelList;
//	}
//	public ProjectMemberModel GetProject(String userName, String projectName, ReturnedObject ro) {
//        Session session = this.sessionFactory.getCurrentSession();
//        
//        Criteria userQuery = session.createCriteria(ProjectMemberModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("projectName",projectName));
//
//        userQuery.setMaxResults(1);
//        
//        ProjectMemberModel projectMembersModelList =  (ProjectMemberModel) userQuery.uniqueResult();
//        return projectMembersModelList;
//	}
//	
//	public void CreateANewProject(String userName, String projectName, ReturnedObject ro) throws ReturnedObject{
//		
//		ProjectMemberModel PMM = GetProject(userName,projectName,ro);
//		if(PMM != null){
//			ro.throwException(false, "\"Project already exists\"");
//			return;
//		}
//		if(!ro.isSuccess()){
//			ro.throwException();
//		}	
//		Session session = this.sessionFactory.getCurrentSession();				
//		PMM = new ProjectMemberModel();
//		PMM.setProjectName(projectName);
//		PMM.setUserName(userName);		
//		session.save(PMM);
//	}
//	public void CreateAMember(String userName, String projectName, String member, ReturnedObject ro){
//		
//		ProjectMemberModel PMM = GetProjectMemberModel(userName,projectName,member,ro);
//		if(PMM != null){
//			ro.setSuccess(false);
//			ro.setMessage("\"Given member already exists in this project\"");
//			return;
//		}
//		if(!ro.isSuccess()){
//			return;
//		}		
//		Session session = this.sessionFactory.getCurrentSession();		
//		PMM = new ProjectMemberModel();
//		PMM.setProjectName(projectName);
//		PMM.setUserName(userName);		
//		session.save(PMM);		
//	}
//	
//	public void DeleteAProject(String userName, String projectName, ReturnedObject ro){
//		Session session=this.sessionFactory.getCurrentSession(); 
//		String hql = "delete from ProjectMemberModel pmm where pmm.userName= :userName and pmm.projectName= :projectName"; 
//		session.createQuery(hql).setString("userName", new String(userName))
//			.setString("projectName", new String(projectName))
//			.executeUpdate();
//	}
//	
//	public void DeleteAMemberFromAllProject(String userName, String member, ReturnedObject ro){
//		Session session=this.sessionFactory.getCurrentSession(); 
//		String hql = " delete from ProjectMemberModel pmm where ( pmm.userName = :userName ) and ( pmm.member = :member ) "; 
//		session.createQuery(hql).setString("userName", new String(userName))
//			.setString("member", new String(member))
//			.executeUpdate();
//	}
//	public void DeleteAMemberFromAProject(String userName,String projectName,String member, ReturnedObject ro){
//		Session session=this.sessionFactory.getCurrentSession(); 
//		String hql = " delete from ProjectMemberModel pmm where ( pmm.userName = :userName ) and "
//				+ "( pmm.projectName = :projectName ) and ( pmm.member = :member ) "; 
//		session.createQuery(hql)
//			.setString("userName", new String(userName))
//			.setString("projectName", new String(projectName))
//			.setString("member", new String(member))
//			.executeUpdate();	
//	}


}
