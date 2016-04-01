(function () {
    'use strict';
 
    angular
        .module('app.member')
        .constant("CONSTANTS_Member_LoginController", {
        	"Member_LoginController" : {
	        	"MEMBER_LOGIN_FORM" : {
	        		"COMPANY_NAME" : {
	        			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":'\\w+'},
	        		"MEMBER_NAME" : {
	        			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":'\\w+'},
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20,
	        			"MINIMUM_LENGTH": 6,
	        			"VALID_PATTERN":"\\w+"}     		
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
        	
//        	.then(function(){
//                $location.path(vm.successLoginLocation);	            			
//    		});
        };
        
        return vm;
 
    }
 
})();