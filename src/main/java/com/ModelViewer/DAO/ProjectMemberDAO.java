package com.ModelViewer.DAO;



import java.util.Set;
import com.ModelViewer.Model.ProjectMemberModel;

public interface ProjectMemberDAO {
	
	public void createProject(ProjectMemberModel projectMemberModel) throws Exception;
	public Set<ProjectMemberModel> readMemberProjects(ProjectMemberModel projectMemberModel) throws Exception;
	public void updateProject(ProjectMemberModel projectMemberModel) throws Exception;
	public void deleteProject(ProjectMemberModel projectMemberModel) throws Exception;
}
