(function () {
    'use strict';
 
    angular
        .module('app')
        .constant("CONSTANTS_Company_ProjectMemberController", {
        	"Company_ProjectMemberController" : {
        	"PROJECTS" : {
        		"NEW_PROJECT_INPUT" : {
        			"MAXIMUM_LENGTH": 30,
        			"MINIMUM_LENGTH": 3,
        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"}
        		}
        	}
         })
        .controller('Company_ProjectMemberController', Company_ProjectMemberController)
        .directive('ngFileUploadDirective', ngFileUploadDirective)
        .run(function ($rootScope, CONSTANTS_Company_ProjectMemberController) {
        	$rootScope.CONSTANTS_Company_ProjectMemberController = CONSTANTS_Company_ProjectMemberController;
        });
 
    Company_ProjectMemberController.$inject = ['AuthService','$scope','ProjectMemberService','MemberService','FileService'];
    function Company_ProjectMemberController(AuthService,$scope,ProjectMemberService,MemberService,FileService) {

    	//local area//////////////
    	var vm = this;
    	
    	//varibles//////////////
    	vm.projectMember = new GetProjectMembersHashmapByUserName();
    	vm.error = null;
    	vm.projectEditMemberDialog = false;
    	vm.projectEditMemberDialog_members = null;
    	vm.projectEditMemberDialog_project = null;
    	//functions///////////
    	vm.GetProjectMembersHashmapByUserName = GetProjectMembersHashmapByUserName;
    	vm.RefreshElements = RefreshElements;
    	vm.DeleteAProject = DeleteAProject;
    	vm.DeleteAMemberFromAProject = DeleteAMemberFromAProject;
    	vm.CreateANewProject = CreateANewProject;
    	
    	//edit member dialog area//////
    	//functions
    	vm.ProjectEditMemberDialog_Toggle = ProjectEditMemberDialog_Toggle;
    	vm.ProjectEditMemberDialog_AddMember = ProjectEditMemberDialog_AddMember;
    	
    	//edit project dialog area/////////
    	var pd = {};
    	vm.pd = pd;

    	vm.pd.projectEditProjectDialog_projectName = null;
    	vm.pd.projectEditProjectDialog_dialogShow = false;
    	vm.pd.progress =0;

    	vm.pd.ProjectEditProjectDialog_Toggle = ProjectEditProjectDialog_Toggle;
    	vm.pd.UploadFileAProjectFile = UploadFileAProjectFile;
    	
    	
    	
        //recieving message, page needed to be refreshed//////////////////////
        $scope.$on('refreshElementsEvent_child', function() { 
        	//alert("reieved on child");
        	RefreshElements();        
        });
    	
        //FileService events////////////////////////////////////
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

        function onError(message){
        	$rootScope.$broadcast("errorGlobal", message); 
        }
        
        
        //edit/add project information dialog area/////////////////////////       
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
        
        ///add members to a project dialog ////////////////////////////
        function ProjectEditMemberDialog_AddMember (project, member){
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
	                	onError(response.message);
	                	vm.projectEditMemberDialog_members = null;
	                }
				});	        	
        }
        
               
    	function ProjectEditMemberDialog_Toggle (project,members) { 
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
		                	onError(response.message);
		                	vm.projectEditMemberDialog_members = null;
		                	vm.projectEditMemberDialog = false;
		                }
			        });
    		}
    	}
    	
    	//base functions//////////////////
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
	                	onError(response.message);
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
	                	onError(response.message);
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
	                	onError(response.message);
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
	                	onError(response.message);
	                	vm.error = response.message;
	                }
		        });       	    	
        }

    	
        //private functions/////////////////////////
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












