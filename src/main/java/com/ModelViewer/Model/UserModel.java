package com.ModelViewer.Model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserModel")
public class UserModel {

	@Id
    @Column(name="userName")
	private String userName;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@Column
	private Timestamp dateCreated;
	
	@Column
	private Timestamp dateLastLoggedIn;
	
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
	
}
