
(function () {
    'use strict';
 
    angular.module('app')
	.directive('navDirective', navDirective)
    .controller('navSettingsModalContoller', navSettingsModalContoller)
    .controller('navNotificationsModalContoller', navNotificationsModalContoller);
    
    navDirective.$inject = [];
    function navDirective(){
    	return {
    	    restrict: 'E',
    	    templateUrl: '/ModelViewer/static-files/directives/nav/nav.html',
    	    scope: {
    	        sm: '=',
    	        st: '=',
    	        srb: '=',
    	        scl: '=',
    	        sn: '=',
    	        ss: '=',
    	        lo: '='
    	    },
    	    require:'ngModel',
    	    controller: navControllerS,
    	    controllerAs: 'nc',
    	    bindToController: true,
    	    compile: function(element, attrs){
    	        if (!attrs.sm) { attrs.sm = false; }
    	        if (!attrs.st) { attrs.st = false; }
    	        if (!attrs.srb) { attrs.srb = false; }
    	        if (!attrs.scl) { attrs.scl = false; }
    	        if (!attrs.sn) { attrs.sn = false; }
    	        if (!attrs.ss) { attrs.ss = false; }
    	        if (!attrs.lo) { attrs.lo = false; }
    	    }
    	};
   
		navControllerS.$inject = ['$uibModal','$scope','$log'];
		function navControllerS ($uibModal, $scope, $log){
			
			$scope.items = ['item1', 'item2', 'item3'];
	
	        $scope.animationsEnabled = true;
	
	        $scope.openSettings = function (size) {
	
	          var modalInstance = $uibModal.open({
	            animation: $scope.animationsEnabled,
	            templateUrl: 'navSettingsModalContent.html',
	            controller: 'navSettingsModalContoller',
	            size: size,
	            resolve: {
	              items: function () {
	                return $scope.items;
	              }
	            }
	          });
	
	          modalInstance.result.then(function (selectedItem) {
	            $scope.selected = selectedItem;
	          }, function () {
	            $log.info('Modal dismissed at: ' + new Date());
	          });
	        };                   	
		
	        $scope.openNotifications = function (size) {
	
	         var modalInstance = $uibModal.open({
	          animation: $scope.animationsEnabled,
	          templateUrl: 'navNotificationsModalContent.html',
	          controller: 'navNotificationsModalContoller',
	          size: size,
	          resolve: {
	            items: function () {
	              return $scope.items;
	            }
	          }
	        });
	
	        modalInstance.result.then(function (selectedItem) {
	          $scope.selected = selectedItem;
	        }, function () {
	          $log.info('Modal dismissed at: ' + new Date());
	        });
	      };
	
	//          $scope.toggleAnimation = function () {
	//            $scope.animationsEnabled = !$scope.animationsEnabled;
	//          };        
		}
	} 
    
    navSettingsModalContoller.$inject = ['$scope','$uibModalInstance','items'];
    function navSettingsModalContoller($scope, $uibModalInstance, items) {

   	  $scope.items = items;
    	  $scope.selected = {
    	    item: $scope.items[0]
    	  };

    	  $scope.ok = function () {
    	    $uibModalInstance.close($scope.selected.item);
    	  };

    	  $scope.cancel = function () {
    	    $uibModalInstance.dismiss('cancel');
    	  };
    }
    
    navNotificationsModalContoller.$inject = ['$scope','$uibModalInstance','items'];
    function navNotificationsModalContoller($scope, $uibModalInstance, items) {

    	  $scope.items = items;
    	  $scope.selected = {
    	    item: $scope.items[0]
    	  };

    	  $scope.ok = function () {
    	    $uibModalInstance.close($scope.selected.item);
    	  };

    	  $scope.cancel = function () {
    	    $uibModalInstance.dismiss('cancel');
    	  };
    }
    
})();