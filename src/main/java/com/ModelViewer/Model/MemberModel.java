package com.ModelViewer.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
@IdClass(MemberModelId.class)
public class MemberModel {
	
	@Id
    @Column(nullable = false, length=50)
	private String userName;
	
	@Id
    @Column(nullable = false, length=50)
	private String member;
	
	@Column(nullable = false, length=50)
	private String password;
	
	@Column(nullable = true, length=50)
	private String firstName;
	
	@Column(nullable = true, length=50)
	private String lastName;
	
	@Column(nullable = true, length=50)
	private String role;
	
	@Column(nullable = true, length=150)
	private String email;	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="userName", insertable = false, updatable = false),
		@JoinColumn(name="projectName", insertable = false, updatable = false)		
	})
	private ProjectMemberModel projectMemberModel;	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userName", insertable = false, updatable = false)
	private UserModel userModel;	
	
	
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ProjectMemberModel getProjectMemberModel() {
		return projectMemberModel;
	}

	public void setProjectMemberModel(ProjectMemberModel projectMemberModel) {
		this.projectMemberModel = projectMemberModel;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
}
