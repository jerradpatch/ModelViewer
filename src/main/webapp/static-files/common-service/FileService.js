(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('FileService', FileService);
 
    FileService.$inject = ['$http','$rootScope'];
    function FileService($http,$rootScope) {   	
    	
    	var baseUrlFileService = '/ModelViewer/FileService/';
    	var service = {};
    	
    	
    	service.UploadFileAProjectFile = UploadFileAProjectFile;
    	service.GetFileAProjectFile = GetFileAProjectFile;
    	service.GetFileAProjectFile_link = GetFileAProjectFile_link;
    	
        function GetFileAProjectFile(userName,projectName,file,companyP) {
            return $http.get(baseUrlMember+'GetFileAProjectFile', {params:{"userName": userName, "projectName": projectName, "member":member, "companyP": companyP}})
            	.then(handleSuccess, handleError('Error MemberService.GetMemberData'));        	
        }

        function GetFileAProjectFile_link(userName,projectName,member) {
            return baseUrlFileService+'GetFileAProjectFile?userName='+userName+'&projectName='+projectName+'&member='+member;   	
        } 
        
        
        function UploadFileAProjectFile(userName,projectName,file) {
        
        	var formData=new FormData();
            formData.append("userName",userName);
            formData.append("projectName",projectName);
            formData.append("file",file);
            
            var ajax = new XMLHttpRequest();
        	ajax.upload.addEventListener("progress", progressHandler, false);
        	ajax.addEventListener("load", completeHandler, false);
        	ajax.addEventListener("error", errorHandler, false);
        	ajax.addEventListener("abort", abortHandler, false);
        	ajax.open("POST",baseUrlFileService+'UploadFileAProjectFile');
        	ajax.send(formData);
        }
        function progressHandler(event){
        	$rootScope.$broadcast("FileService.progress",(event.loaded / event.total) * 100);        	
        }
        function completeHandler(event){
        	$rootScope.$broadcast("FileService.complete", 100); 
        }
        function errorHandler(event){
        	$rootScope.$broadcast("FileService.error", "file error"); 
        }
        function abortHandler(event){
        	$rootScope.$broadcast("FileService.abort", "file aborted"); 
        }

          
        /*
        
        function UploadFileAProjectFile(userName,projectName,file) {
        	
        	var formData=new FormData();
            formData.append("userName",userName);
            formData.append("projectName",projectName);
            formData.append("file",file);
            console.log(formData)
            $http.post(baseUrlFileService+'UploadFileAProjectFile', formData, {
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                },
                headers: { 'Content-Type': undefined }
                }).then(handleSuccess, handleError('Error MemberService.CreateAMember'));
        } */
       
        
     // private functions
        
        function handleSuccess(res) {
            return { success: true, message: res.data };
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
        
        return service;
    }
    
    
    
})();