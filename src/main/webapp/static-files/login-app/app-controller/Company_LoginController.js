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
 
    Company_LoginController.$inject = ['$location','$cookieStore','$rootScope','UserService'];
    function Company_LoginController($location, $cookieStore, $rootScope, UserService) {
        var vm = this;
        
        vm.successLoginLocation = '/Company_AccountView';
        
        vm.login = login;
        vm.SetCredentials = SetCredentials;
        vm.ClearCredentials = ClearCredentials;
        
        var rd = {};
        vm.rd = rd;
        vm.rd.error = null;
        vm.rd.RegisterCompany = RegisterCompany;
        
        function initController() {
            // reset login status
            vm.ClearCredentials();
        };
        
        
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
        
 
        function login() {
            //vm.dataLoading = true;
            Login(vm.username, vm.password, function (response) {
                if (response.success) {
                    SetCredentials(vm.username, vm.password);
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
 
        function SetCredentials(username, password) {
 
            $rootScope.globals = {
                currentUser: {
                    userName: username,
                    password: password
               }
            };
 
           // $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookieStore.put('globals', $rootScope.globals);
        }
 
        function ClearCredentials() {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            //$http.defaults.headers.common.Authorization = 'Basic ';
        }
    }
 
    // Base64 encoding service used by AuthenticationService
    var Base64 = {
 
        keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
 
        encode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);
 
                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;
 
                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }
 
                output = output +
                    this.keyStr.charAt(enc1) +
                    this.keyStr.charAt(enc2) +
                    this.keyStr.charAt(enc3) +
                    this.keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);
 
            return output;
        },
 
        decode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;
 
            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input)) {
                window.alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
 
            do {
                enc1 = this.keyStr.indexOf(input.charAt(i++));
                enc2 = this.keyStr.indexOf(input.charAt(i++));
                enc3 = this.keyStr.indexOf(input.charAt(i++));
                enc4 = this.keyStr.indexOf(input.charAt(i++));
 
                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;
 
                output = output + String.fromCharCode(chr1);
 
                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }
 
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
 
            } while (i < input.length);
 
            return output;
        }
    }
 
})();