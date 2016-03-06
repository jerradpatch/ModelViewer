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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;

@Entity
public class ProjectMemberModel implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1065589547453243877L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true,nullable = false)
	private String uuid;
	
	@Column(nullable = false,length=50)
	private String projectName;

	@Column(nullable = true,length=300)
	private String story;	
	
	@JoinColumn(name="uuid", insertable = false, updatable = false)
	@ManyToOne (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private UserModel userModel;	

	@ManyToMany (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MemberModel> members;
	
	@JoinColumn(name="uuid")
	@OneToMany (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<FileMetaModel> fileModels;	
	
	
	
	public ProjectMemberModel(){
		super();
	}
	
	public ProjectMemberModel(String userName, String projectName){
		super();
		this.projectName = projectName;
	}
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) throws ReturnedObject {
		ValidateUtil.validateProjectName(projectName);
		this.projectName = projectName;
	}
	
	public String getStory() {
		return story;
	}

	public void setStory(String story) throws ReturnedObject {
		ValidateUtil.validateTextField(story);
		this.story = story;
	}
	
	public Set<MemberModel> getMembers() {
		return members;
	}
	public void setMembers(Set<MemberModel> members) {
		this.members = members;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public Set<FileMetaModel> getFileModels() {
		return fileModels;
	}

	public void setFileModels(Set<FileMetaModel> fileModels) {
		this.fileModels = fileModels;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof ProjectMemberModel){
			ProjectMemberModel pobj = (ProjectMemberModel) obj;
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
