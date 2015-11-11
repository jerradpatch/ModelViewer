package com.ModelViewer.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;

@Entity
@IdClass(ProjectMemberModelId.class)
public class ProjectMemberModel {

	@Id
	@JoinColumn(nullable=false)
	private String userName;
	
	@Id
	@Column(length=20,nullable=false)
	private String projectName;
	
	@Id
	@Column(length=20,nullable=false)
	private String member;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
}
