package com.ModelViewer.DAO;

import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.ProjectInfoModel;

public interface ProjectInfoDAO {

	ProjectInfoModel GetProjectInfo(String userName, String projectName, ReturnedObject ro);
	void CreateProjectInfo(ProjectInfoModel pim, ReturnedObject ro);
}
