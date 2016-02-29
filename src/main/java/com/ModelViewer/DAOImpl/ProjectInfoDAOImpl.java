package com.ModelViewer.DAOImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ModelViewer.DAO.ProjectInfoDAO;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectInfoModel;

public class ProjectInfoDAOImpl implements ProjectInfoDAO{

	private static final Logger logger = LoggerFactory.getLogger(ProjectInfoDAOImpl.class);
	
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	public ProjectInfoModel GetProjectInfo(String userName, String projectName, ReturnedObject ro) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Criteria userQuery = session.createCriteria(ProjectInfoModel.class);
//        userQuery.add(Restrictions.eq("userName",userName));
//        userQuery.add(Restrictions.eq("projectName",projectName));
//        @SuppressWarnings("unchecked")
//		List<ProjectInfoModel> projectMembersModelList =  userQuery.list();
//        
//        ProjectInfoModel projectInfo = null;
//        if(projectMembersModelList.size() == 1){
//        	projectInfo = projectMembersModelList.get(0);
//        }
//        else if(projectMembersModelList.size() == 0){
//        	projectInfo = null;
//        }
//        else {
//        	ro.setMessage("ProjectInfoDAOImpl: there was more then one project found, should only be one");
//        	ro.setSuccess(false);
//        } 
//        if(projectInfo == null){
//        	return new ProjectInfoModel();
//        }
//        return projectInfo;	
		return null;
	}

	public void CreateProjectInfo(ProjectInfoModel pim, ReturnedObject ro) {
//		ProjectInfoModel PIM = GetProjectInfo(pim.getUserName(),pim.getProjectName(),ro);
//		if(!ro.isSuccess()){
//			return;
//		}
//		logger.debug("pim1:"+PIM);
//		if(PIM != null){
//			DeleteProjectInfo(PIM,ro);
//			if(!ro.isSuccess()){
//				return;
//			}
//		}	
//		this.sessionFactory.getCurrentSession().save(pim);	
	}
	
	public void DeleteProjectInfo(ProjectInfoModel pim, ReturnedObject ro){
//		Session session=this.sessionFactory.getCurrentSession(); 
//		String hql = "delete from ProjectInfoModel pmm where pmm.userName= :userName and pmm.projectName= :projectName"; 
//		session.createQuery(hql)
//			.setString("userName", new String(pim.getUserName()))
//			.setString("projectName", new String(pim.getProjectName()))
//			.executeUpdate();				
	}

}







