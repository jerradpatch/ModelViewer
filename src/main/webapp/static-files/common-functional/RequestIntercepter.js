(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('RequestIntercepter', RequestIntercepter);
 
    RequestIntercepter.$inject = ['AuthService', 'UserService', 'MemberService'];
    function RequestIntercepter(AuthService) {    	
    	return {
    		'request': function(config) {
    			if(config.method == 'POST'){
    				if(config.url.indexOf("login") < 0 && config.url.indexOf("createUser") < 0){
    					var postVal = config.data;
    					if(AuthService.user){
    						config.data = 
    						{"auth":
    							{"userModel": {
    									"user":UserService.data.user,
    									"password":UserService.data.password
    								}
    							},
    						 "data":postVal
    						}
    					} else {
    						config.data = 
    						{"auth":
    							{"memberModel": {
    									"user":MemberService.data.user,
    									"password":MemberService.data.password
    								}
    							},
    						 "data":postVal
    						}    						
    					}
    				}
    			}
    			return config;
		    },
    	}
    };
 
})();