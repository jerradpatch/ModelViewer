package com.ModelViewer.Model;

import java.io.Serializable;

public class ProjectInfoModelId implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1065589547453243877L;
	
	private String userName;
	private String projectName;
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof ProjectInfoModelId){
			ProjectInfoModelId pobj = (ProjectInfoModelId) obj;
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
