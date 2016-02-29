package com.ModelViewer.Model;

import java.io.Serializable;

public class ProjectMemberModelId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4105543711765862775L;
	
	private String userName;
	private String projectName;
	
	public ProjectMemberModelId(){
		super();
	}

	public ProjectMemberModelId(String userName, String projectName){
		super();
		this.userName = userName;
		this.projectName = projectName;
	}
	
	public ProjectMemberModelId(String userName, String projectName, String member){
		super();
		this.userName = userName;
		this.projectName = projectName;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof ProjectMemberModelId){
			ProjectMemberModelId pobj = (ProjectMemberModelId) obj;
			if(pobj.userName.equals(this.userName) &&
					pobj.projectName.equals(this.projectName))
				return true;
		}
		return false;
	}
	
	@Override 
	public int hashCode(){
		return (this.userName+this.projectName).hashCode();
	}
}
