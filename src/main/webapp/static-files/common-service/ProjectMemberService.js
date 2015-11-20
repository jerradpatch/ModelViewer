(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('ProjectMemberService', ProjectMemberService);
 
    ProjectMemberService.$inject = ['$http'];
    function ProjectMemberService($http) {
    	
    	var baseUrl = '/ModelViewer/ProjectMemberService/'
    	
        var service = {};
 
    	service.baseUrl = baseUrl;
        service.GetHashMapOfProjectAndMember = GetHashMapOfProjectAndMember;
        service.GetAListOfMembers = GetAListOfMembers;
        service.DeleteAProject = DeleteAProject;
        service.DeleteAMemberFromAProject = DeleteAMemberFromAProject;
        service.CreateANewProject = CreateANewProject;
        service.CreateAMember = CreateAMember;
        service.GetProjectsMemberIsAPartOf = GetProjectsMemberIsAPartOf;
        
        return service;
       
        function GetProjectsMemberIsAPartOf(userName,member, companyMemberP){
            return $http.get(baseUrl+'GetProjectsMemberIsAPartOf', {params:{"userName": userName,"member": member, "companyP": companyMemberP }}).then(handleSuccess, handleError('Error UserService.GetHashMapOfProjectAndMember'));       	
        }
        
        function GetHashMapOfProjectAndMember(userName, companyP) {
            return $http.get(baseUrl+'GetHashMapOfProjectAndMember', {params:{"userName": userName, "companyP": companyP }}).then(handleSuccess, handleError('Error UserService.GetHashMapOfProjectAndMember'));
        }
        
        function GetAListOfMembers(userName, companyP) {
            return $http.get(baseUrl+'getAListOfMembers', {params:{"userName": userName, "companyP": companyP }}).then(handleSuccess, handleError('Error UserService.getAListOfMembers'));
        }
        
        function DeleteAProject(userName,projectName, companyP) {
        	return $http.get(baseUrl+'DeleteAProject', {params:{"userName": userName, "projectName":projectName, "companyP": companyP }}).then(handleSuccess, handleError('Error UserService.DeleteAProject'));
        }
        function DeleteAMemberFromAProject(userName,projectName, member, companyP) {
        	return $http.get(baseUrl+'DeleteAMemberFromAProject', {params:{"userName": userName, "projectName":projectName, "member":member, "companyP": companyP }}).then(handleSuccess, handleError('Error UserService.DeleteAMember'));
        }
        
        function CreateANewProject(userName,projectName, companyP) {
        	return $http.get(baseUrl+'CreateANewProject', {params:{"userName": userName, "projectName":projectName, "companyP": companyP }}).then(handleSuccess, handleError('Error UserService.CreateANewProject'));
        }
        function CreateAMember(userName,projectName,member, companyP) {
        	return $http.get(baseUrl+'CreateAMember', {params:{"userName": userName,"projectName": projectName,"member":member, "companyP": companyP }}).then(handleSuccess, handleError('Error UserService.CreateAMember'));
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