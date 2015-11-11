(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('Member_AccountController', Member_AccountController);
 
    Member_AccountController.$inject = ['$rootScope','ProjectMemberService','FileService'];
    function Member_AccountController($rootScope, ProjectMemberService, FileService) {
    	
    	var vm = this;
    	
    	vm.projectsMemberIsAPartOf = GetProjectsMemberIsAPartOf(GetUserName(),GetMemberName());
    	
    	vm.GetProjectsMemberIsAPartOf = GetProjectsMemberIsAPartOf;

    	
    	var md = {};
    	vm.md = md;
    	vm.md.ToggleAndSetModel = ToggleAndSetModel;
    	vm.md.modelVisible = false;
    	vm.md.modelToLoad = null;
    	
    	function ToggleAndSetModel(userName, projectName, member){    
    		if(vm.md.modelVisible){
    			vm.md.modelToLoad = null;
    			vm.md.modelVisible = false;
    		}else {
    			vm.md.modelToLoad = FileService.GetFileAProjectFile_link(userName,projectName,member);
    			vm.md.modelVisible = true;
    		}
    	}
    	
    	function GetProjectsMemberIsAPartOf(userName,member){
            var ret;
            //alert(userName+" "+member);
            ProjectMemberService.GetProjectsMemberIsAPartOf(userName,member)
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
    	
    	//private functions
    	function GetMemberName(){
        	if($rootScope.globals){
        		if($rootScope.globals.currentUser){
        			if($rootScope.globals.currentUser.member){
        				var member = $rootScope.globals.currentUser.member;
        				return member;
        			}
        		}
        	}   		
    	}
    	function GetUserName(){
        	if($rootScope.globals){
        		if($rootScope.globals.currentUser){
        			if($rootScope.globals.currentUser.userName){
        				var userName = $rootScope.globals.currentUser.userName;
        				return userName;
        			}
        		}
        	}     		   		
    	}    	
    	
    	
    	return vm;
    	
    }
 
 
})();