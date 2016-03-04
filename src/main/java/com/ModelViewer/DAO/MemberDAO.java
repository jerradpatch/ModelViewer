package com.ModelViewer.DAO;

import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;

import com.ModelViewer.Model.MemberModel;
import com.ModelViewer.Model.ProjectMemberModel;

public interface MemberDAO {

	public void createMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	public MemberModel readMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	public Set<ProjectMemberModel> readMemberProjects(MemberModel memberModel) throws Exception;
	public void updateMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	public void deleteMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	
}
