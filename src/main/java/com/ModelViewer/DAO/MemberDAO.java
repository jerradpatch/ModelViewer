package com.ModelViewer.DAO;

import java.util.List;

import com.ModelViewer.Model.MemberModel;

public interface MemberDAO {

	public List<String> GetListOfMember(String UserName);
	public void CreateUpdateAMember(String userName, String member, String password);
	public void DeleteAMember(String userName, String member);
	public MemberModel GetMemberData(String userName, String member);
	public boolean ComparePasswords(String userName, String member, String password);
}
