package com.ModelViewer.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import javax.persistence.Index;
import javax.persistence.CascadeType;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.LoginApp.Service.UserService;
import com.ModelViewer.Model.Support.JacksonDepthLimit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * JacksonDepthLimit - to limit the depth jackson parser will look into the object.
 * Collection unmodifibile returned to stop adding items, and thus not adding the uuid when the item is added. Needed for hibernate
 * @author jerrad
 *
 */
@Table(indexes = {@Index(columnList="userName,password")})
@Entity
public class UserModel extends JacksonDepthLimit implements Serializable{

	@JsonIgnore
	private static final Logger logger = Logger.getLogger(UserModel.class);
	
	
	private static final long serialVersionUID = 8966262352528139382L;
	
	@Id
//	@GeneratedValue(generator="system-uuid")
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(unique = true, nullable = false)
	private String uuid;
	
	@Column(unique = true, nullable = false, length=50)
	private String userName;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false, length=50)
	private String password;
	
	@Column(nullable = true, length=150)
	private String email;
	
	@Column(nullable = false)
	private Timestamp dateCreated;
	
	@Column(nullable = false)
	private Timestamp dateLastLoggedIn;

	@JsonIgnoreProperties({"userModel"})
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userModel", cascade = CascadeType.ALL)
    private Set<MemberModel> memberModels;

	@JsonIgnoreProperties({"userModel"})
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userModel", cascade = CascadeType.ALL)
	private Set<ProjectMemberModel> projectMemberModels;
	
	@JsonIgnoreProperties({"userModel"})
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userModel", cascade = CascadeType.ALL)
    private Set<FileMetaModel> fileMetaModels;
	
	
	public UserModel(){
		super();
		logger.debug("created uuid");
		this.uuid = UUID.randomUUID().toString();
		this.fileMetaModels = new HashSet<FileMetaModel>();
		this.memberModels = new HashSet<MemberModel>();
		this.projectMemberModels = new HashSet<ProjectMemberModel>();
	}
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		logger.debug("set uuid");
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
	public Set<MemberModel> getMemberModels() {
		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
			return null;
		} else {
			for(MemberModel fmm : this.memberModels){
				fmm.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
			}
			return Collections.unmodifiableSet(memberModels);
		}
	}
	public void addMemberModel(MemberModel memberModel){
		this.memberModels.add(memberModel);
		memberModel.setUserModel(this);
	}
	public void setMemberModels(Set<MemberModel> members) {
		this.memberModels = members;
	}
	public Set<ProjectMemberModel> getProjectMemberModels() {
		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
			return null;
		} else {
			for(ProjectMemberModel fmm : this.projectMemberModels){
				fmm.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
			}
			return Collections.unmodifiableSet(projectMemberModels);
		}
	}
	public void addProjectMemberModel(ProjectMemberModel projectMemberModel){
		this.projectMemberModels.add(projectMemberModel);
		projectMemberModel.setUserModel(this);
	}
	public void setProjectMemberModel(Set<ProjectMemberModel> projectMemberModels) {
		this.projectMemberModels = projectMemberModels;
	}	
	public Set<FileMetaModel> getFileMetaModels() {
		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
			return null;
		} else {
			for(FileMetaModel fmm : this.fileMetaModels){
				fmm.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
			}
			return Collections.unmodifiableSet(fileMetaModels);
		}
	}
	public void setFileMetaModels(Set<FileMetaModel> fileMetaModels) {
		this.fileMetaModels = fileMetaModels;
	}
	public void addFileMetaModel(FileMetaModel fileMetaModel){
		this.fileMetaModels.add(fileMetaModel);
		fileMetaModel.setUserModel(this);
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
