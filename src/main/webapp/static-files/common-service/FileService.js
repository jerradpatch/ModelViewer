(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('FileService', FileService);
 
    FileService.$inject = ['$http','$rootScope'];
    function FileService($http,$rootScope) {   	
    	
    	var baseUrlFileService = '/ModelViewer/FileService/';
    	var service = {};
    	
    	var filesBeingUploaded = {}; 
    	var fileName = null;
    	var projectName = null;
    	
    	service.UploadFileAProjectFile = UploadFileAProjectFile;
    	service.GetFileAProjectFile = GetFileAProjectFile;
    	service.GetFileAProjectFile_link = GetFileAProjectFile_link;
    	service.GetAllFileMetaData = GetAllFileMetaData;
    	service.DeleteFile = DeleteFile;
    	service.filesBeingUploaded = filesBeingUploaded;
    	
    	$rootScope.$on('FileService.recieve.abort', function(event,data) {
        	//alert("reieved on child");
    		var projectName = data.projectName;
    		var fileName = data.fileName;
        	 if(data.hasOwnProperty("projectName") && data.hasOwnProperty("fileName")){
        		 if(projectName != null && fileName != null){      			 
        			 if(service.filesBeingUploaded != null){
         				 if(service.filesBeingUploaded[projectName] != null){
         					 if(service.filesBeingUploaded[projectName][fileName] != null){
         						var uploadingAjax = service.filesBeingUploaded[projectName][fileName];
         						uploadingAjax.abort();
         					 }
         				 }      
        			 }      				 
        		 }
        	 }  
        });
    	
        function GetFileAProjectFile(userName,userP, member, memberP, projectName,fileName) {
        	if(userP == null){
	            return $http.get(baseUrlFileService+'GetFileAProjectFile', {params:{"projectName":projectName, "member": member, "memberP": memberP, "fileName":fileName}})
	            	.then(handleSuccess, handleError('Error FileService.GetFileAProjectFile '+userName+" "+projectName));  
        	} else if (memberP==null){
        		return $http.get(baseUrlFileService+'GetFileAProjectFile', {params:{"userName": userName, "userP": userP, "projectName":projectName, "fileName":fileName}})
            		.then(handleSuccess, handleError('Error FileService.GetFileAProjectFile '+userName+" "+projectName)); 
        	} else {
        		return {"success":false,"message":"using this method incorrectly"};
        	}
        	
        }

        function GetFileAProjectFile_link(userName,projectName,member) {
            return baseUrlFileService+'GetFileAProjectFile?userName='+userName+'&projectName='+projectName+'&member='+member;   	
        } 
        
        function GetAllFileMetaData(userName,projectName,uPass,member,memberP)	{
            return $http.get(baseUrlFileService+'GetAllFileMetaData', {params:{"userName": userName, "projectName": projectName, "uPass":uPass, "member":member, "memberP":memberP}})
        	.then(handleSuccess, handleError('Error FileService.GetAllFileMetaData '+userName+" "+projectName));        
        }       
        
        function UploadFileAProjectFile(userName,projectName,userP,file) {
        	var formData = new FormData();
            formData.append("userName",userName);
            formData.append("projectName",projectName);
            formData.append("userP",userP);
            formData.append("file",file);
            
            var ajax = new XMLHttpRequest();
            
            var pn = service.filesBeingUploaded[projectName];
            if( pn == null){
            	var qn = {};
            	qn[file.name] = ajax;
            	service.filesBeingUploaded[projectName] = qn;
            } 
            
            var fn = service.filesBeingUploaded[projectName][file.name];
            if(fn == null){
            	
            	service.filesBeingUploaded[projectName][file.name] = ajax;
            }

            ajax.upload.addEventListener("progress", 
        			(function (event) { 
        				service.fileName = file.name;
        				service.projectName = projectName;
        				$rootScope.$broadcast("FileService.progress", {"projectName":projectName,"fileName":file.name, "progress":((event.loaded / event.total) * 100)});  
        			}),
        			false);
            ajax.addEventListener("load", 
        			(function (event) { 
        				service.fileName = null;
        				service.projectName = null;       
        				$rootScope.$broadcast("FileService.complete", {"projectName":projectName,"fileName":file.name, "progress":100}); 
        			}), 
        			false);
            ajax.addEventListener("error", 
        			(function (event) { 
        				$rootScope.$broadcast("FileService.error", {"projectName":projectName,"fileName":file.name, "progress":0}); 
        				service.fileName = null;
        				service.projectName = null;   
        			}), 
        			false);
            ajax.addEventListener("abort", 
        			(function (event) { 
        				$rootScope.$broadcast("FileService.abort", {"projectName":projectName,"fileName":file.name, "progress":0});
        				service.fileName = null;
        				service.projectName = null;   
        			}), 
        			false);
        	
            ajax.open("POST",baseUrlFileService+'UploadFileAProjectFile');
            ajax.send(formData);
            
            service.filesBeingUploaded[projectName][file.name] = ajax;
        }
        
        function DeleteFile(userName,projectName,fileName,uPass) {
            return $http.get(baseUrlFileService+'DeleteFile', {params:{"userName": userName, "projectName": projectName, "fileName":fileName, "uPass": uPass}})
            	.then(handleSuccess, handleError('Error FileService.DeleteFile '+JSON.stringify({params:{"userName": userName, "projectName": projectName, "fileName":fileName, "uPass": uPass}})));        	
        }
        
     // private functions
        
        function handleSuccess(res) {
            return res.data;
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
        
        return service;
    }
    
    
    
})();