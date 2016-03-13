package com.ModelViewer.Model;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import com.ModelViewer.DAO.Validation.ValidateUtil;
import com.ModelViewer.LoginApp.Service.ReturnedObject;

@Entity
public class FileMetaModel implements Serializable {
	

	private static final long serialVersionUID = 2674621279979020137L;
	private static final String MODEL_MIME_TYPE = "application/zip";
	private static final String JPEG_MIME_TYPE = "image/jpeg";
	private static final String PNG_MIME_TYPE = "image/png";
	private static final String File_TYPE_NOT_ALLOWED = "\"This file type is not allowed. Only \".zip\", \".jpg\\.jpeg\", and \".png\" file types are allowed.\"";
	private static final String COULD_NOT_SAVE_FILE = "\"Could not save file, file empty.\"";
	
	@Id
//	@GeneratedValue(generator="system-uuid")
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(unique = true, nullable = false)
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
	
	@JoinColumn (name = "userModel_uuid_fk", referencedColumnName="uuid")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private UserModel userModel;

	@JoinColumn (name = "projectMemberModel_uuid_fk", referencedColumnName="uuid")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private ProjectMemberModel projectMemberModel;
	
	@Transient
	private InputStream inputStream;
	
	public FileMetaModel() {
		super();
		this.uuid = UUID.randomUUID().toString();
	}
	
	public FileMetaModel(String fileName, String fileLocation, Long fileSize, String mimeType, Timestamp dateCreated) {
		super();
		this.uuid = UUID.randomUUID().toString();
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
		if(!mimeType.equals(PNG_MIME_TYPE) && !mimeType.equals(JPEG_MIME_TYPE) && !mimeType.equals(MODEL_MIME_TYPE)
				&& !mimeType.equals(PNG_MIME_TYPE)){
			ReturnedObject.sThrowException(File_TYPE_NOT_ALLOWED);
	 	}
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
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) throws ReturnedObject {
        if (inputStream == null) {
        	ReturnedObject.sThrowException(COULD_NOT_SAVE_FILE);
        }
        this.inputStream = inputStream;
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
