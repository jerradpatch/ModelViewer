(function () {
    'use strict';
 
    angular
        .module('app', ['ngRoute', 'ngCookies', 'ngFileUpload'])
        .config(config)
        .run(run);
 
    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
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
            .when('/Member_AccountView', {
                controller: 'Member_AccountController',
                templateUrl: '/ModelViewer/static-files/member-app/app-view/Member_AccountView.html',
                controllerAs: 'vm'
            })
            .otherwise({ redirectTo: '/HomeView' });
        
//        if(window.history && window.history.pushState){
//            $locationProvider.html5Mode(true);
//        }
    }
 
    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
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
 
})();