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
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"},
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20,
	        			"MINIMUM_LENGTH": 6,
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"}       			
	        	},
		    	"COMPANY_REGISTRATION_FORM" : {
		    		"COMPANY_NAME" : {
		    			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"},			    			
		    		"PASSWORD": {
		    			"MAXIMUM_LENGTH": 20,
	        			"MINIMUM_LENGTH": 6,
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"},			    		
	    			"EMAIL": {
		    			"MAXIMUM_LENGTH": 50,
	        			"MINIMUM_LENGTH": 8,
	        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"}			    			
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