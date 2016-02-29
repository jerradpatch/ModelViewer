package com.ModelViewer.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;

@Entity
@IdClass(ProjectMemberModelId.class)
public class ProjectMemberModel{


	@Id
	@Column(nullable = false,length=50)
	private String userName;
	
	@Id
	@Column(nullable = false,length=50)
	private String projectName;

	@Column(nullable = true,length=300)
	private String story;	
	

	@OneToMany (cascade=CascadeType.ALL, mappedBy="projectMemberModel", fetch = FetchType.LAZY)
	private Set<MemberModel> members = new HashSet<MemberModel>();
	
	
	
	public ProjectMemberModel(){
		super();
	}
	
	public ProjectMemberModel(String userName, String projectName){
		super();
		this.userName = userName;
		this.projectName = projectName;
	}
	
	
	
	
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
	public Set<MemberModel> getMembers() {
		return members;
	}
	public void setMembers(Set<MemberModel> members) {
		this.members = members;
	}
	
	
	
	
}
