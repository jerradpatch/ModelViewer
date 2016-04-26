package com.ModelViewer.DAO;

import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;

import com.ModelViewer.Model.MemberModel;

public interface MemberDAO {

	public MemberModel createMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	public MemberModel readMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	public Set<MemberModel> readMemberList(MemberModel MemberModel) throws Exception; //all members in a user
	public void updateMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	public void deleteMember(@RequestBody(required = true) MemberModel memberModel) throws Exception;
	public MemberModel readMember_userNamePassword(MemberModel memberModel);
	
}
