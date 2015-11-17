(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS", {
        	"Company_MemberController" : {
	        	"CREATE_MEMBER_FORM" : {
	        		"MEMBER" : {
	        			"MAXIMUM_LENGTH": 30 },
	        		"PASSWORD": {
	        			"MAXIMUM_LENGTH": 20 }      		
	        	}
        	}
         })
        .controller('Company_MemberController', Company_MemberController)
        .run(function ($rootScope, CONSTANTS) {
        	if("CONSTANTS" in $rootScope){
        		$rootScope.CONSTANTS.Company_MemberController = CONSTANTS.Company_MemberController;
        	}else {
        		$rootScope.CONSTANTS = CONSTANTS;
        	}
        });
 
    Company_MemberController.$inject = ['AuthService','$scope','MemberService'];
    function Company_MemberController(AuthService, $scope, MemberService) {
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

    	//messages//////////////////////////////
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
        
        ////////////dialog functions//////////////
        function MemberDialogAccept() {
        	CreateUpdateAMember(vm.memberNameToCreate,vm.memberPasswordToCreate);
        }
        function GetMemberData(member) {
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			MemberService.GetMemberData(userName,member,pass) 
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
        
        function GetAListOfMembers() {   	
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			MemberService.GetAListOfMembers(userName,pass) 
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
        //////////////hybrid services////////////////////////
        function DeleteMember (member){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			MemberService.DeleteMember(userName,member,pass) 
				.then(function (response) {
	                if (response.success) {			                   
	                    vm.error = null;
	                    RefreshPageElements();
	                } else {			                	
	                	vm.error = response.message;
	                }
		        });       	 	
        }
        
        function CreateUpdateAMember (member,password){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			MemberService.CreateUpdateAMember(userName,member,password,pass) 
				.then(function (response) {
	                if (response.success) {			                   
	                    vm.error = null;
	                    RefreshElements();
	                } else {			                	
	                	vm.error = response.message;
	                }
		        });      	 	
        }
        
        ////////////////private functions  /////////////////////// 
        function RefreshElements() { 
        	GetAListOfMembers();
        }
        
        return vm;
    }
})();