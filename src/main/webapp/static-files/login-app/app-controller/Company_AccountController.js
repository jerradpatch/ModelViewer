(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('Company_AccountController', Company_AccountController);
 
    Company_AccountController.$inject = ['$scope'];
    function Company_AccountController($scope) {

    	
        $scope.$on('refreshElementsEvent_parent', function() { 
        	//alert("event recieved on parent");
        	$scope.$broadcast('refreshElementsEvent_child');       
        });
    }

 
})();