(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('Member_AccountController', Member_AccountController);
 
    Member_AccountController.$inject = ['AuthService','$rootScope','ProjectMemberService','FileService'];
    function Member_AccountController(AuthService,$rootScope, ProjectMemberService, FileService) {
    	
    	var vm = this;
    	
    	vm.projectsMemberIsAPartOf = GetProjectsMemberIsAPartOf();
    	
    	vm.GetProjectsMemberIsAPartOf = GetProjectsMemberIsAPartOf;

    	
    	var md = {};
    	vm.md = md;
    	vm.md.ToggleAndSetModel = ToggleAndSetModel;
    	vm.md.modelVisible = false;
    	vm.md.modelToLoad = null;
    	
    	function ToggleAndSetModel(projectName){    
    		if(vm.md.modelVisible){
    			vm.md.modelToLoad = null;
    			vm.md.modelVisible = false;
    		}else {
            	var memberPass = AuthService.GetPassword();
            	var userName = AuthService.GetUser();
            	var member = AuthService.GetMember();
            	
    			vm.md.modelToLoad = FileService.GetFileAProjectFile_link(userName,projectName,member,memberPass);
    			vm.md.modelVisible = true;
    		}
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
	                    	
	                    	vm.projectsMemberIsAPartOf = data;
	                    } else {
	                    	ret = response;
	                    }
                	} else {
                		ret = response;
                	}
                });   		  		
    	}
    	
    	
    	return vm;
    	
    }
 
 
})();