(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS_Member_ProjectController", {
        	"Member_ProjectController" : {
	        	"MEMBER_LOGIN_FORM" : {
	        		"COMPANY_NAME" : {
	        			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"},
	        		"MEMBER_NAME" : {
	        			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"},
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20,
	        			"MINIMUM_LENGTH": 6,
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"}     		
	        		}  
	        	}
         })
        .controller('Member_ProjectController', Member_ProjectController)
        .run(function ($rootScope, CONSTANTS_Member_ProjectController) {
        	$rootScope.CONSTANTS_Member_ProjectController = CONSTANTS_Member_ProjectController;
        });
 
    Member_ProjectController.$inject = ['$location','$cookieStore','AuthService','MemberService','FileService'];
    function Member_ProjectController($location,$cookieStore,AuthService, MemberService, FileService) {
        var vm = this;

        vm.GetAllFileMetaDataForAProject = GetAllFileMetaDataForAProject();	
        vm.projectMeta = {};
        
        function GetProject(){
        	return $cookieStore.get("Member_ProjectController.currentProject");
        }
        
    	function GetAllFileMetaDataForAProject(){
        	var userName = AuthService.GetUser();
        	var projectName = GetProject();
        	if(projectName == null){
        		return;
        	}
        	var member = AuthService.GetMember();
        	var memberPass = AuthService.GetPassword();
    		FileService.GetAllFileMetaData(userName,projectName,null,member,memberPass)
    		.then(function (response) {
                if (response.success) {
                	console.log(response.message);
                	vm.projectMeta = response.message;
                } else {			                	
                	vm.error = response.message;
                	onError(response.message);
                }
			});	  		  		
    	}
        return vm;
    }
 
})();