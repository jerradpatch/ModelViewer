package com.ModelViewer.Model;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;
import com.ModelViewer.Model.Support.JacksonDepthLimit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class MemberModel extends JacksonDepthLimit implements Serializable{
	
	private static final long serialVersionUID = 6676621279977420137L;
	
	@Transient
	@JsonIgnore
	private static final Logger logger = Logger.getLogger(MemberModel.class);
	@Transient
	@JsonIgnore
	private ObjectMapper mapper = new ObjectMapper();
	
	@Id
//	@GeneratedValue(generator="system-uuid") //this is so I can keep the transactional manager to roll back any 
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(unique = true, nullable = false)
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
	
	@Column(nullable = true, length=50)
	private String website;	
	
	@Column(nullable = true, length=50)
	private String address;	
	
	@Column(nullable = true, length=10)
	private String unit;	
	
	@Column(nullable = true, length=2)
	private String state;	
	
	@Column(nullable = true, length=15)
	private String zipcode;
	
	@JsonIgnoreProperties({"members"})
	@ManyToMany (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ProjectMemberModel> projectMemberModels;
	
	@JsonIgnoreProperties({"memberModels"})
	@JoinColumn (nullable=false, name = "userModel_uuid_fk", referencedColumnName="uuid")
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.DETACH)
	private UserModel userModel;
	

	public MemberModel(String memberName, String password, String firstName, String lastName, UserModel userModel){
		this.uuid = UUID.randomUUID().toString();
		this.memberName = memberName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userModel = userModel;
		this.projectMemberModels = new HashSet<ProjectMemberModel>();

		logger.debug("member created" + uuid +memberName+password+firstName+lastName+userModel);
	}
	
	public MemberModel() {
		super();
		this.uuid = UUID.randomUUID().toString();
		//this.projectMemberModels = new HashSet<ProjectMemberModel>();
		
		logger.debug("member created " + uuid);
	}	
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		logger.debug("uuid set: "+ uuid);
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
		//ValidateUtil.validateUserName(firstName);
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws ReturnedObject {
		//ValidateUtil.validateUserName(lastName);
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) throws ReturnedObject {
		//ValidateUtil.validateFileName(role);
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws ReturnedObject {
		//ValidateUtil.validateEmail(email);
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Set<ProjectMemberModel> getProjectMemberModel() {
//		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
//			return null;
//		} else {
//			for(ProjectMemberModel fmm : this.projectMemberModels){
//				fmm.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
//			}
			return this.projectMemberModels;
//		}
	}

	public void setProjectMemberModel(Set<ProjectMemberModel> projectMemberModel) throws ReturnedObject {
		this.projectMemberModels = projectMemberModel;
		logger.debug("setProjectMemberModel");
	}

	public UserModel getUserModel() {
//		if(this.getMaxDepthLimit() <= this.getCurrentDepthLimit()){
//			return null;
//		} else {
//			userModel.setCurrentDepthLimit(this.getCurrentDepthLimit() + 1);
			return this.userModel;
//		}
	}	

	public void setUserModel(UserModel userModel){
		this.userModel = userModel;
		try {
			logger.debug("setUserModel "+mapper.writeValueAsString(userModel));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
