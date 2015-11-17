(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS", {
        	"Member_LoginController" : {
        	"MEMBER_LOGIN_FORM" : {
        		"MEMBER" : {
        			"MAXIMUM_LENGTH": 30 },
        		"PASSWORD": {
        			"MAXIMUM_LENGTH": 20 }     		
        		}  
        	}
         })
        .controller('Member_LoginController', Member_LoginController)
        .run(function ($rootScope, CONSTANTS) {
        	if("CONSTANTS" in $rootScope){
        		$rootScope.CONSTANTS.Member_LoginController = CONSTANTS.Member_LoginController;
        	}else {
        		$rootScope.CONSTANTS = CONSTANTS;
        	}
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