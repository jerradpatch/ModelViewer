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
        .run(function ($rootScope, CONSTANTS_Company_MemberController) {
        	$rootScope.CONSTANTS_Company_MemberController = CONSTANTS_Company_MemberController;
        });
 
    Company_MemberController.$inject = ['AuthService','$rootScope','$scope','MemberService','UserService'];
    function Company_MemberController(AuthService, $rootScope, $scope, MemberService, UserService) {
        var vm = this;       

        vm.createMemberDialog= false;
        
//        vm.memberNameToCreate = "";
//        vm.memberPasswordToCreate = "";	 
//        vm.memberNameToCreateOld = "";
//        vm.memberPasswordToCreateOld = "";      
        
        var currentUser = AuthService.readUserModel();
        vm.members = UserService.readMemberList(currentUser);
        vm.error = null;
       
//        vm.MemberDialogAccept = MemberDialogAccept;
//        vm.GetMemberData = GetMemberData;
        
//        vm.GetAListOfMembers = GetAListOfMembers;
//    	vm.DeleteMember = DeleteMember;
//    	vm.CreateUpdateAMember = CreateUpdateAMember;
//    	vm.RefreshElements = RefreshElements;

    	//messages//////////////////////////////
        //recieving message
        $scope.$on('refreshElementsEvent_child', function() { 
        	//alert("reieved on child");
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
//        function MemberDialogAccept() {
//        	CreateUpdateAMember(vm.memberNameToCreate,vm.memberPasswordToCreate);
//        }
//        function GetMemberData(memberModel) {
//        	updateMember
//        	var pass = AuthService.GetPassword();
//        	var userName = AuthService.GetUser();
//			MemberService.GetMemberData(userName,member,pass) 
//				.then(function (response) {
//	                if (response.success) {
//	                    var message = response.message;
//	                    vm.memberNameToCreateOld = message.member;
//	                    vm.memberPasswordToCreateOld = message.password;
//	                    vm.memberNameToCreate = message.member;
//	                    vm.memberPasswordToCreate = message.password;
//	                } else {
//	                	onError(response.message);
//	                	vm.memberNameToCreate = "";
//	                    vm.memberPasswordToCreate = "";
//	                	vm.memberNameToCreateOld = "";
//	                    vm.memberPasswordToCreateOld = "";
//	                	vm.error = response.message;
//	                }
//	            });	       	
//        }
//        
//        function GetAListOfMembers() {   	
//        	var pass = AuthService.GetPassword();
//        	var userName = AuthService.GetUser();
//			MemberService.GetAListOfMembers(userName,pass) 
//				.then(function (response) {
//	                if (response.success) {
//	                    vm.members = response.message;
//	                    vm.error = null;
//	                } else {
//	                	onError(response.message);
//	                	vm.members = null;
//	                	vm.error = response.message;
//	                }
//	            });	
//        }
        //////////////hybrid services////////////////////////
//        function DeleteMember (member){
//        	var pass = AuthService.GetPassword();
//        	var userName = AuthService.GetUser();
//			MemberService.DeleteMember(userName,member,pass) 
//				.then(function (response) {
//	                if (response.success) {			                   
//	                    vm.error = null;
//	                    RefreshPageElements();
//	                } else {	
//	                	onError(response.message);
//	                	vm.error = response.message;
//	                }
//		        });       	 	
//        }
        
//        function CreateUpdateAMember (member,password){
//        	var userPass = AuthService.GetPassword();
//        	var userName = AuthService.GetUser();
//      	
//			MemberService.CreateUpdateAMember(userName,member,password,userPass,vm.memberNameToCreateOld,vm.memberPasswordToCreateOld) 
//		
//				.then(function (response) {
//	                if (response.success) {			                   
//	                    vm.error = null;
//	                    RefreshElements();
//	                } else {
//	                	onError(response.message);
//	                	vm.error = response.message;
//	                }
//		        });      	 	
//        }
        
        ////////////////private functions  /////////////////////// 
//        function RefreshElements() { 
//        	GetAListOfMembers();
//        }
        
        return vm;
    }
})();