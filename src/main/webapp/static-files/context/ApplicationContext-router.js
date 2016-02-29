(function () {
    'use strict';
 
    angular
        .module('app', ['app.member','ngRoute', 'ngCookies','ngFileUpload','angular-carousel','ngAnimate','ngResource','ui.bootstrap'])
        .config(config)
        .factory('HTTPInterceptorErrorService', HTTPInterceptorErrorService)
        .run(run);
 
    config.$inject = ['$routeProvider', '$locationProvider', '$httpProvider'];
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
    		  responseError: function(response) {
    			  	if(response.data){
    			  		ErrorModel.pushModel(response);
    			  		return response;
    			  	} else {
    			  		var errorModel = ErrorModel.getNewModel(false,response);
    			  		ErrorModel.pushModel(errorModel);
    			  		return errorModel.model;
    			  	}
    			  	return;
    			}
    	  };
    	  
    	  
    }


 
})();