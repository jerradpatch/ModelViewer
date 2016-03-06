package com.ModelViewer.Model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;

@Entity
public class FileMetaModel implements Serializable{
	

	private static final long serialVersionUID = 2674621279979020137L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true)
	private String uuid;


	@Column(nullable = false, length=50)
	private String fileName;
	
	@Column(nullable = false, length=150)
	private String fileLocation;
	
	@Column(nullable = false)
	private Long fileSize;
	
	@Column(nullable = false, length=30)
	private String mimeType;
	
	@Column(nullable = false)
	private Long downloadCount;
	
	@Column(nullable = false)
	private Timestamp dateCreated;
	
	@Column(nullable = false)
	private Timestamp dateLastDownloaded;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private UserModel userModel;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private ProjectMemberModel projectMemberModel;
	
	public FileMetaModel() {
		super();
	}
	
	public FileMetaModel(String fileName, String fileLocation, Long fileSize, String mimeType, Timestamp dateCreated) {
		super();
		this.fileName = fileName;
		this.fileLocation = fileLocation;
		this.fileSize = fileSize;
		this.mimeType = mimeType;
		this.dateCreated = dateCreated;
	}	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) throws ReturnedObject {
		ValidateUtil.validateFileName(fileName);
		this.fileName = fileName;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) throws ReturnedObject {
		ValidateUtil.validateFileName(fileLocation);
		this.fileLocation = fileLocation;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) throws ReturnedObject {
		ValidateUtil.validateFileName(mimeType);
		this.mimeType = mimeType;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public ProjectMemberModel getProjectMemberModel() {
		return projectMemberModel;
	}

	public void setProjectMemberModel(ProjectMemberModel projectMemberModel) {
		this.projectMemberModel = projectMemberModel;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof FileMetaModel){
			FileMetaModel pobj = (FileMetaModel) obj;
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
