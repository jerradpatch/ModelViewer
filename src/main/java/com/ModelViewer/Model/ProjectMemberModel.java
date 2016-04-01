package com.ModelViewer.Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.Support.JacksonDepthLimit;

@Entity
public class ProjectMemberModel extends JacksonDepthLimit implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1065589547453243877L;

	@Id
//	@GeneratedValue(generator="system-uuid")
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(unique = true, nullable = false)
	private String uuid;
	
	@Column(nullable = false,length=50)
	private String projectName;

	@Column(nullable = true,length=300)
	private String story;	

	@JoinColumn (name = "userModel_uuid_fk", referencedColumnName="uuid")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private UserModel userModel;
	
	@ManyToMany (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MemberModel> members;
	
	@JoinColumn(name="projectMemberModel_uuid_fk", referencedColumnName="uuid")
	@OneToMany (cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<FileMetaModel> fileModels;	
	
	
	
	public ProjectMemberModel(){
		super();
		this.uuid = UUID.randomUUID().toString();
	}
	
	public ProjectMemberModel(String userName, String projectName){
		super();
		this.projectName = projectName;
		this.uuid = UUID.randomUUID().toString();
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
		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
			return null;
		} else {
			for(MemberModel fmm : this.members){
				fmm.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
			}
			return members;
		}
	}
	public void setMembers(Set<MemberModel> members) {
		this.members = members;
	}

	public UserModel getUserModel() {
		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
			return null;
		} else {
			userModel.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
			return userModel;
		}
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public Set<FileMetaModel> getFileModels() {
		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
			return null;
		} else {
			for(FileMetaModel fmm : this.fileModels){
				fmm.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
			}
			return fileModels;
		}
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
