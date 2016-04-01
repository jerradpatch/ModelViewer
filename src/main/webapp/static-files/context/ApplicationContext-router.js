(function () {
    'use strict';
 
    angular
        .module('app', ['app.member','ngRoute', 'ngCookies','ngFileUpload','angular-carousel','ngAnimate','ngResource','ui.bootstrap'])
        .config(config)
        .factory('HTTPInterceptorErrorService', HTTPInterceptorErrorService)
        .factory('RequestIntercepter', RequestIntercepter)
        .run(run);
 
    config.$inject = ['$routeProvider', '$locationProvider', '$httpProvider',];
    function config($routeProvider, $locationProvider, $httpProvider) {
        $routeProvider
	        .when('/HomeView', {
	     
	            templateUrl: '/ModelViewer/static-files/common-view/HomeView.html',
	            controllerAs: 'vm'
	        })
            .when('/Company_LoginView', {
                controller: 'Company_LoginController',
                templateUrl: '/ModelViewer/static-files/login-app/app-view/Company_LoginView.html',
                controllerAs: 'vm'
            })           
            .when('/Company_AccountView', {
                controller: 'Company_AccountController',
                templateUrl: '/ModelViewer/static-files/login-app/app-view/Company_AccountView.html',
                controllerAs: 'vm'
            }) 
            .when('/Company_RegisterView', {
                controller: 'Company_RegisterController',
                templateUrl: '/ModelViewer/static-files/login-app/app-view/Company_RegisterView.html',
                controllerAs: 'vm'
            })
            .otherwise({ redirectTo: '/HomeView' });
        
//        if(window.history && window.history.pushState){
//            $locationProvider.html5Mode(true);
//        }
        
        $httpProvider.interceptors.push('HTTPInterceptorErrorService');
        $httpProvider.interceptors.push('RequestIntercepter');
    }
 
    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http', 'ErrorModel'];
    function run($rootScope, $location, $cookieStore, $http, ErrorModel) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        $rootScope.ErrorModel = ErrorModel;

//        if ($rootScope.globals.currentUser) {
//            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
//        }
 
//        $rootScope.$on('$locationChangeStart', function (event, next, current) {
//            // redirect to login page if not logged in and trying to access a restricted page
//            var restrictedPage = $.inArray($location.path(), ['/LoginView', '/RegisterView']) === -1;
//            var loggedIn = $rootScope.globals.currentUser;
//            if (restrictedPage && !loggedIn) {
//                $location.path('/LoginView');
//            }
//        });
    }
    
    HTTPInterceptorErrorService.$inject = ['$q','$rootScope','ErrorModel'];
    function HTTPInterceptorErrorService($q,$rootScope,ErrorModel) { 
    	  return {
    		  'responseError': function(response) {
			  		var errorModel = ErrorModel.getNewModel(false,response);
			  		ErrorModel.pushModel(errorModel); 
			  		return $q.reject(errorModel);
    			}
    	  }; 
    }

    RequestIntercepter.$inject = ['AuthService','ErrorModel','$q'];
    function RequestIntercepter(AuthService,ErrorModel,$q) {    	
    	return {
    		'request': function(config) {
    			if(config.method == 'POST'){
    				if(config.url.indexOf("login") < 0 && config.url.indexOf("createUser") < 0){
    					var postVal = config.data;
    					if(AuthService.user){
    						config.data =  {"auth": {"userModel": AuthService.userModel }, "data":postVal };
    					} else {
    						config.data =  {"auth": {"memberModel": AuthService.memberModel }, "data":postVal };    						
    					}
    				}
    			}
    			return config;
		    },
		    'response': function(response) {
		    	if(response.config.method == 'POST'){
			    	if(response.data){
						var retStatus = response.data;			
			    		if(retStatus.status_boolean){
			    			return retStatus.message_string;
			    		} else {
			    			var errorModel = ErrorModel.getNewModel(false,retStatus.message_string);
					  		ErrorModel.pushModel(errorModel); 
					  		return $q.reject(false); //error model does not set the message on the screen????000227
			    		}
					}
			    	return $q.reject(false);
		    	}
		    	return response;
		    }
    	};
    };
 
})();