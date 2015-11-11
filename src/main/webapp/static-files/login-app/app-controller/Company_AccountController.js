(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('Company_AccountController', Company_AccountController);
 
    Company_AccountController.$inject = ['$location','$cookieStore','$scope','$rootScope'];
    function Company_AccountController($location, $cookieStore, $scope, $rootScope) {

    	
        $scope.$on('refreshElementsEvent_parent', function() { 
        	//alert("event recieved on parent");
        	$scope.$broadcast('refreshElementsEvent_child');       
        });
    }

 
})();