package com.ModelViewer.DAO;

import java.util.List;

import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.MemberModel;

public interface MemberDAO {

	public List<String> GetListOfMember(String userName, ReturnedObject ro);
	public String GetMemberPassword(String userName, String member, ReturnedObject ro);
	public MemberModel GetMemberData(String userName, String member, ReturnedObject ro);
	public void CreateUpdateAMember(String userName, String member, String password, ReturnedObject ro);
	public void DeleteAMember(String userName, String member, ReturnedObject ro);
	
}
