(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('Company_AccountController', Company_AccountController);
 
    Company_AccountController.$inject = ['$scope','$animate'];
    function Company_AccountController($scope, $animate) {
    	
    	var vm = this;
    	
    	vm.error = null;
    	
        $scope.$on('errorGlobal', function(message) { 
        	//alert("event recieved on parent");
        	vm.error = message;  
        	var ele = angular.element('#id');
        	$animate.setClass(ele,'Company_AccountView_fadeError','Company_AccountView_fadeError');  	
        });   	
        $scope.$on('refreshElementsEvent_parent', function() { 
        	//alert("event recieved on parent");
        	$scope.$broadcast('refreshElementsEvent_child');       
        });
    }

 
})();