(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('MemberService', MemberService);
 
    MemberService.$inject = ['$http'];
    function MemberService($http) {
    	
    	var baseUrlMember = '/ModelViewer/MemberService/';
    	var baseUrlHybrid = '/ModelViewer/MemberAndProjectMemberHybridService/';
    	
        var service = {};
 
    	service.baseUrlMember = baseUrlMember;
    	service.baseUrlHybrid = baseUrlHybrid;
    	
        service.GetAListOfMembers = GetAListOfMembers;
        service.GetMemberData = GetMemberData;
        service.DeleteMember = DeleteMember;
    	service.CreateUpdateAMember = CreateUpdateAMember;
        
        return service;
       
        
        function GetAListOfMembers(userName) {
            return $http.get(baseUrlMember+'GetAListOfMembers', {params:{"userName": userName }}).then(handleSuccess, handleError('Error MemberService.getAListOfMembers'));
        }
        function GetMemberData(userName,member) {
            return $http.get(baseUrlMember+'GetMemberData', {params:{"userName": userName, "member":member }}).then(handleSuccess, handleError('Error MemberService.GetMemberData'));        	
        }
        //hybrid service
        function DeleteMember(userName,member) {
        	return $http.get(baseUrlHybrid+'DeleteMember', {params:{"userName": userName, "member":member }}).then(handleSuccess, handleError('Error MemberService.DeleteAMember'));
        }
        
        function CreateUpdateAMember(userName,member,password) {
        	return $http.get(baseUrlMember+'CreateUpdateAMember', {params:{"userName": userName, "member":member, "password":password }}).then(handleSuccess, handleError('Error MemberService.CreateAMember'));
        }        
        
 
        // private functions
 
        function handleSuccess(res) {
            return { success: true, message: res.data };
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }

    }
 
})();