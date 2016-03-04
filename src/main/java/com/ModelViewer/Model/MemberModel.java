package com.ModelViewer.Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;

@Entity
public class MemberModel implements Serializable{
	
	private static final long serialVersionUID = 6676621279977420137L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true)
	private String uuid;
	
	
    @Column(nullable = false, length=50)
	private String memberName;
	
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
	
	@ManyToMany (cascade=CascadeType.ALL, mappedBy="memberModel", fetch = FetchType.LAZY)
	private Set<ProjectMemberModel> projectMemberModel = new HashSet<ProjectMemberModel>();
	
	@JoinColumn(name="Id", insertable = false, updatable = false)
	@ManyToOne(fetch=FetchType.LAZY)
	private UserModel userModel;
	
	public MemberModel(String uuid, String memberName, String password, String firstName, String lastName,
			String role, String email, Set<ProjectMemberModel> projectMemberModel, UserModel userModel) {
		super();
		this.uuid = uuid;
		this.memberName = memberName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.email = email;
		this.projectMemberModel = projectMemberModel;
		this.userModel = userModel;
	}

	public MemberModel(String memberName, String password, UserModel userModel){
		
	}
	
	public MemberModel() {
		super();
	}	
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getmemberName() {
		return memberName;
	}

	public void setMember(String memberName) throws ReturnedObject {
		ValidateUtil.validateMemberName(memberName);
		this.memberName = memberName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws ReturnedObject {
		ValidateUtil.validatePassword(password);
		this.password = password;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws ReturnedObject {
		ValidateUtil.validateUserName(firstName);
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws ReturnedObject {
		ValidateUtil.validateUserName(lastName);
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) throws ReturnedObject {
		ValidateUtil.validateFileName(role);
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws ReturnedObject {
		ValidateUtil.validateEmail(email);
		this.email = email;
	}

	public Set<ProjectMemberModel> getProjectMemberModel() {
		return projectMemberModel;
	}

	public void setProjectMemberModel(Set<ProjectMemberModel> projectMemberModel) throws ReturnedObject {
		this.projectMemberModel = projectMemberModel;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
	
	
	
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof MemberModel){
			MemberModel pobj = (MemberModel) obj;
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
