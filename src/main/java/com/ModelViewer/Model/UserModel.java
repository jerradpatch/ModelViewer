package com.ModelViewer.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;

@Entity
public class UserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8966262352528139382L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true)
	private String uuid;
	
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

	@JoinColumn(name="Id", insertable = true, updatable = true)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    private Set<FileModel> FileModels;
	
	@JoinColumn(name="Id", insertable = true, updatable = true)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    private Set<MemberModel> members;
	
	@JoinColumn(name="Id", insertable = true, updatable = true)
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private Set<ProjectMemberModel> projectMemberModels = new HashSet<ProjectMemberModel>();
	
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) throws ReturnedObject {
		ValidateUtil.validateUserName(userName);
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) throws ReturnedObject {
		ValidateUtil.validatePassword(password);
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws ReturnedObject {
		ValidateUtil.validateEmail(email);
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
	public Set<ProjectMemberModel> getProjectMemberModels() {
		return projectMemberModels;
	}
	public void setProjectMemberModel(Set<ProjectMemberModel> projectMemberModels) {
		this.projectMemberModels = projectMemberModels;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof UserModel){
			UserModel pobj = (UserModel) obj;
			if(pobj.uuid.equals(this.uuid)){
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public int hashCode(){
		return (this.uuid).hashCode();
	}
}
