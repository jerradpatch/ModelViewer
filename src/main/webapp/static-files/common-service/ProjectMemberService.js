(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('ProjectMemberService', ProjectMemberService);
 
    ProjectMemberService.$inject = ['$http'];
    function ProjectMemberService($http) {
    	
    	var baseUrl = '/ModelViewer/ProjectMemberService/';
    	
        var service = {};
 
    	service.baseUrl = baseUrl;
    	
        service.createProject = createProject;
        service.readMemberProjects = readMemberProjects;
        service.readUserProjects = readUserProjects;
        service.readUserProjectsAndMembers = readUserProjectsAndMembers;
        service.updateProject = updateProject;
        service.deleteProject = deleteProject;
        
        return service;
 
	    function createProject(projectMemberModel){
	    	return $http.get(baseUrl+'createProject', 
	    			{params:{"projectMemberModel": projectMemberModel}}).then(function(projectMemberModelRet){
				if(projectMemberModelRet.uuid){
					service.data[projectMemberModelRet.uuid] = projectMemberModelRet;
				} else {
					console.log("create project no uuid");
				}
			});       	
	    }        
	    function readMemberProjects(memberModel){
		    return $http.get(baseUrl+'readMemberProjects', 
		    		{params:{"memberModel": memberModel}}).then(function(projectModelList){
		    			projectModelList.forEach(function(projectModelRet){
            				if(projectModelRet.uuid){
            					if(service.data[projectModelRet.uuid]){
            						angular.copy(projectModelRet, service.data[projectModelRet.uuid]);
            					} else {
            						service.data[projectModelRet.uuid] = projectModelRet;
            					}
            				} else {
            					console.log("createMember no uuid");
            				}
        				});
        			});	      	
		}
	    function readUserProjects(userModel){
		    return $http.get(baseUrl+'readUserProjects', 
		    		{params:{"userModel": userModel}}).then(function(projectModelList){
		    			projectModelList.forEach(function(projectModelRet){
            				if(projectModelRet.uuid){
            					if(service.data[projectModelRet.uuid]){
            						angular.copy(projectModelRet, service.data[projectModelRet.uuid]);
            					} else {
            						service.data[projectModelRet.uuid] = projectModelRet;
            					}
            				} else {
            					console.log("createMember no uuid");
            				}
        				});
        			});	     	
		}
	    function readUserProjectsAndMembers(userModel){
	    	return $http.get(baseUrl+'readUserProjectsAndMembers', 
	    			{params:{"userModel": userModel}}).then(function(projectModelList){
		    			projectModelList.forEach(function(projectModelRet){
            				if(projectModelRet.uuid){
            					if(service.data[projectModelRet.uuid]){
            						angular.copy(projectModelRet, service.data[projectModelRet.uuid]);
            					} else {
            						service.data[projectModelRet.uuid] = projectModelRet;
            					}
            				} else {
            					console.log("createMember no uuid");
            				}
        				});
        			});      	
	    }        
	    function updateProject(projectMemberModel){
		    return $http.get(baseUrl+'updateProject', 
		    		{params:{"projectMemberModel": projectMemberModel}}).then(function(){
        				service.data[projectMemberModel.uuid] = projectMemberModel;
        			});     	
		}
	    function deleteProject(projectMemberModel){
		    return $http.get(baseUrl+'deleteProject', 
		    		{params:{"projectMemberModel": projectMemberModel}}).then(function(){
        				var index = service.data.indexOf(projectMemberModel.uuid);
        				service.data.splice(index,1);
        			});      	
		}

        
//        function GetProjectsMemberIsAPartOf(userName,member, companyMemberP){
//            return $http.get(baseUrl+'GetProjectsMemberIsAPartOf', {params:{"userName": userName,"member": member, "companyP": companyMemberP }}).then(handleSuccess, handleError('Error ProjectMemberService.GetProjectsMemberIsAPartOf'));       	
//        }
//        
//        function GetHashMapOfProjectAndMember(userName, companyP) {
//            return $http.get(baseUrl+'GetHashMapOfProjectAndMember', {params:{"userName": userName, "companyP": companyP }}).then(handleSuccess, handleError('Error ProjectMemberService.GetHashMapOfProjectAndMember'));
//        }
//        
//        function GetAListOfMembers(userName, companyP) {
//            return $http.get(baseUrl+'getAListOfMembers', {params:{"userName": userName, "companyP": companyP }}).then(handleSuccess, handleError('Error ProjectMemberService.getAListOfMembers'));
//        }
//        
//        function DeleteAProject(userName,projectName, companyP) {
//        	return $http.get(baseUrl+'DeleteAProject', {params:{"userName": userName, "projectName":projectName, "companyP": companyP }}).then(handleSuccess, handleError('Error ProjectMemberService.DeleteAProject'));
//        }
//        function DeleteAMemberFromAProject(userName,projectName, member, companyP) {
//        	return $http.get(baseUrl+'DeleteAMemberFromAProject', {params:{"userName": userName, "projectName":projectName, "member":member, "companyP": companyP }}).then(handleSuccess, handleError('Error ProjectMemberService.DeleteAMember'));
//        }
//        
//        function CreateANewProject(userName,projectName, companyP) {
//        	return $http.get(baseUrl+'CreateANewProject', {params:{"userName": userName, "projectName":projectName, "companyP": companyP }}).then(handleSuccess, handleError('Error ProjectMemberService.CreateANewProject'));
//        }
//        function CreateAMember(userName,projectName,member, companyP) {
//        	return $http.get(baseUrl+'CreateAMember', {params:{"userName": userName,"projectName": projectName,"member":member, "companyP": companyP }}).then(handleSuccess, handleError('Error ProjectMemberService.CreateAMember'));
//        }               
 
        // private functions
 
//        function handleSuccess(res) {
//            return res.data;
//        }
 
//        function handleError(error) {
//            return function () {
//                return { success: false, message: error };
//            };
//        }

    }
 
})();