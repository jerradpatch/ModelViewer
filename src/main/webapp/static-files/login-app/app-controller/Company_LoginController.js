(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS_Company_LoginController", {
        	"Company_LoginController" : {
	        	"COMPANY_LOGIN_FORM" : {
	        		"COMPANY_NAME" : {
	        			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":'\\w+'},
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20,
	        			"MINIMUM_LENGTH": 6,
	        			"VALID_PATTERN":'\\w+'}       			
	        	},
		    	"COMPANY_REGISTRATION_FORM" : {
		    		"COMPANY_NAME" : {
		    			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":'\\w+'},			    			
		    		"PASSWORD": {
		    			"MAXIMUM_LENGTH": 20,
	        			"MINIMUM_LENGTH": 6,
	        			"VALID_PATTERN":'\\w+'},			    		
	    			"EMAIL": {
		    			"MAXIMUM_LENGTH": 50,
	        			"MINIMUM_LENGTH": 8,
	        			"VALID_PATTERN":''}			    			
		    	}
        	}
         })
        .controller('Company_LoginController', Company_LoginController)
        .run(function ($rootScope, CONSTANTS_Company_LoginController) {
        	$rootScope.CONSTANTS_Company_LoginController = CONSTANTS_Company_LoginController;
        });
 
    Company_LoginController.$inject = ['$location','AuthService','UserService'];
    function Company_LoginController($location,AuthService,UserService) {
        var vm = this;
        vm.login = login;
        
        var rd = {};
        vm.rd = rd;
        vm.rd.RegisterCompany = RegisterCompany;
        vm.rd.error = null;
      
        return vm;
        
        function login(userName, password) {       	     
        	var um = UserService.newUserModel({'userName':userName,'password':password});
        	UserService.login(um).then(function(umRet){
				AuthService.updateUserModel(umRet);
				$location.path('/Company_AccountView');	  
        	});

        };
        
        //////////// RD DIALOG  area//////////////////
        function RegisterCompany(companyName,passwordA,passwordB,email){
        	if(passwordA === passwordB){
        		var userModel = UserService.newUserModel({'userName':companyName,'password':passwordA,'email':email});
        		UserService.createUser(userModel).then(function(umRet){
        			AuthService.updateUserModel(umRet);
            		$location.path('/Company_AccountView');	 	
        		});
        	} 
        }
    }
 
})();










