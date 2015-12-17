(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('FileService', FileService);
 
    FileService.$inject = ['$http','$rootScope'];
    function FileService($http,$rootScope) {   	
    	
    	var baseUrlFileService = '/ModelViewer/FileService/';
    	var service = {};
    	
    	var fileName = null;
    	var projectName = null;
    	
    	service.UploadFileAProjectFile = UploadFileAProjectFile;
    	service.GetFileAProjectFile = GetFileAProjectFile;
    	service.GetFileAProjectFile_link = GetFileAProjectFile_link;
    	service.GetAllFileMetaData = GetAllFileMetaData;
    	service.DeleteFile = DeleteFile;
    	service.ajax = null;
    	
    	$rootScope.$on('FileService.recieve.abort', function(event,data) {
        	//alert("reieved on child");
        	 if(data.hasOwnProperty("projectName") && data.hasOwnProperty("fileName")){
        		 if(data.name == fileName && data.name == fileName){      			 
        			 if(service.ajax != null){
        				 service.ajax.abort(); 
         				 service.fileName = null;
        				 service.projectName = null;       
        			 }      				 
        		 }
        	 }  
        });
    	test the file abort!!!!!
        function GetFileAProjectFile(userName,projectName,file,companyP) {
            return $http.get(baseUrlFileService+'GetFileAProjectFile', {params:{"userName": userName, "projectName": projectName, "member":member, "companyP": companyP}})
            	.then(handleSuccess, handleError('Error FileService.GetFileAProjectFile '+userName+" "+projectName));        	
        }

        function GetFileAProjectFile_link(userName,projectName,member) {
            return baseUrlFileService+'GetFileAProjectFile?userName='+userName+'&projectName='+projectName+'&member='+member;   	
        } 
        
        function GetAllFileMetaData(userName,projectName,uPass)	{
            return $http.get(baseUrlFileService+'GetAllFileMetaData', {params:{"userName": userName, "projectName": projectName, "uPass":uPass}})
        	.then(handleSuccess, handleError('Error FileService.GetAllFileMetaData '+userName+" "+projectName));        
        }       
        
        function UploadFileAProjectFile(userName,projectName,userP,file) {
        	var formData=new FormData();
            formData.append("userName",userName);
            formData.append("projectName",projectName);
            formData.append("userP",userP);
            formData.append("file",file);
            
            service.ajax = new XMLHttpRequest();
            
            service.ajax.upload.addEventListener("progress", 
        			(function (event) { 
        				service.fileName = file.name;
        				service.projectName = projectName;
        				$rootScope.$broadcast("FileService.progress", {"projectName":projectName,"fileName":file.name, "progress":((event.loaded / event.total) * 100)});  
        			}),
        			false);
            service.ajax.addEventListener("load", 
        			(function (event) { 
        				service.fileName = null;
        				service.projectName = null;       
        				$rootScope.$broadcast("FileService.complete", {"projectName":projectName,"fileName":file.name, "progress":100}); 
        			}), 
        			false);
            service.ajax.addEventListener("error", 
        			(function (event) { 
        				$rootScope.$broadcast("FileService.error", {"projectName":projectName,"fileName":file.name, "progress":0}); 
        				service.fileName = null;
        				service.projectName = null;   
        			}), 
        			false);
            service.ajax.addEventListener("abort", 
        			(function (event) { 
        				$rootScope.$broadcast("FileService.abort", {"projectName":projectName,"fileName":file.name, "progress":0});
        				service.fileName = null;
        				service.projectName = null;   
        			}), 
        			false);
        	
            service.ajax.open("POST",baseUrlFileService+'UploadFileAProjectFile');
            service.ajax.send(formData);
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