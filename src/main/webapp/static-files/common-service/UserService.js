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
	    	return $http(
	    			createUserPostObj('login',userModel)
	    	).then(function(userModelRet){
	    		var deferred = $q.defer();
				createUpdateModel(userModel, userModelRet);
				deferred.resolve(userModelRet);
	    		return deferred.promise;
			});
	    }
	    function createUser(userModel) {
	    	return $http(
	    			{
	          		  method: 'POST',
	          		  url: baseUrl+'createUser',	
	          		  data: userModel,
	          		  headers: {'Content-Type': 'application/json'}
	    			}
	    	).then(function(userModelRet){
				var deferred = $q.defer();
				createUpdateModel(userModel, userModelRet);
				deferred.resolve(userModelRet);
	    		return deferred.promise;				
			});
	    }
	    function readUser(userModel) {
	    	return $http(
	    			createUserPostObj('readUser',userModel)
	    	).then(function(userModelRet){
				var deferred = $q.defer();
				createUpdateModel(userModel, userModelRet);
				deferred.resolve(userModelRet);
	    		return deferred.promise;
			});  
	    }
	    function updateUser(userModel) {
	    	return $http(
	    			createUserPostObj('updateUser',userModel)
			).then(function(userModelRet){
				var deferred = $q.defer();
				createUpdateModel(userModel, userModelRet);
				deferred.resolve(userModelRet);
	    		return deferred.promise;
			});
	    }
        function readMemberList(userModel) { //read member list from the user service
        	return $http(
	    			createUserPostObj('readMemberList',userModel)
			).then(function(memberModelList){
				var deferred = $q.defer();
				memberModelList.forEach(function(model){
					MemberService.createUpdateModel(null,model);
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
        function createUpdateModel(oldModel, newModel){
        	if(newModel.uuid != null){
            	var updateModel = service.data[newModel.uuid];
            	if(updateModel != null){
	            	for(var key in newModel){
	            		updateModel[key] = newModel[key];
	            	}
            	} else {
            		if(oldModel != null && oldModel.password != null){
            			newModel["password"] = oldModel.password;
            		}
            		service.data[newModel.uuid] = newModel;
            	}
        	}
        }
        function createUserPostObj (endpointName, model) {
        	return {
        		  method: 'POST',
        		  url: baseUrl+endpointName,	
        		  data: {"userModel": model},
        		  headers: {'Content-Type': 'application/json'}
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