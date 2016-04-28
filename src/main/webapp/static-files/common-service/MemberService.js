(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('MemberService', MemberService);
 
    MemberService.$inject = ['$q','$http', 'AuthService'];
    function MemberService($q, $http, AuthService) {
    	
    	var baseUrlMember = '/ModelViewer/MemberService/';
//    	var baseUrlHybrid = '/ModelViewer/MemberAndProjectMemberHybridService/';
    	
        var service = {};
 
    	service.baseUrlMember = baseUrlMember;
//    	service.baseUrlHybrid = baseUrlHybrid;
    	
        service.createMember = createMember;
        service.readMemberList = readMemberList;
        service.readMember = readMember;
    	service.updateMember = updateMember;
    	service.deleteAMember = deleteAMember;
    	service.newMemberModel = newMemberModel;
    	service.data = {};
    	
        return service;
       
	    function login(userName,memberName,password) {
	    	var userModel = UserService.newUserModel(userName);
	    	var memberModel = MemberService.newUserModel(memberName,password);
	    	memberModel['userModel'] = userModel;
	    	return $http.get(
	    			baseUrl+'login', 
	    			{params:{"memberModel": memberModel}}).then(function(memberModelRet){
	    				AuthService.setMemberModel(memberModelRet);
	    				return memberModelRet;
	    			});
	    }
        function createMember(memberModel) {
        	return $http.post(
        			baseUrlMember+'createMember', 
	    			{"memberModel": memberModel},
        			{headers: {'Content-Type': 'application/json'}}).then(function(memberModelRet){
        				var deferred = $q.defer();
        				createUpdateModel(memberModel, memberModelRet);
    					deferred.resolve(memberModelRet);
        	    		return deferred.promise;	
        			});     	
        }
        function readMemberList(memberModel) {
        	return $http(
        				createMemberPostObj("readMemberList", memberModel)
        			).then(function(memberModelList){
        				var deferred = $q.defer();
        				memberModelList.forEach(function(memberModelRet){
            				createUpdateModel(memberModel, memberModelRet);
        				});
        				deferred.resolve(memberModelList);
        				return deferred.promise;	
        			});	
        }
        function readMember(memberModel) {
        	return $http.post(
        			baseUrlMember+"readMember",  
        			{"memberModel": memberModel},
        			{headers: {'Content-Type': 'application/json'}}).then(function(memberModelRet){
        				var deferred = $q.defer();
        				createUpdateModel(memberModel, memberModelRet);
    					deferred.resolve(memberModelRet);
        	    		return deferred.promise;	
        			});     	
        }
        function updateMember(memberModel) {
        	return $http.post(
        			baseUrlMember+"updateMember",  
        			{"memberModel": memberModel},
        			{headers: {'Content-Type': 'application/json'}}).then(function(){
        				var deferred = $q.defer();
        				createUpdateModel(memberModel, memberModelRet);
    					deferred.resolve(memberModelRet);
        	    		return deferred.promise;	
        			});        	
        }
        function deleteAMember(memberModel) {
        	return $http.post(
        			baseUrlMember+"deleteAMember",  
        			{"memberModel": memberModel},
        			{headers: {'Content-Type': 'application/json'}}).then(function(){
        				if(memberModel.uuid != null){
        					var index = service.data.indexOf(memberModel.uuid);
        					service.data.splice(index,1);
        				}
        			});
        }
        
        //////////////////////not service hitting functions
        function newMemberModel(args){
        	return {
        		"memberName": args.memberName,
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
        
        function createMemberPostObj (endpointName, model) {
        	return {
        		  method: 'POST',
        		  url: baseUrlMember+endpointName,	
        		  data: {"memberModel": model},
        		  headers: {'Content-Type': 'application/json'}
        	}
        	
        }
        
        
        
//        function GetAListOfMembers(userName, companyP) {
//            return $http.get(baseUrlMember+'GetAListOfMembers', {params:{"userName": userName, "companyP": companyP}}).then(handleSuccess, handleError('Error MemberService.getAListOfMembers'));
//        }
//        function GetMemberData(userName, member, companyP) {
//            return $http.get(baseUrlMember+'GetMemberData', {params:{"userName": userName, "member":member, "companyP":companyP}}).then(handleSuccess, handleError('Error comparing passwords'));        	
//        }
//        function ComparePasswordsForMember(userName,member,memberPassword) {
//            return $http.get(baseUrlMember+'ComparePasswordsForMember', {params:{"userName": userName, "member":member, "memberPassword":memberPassword}}).then(handleSuccess, handleError('Error comparing passwords'));        	
//        }
//        //hybrid service
//        function DeleteMember(userName,member, companyP) {	
//        	return $http.get(baseUrlHybrid+'DeleteMember', {params:{"userName": userName, "member":member, "companyP": companyP }}).then(handleSuccess, handleError('Error MemberService.DeleteAMember'));
//        }
//        
//        function CreateUpdateAMember(userName,member,memberPassword, companyP, memberNameOld,memberPasswordOld) {
//        	return $http.get(baseUrlMember+'CreateUpdateAMember', {params:{"userName": userName, "member":member, "password":memberPassword, "companyP": companyP, "memberNameOld":memberNameOld, "memberPasswordOld": memberPasswordOld}}).then(handleSuccess, handleError('Error MemberService.CreateAMember'));
//        }        
        
 
        // private functions
 
//        function handleSuccess(res) {
//            return res.data;
//        }
// 
//        function handleError(error) {
//            return function () {
//                return { success: false, message: error };
//            };
//        }

    }
 
})();