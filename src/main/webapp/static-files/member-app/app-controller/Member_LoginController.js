(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS_Member_LoginController", {
        	"Member_LoginController" : {
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
        .controller('Member_LoginController', Member_LoginController)
        .run(function ($rootScope, CONSTANTS_Member_LoginController) {
        	$rootScope.CONSTANTS_Member_LoginController = CONSTANTS_Member_LoginController;
        });
 
    Member_LoginController.$inject = ['$location','AuthService','MemberService'];
    function Member_LoginController($location,AuthService, MemberService) {
        var vm = this;
        
        vm.successLoginLocation = '/Member_AccountView';
        
        vm.login = login;
 
        
        function login() {       	
        	MemberService.ComparePasswordsForMember(vm.companyName,vm.memberName,vm.memberPassword)
            .then(function (response) {
            	if("success" in response){
	            	if(response.success){
	            		AuthService.SetCredentials(vm.companyName, vm.memberPassword, vm.memberPassword);
	                    $location.path(vm.successLoginLocation);
	            	} else {
	            		if("message" in response){
	            			vm.error = response.message;
	            		} else {
	            			vm.error = "";
	            		}
	            	}
            	}
            });       
        };
        
        return vm;
 
    }
 
})();