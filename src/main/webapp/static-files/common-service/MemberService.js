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
        service.ComparePasswordsForMember = ComparePasswordsForMember;
        service.DeleteMember = DeleteMember;
    	service.CreateUpdateAMember = CreateUpdateAMember;
        
        return service;
       
        
        function GetAListOfMembers(userName, companyP) {
            return $http.get(baseUrlMember+'GetAListOfMembers', {params:{"userName": userName, "companyP": companyP}}).then(handleSuccess, handleError('Error MemberService.getAListOfMembers'));
        }
        function GetMemberData(userName, member, companyP) {
            return $http.get(baseUrlMember+'GetMemberData', {params:{"userName": userName, "member":member, "companyP":companyP}}).then(handleSuccess, handleError('Error comparing passwords'));        	
        }
        function ComparePasswordsForMember(userName,member,memberPassword) {
            return $http.get(baseUrlMember+'ComparePasswordsForMember', {params:{"userName": userName, "member":member, "memberPassword":memberPassword}}).then(handleSuccess, handleError('Error comparing passwords'));        	
        }
        //hybrid service
        function DeleteMember(userName,member, companyP) {	
        	return $http.get(baseUrlHybrid+'DeleteMember', {params:{"userName": userName, "member":member, "companyP": companyP }}).then(handleSuccess, handleError('Error MemberService.DeleteAMember'));
        }
        
        function CreateUpdateAMember(userName,member,memberPassword, companyP, memberNameOld,memberPasswordOld) {
        	return $http.get(baseUrlMember+'CreateUpdateAMember', {params:{"userName": userName, "member":member, "password":memberPassword, "companyP": companyP, "memberNameOld":memberNameOld, "memberPasswordOld": memberPasswordOld}}).then(handleSuccess, handleError('Error MemberService.CreateAMember'));
        }        
        
 
        // private functions
 
        function handleSuccess(res) {
            return res.data;
        }
 
        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }

    }
 
})();