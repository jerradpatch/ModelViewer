(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS", {
        	"Member_LoginController" : {
        	"COMPANY_LOGIN_FORM" : {
        		"COMPANY_NAME" : {
        			"MAXIMUM_LENGTH": 20 },
        		"PASSWORD": {
        			"MAXIMUM_LENGTH": 20 },      		
        		}  
        	}
         })
        .controller('Member_LoginController', Member_LoginController)
        .run(function ($rootScope, CONSTANTS) {
        	$rootScope.CONSTANTS = CONSTANTS;
        });
 
    Member_LoginController.$inject = ['$location','$cookieStore','$rootScope','MemberService'];
    function Member_LoginController($location, $cookieStore, $rootScope, MemberService) {
        var vm = this;
        
        vm.successLoginLocation = '/Member_AccountView';
        
        vm.login = login;
        vm.SetCredentials = SetCredentials;
        
        function initController() {
            // reset login status
            vm.ClearCredentials();
        };
 
        function login(userName, member, password) {
            //vm.dataLoading = true;
            Login(userName, member, password, function (response) {
                if (response.success) {
                    SetCredentials(userName, member, password);
                    $location.path(vm.successLoginLocation);
                } else {
                    //FlashService.Error(response.message);
                	vm.error = response.message;
                	//vm.username = '';
                	//vm.password = '';
                    //vm.dataLoading = false;
                }
            });
        };

        function Login(userName, member, password, callback) {

            var ret;
            MemberService.GetMemberData(userName,member)
                .then(function (response) {
                	if(response.success){
                		var data = response.message;
	                    if (data !== null && data.password === password) {
	                    	ret = { success: true };
	                    } else {
	                    	ret = { success: false, message: 'Company, member, or password is incorrect' };
	                    }
                	} else {
                		ret = { success: false, message: response.message };
                	}
                    callback(ret);
                });
        }
 
        function SetCredentials(userName, member, password) {

            $rootScope.globals = {
                currentUser: {
                    member: member,
                    userName:userName
                }
            };
            
            $cookieStore.put('globals', $rootScope.globals); 
        }
 
    }
 
})();