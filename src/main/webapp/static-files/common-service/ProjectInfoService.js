(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('ProjectInfoService', ProjectInfoService);
 
    ProjectInfoService.$inject = ['$http'];
    function ProjectInfoService($http) {
    	
    	var baseUrl = '/ModelViewer/ProjectInfoService/'
    	
        var service = {};
    	
        service.ReadProjectInfo = ReadProjectInfo;
        service.CreateUpdateProjectInfo = CreateUpdateProjectInfo;
        
    	return service;

        function ReadProjectInfo(userName, userPass, projectName,memberName, memberPass) {
        	return $http.get(baseUrl+'ReadProjectInfo', {params:{"userName": userName, "userPass": userPass, "projectName": projectName,"memberName":memberName, "memberPass": memberPass}}).then(handleSuccess, handleError('Error ProjectInfoService.ReadStory'));
        } 
        
        function CreateUpdateProjectInfo(userName, userPass, projectName,memberName, memberPass, story) {
        	return $http.get(baseUrl+'CreateUpdateProjectInfo', {params:{"userName": userName, "userPass": userPass, "projectName": projectName,"memberName":memberName, "memberPass": memberPass, "story": story }}).then(handleSuccess, handleError('Error ProjectInfoService.CreateUpdateStory'));
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