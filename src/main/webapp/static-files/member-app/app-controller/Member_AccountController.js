(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS_Member_AccountController", {
        	"Member_AccountController" : {
        	"IMAGE_BASE_URL" : "FileService/GetFileAProjectFile"    		
        	}
         })
        .controller('Member_AccountController', Member_AccountController)
        .run(function ($rootScope, CONSTANTS_Member_AccountController) {
        	$rootScope.CONSTANTS_Member_AccountController = CONSTANTS_Member_AccountController;
        });
 
    Member_AccountController.$inject = ['AuthService','$location','ProjectMemberService','FileService','$cookieStore'];
    function Member_AccountController(AuthService,$location,ProjectMemberService, FileService,$cookieStore) {
    	
    	var projectPage = "/Member_ProjectView";
    	
    	var vm = this;
    	
    	vm.gotoProjectPage = gotoProjectPage;
    	
    	
    	vm.projectsMemberIsAPartOf = {};
    	GetProjectsMemberIsAPartOf();
    	
    	vm.GetProjectsMemberIsAPartOf = GetProjectsMemberIsAPartOf;
    	vm.GetAllFileMetaDataForAProject = GetAllFileMetaDataForAProject;
    	vm.GetResource = GetResource;
    	
    	var md = {};
    	vm.md = md;
    	vm.md.ToggleAndSetModel = ToggleAndSetModel;
    	vm.md.modelVisible = false;
    	vm.md.modelToLoad = null;
    	
    	
    	function gotoProjectPage (projectName){
    		$cookieStore.put("Member_ProjectController.currentProject",projectName);
    		$location.path(projectPage);
    	}
    	
    	function ToggleAndSetModel(projectName){    
    		if(vm.md.modelVisible){
    			vm.md.modelToLoad = null;
    			vm.md.modelVisible = false;
    		}else {
            	var memberPass = AuthService.GetPassword();
            	var userName = AuthService.GetUser();
            	var member = AuthService.GetMember();
            	
 //   			vm.md.modelToLoad = FileService.GetFileAProjectFile_link(userName,projectName,member,memberPass);
    			vm.md.modelVisible = true;
    		}
    	}
    	
    	function GetResource(projectName, resource){
        	var userName = AuthService.GetUser();
        	var member = AuthService.GetMember();
        	var memberPass = AuthService.GetPassword();
    		
        	console.log(resource);
    		var url = $rootScope.CONSTANTS_Member_AccountController.Member_AccountController.IMAGE_BASE_URL + "?" +
    		"userName=" + userName + 
    		"&projectName=" + projectName +
    		"&member=" + member + 
    		"&memberP=" + memberPass + 
    		"&fileName=" + resource;

    		return url;
    	}
    	
    	function GetProjectsMemberIsAPartOf(){
          var ret;
        	var memberPass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
        	var member = AuthService.GetMember();
            ProjectMemberService.GetProjectsMemberIsAPartOf(userName,member,memberPass)
                .then(function (response) {
                	if(response.success){
                		var data = response.message;
	                    if (data !== null) {	                    	
	                    	//vm.projectsMemberIsAPartOf = data;	                    	
	                    	data.forEach(function (projectName) {
	                    		GetAllFileMetaDataForAProject(projectName);
	                    	});
	                    } else {
	                    	ret = response;
	                    }
                	} else {
                		ret = response;
                	}
                });   		  		
    	}

    	function GetAllFileMetaDataForAProject(projectName){
        	var userName = AuthService.GetUser();
        	var member = AuthService.GetMember();
        	var memberPass = AuthService.GetPassword();
    		FileService.GetAllFileMetaData(userName,projectName,null,member,memberPass)
    		.then(function (response) {
                if (response.success) {
                	vm.projectsMemberIsAPartOf[projectName] = response.message;
                } else {			                	
                	vm.error = response.message;
                	onError(response.message);
                }
			});	  		  		
    	}
    	
    
    	
    	
    	return vm;
    	
    }
 
 
})();