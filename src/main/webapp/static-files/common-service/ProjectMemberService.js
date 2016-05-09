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
        
        testService();
        
        return service;
 
        function testService(){
        	var userModel = AuthService.readUserModel();
        	var projectmemberModel = newProjectMemberModel({"memberName":"anna", "password":"12345", "email": "anna@anna.com", "userModel": userModel});
        	projectmemberModel.uuid = "0";
        	createUpdateModel(null,projectmemberModel);
        	projectmemberModel = newProjectMemberModel({"memberName":"anna1", "password":"12345", "email": "anna1@anna.com", "userModel": userModel});
        	projectmemberModel.uuid = "1";
        	createUpdateModel(null,projectmemberModel);
        	projectmemberModel = newProjectMemberModel({"memberName":"anna2", "password":"12345", "email": "anna2@anna.com", "userModel": userModel});
        	projectmemberModel.uuid = "2";
        	createUpdateModel(null,projectmemberModel);
        	projectmemberModel = newProjectMemberModel({"memberName":"anna3", "password":"12345", "email": "anna3@anna.com", "userModel": userModel});
        	projectmemberModel.uuid = "3";
        	createUpdateModel(null,projectmemberModel);
        }
        
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

//////////////////////not service hitting functions
        function newProjectMemberModel(args){
        	return {
        		"projectName": args.projectName,
        		"password": args.password,
        		"userModel": args.userModel,
        		"memberModel": args.memberModel 
        	};
        } 
        
        function createUpdateModel(oldModel, newModel){
        	if(newModel.uuid != null){
            	var updateModel = service.data[newModel.uuid];
            	if(updateModel != null){
	            	for(var key in newModel){
	            		updateModel[key] = newModel[key];
	            	}
            	} else {
            		if(oldModel != null && oldModel.password != null){
            			newModel["password"] = oldModel.password;
            		}
            		service.data[newModel.uuid] = newModel;
            	}
        	}
        }
        
        function createProjectMemberPostObj (endpointName, model) {
        	return {
        		  method: 'POST',
        		  url: baseUrlMember+endpointName,	
        		  data: {"memberModel": model},
        		  headers: {'Content-Type': 'application/json'}
        	};
        	
        }       

    }
 
})();