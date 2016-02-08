(function () {
    'use strict';
 
    angular
        .module('app.member', ['ngRoute','angular-carousel-3d','ngAnimate'])
        .config(config)
        .run(run);
 
    config.$inject = ['$routeProvider'];
    function config($routeProvider) {
    	 $routeProvider            
    	 .when('/Member_AccountView', {
             controller: 'Member_AccountController',
             templateUrl: '/ModelViewer/static-files/member-app/app-view/Member_AccountView.html',
             controllerAs: 'vm'
         })
         .when('/Member_ProjectView', {
            controller: 'Member_ProjectController',
            templateUrl: '/ModelViewer/static-files/member-app/app-view/Member_ProjectView.html',
            controllerAs: 'vm'
        });
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