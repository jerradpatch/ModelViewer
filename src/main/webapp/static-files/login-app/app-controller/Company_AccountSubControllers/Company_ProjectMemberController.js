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
       // .directive('ngFileUploadDirective', ngFileUploadDirective)
        .run(function ($rootScope, CONSTANTS_Company_ProjectMemberController) {
        	$rootScope.CONSTANTS_Company_ProjectMemberController = CONSTANTS_Company_ProjectMemberController;
        });
 
    Company_ProjectMemberController.$inject = ['AuthService','$rootScope','$scope','ProjectMemberService','MemberService','FileService','Upload'];
    function Company_ProjectMemberController(AuthService,$rootScope,$scope,ProjectMemberService,MemberService,FileService, Upload) {

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
    	vm.pd.projectMetaData = {}; 
    	// {[{Name:"groupName",[{Name:"ItemName","type":jpeg,jpg..,"status":"complete,uploading,queued","progress":0-100},...]},....]}
    	//if(vm.pd.projectEditProjectDialog_dialogShow){
    	//	GetAllFileMetaData();
    	//}
    	vm.pd.DeleteFileFromProject = DeleteFileFromProject;
    	
    	
    	//vm.pd.progress = 0;

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
        		var fileObj = findFileIn_projectMetaData(data.projectName, data.fileName);
        		if(fileObj != null){
    				fileObj.status = "uploading";
    				fileObj.progress = Math.round(data.progress);
        		}      		
        	});
        });
        
        // need to write an update function, to update the nested values
        $scope.$on('FileService.complete', function(event,data) {
        	$scope.$apply(function(){
	    		var fileObj = findFileIn_projectMetaData(data.projectName, data.fileName);
	    		//alert(JSON.stringify(fileObj));
	    		if(fileObj != null){
					fileObj.status = "complete";
					fileObj.progress = 100;
					//alert(JSON.stringify(fileObj));
					//updateValueIn_projectMetaData(fileName,"status",fileObj);
	    		}
        	});
        });        
        $scope.$on('FileService.abort', function(event,data) {
        	$scope.$apply(function(){
        		if(data.projectName != null && data.fileName != null){
        			deleteAFileFrom_projectMetaData_list(data.projectName,data.fileName);
        		}
        	});
        });
        
        $scope.$watch('files', function () {
        	if($scope.files != null && vm.pd.projectEditProjectDialog_projectName != null){
	        	var files = $scope.files;
	        	//add them to a group
	        	files.forEach(function(file) {
	        		addAFileTo_projectMetaData_list(vm.pd.projectEditProjectDialog_projectName, file.name);
	        	});
	        	//set everythign to queued
	        	files.forEach(function(file) {
	        		var fileObj = findFileIn_projectMetaData(file.name);
	        		if(fileObj != null){
	        			fileObj.status = "queued";
	        			fileObj.progress = 0;
	        		}
	        	});
	        	//set individule files as they are being uploaded
	        	files.forEach(function(file) {
	        		var fileObj = findFileIn_projectMetaData(vm.pd.projectEditProjectDialog_projectName, file.name);
	        		if(fileObj != null){
        				fileObj.status = "uploading";   
	        			fileObj.progress = 0;
	        		}
	
	        		UploadFileAProjectFile(vm.pd.projectEditProjectDialog_projectName,file);
	        	});
        	}
        });
        $scope.$watch('file', function () {
            if ($scope.file != null) {
            	$scope.files = [$scope.file]; 
            }
        });
        

        function onError(message){
        	$rootScope.$broadcast("errorGlobal", message); 
        }
        

        
        //edit/add project information dialog area////////////////////////////////////////////////////////////////////////////////////////////  /    
        function ProjectEditProjectDialog_Toggle(project){
        	//functionality, file upload, delete file uploaded, display current model uploaded- with timestamp
        	vm.pd.projectEditProjectDialog_projectName = project;
        	vm.pd.projectEditProjectDialog_dialogShow = !vm.pd.projectEditProjectDialog_dialogShow;
        	if(vm.pd.projectEditProjectDialog_dialogShow){
        		GetAllFileMetaData();
        	}
        }
        function abortFileAProjectFile(projectName,fileName){
        	$rootScope.$broadcast("FileService.recieve.abort", {"projectName":projectName,"fileName":fileName}); 
        }
        //add a new box
        function addAFileTo_projectMetaData_list(projectName, fileName){
        	
        	var category = fileCategory(fileName);
        	if(category == null){
        		return; //could set an error here for wrong file type, maybe validation done at an upper layer
        	}
        	//if var not set, somthign is wrong should have been set, since the dialog is open, return
        	if(vm.pd.projectMetaData[projectName] == null){
        		return;
        	}
        	if(vm.pd.projectMetaData[projectName].Images == null || vm.pd.projectMetaData[projectName].Models == null){
        		return;
        	}
        	if(category == "image"){
        		var Images = vm.pd.projectMetaData[projectName].Images;
        		if(findFileByName(Images, fileName) == null){
        			Images.push({
        			"name":fileName,
        			"type":fileType(fileName)});
        		}

        	} else {
        		//("model")
        		var Models = vm.pd.projectMetaData[projectName].Models;
        		if(findFileByName(Models, fileName) == null){
        			Models.push({
        			"name":fileName,
        			"type":fileType(fileName)});
        		}
        	}
        }
        function deleteAFileFrom_projectMetaData_list(projectName, fileName){
        	var category = fileCategory(fileName);
        	if(category == null){
        		return; //could set an error here for wrong file type, maybe validation done at an upper layer
        	}
        	//if var not set, somthign is wrong should have been set, since the dialog is open, return
        	if(vm.pd.projectMetaData[projectName] == null){
        		return;
        	}
        	if(vm.pd.projectMetaData[projectName].Images == null && vm.pd.projectMetaData[projectName].Models == null){
        		return;
        	}
        	if(category == "image"){
        		var Images = vm.pd.projectMetaData[projectName].Images;
        		deleteFileByName(Images, fileName);
        	} else {
        		var Models = vm.pd.projectMetaData[projectName].Models;
        		deleteFileByName(Models, fileName);
        	}       	
        }
        
        function updateValueIn_projectMetaData(fileName, propertyName, value){
        	if(vm.pd.projectMetaData == null){
        		return false;
        	}
        	if(fileName == null || updateObj == null){
        		return false;
        	}
        	var val = vm.pd.projectMetaData;
        	for (var group in val){
				if (val.hasOwnProperty(group)) {
					for (var item in val[group]){
						if(item.name == fileName){
							vm.pd.projectMetaData[group][item].name = value;
						}
					}
        		}
        	}
        	return false;
        }
        
        function findFileIn_projectMetaData(projectName, fileName){
        	if(vm.pd.projectMetaData[projectName] == null){
        		return null;
        	}
        	var val = vm.pd.projectMetaData[projectName];
        	for (var group in val){
				if (val.hasOwnProperty(group)) {
	        		var file = findFileByName(val[group], fileName);
	        		if(file != null){
	        			return file;
	        		}
        		}
        	}
        	return null;
        }
        //TODO test this
        function deleteFileByName(ObjList, fileName){
        	var val = null;
        	for(var i = 0; i < ObjList.length; ++i){
        		var item = ObjList[i];
    			if('name' in item){
    				var name = item.name;     
    				if(name != null && fileName != null) {
    					if(name == fileName){
    						//alert(JSON.stringify(item));
    						ObjList.splice(i,1);
    					}
    				}
    			}
    		}
        }
        function findFileByName(ObjList, fileName){
        	var val = null;
        	ObjList.forEach(function(item){
    			if('name' in item){
    				var name = item.name;     
    				if(name != null && fileName != null) {
    					if(name == fileName){
    						//alert(JSON.stringify(item));
    						val = item;
    					}
    				}
    			}
    		});
        	return val;
        }
        //get item cate
        function fileCategory(fileName){
        	var partsArray = fileName.split('.');
        	var sizeArray = partsArray.length;
        	if(sizeArray != 2){
        		return null;
        	} 
        	var type = partsArray[1];
        	if(type == "jpg" || type == "jpeg" || type == "png" ){
        		return "image";
        	} else {
        		return "model";
        	}
        	
        }
        //get item type
        function fileType(fileName){
        	var partsArray = fileName.split('.');
        	var sizeArray = partsArray.length;
        	if(sizeArray != 2){
        		return null;
        	} 
        	var type = sizeArray[1];
        	if(type == "jpg" ){
        		return "jpg";
        	} else if ( type == "jpeg"){
        		return "jpeg";
        	} else if (type == "png") {
        		return "png";
        	} else if (type == "unity3d"){
        	   return "unity3d";
        	} else {
        		 return null;
        	}
        	
        }
        function setFileUploading(fileName){
        	//find that file object varible se equal to current file being uploaded
        }
        function UploadFileAProjectFile(projectName,file){
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
        	FileService.UploadFileAProjectFile(userName, projectName, pass, file);
        }
        function DeleteFileFromProject(fileName){
        	if(fileName == null){
        		return;
        	}
        	if(vm.pd.projectEditProjectDialog_projectName == null){
        		return;
        	}
        	var pass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
        	var projectName = vm.pd.projectEditProjectDialog_projectName;
        	
        	var fileObj = findFileIn_projectMetaData(projectName,fileName);
        	if(fileObj == null || fileObj.status == null || fileObj.status == "complete"){
	        	FileService.DeleteFile(userName,projectName,fileName,pass)
	        	.then(function (response) {
	                if (response.success) {
	                	//if success then also remove it from the current local project file list
	                	//vm.pd.projectMetaData[projectName] = response.message;	
	                	deleteAFileFrom_projectMetaData_list(projectName,fileName);
	                	
	                } else {			                	
	                	vm.error = response.message;
	                	onError(response.message);
	                }
				});	
        	} else if (fileObj.status == "queued"){
        		deleteAFileFrom_projectMetaData_list(projectName,fileName);
        	} else if (fileObj.status == "uploading"){
        		abortFileAProjectFile(projectName, fileName);
        	}
        }
        function GetAllFileMetaData(){
        	if(vm.pd.projectEditProjectDialog_projectName != null){
        		var projectName = vm.pd.projectEditProjectDialog_projectName;
        		var uPass = AuthService.GetPassword();
            	var userName = AuthService.GetUser();
            	
            	if(projectName in vm.pd.projectMetaData && vm.pd.projectMetaData[projectName] != null){
            		return;
            	}
            	
        		FileService.GetAllFileMetaData(userName,projectName,uPass)
        		.then(function (response) {
	                if (response.success) {
	                	vm.pd.projectMetaData[projectName] = response.message;			                	
	                } else {			                	
	                	vm.error = response.message;
	                	onError(response.message);
	                	vm.pd.projectMetaData[projectName] = null;
	                }
				});	 
        	}
        }    
        ///add members to a project dialog //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	                	vm.projectMember = {};
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
//    ngFileUploadDirective.$inject = ['$document'];
//    function ngFileUploadDirective($document){
//    	  return {
//    		    link: function(scope, element, attr) {
//    		    	
//    		    	element.on('change', function(event) {
//    		    		event.preventDefault();  
//    		    		
//    		    		$document.on('change', function(){
//    		    			var fileUploadDirective = {};
//    		    			var file = element[0].files[0];  
//    		    			fileUploadDirective.file = file;    		    			
//    		    			scope.fileUploadDirective = fileUploadDirective;
//    		    			
//    		    		});
//    		    	});
//    		    }
//    	  }
//    }
    
    	  
})();












