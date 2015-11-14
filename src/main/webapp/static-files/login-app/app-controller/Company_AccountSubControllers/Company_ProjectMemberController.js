(function () {
    'use strict';
 
    angular
        .module('app')
        .constant()
        .controller('Company_ProjectMemberController', Company_ProjectMemberController)
        .directive('ngFileUploadDirective', ngFileUploadDirective);
 
    Company_ProjectMemberController.$inject = ['AuthService','$scope','ProjectMemberService','MemberService','FileService'];
    function Company_ProjectMemberController(AuthService,$scope,ProjectMemberService,MemberService,FileService) {

    	//local area
    	var vm = this;
    	
    	//varibles
    	vm.projectMember = new GetProjectMembersHashmapByUserName();
    	vm.error = null;
    	vm.projectEditMemberDialog = false;
    	vm.projectEditMemberDialog_members = null;
    	vm.projectEditMemberDialog_project = null;
    	//functions
    	vm.GetProjectMembersHashmapByUserName = GetProjectMembersHashmapByUserName;
    	vm.RefreshElements = RefreshElements;
    	vm.DeleteAProject = DeleteAProject;
    	vm.DeleteAMemberFromAProject = DeleteAMemberFromAProject;
    	vm.CreateANewProject = CreateANewProject;
    	vm.GetUserName = GetUserName;
    	
    	//edit member dialog area
    	//functions
    	vm.projectEditMemberDialog_Toggle = projectEditMemberDialog_Toggle;
    	vm.projectEditMemberDialog_AddMember = projectEditMemberDialog_AddMember;
    	
    	//edit project dialog area
    	var pd = {};
    	vm.pd = pd;
    	//varibles
    	vm.pd.projectEditProjectDialog_projectName = null;
    	vm.pd.projectEditProjectDialog_dialogShow = false;
    	vm.pd.progress =0;
    	//functions
    	vm.pd.ProjectEditProjectDialog_Toggle = ProjectEditProjectDialog_Toggle;
    	vm.pd.UploadFileAProjectFile = UploadFileAProjectFile;
    	
    	
        //recieving message, page needed to be refreshed
        $scope.$on('refreshElementsEvent_child', function() { 
        	//alert("reieved on child");
        	RefreshElements();        
        });
    	
        //FileService events
        $scope.$on('FileService.progress', function(event,data) {
        	$scope.$apply(function(){
        		vm.pd.progress = data;
        		vm.pd.progress = Math.round(vm.pd.progress);
        	});
        });
        $scope.$on('FileService.complete', function(event,data) {
        	$scope.$apply(function(){
        		vm.pd.progress = data;
        		vm.pd.progress = Math.round(vm.pd.progress);
        	});
        });
//        $scope.$on('FileService.error', function(data) {
//        	@TODO
//        });
//        $scope.$on('FileService.abort', function(data) {
//        	@TODO
//        });
        
        
        //edit/add project information dialog area
        
        function ProjectEditProjectDialog_Toggle(project){
        	//functionality, file upload, delete file uploaded, display current model uploaded- with timestamp
        	vm.pd.projectEditProjectDialog_projectName = project;
        	vm.pd.projectEditProjectDialog_dialogShow = !vm.pd.projectEditProjectDialog_dialogShow;       	
        }

        function UploadFileAProjectFile(projectName,file){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
        	FileService.UploadFileAProjectFile(userName, projectName, file,pass);
        }
                
        
        //add member button dialog area
        function projectEditMemberDialog_AddMember (project, member){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			ProjectMemberService.CreateAMember(userName,project,member,pass) 
				.then(function (response) {
	                if (response.success) {			
	                	var databaseMembers = response.message;			                	
	                	RefreshElements();
	                	vm.projectEditMemberDialog_project = null;
	                } else {			                	
	                	vm.error = response.message;
	                	vm.projectEditMemberDialog_members = null;
	                }
				});	        	
        }
        
               
    	function projectEditMemberDialog_Toggle (project,members) { 
    		if(vm.projectEditMemberDialog){
    			vm.projectEditMemberDialog = false;
    		} else {
            	var pass = AuthService.GetPassword();
            	var userName = AuthService.GetUser();
    			MemberService.GetAListOfMembers(userName,pass) 
    				.then(function (response) {
		                if (response.success) {			
		                	var databaseMembers = response.message;			                	
		                	vm.projectEditMemberDialog_members = SubtractBSetFromASet(databaseMembers, members);
		                	vm.projectEditMemberDialog_project = project;
		                	vm.projectEditMemberDialog = true;
		                } else {			                	
		                	vm.error = response.message;
		                	vm.projectEditMemberDialog_members = null;
		                	vm.projectEditMemberDialog = false;
		                }
			        });
    		}
    	}
    	
    	//base dom functions
        function GetProjectMembersHashmapByUserName() {     	
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			ProjectMemberService.GetHashMapOfProjectAndMember(userName,pass) 
				.then(function (response) {
	                if (response.success) {			                   
	                    vm.error = null;
	                    vm.projectMember = response.message;
	                } else {			                	
	                	vm.error = response.message;
	                	vm.projectMember = null;
	                }
		        });	
        }
        
        function DeleteAProject (projectName){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			ProjectMemberService.DeleteAProject(userName,projectName,pass) 
				.then(function (response) {
	                if (response.success) {			                   
	                    vm.error = null;
	                    RefreshElements();
	                } else {
	                	 //alert(response);
	                	vm.error = response.message;
	                }
	                
		        });        	 	
        }
        
        function DeleteAMemberFromAProject (projectName, member){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			ProjectMemberService.DeleteAMemberFromAProject(userName,projectName,member,pass) 
				.then(function (response) {
	                if (response.success) {			                   
	                    vm.error = null;
	                    RefreshElements();
	                } else {			                	
	                	vm.error = response.message;
	                }
		        });      	       	 	
        }
        	
        function CreateANewProject (projectName){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
			ProjectMemberService.CreateANewProject(userName,projectName,pass) 
				.then(function (response) {
	                if (response.success) {			                   
	                    vm.error = null;
	                    RefreshElements();
	                } else {			                	
	                	vm.error = response.message;
	                }
		        });       	    	
        }

    	
        //private functions
        function RefreshElements(){
        	GetProjectMembersHashmapByUserName();     	
        }
        
        function SubtractBSetFromASet(aSet, bSet){
        	bSet.forEach(function(bItem){
        		var index = aSet.indexOf(bItem);
        		if (index > -1) {
        			aSet.splice(index, 1);
        		}
       		
        	});
        	return aSet;
        }
        

        return vm;
    }
    
    //sets the FileUploadDirective_file with the file
    ngFileUploadDirective.$inject = ['$document'];
    function ngFileUploadDirective($document){
    	  return {
    		    link: function(scope, element, attr) {
    		    	
    		    	element.on('change', function(event) {
    		    		event.preventDefault();  
    		    		
    		    		$document.on('change', function(){
    		    			var fileUploadDirective = {};
    		    			var file = element[0].files[0];  
    		    			fileUploadDirective.file = file;    		    			
    		    			scope.fileUploadDirective = fileUploadDirective;
    		    			
    		    		});
    		    	});
    		    }
    	  }
    }
    
    	  
})();












