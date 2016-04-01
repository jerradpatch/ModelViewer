(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('MemberService', MemberService);
 
    MemberService.$inject = ['$http', 'AuthService'];
    function MemberService($http, AuthService) {
    	
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
        			baseUrlMember+"createMember",  
        			{params:{"memberModel": memberModel}}).then(function(memberModelRet){
        				if(memberModelRet.uuid){
        					service.data[memberModelRet.uuid] = memberModelRet;
        				} else {
        					console.log("createMember no uuid");
        				}
        			});     	
        }
        function readMemberList(memberModel) {
        	return $http.post(
        			baseUrlMember+"readMemberList",  
        			{params:{"memberModel": memberModel}}).then(function(memberModelList){
        				memberModelList.forEach(function(memberModelRet){
            				if(memberModelRet.uuid){
            					if(service.data[memberModelRet.uuid]){
            						angular.copy(memberModelRet, service.data[memberModelRet.uuid]);
            					} else {
            						service.data[memberModelRet.uuid] = memberModelRet;
            					}
            				} else {
            					console.log("createMember no uuid");
            				}
        				});
        			});	
        }
        function readMember(memberModel) {
        	return $http.post(
        			baseUrlMember+"readMember",  
        			{params:{"memberModel": memberModel}}).then(function(memberModelRet){
    				    if(memberModelRet.uuid){
        					if(service.data[memberModelRet.uuid]){
        						angular.copy(memberModelRet, service.data[memberModelRet.uuid]);
        					} else {
        						service.data[memberModelRet.uuid] = memberModelRet;
        					}
        				} else {
        					console.log("createMember no uuid");
        				}
        			});     	
        }
        function updateMember(memberModel) {
        	return $http.post(
        			baseUrlMember+"updateMember",  
        			{params:{"memberModel": memberModel}}).then(function(){
        				service.data[memberModel.uuid] = memberModel;
        			});        	
        }
        function deleteAMember(memberModel) {
        	return $http.post(
        			baseUrlMember+"deleteAMember",  
        			{params:{"memberModel": memberModel}}).then(function(){
        				var index = service.data.indexOf(memberModel.uuid);
        				service.data.splice(index,1);
        			});
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