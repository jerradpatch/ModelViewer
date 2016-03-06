package com.ModelViewer.DAO;

import java.util.Set;

import com.ModelViewer.Model.FileMetaModel;

public interface FileMetaDAO {

	public void createFileMeta(FileMetaModel fileMetaModel) throws Exception;
	public Set<FileMetaModel> readFileMetaList(FileMetaModel fileMetaModel) throws Exception;
	public void deleteFileMeta(FileMetaModel fileMetaModel) throws Exception;
	
}
