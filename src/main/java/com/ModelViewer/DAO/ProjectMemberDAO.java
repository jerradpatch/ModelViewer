package com.ModelViewer.DAO;

import java.util.HashMap;
import java.util.List;

import com.ModelViewer.Model.ProjectMemberModel;

public interface ProjectMemberDAO {
	
	public List<ProjectMemberModel> GetListOfProjectsAndMembers(String UserName);
	public HashMap<String, List<String>> GetHashMapOfProjectAndMember(String UserName);
	public List<String> GetProjectsMemberIsAPartOf(String userName, String member);
	public ProjectMemberModel GetProjectMemberModel(String userName, String projectName, String member);
	public void CreateANewProject(String userName, String projectName);
	public void CreateAMember(String userName, String projectName, String member);
	public void DeleteAProject(String userName, String projectName);
	public void DeleteAMemberFromAllProject(String userName, String member);
	public void DeleteAMemberFromAProject(String userName,String projectName,String member);
	
}
