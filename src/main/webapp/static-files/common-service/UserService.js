(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$http','AuthService','$q','MemberService'];
    function UserService($http, AuthService, $q, MemberService) {
    	
    	var baseUrl = '/ModelViewer/UserService/';
    	
        var service = {};
        
    	service.baseUrl = baseUrl;
    	service.newUserModel = newUserModel;
    	service.login = login;
        service.createUser = createUser;
        service.readUser = readUser;
        service.updateUser = updateUser;
        service.readMemberList = readMemberList;
        service.data = {};
        return service;
 
	    function login(userModel) {
	    	return $http.post(baseUrl+'login', JSON.stringify(userModel)).then(function(userModelRet){
	    		var deferred = $q.defer();
	    		if(userModelRet.uuid){
	    			userModelRet["password"] = userModel.password;
					service.data[userModelRet.uuid] = userModelRet;
					deferred.resolve(userModelRet);
				} else {
					console.log("login user no uuid");
					deferred.reject();
				}
	    		return deferred.promise;
			});
	    }
	    function createUser(userModel) {
	    	return $http.post(
	    			baseUrl+'createUser', 
	    			userModel,
	    			{headers: {'Content-Type': 'application/json'}}).then(function(userModelRet){
				var deferred = $q.defer();
	    		if(userModelRet.uuid){
	    			userModelRet["password"] = userModel.password;
					service.data[userModelRet.uuid] = userModelRet;
					deferred.resolve(userModelRet);
				} else {
					console.log("login user no uuid");
					deferred.reject();
				}
	    		return deferred.promise;				
			});
	    }
	    function readUser(userModel) {
	    	return $http.post(baseUrl+'readUser', 
	    			{params:{"userModel": userModel}}).then(function(userModelRet){
				var deferred = $q.defer();
	    		if(userModelRet.uuid){
	    			createUpdateUserModel(userModelRet);
					deferred.resolve(userModelRet);
				} else {
					console.log("login user no uuid");
					deferred.reject();
				}
	    		return deferred.promise;	
			});  
	    }
	    function updateUser(userModel) {
	    	return $http.post(baseUrl+'updateUser', 
	    			{params:{"userModel": userModel}}).then(function(userModelRet){
				var deferred = $q.defer();
	    		if(userModelRet.uuid){
	    			createUpdateUserModel(userModelRet);
					deferred.resolve(userModelRet);
				} else {
					console.log("login user no uuid");
					deferred.reject();
				}
	    		return deferred.promise;
			});
	    }
        function readMemberList(userModel) { //read member list from the user service
        	return $http.post(
        			baseUrl+"readMemberList",  
        			{"userModel": userModel}).then(function(memberModelList){
				var deferred = $q.defer();
				memberModelList.forEach(function(model){
					MemberService.createUpdateModel(model);
				});
				deferred.resolve(memberModelList);
	    		return deferred.promise;	 
			});	
        }
	    
//////////////////////////////////////////////////////non service hitting functions
        function newUserModel(args){
        	return {
        		"userName": args.userName,
        		"password": args.password,
        		"email": args.email      		
        	};
        }       
        function createUpdateUserModel(newUserModel){
        	if(newUserModel.uuid != null){
            	var updateUserModel = service.data[newUserModel.uuid];
            	if(updateUserModel != null){
	            	for(var key in newUserModel){
	            		updateUserModel[key] = newUserModel[key];
	            	}
            	} else {
            		service.data[newUserModel.uuid] = newUserModel;
            	}
        	}
        }

//        function ComparePasswordsForUser(userName, password) {
//            return $http.get(baseUrl+'ComparePasswordsForUser', {params:{"userName": userName,"password": password }}).then(handleSuccess, handleError("No response from pasword compare"));
//        }
 
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