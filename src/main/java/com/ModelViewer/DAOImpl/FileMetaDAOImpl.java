package com.ModelViewer.DAOImpl;

import java.sql.Timestamp;
import java.util.Set;
import org.hibernate.SessionFactory;
import com.ModelViewer.DAO.FileMetaDAO;
import com.ModelViewer.Model.FileMetaModel;

public class FileMetaDAOImpl implements FileMetaDAO{

	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	
	public void createFileMeta(FileMetaModel fileMetaModel) throws Exception {
    	java.util.Date date= new java.util.Date();
    	Timestamp ts = new Timestamp(date.getTime()); 
    	fileMetaModel.setDateCreated(ts);
		this.sessionFactory.getCurrentSession().persist(fileMetaModel);
	}
	public FileMetaModel readFileMeta(FileMetaModel fileMetaModel) throws Exception {
		return this.sessionFactory.getCurrentSession().get(FileMetaModel.class, fileMetaModel.getUuid());
	}
	public Set<FileMetaModel> readFileMetaList(FileMetaModel fileMetaModel) throws Exception {
		FileMetaModel fm = this.sessionFactory.getCurrentSession().get(FileMetaModel.class, fileMetaModel.getUuid());
		return fm.getProjectMemberModel().getFileModels();
	}
	public void deleteFileMeta(FileMetaModel fileMetaModel) throws Exception {
		this.sessionFactory.getCurrentSession().delete(fileMetaModel);
	}


	
	
	
	
	
	
	
//	private static final String EMPTY_STRING = "\"\"";
//	private static final String FILE_BASE_LOCATION ="/userData/userProjects/";
//	private static final String MODEL_NAME_PROJECT_TYPE = "folder";
//	private static final String MODEL_NAME_JPG_TYPE = ".jpg";
//	private static final String MODEL_NAME_JPEG_TYPE = ".jpeg";
//	private static final String MODEL_NAME_PNG_TYPE = ".png";
//	private static final String MODEL_NAME_ZIP_TYPE = ".zip";
//	
//	private static final Logger logger = LoggerFactory.getLogger(FileDAOImpl.class);
	
//	public InputStream GetFileAProjectFile(String userName,String projectName,String fileName, ReturnedObject ro){
//		
//		String path = makeFilePath(userName, projectName, fileName);
//		
//		//test if file exists
//		if(!fileExists(path)){
//    		ro.setSuccess(false);
//    		ro.setMessage("\"file does not exist\"");
//    		logger.error("userName: "+userName+" projectName: "+projectName+" fileName: "+fileName+" 'does not exist'");
//    		return null;
//		}
//		InputStream is = null;
//	    try {
//	    	File file = new File(path.toString());
//	    	logger.debug("get a file path: "+path);
//	        // get your file as InputStream
//	        is = new BufferedInputStream(new FileInputStream(file));
//	        // copy it to response's OutputStream
//	       // org.apache.commons.io.IOUtils.copy(is, os);
//	        return is;
//	        
//	      } catch (FileNotFoundException ex) {
//	        logger.error("Error writing file to output stream. Filename was '{}'");
//	
//	      } 
//	    return null;
//	}
	/**
	 * @return null and ro set if error, else value
	 */
//	public List<String> GetFileListForAProject(String userName, String projectName, ReturnedObject ro){
//		
//		String location = makeFolderPath(userName, projectName);
//    	if(location == null){
//    		ro.setSuccess(false);
//    		ro.setMessage("path is empty");
//    		return null;
//    	}
//    	File folder = CreatePathIfNotExist(new File(location), ro);
//    	
//    	if(folder.listFiles() == null){
//    		ro.setSuccess(false);
//    		ro.setMessage("\"not a folder\"");
//    		return null;
//    	}
//    	
//    	List<String> filesNames = new ArrayList<String>();
//    	File[] files = folder.listFiles();
//    	
//    	for(File F : files){
//    		filesNames.add(F.getName());
//    	}
//    	return filesNames;
//	}
	
	/**
	 * @return null if path is unable to be made, empty list if no files, otherwise files list< name and type>; type, is empty if there is no extension
	 */
//	public HashMap<Object,Object> GetFileListForAProjectAndMetaData(String userName, String projectName, ReturnedObject ro){
//		String location = makeFolderPath(userName, projectName);
//    	
//		return recursivlyFindFiles(0,4,new File(location));
//	}
//	public List<HashMap<String,String>> GetFileListForAProjectAndMetaData(String userName, String projectName, ReturnedObject ro){
//		
//		String location = makeFolderPath(userName, projectName);
//    	if(location == null){
//    		ro.setSuccess(false);
//    		ro.setMessage("Couldnt make the path");
//    		return null;
//    	}
//    	
//    	List<HashMap<String,String>> metaData = new ArrayList<HashMap<String,String>>();
//    	File[] files = new File(location).listFiles();
//    	if(files == null){
//    		ro.setSuccess(true);
//    		ro.setMessage("");
//    		return metaData;  		
//    	}
//    	
//    	for(File F : files){
//    		HashMap<String,String> hm = new HashMap<String,String>();
//    		String fileName = F.getName();
//    		
//    		hm.put("name", fileName);
//    		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") < fileName.length()){
//    			int typeIndex = fileName.lastIndexOf(".") + 1;
//    			String type = fileName.substring(typeIndex, fileName.length());
//    			hm.put("type", type);
//    		} else {
//    			hm.put("type", "");
//    		}
//
//    		metaData.add(hm);
//    	}
//    	return metaData;
//	}
	
//	public Integer GetProjectFileCount(String userName, String projectName, ReturnedObject ro){
//		
//		List<String> files = GetFileListForAProject(userName, projectName, ro);
//		int finalCount = 0;
//		for(String fName : files){
//			int periodLocation = fName.lastIndexOf('.');
//			if(periodLocation >= 0){
//				String type = fName.substring(periodLocation, fName.length());
//				if(type.equals(MODEL_NAME_PROJECT_TYPE)){
//					finalCount++;
//				}
//			} else {
//				logger.info("GetProjectFileCount: userName: "+userName+" projectName: "+projectName+"; images dont have .extensions" );
//			}	
//		}
//    	return finalCount;
//	}
//	
//	/**
//	 * @return returns null and ro set if error, else returns value
//	 */
//	public Integer GetProjectImageCount(String userName, String projectName, ReturnedObject ro){
//		
//		List<String> files = GetFileListForAProject(userName, projectName, ro);
//		if(files == null || !ro.isSuccess()){
//			return null;
//		}			
//		int finalCount = 0;
//		for(String fName : files){
//			int periodLocation = fName.lastIndexOf('.');
//			if(periodLocation >= 0){
//				String type = fName.substring(periodLocation, fName.length());
//				if(type.equals(MODEL_NAME_JPG_TYPE) || type.equals(MODEL_NAME_JPEG_TYPE) || type.equals(MODEL_NAME_PNG_TYPE)){
//					finalCount++;
//				}
//			} else {
//				logger.info("GetProjectImageCount: userName: "+userName+" projectName: "+projectName+"; images dont have .extensions" );
//			}
//		}
//    	return finalCount;
//	}
//	
//	public List<String> GetProjectImageNames(String userName, String projectName, ReturnedObject ro){
//		
//		List<String> files = GetFileListForAProject(userName, projectName, ro);
//		List<String> finalImg = new ArrayList<String>();
//		for(String fName : files){
//			int periodLocation = fName.lastIndexOf('.');
//			if(periodLocation >= 0){
//				String type = fName.substring(periodLocation, fName.length());
//				if(type.equals(MODEL_NAME_JPG_TYPE) || type.equals(MODEL_NAME_JPEG_TYPE) || type.equals(MODEL_NAME_PNG_TYPE)){
//					finalImg.add(fName);
//				}
//			} else {
//				logger.info("GetProjectImageNames: userName: "+userName+" projectName: "+projectName+"; images dont have .extensions" );
//			}
//		}
//    	return finalImg;
//	}
//	
//	public String GetProjectProjectName(String userName, String projectName, ReturnedObject ro){
//		
//		List<String> files = GetFileListForAProject(userName, projectName, ro);
//		for(String fName : files){
//			int periodLocation = fName.lastIndexOf('.');
//			if(periodLocation >= 0){
//				String type = fName.substring(periodLocation, fName.length());
//				if(type.equals(MODEL_NAME_PROJECT_TYPE)){
//					return fName;  
//				}
//			} else {
//				logger.info("GetProjectProjectName: userName: "+userName+" projectName: "+projectName+"; projects dont have .extensions" );
//			}
//		}
//    	return "";
//	}
//	
//	
//	public void UploadAProjectFile_streaming(String userName, String projectName, String fileName, InputStream in, ReturnedObject ro) throws IOException{
//		
//    	BufferedOutputStream out = null;
//    	//upload the file
//
//        try {
//        	
//        	CreatePathIfNotExist(new File(makeFolderPath(userName,projectName)), ro);
//        	if(ro.isSuccess() == false){
//        		return;
//        	}
//        	logger.debug("unzipping file:"+fileName);
//        	
//            String fileType = getFileExtension(fileName);
//            if(fileType.equals(MODEL_NAME_ZIP_TYPE)){
//            	//save temp file
//            	String locationTempFile = makeFilePath(userName, projectName, "temp.zip");
//                File fileTempFile = new File(locationTempFile); //open tempFile
//                deleteFolderOrFile(fileTempFile, ro); //delete existing tempFile
//
//                out = new BufferedOutputStream(new FileOutputStream(fileTempFile)); //open stream to write file
//                IOUtils.copy(in,out); //write to tempFile
//                
//                //unzip temp file
//                String location = makeFilePath(userName, projectName, fileName);
//                File file = new File(location); //open tempFile
//                deleteFolderOrFile(file, ro); //delete existing unziped directory
//
//                ro.setSuccess(true);
//                unZipIt(fileTempFile.getAbsolutePath(), file.getParent(), ro); //unzip files
//                deleteFolderOrFile(fileTempFile, ro); //delete existing temp.zip to free up space
//                if(!ro.isSuccess()){
//                	return;
//                }
//            } else {
//            	//save single file
//            	String location = makeFilePath(userName, projectName, fileName);
//                File file = new File(location); //open tempFile
//                deleteFolderOrFile(file, ro); //delete existing tempFile if exists
//    			if(!ro.isSuccess()){
//    				return;
//    			}
//                out = new BufferedOutputStream(new FileOutputStream(file)); //open stream to write file
//                IOUtils.copy(in,out); //write to tempFile
//            }
//            
//            ro.setSuccess(true);
//            ro.setMessage(EMPTY_STRING);
//        } catch (Exception e) {
//            ro.setSuccess(false);
//            ro.setMessage("\""+e.getMessage()+"\"");  
//            logger.error("userName:"+userName+" projectName:"+projectName+" fileName:"+fileName+" "+e.toString(), e);
//        } finally {
//        	if(out != null){
//        		out.close();
//        	}
//        }
//	}
//
//	public void DeleteFile(String userName, String projectName, String fileName, ReturnedObject ro) {
//
//		String path = this.makeFilePath(userName, projectName, fileName);
//		File deleteFile = new File(path);
//		if(deleteFile.exists()){
//			deleteFolderOrFile(deleteFile,ro);
//			if(!ro.isSuccess()){
//				return;
//			}
//			
//			ro.setSuccess(true);
//			ro.setMessage("");
//		} else {
//			ro.setSuccess(false);
//			ro.setMessage("\"DeleteFile, fileName not found\"");		
//		}
//		return;	
//	}
//	
	
	
	
	
	
	
	////--------------------util---------------------
//	
//	private boolean fileExists(String path){
//		File f = new File(path);
//		if(f.exists() && !f.isDirectory()) { 
//			return true;
//		}		
//		return false;
//	}
//
//	private String getFileExtension(String fileName){
//		int periodLocation = fileName.lastIndexOf('.');
//		if(periodLocation == -1){
//    		return "folder";
//		}
//		String type = fileName.substring(periodLocation, fileName.length());
//		return type;
//	}
//	
//	private String makeFilePath(String userName, String projectName, String fileName){
//    	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
//    	path.append(userName).append("/").append(projectName).append("/").append(fileName);
//		return path.toString();
//	}
//	
//	private String makeFolderPath(String userName, String projectName){
//    	StringBuilder path = new StringBuilder(FILE_BASE_LOCATION);
//    	path.append(userName).append("/").append(projectName);
//		return path.toString();
//	}
	
//	File CreatePathIfNotExist(File file, ReturnedObject ro){
//
//    	logger.debug("CreateFolderIfNotExist path: "+ file.getAbsolutePath());
// 
//    	if (!file.exists()) {
//    		if(file.isFile()){
//    			file = file.getParentFile();
//    		}
//    	    try{
//    	    	file.mkdirs();
//    	    } 
//    	    catch(SecurityException se){
//        		ro.setSuccess(false);
//        		ro.setMessage("\"couldnt make the folder\"");
//        		logger.error(file.getAbsolutePath(), se);
//        		return null;
//    	    }        
//    	}
//    	
//    	return file;
//	}
	
//	private void deleteFolderOrFile(File file, ReturnedObject ro){
//		if(file.exists()){
//			if(file.isDirectory()){
//				try {
//					FileUtils.deleteDirectory(file);
//				} catch (IOException e) {
//					ro.setSuccess(false);
//					ro.setMessage("\"could not delete file: "+file.getName()+"\"");
//					logger.error(file.getAbsolutePath(),e);
//				}
//			} else {
//				file.delete();
//			}
//		}
//	}

//    private HashMap<Object,Object> recursivlyFindFiles (int depthStart, int MAX_DEPTH, File rootFile ) {
//
//        if(!rootFile.exists() || rootFile.listFiles().length == 0){
//        	//if folder contains nothing
//        	HashMap<Object,Object> nameType = new HashMap<Object,Object>();
//        	nameType.put("name",rootFile.getName());
//        	nameType.put("type","folder");
//        	nameType.put("list",new ArrayList<Object>());
//        	return nameType;    	
//        }
//        
//        File[] list = rootFile.listFiles();
//        List<Object> fileList = new ArrayList<Object>();
//        for ( File f : list ) {
//            if ( f.isDirectory() ) {
//            	Object returned = null;
//            	if(depthStart < MAX_DEPTH){
//	            	returned = recursivlyFindFiles(++depthStart, MAX_DEPTH, f );
//            	} else {           		
//            		returned = new HashMap<String,String>().put(f.getName(),"folder");
//                	fileList.add(returned);
//            	}
//	            fileList.add(returned);
//            }
//            else {
//            	logger.debug("recursivlyFindFiles: "+f.getName());
//            	String fileName = f.getName();
//            	String fileExtension = getFileExtension(f.getName());
//            	HashMap<String,String> nameType = new HashMap<String,String>();
//            	nameType.put("name",fileName);
//            	nameType.put("type",fileExtension);
//            	fileList.add(nameType);
//            	logger.debug("recursivlyFindFiles List: "+fileList.toString());
//            }
//        }
//        
//        HashMap<Object,Object> hm = new HashMap<Object,Object>();
//        hm.put("name",rootFile.getName());
//    	hm.put("type","folder");
//        hm.put("list", fileList);
//        return hm;
//    }

	
//    /**
//     * Unzip it
//     * @param absZipFile input zip file
//     * @param output zip file output folder
//     */
//    private void unZipIt(String absZipFile, String absOutputFolder, ReturnedObject ro){
//
//    	//     byte[] buffer = new byte[1024];
//    	ZipInputStream zis = null;	
//    	FileOutputStream fos = null;
//    	try{	
//
//    		//get the zip file content
//    		zis = new ZipInputStream(new FileInputStream(absZipFile));
//    		//get the zipped file list entry
//    		ZipEntry ze = zis.getNextEntry();
//    		
//   		
//    		while(ze != null){
//
//    			String fileName = ze.getName();
//    			File newFile = new File(absOutputFolder + File.separator + fileName);
//
//    			//create all non exists folders
//    			//else you will hit FileNotFoundException for compressed folder
//    			if(ze.isDirectory()){
//    				CreatePathIfNotExist(newFile, ro);
//	        		if(!ro.isSuccess()){
//	        			return;
//	        		}
//    			} else {
//    				CreatePathIfNotExist(newFile.getParentFile(), ro);    				
//	        		if(!ro.isSuccess()){
//	        			return;
//	        		}    
//	        		
//	    			fos = new FileOutputStream(newFile);             
//	    			IOUtils.copy(zis,fos);
//	    			fos.close();	
//    			}
//        		   
//    			ze = zis.getNextEntry();
//    		}
//
//    	}catch(IOException e){
//    		ro.setSuccess(false);
//    		ro.setMessage("\""+e.getMessage()+"\"");
//    		logger.error("absZipFile:"+absZipFile+" "+"absOutputFolder:"+absOutputFolder,e);
//    	} finally {
//    		try {
//    			if(fos != null){
//    				fos.close();
//    			}
//    			if(zis != null){
//					zis.closeEntry();
//					zis.close();
//    			}
//			} catch (IOException e) {
//				ro.setSuccess(false);
//				ro.setMessage("\""+e.getMessage()+"\"");
//				logger.error("",e);
//				
//			}
//    		
//    	}
//    }
  

}



















