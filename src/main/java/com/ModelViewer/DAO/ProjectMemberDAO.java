package com.ModelViewer.DAO;



import org.springframework.web.bind.annotation.RequestBody;

import com.ModelViewer.Model.ProjectMemberModel;

public interface ProjectMemberDAO {
	
	public void createProject(@RequestBody(required = true) ProjectMemberModel projectMemberModel) throws Exception;
	public void updateProject(@RequestBody(required = true) ProjectMemberModel projectMemberModel) throws Exception;
	public void deleteProject(@RequestBody(required = true) ProjectMemberModel projectMemberModel) throws Exception;
}
