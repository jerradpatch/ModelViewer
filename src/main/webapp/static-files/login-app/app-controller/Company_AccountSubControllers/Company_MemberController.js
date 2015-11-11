(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('Company_MemberController', Company_MemberController);
 
    Company_MemberController.$inject = ['$location','$cookieStore','$scope','$rootScope','MemberService'];
    function Company_MemberController($location, $cookieStore, $scope, $rootScope, MemberService) {
        var vm = this;       

        vm.createMemberDialog= false;
        vm.memberNameToCreate = "";
        vm.memberPasswordToCreate = "";	       	
        vm.members = GetAListOfMembers();
        vm.error = null;
       
        vm.MemberDialogAccept = MemberDialogAccept;
        vm.GetMemberData = GetMemberData;
        
        vm.GetAListOfMembers = GetAListOfMembers;
    	vm.DeleteMember = DeleteMember;
    	vm.CreateUpdateAMember = CreateUpdateAMember;
    	vm.RefreshElements = RefreshElements;

        //recieving message
        $scope.$on('refreshElementsEvent_child', function() { 
        	//alert("reieved on child");
        	RefreshElements();        
        });

        
        //sending message
        function RefreshPageElements() { 
        	//alert("sent to parent");
        	$scope.$emit('refreshElementsEvent_parent');
        }       
        
        //dialog functions
        function MemberDialogAccept() {
        	CreateUpdateAMember(vm.memberNameToCreate,vm.memberPasswordToCreate);
        }
        function GetMemberData(member) {
        	if($rootScope.globals){
        		if($rootScope.globals.currentUser){
        			if($rootScope.globals.currentUser.userName){
        			var userName = $rootScope.globals.currentUser.userName;
        			MemberService.GetMemberData(userName,member) 
        				.then(function (response) {
			                if (response.success) {
			                    var message = response.message;
			                    vm.memberNameToCreate = message.member;
			                    vm.memberPasswordToCreate = message.password;
			                } else {
			                	vm.memberNameToCreate = "";
			                    vm.memberPasswordToCreate = "";
			                	vm.error = response.message;
			                }
			            });
        			}
        		}
        	}	       	
        }
        
        //private functions   
        function RefreshElements() { 
        	GetAListOfMembers();
        }
        
        function GetAListOfMembers() {   	
        	if($rootScope.globals){
        		if($rootScope.globals.currentUser){
        			if($rootScope.globals.currentUser.userName){
        			var userName = $rootScope.globals.currentUser.userName;
        			MemberService.GetAListOfMembers(userName) 
        				.then(function (response) {
			                if (response.success) {
			                    vm.members = response.message;
			                    vm.error = null;
			                } else {
			                	vm.members = null;
			                	vm.error = response.message;
			                }
			            });
        			}
        		}
        	}
        }
        //hybrid
        function DeleteMember (member){
        	if($rootScope.globals){
        		if($rootScope.globals.currentUser){
        			if($rootScope.globals.currentUser.userName){
        			var userName = $rootScope.globals.currentUser.userName;
        			MemberService.DeleteMember(userName,member) 
        				.then(function (response) {
			                if (response.success) {			                   
			                    vm.error = null;
			                    RefreshPageElements();
			                } else {			                	
			                	vm.error = response.message;
			                }
				        });
        			}
        		}
        	}        	 	
        }
        
        function CreateUpdateAMember (member,password){
        	if($rootScope.globals){
        		if($rootScope.globals.currentUser){
        			if($rootScope.globals.currentUser.userName){
        			var userName = $rootScope.globals.currentUser.userName;
        			MemberService.CreateUpdateAMember(userName,member,password) 
        				.then(function (response) {
			                if (response.success) {			                   
			                    vm.error = null;
			                    RefreshElements();
			                } else {			                	
			                	vm.error = response.message;
			                }
				        });
        			}
        		}
        	}        	 	
        }
        
        return vm;
    }
})();