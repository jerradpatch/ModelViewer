package com.ModelViewer.Model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;

@Entity
@IdClass(ProjectInfoModelId.class)
public class ProjectInfoModel {

	@Id
	@Column
	private String userName;
	
	@Id
	@Column
	private String projectName;
	
	@Column
	private String story;


	public String getUserName() {
		if(userName == null){
			return "";
		}
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectName() {
		if(projectName == null){
			return "";
		}
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStory() {
		if(story == null){
			return "";
		}
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

}
