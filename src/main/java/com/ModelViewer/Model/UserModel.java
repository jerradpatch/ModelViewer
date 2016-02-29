package com.ModelViewer.Model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class UserModel {

	@Id
	@Column(nullable = false, length=50)
	private String userName;
	
	@Column(nullable = false, length=50)
	private String password;
	
	@Column(nullable = true, length=150)
	private String email;
	
	@Column(nullable = false)
	private Timestamp dateCreated;
	
	@Column(nullable = false)
	private Timestamp dateLastLoggedIn;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userModel", cascade=CascadeType.PERSIST)
    private Set<MemberModel> members;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Timestamp getDateLastLoggedIn() {
		return dateLastLoggedIn;
	}
	public void setDateLastLoggedIn(Timestamp dateLastLoggedIn) {
		this.dateLastLoggedIn = dateLastLoggedIn;
	}
	public Set<MemberModel> getMembers() {
		return members;
	}
	public void setMembers(Set<MemberModel> members) {
		this.members = members;
	}
	
}
