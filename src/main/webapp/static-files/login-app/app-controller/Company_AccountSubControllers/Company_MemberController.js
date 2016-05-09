(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS_Company_MemberController", {
        	"Company_MemberController" : {
	        	"CREATE_MEMBER_FORM" : {
	        		"MEMBER" : {
	        			"MAXIMUM_LENGTH": 30,
	        			"MINIMUM_LENGTH": 3,
	        			"VALID_PATTERN":"\\w+"},
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20,
	        			"MINIMUM_LENGTH": 6,
	        			"VALID_PATTERN":"\\w+"}     		
	        	}
        	}
         })
        .controller('Company_MemberController', Company_MemberController)
        .controller('ModalInstanceCtrl', ModalInstanceCtrl)
        .run(function ($rootScope, CONSTANTS_Company_MemberController) {
        	$rootScope.CONSTANTS_Company_MemberController = CONSTANTS_Company_MemberController;
        });
 
    Company_MemberController.$inject = ['AuthService','$rootScope','$scope','MemberService','UserService','$uibModal','$log'];
    function Company_MemberController(AuthService, $rootScope, $scope, MemberService, UserService, $uibModal, $log) {
        var vm = this;       

        vm.memberService = MemberService;
        vm.createMemberDialog= false;
           
        var currentUser = AuthService.readUserModel();
        vm.members = UserService.readMemberList(currentUser);
        vm.error = null;
       
       // vm.MemberDialogAccept = MemberDialogAccept;

    	//messages//////////////////////////////
        //recieving message
        $scope.$on('refreshElementsEvent_child', function() { 
        	MemberService.readMemberList();       
        });
        
        function onError(message){
        	$rootScope.$broadcast("errorGlobal", message); 
        }

        
        //sending message
        function RefreshPageElements() { 
        	$scope.$emit('refreshElementsEvent_parent');
        }       
        
        ////////////dialog functions//////////////
        function MemberDialogAccept(memberName, password) {
        	var userModel = AuthService.readUserModel();
        	var memberModel = MemberService.newMemberModel({
        		"memberName":memberName, 
        		"password":password,
        		"userModel":userModel});
        	MemberService.createMember(memberModel).then(function(retMemberModel){
        		UserService.readMemberList(AuthService.readUserModel());
        	});
        }

        $scope.items = ['item1', 'item2', 'item3'];

        $scope.animationsEnabled = true;

        $scope.open = function (size) {

          var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            templateUrl: 'myModalContent.html',
            controller: 'ModalInstanceCtrl',
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

        $scope.toggleAnimation = function () {
          $scope.animationsEnabled = !$scope.animationsEnabled;
        };        
        
        return vm;
    }
    
    ModalInstanceCtrl.$inject = ['$scope','$uibModalInstance','items'];
    function ModalInstanceCtrl($scope, $uibModalInstance, items) {

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










