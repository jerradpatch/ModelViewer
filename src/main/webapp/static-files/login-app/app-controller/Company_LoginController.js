(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS", {
        	"Company_LoginController" : {
	        	"COMPANY_LOGIN_FORM" : {
	        		"COMPANY_NAME" : {
	        			"MAXIMUM_LENGTH": 20 },
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20 }      		
	        	},
		    	"COMPANY_REGISTRATION_FORM" : {
		    		"COMPANY_NAME" : {
		    			"MAXIMUM_LENGTH": 20 },
		    		"PASSWORD": {
		    			"MAXIMUM_LENGTH": 20 },
	    			"EMAIL": {
		    			"MAXIMUM_LENGTH": 20 }
		    	}
        	}
         })
        .controller('Company_LoginController', Company_LoginController)
        .run(function ($rootScope, CONSTANTS) {
        	$rootScope.CONSTANTS = CONSTANTS;
        });
 
    Company_LoginController.$inject = ['AuthService','UserService'];
    function Company_LoginController(AuthService, UserService) {
        var vm = this;
        
        vm.successLoginLocation = '/Company_AccountView';
        
        vm.login = login;
        
        var rd = {};
        vm.rd = rd;
        vm.rd.error = null;
        vm.rd.RegisterCompany = RegisterCompany;
        

        function login() {
            //vm.dataLoading = true;
            Login(vm.username, vm.password, function (response) {
                if (response.success) {
                	AuthService.SetCredentials(vm.username, vm.password);
                    $location.path(vm.successLoginLocation);
                } else {
                    //FlashService.Error(response.message);
                	vm.error = response.message;
                	//vm.username = '';
                	//vm.password = '';
                    //vm.dataLoading = false;
                }
            });
        };

        //go to the user service and check if the passwords match
        function Login(username, password, callback) {

            var ret;
            UserService.GetUserByUserName(username)
                .then(function (response) {
                	if(response.success){
                		var user = response.message;
	                    if (user !== null && user.password === password) {
	                    	ret = { success: true };
	                    } else {
	                    	ret = { success: false, message: 'Username or password is incorrect' };
	                    }
                	} else {
                		ret = { success: false, message: response.message };
                	}
                    callback(ret);
                });
        }
        
        //////////// RD DIALOG  area//////////////////
        function RegisterCompany(companyName,passwordA,passwordB,email){
        	if(passwordA === passwordB){
        		UserService.CreateUser(companyName,passwordA,email)
	        		.then(function (response) {
		                if (response.success) {			                	
	                		//alert(JSON.stringify(response));
		                    vm.rd.error = null;
		                    SetCredentials(vm.username, vm.password);
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