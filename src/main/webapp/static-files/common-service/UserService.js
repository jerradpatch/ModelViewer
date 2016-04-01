(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http','AuthService','$q'];
    function UserService($http, AuthService, $q) {
    	
    	var baseUrl = '/ModelViewer/UserService/';
    	
        var service = {};
        
    	service.baseUrl = baseUrl;
    	service.newUserModel = newUserModel;
    	service.login = login;
        service.createUser = createUser;
        service.readUser = readUser;
        service.updateUser = updateUser;
        service.data = {};
        return service;
 
        function newUserModel(args){
        	return {
        		"userName": args.userName,
        		"password": args.password,
        		"email": args.email
        	};
        }
	    function login(userModel) {
	    	return $http.post(baseUrl+'login', JSON.stringify(userModel)).then(function(dataRet){
				if(dataRet.uuid){
					service.data[dataRet.uuid] = dataRet;
					return $q.resolve(dataRet);
				} else {
					console.log("login user no uuid");
					return $q.reject();
				}
			});
	    }
	    function createUser(userModel) {
	    	return $http.post(
	    			baseUrl+'createUser', 
	    			userModel,
	    			{headers: {'Content-Type': 'application/json'}}).then(function(dataRet){
				if(dataRet.uuid){
					service.data[dataRet.uuid] = dataRet;
				} else {
					console.log("create user no uuid");
				}				
			});
	    }
	    function readUser(userModel) {
	    	return $http.post(baseUrl+'readUser', 
	    			{params:{"userModel": userModel}}).then(function(userModelRet){
			    if(userModelRet.uuid){
					if(service.data[userModelRet.uuid]){
						angular.copy(userModelRet, service.data[userModelRet.uuid]);
					} else {
						service.data[userModelRet.uuid] = userModelRet;
					}
				} else {
					console.log("create user no uuid");
				}
			});  
	    }
	    function updateUser(userModel) {
	    	return $http.post(baseUrl+'updateUser', 
	    			{params:{"userModel": userModel}}).then(function(){
        				service.data[updateUser.uuid] = updateUser;
        			});
	    }
	    
//        function ComparePasswordsForUser(userName, password) {
//            return $http.get(baseUrl+'ComparePasswordsForUser', {params:{"userName": userName,"password": password }}).then(handleSuccess, handleError("No response from pasword compare"));
//        }
// 
//        function CreateUser(userName,password,email) { 
//            return $http.post(baseUrl+'CreateUser', {"userName": userName,"password": password, "email": email}).then(handleSuccess, handleError("Could not create user"));
//        }
 
//        function UpdateUserByUserName(userName, companyP) {
//            return $http.put(baseUrl+'GetUserByUserName', {params:{"userName": userName }}).then(handleSuccess, handleError('Error updating user'));
//        }
 
//        function DeleteByUserName(userName) {
//            return null;//$http.delete(baseUrl+'DeleteByUserName', {params:{"userName": userName }}).then(handleSuccess, handleError('Error deleting user'));
//        }
 
        // private functions
        	//error handeling angular way---- always a success unless internal server error/exception then its always an error
        		//angular issue, angular does not allow the error to be retrieved, so errors are always returned as successes then parsed.
//        function handleSuccess(res) {
//        	return res.data;
//        }
 
//        function handleError(error) {
//            return { success: false, message: error};
//        }
        
    }
 
})();