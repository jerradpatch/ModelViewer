(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS", {
        	"Company_LoginController" : {
	        	"COMPANY_LOGIN_FORM" : {
	        		"COMPANY_NAME" : {
	        			"MAXIMUM_LENGTH": 50 },
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20 }      		
	        	},
		    	"COMPANY_REGISTRATION_FORM" : {
		    		"COMPANY_NAME" : {
		    			"MAXIMUM_LENGTH": 50 },
		    		"PASSWORD": {
		    			"MAXIMUM_LENGTH": 20 },
	    			"EMAIL": {
		    			"MAXIMUM_LENGTH": 30 }
		    	}
        	}
         })
        .controller('Company_LoginController', Company_LoginController)
        .run(function ($rootScope, CONSTANTS) {
        	if("CONSTANTS" in $rootScope){
        		$rootScope.CONSTANTS.Company_LoginController = CONSTANTS.Company_LoginController;
        	}else {
        		$rootScope.CONSTANTS = CONSTANTS;
        	}
        });
 
    Company_LoginController.$inject = ['$location','AuthService','UserService'];
    function Company_LoginController($location,AuthService,UserService) {
        var vm = this;
        
        vm.successLoginLocation = '/Company_AccountView';
        
        vm.login = login;
        
        var rd = {};
        vm.rd = rd;
        vm.rd.error = null;
        vm.rd.RegisterCompany = RegisterCompany;
        

        function login() {       	
        	UserService.ComparePasswordsForUser(vm.username,vm.password)
            .then(function (response) {
            	if("success" in response){
	            	if(response.success){
	            		AuthService.SetCredentials(vm.username, vm.password, "");
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
        
        //////////// RD DIALOG  area//////////////////
        function RegisterCompany(companyName,passwordA,passwordB,email){
        	if(passwordA === passwordB){
        		UserService.CreateUser(companyName,passwordA,email)
	        		.then(function (response) {
		                if (response.success) {			                	
	                		//alert(JSON.stringify(response));
		                    vm.rd.error = null;
		                    AuthService.SetCredentials(companyName, passwordA);
		                    $location.path(vm.successLoginLocation);
		                    return;
		                }			                	
		                
		                vm.rd.error = response.message;
		                //alert(vm.rd.error);
			        });
        	} else {
        		 vm.rd.error = "Passwords do not match";
        	}
        }

    }
 
})();