package com.ModelViewer.DAO;

import java.util.HashMap;
import java.util.List;

import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectMemberModel;

public interface ProjectMemberDAO {
	
	public List<ProjectMemberModel> GetListOfProjectsAndMembers(String userName, ReturnedObject ro);
	public HashMap<String, List<String>> GetHashMapOfProjectAndMember(String userName, ReturnedObject ro);
	public List<String> GetProjectsMemberIsAPartOf(String userName, String member, ReturnedObject ro);
	public List<String> GetListOfUserProject(String userName, String projectName, ReturnedObject ro);
	public ProjectMemberModel GetProject(String userName, String projectName, ReturnedObject ro);
	public ProjectMemberModel GetProjectMemberModel(String userName, String projectName, String member, ReturnedObject ro);
	public void CreateANewProject(String userName, String projectName, ReturnedObject ro);
	public void CreateAMember(String userName, String projectName, String member, ReturnedObject ro);
	public void DeleteAProject(String userName, String projectName, ReturnedObject ro);
	public void DeleteAMemberFromAllProject(String userName, String member, ReturnedObject ro);
	public void DeleteAMemberFromAProject(String userName,String projectName,String member, ReturnedObject ro);
	
}
