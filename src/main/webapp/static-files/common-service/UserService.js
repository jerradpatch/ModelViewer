(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http'];
    function UserService($http) {
    	
    	var baseUrl = '/ModelViewer/UserService/'
    	
        var service = {};
 
    	service.baseUrl = baseUrl;
        service.GetUserByUserName = GetUserByUserName;
        service.CreateUser = CreateUser;
        service.GetUserByUserName = GetUserByUserName;
        service.DeleteByUserName = DeleteByUserName;
 
      
        function GetUserByUserName(userName) {
            return $http.get(baseUrl+'GetUserByUserName', {params:{"userName": userName }}).then(handleSuccess, handleError);
        }
 
        function CreateUser(userName,password,email) { 
            return $http.post(baseUrl+'CreateUser', {"userName": userName,"password": password, "email": email}).then(handleSuccess);
        }
 
//        function UpdateUserByUserName(userName, companyP) {
//            return $http.put(baseUrl+'GetUserByUserName', {params:{"userName": userName }}).then(handleSuccess, handleError('Error updating user'));
//        }
 
//        function DeleteByUserName(userName) {
//            return null;//$http.delete(baseUrl+'DeleteByUserName', {params:{"userName": userName }}).then(handleSuccess, handleError('Error deleting user'));
//        }
 
        // private functions
        	//error handeling angular way---- always a success unless internal server error/exception then its always an error
        		//angular issue, angular does not allow the error to be retrieved, so errors are always returned as successes then parsed.
        function handleSuccess(res) {
        	if(!(typeof res === 'undefined') && !(res === null) && !(res === "null")){
        		if(!(typeof res.data === 'undefined') && !(res.data === null) && !(res.data === "null")){
	        		if("success" in res.data){
		    			if("message" in res.data){   	
		    				//message from the backend
		    				return { success: res.data.success, message: res.data.message };
		    			}else{   				
		    				return { success: res.data.success, message: '' };
		    			}      			
		        	} 
        		}
        	}
        	return { success: true, message: res.data};
        }
 
        function handleError(error) {
            return { success: false, message: "Unknown server error, try again later."};
        }
        
        return service;
    }
 
})();