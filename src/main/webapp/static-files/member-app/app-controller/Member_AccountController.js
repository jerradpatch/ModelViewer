(function () {
    'use strict';
 
    angular
        .module('app.member')
        .constant("CONSTANTS_Member_AccountController", {
        	"Member_AccountController" : {
        	"IMAGE_BASE_URL" : "FileService/GetFileAProjectFile"    		
        	}
         })
        .controller('Member_AccountController', Member_AccountController)
        .run(function ($rootScope, CONSTANTS_Member_AccountController) {
        	$rootScope.CONSTANTS_Member_AccountController = CONSTANTS_Member_AccountController;
        });
 
    Member_AccountController.$inject = ['AuthService','$location','ProjectMemberService','FileService','$cookieStore','$log'];
    function Member_AccountController(AuthService,$location,ProjectMemberService, FileService,$cookieStore,$log) {
    	
    	var projectPage = "/Member_ProjectView";
    	
    	var vm = this;

        vm.options2 = {
            visible: 7,
            perspective: 35,
            startSlide: 0,
            border: 1,
            dir: 'ltr',
            width: 320,
            height: 468,
            space: 350,
            controls: true
        };

  //      vm.removeSlide = removeSlide;
  //      vm.addSlide = addSlide;
        vm.selectedClick = selectedClick;
  //      vm.slideChanged = slideChanged;
 //       vm.beforeChange = beforeChange;
 //       vm.lastSlide = lastSlide;

        
    	vm.gotoProjectPage = gotoProjectPage;
    	
    	
    	vm.projectsMemberIsAPartOf = {};
    	GetProjectsMemberIsAPartOf();
    	
    	vm.GetProjectsMemberIsAPartOf = GetProjectsMemberIsAPartOf;
    	vm.GetAllFileMetaDataForAProject = GetAllFileMetaDataForAProject;
    	vm.GetResource = GetResource;
    	vm.getCompanyName = getCompanyName;
    	
    	var md = {};
    	vm.md = md;
    	vm.md.ToggleAndSetModel = ToggleAndSetModel;
    	vm.md.modelVisible = false;
    	vm.md.modelToLoad = null;
    	
    	
        // ANY HTML
        //===================================
        vm.slides2 = null;
        	
        function initSlides(P){
        	//returns an array
        	var returnArray = [];
        	var keys = Object.keys(P);
        	keys.forEach(function(projectName){
    			var aProjectsResources = P[projectName];
    			var imageSelected = null;
    			var imageCount = 0;
    			if(aProjectsResources["Images"] && aProjectsResources["Images"].length!=0){        				
    				aProjectsResources["Images"].forEach(function(image){
    					if(image.name.startsWith("main")){
    						imageSelected = image.url;
    					}
    				});
    				if(imageSelected == null){
    					imageSelected = aProjectsResources["Images"][0].url;
    				}
    				imageCount = aProjectsResources["Images"].length;
    			}
				if(imageSelected == null){
					imageSelected = "/ModelViewer/static-files/modules/logo.png"; //no image given
				}
				var modelCount = 0;
				if(aProjectsResources["Models"] && aProjectsResources["Models"].length!=0){
					modelCount = aProjectsResources["Models"].length;
				}
				returnArray.push({"projectName":projectName,"imageUrl":imageSelected, "imageCount":imageCount, "modelCount":modelCount});
        	});
        	vm.slides2 = returnArray;
        };


    	//-----------------------slide callbacks start-------------------------
//        function lastSlide(index) {
//            $log.log('Last Slide Selected callback triggered. \n == Slide index is: ' + index + ' ==');
//        }
//
//        function beforeChange(index) {
//            $log.log('Before Slide Change callback triggered. \n == Slide index is: ' + index + ' ==');
//        }

        function selectedClick(index) {
            //$log.log('Selected Slide Clicked callback triggered. \n == Slide index is: ' + index + ' ==');
        	var projectName = vm.slides2[index]["projectName"];
        	gotoProjectPage(projectName);
        }

//        function slideChanged(index) {
//            $log.log('Slide Changed callback triggered. \n == Slide index is: ' + index + ' ==');
//        }


//        function addSlide(slide, array) {
//            array.push(slide);
//        }
//
//        function removeSlide(index, array) {
//            array.splice(array.indexOf(array[index]), 1);
//        }
        
      //-----------------------slide callbacks end-------------------------    	
    	function gotoProjectPage (projectName){
    		$cookieStore.put("Member_ProjectController.currentProject",projectName);
    		$location.path(projectPage);
    	}
    	
    	function ToggleAndSetModel(projectName){    
    		if(vm.md.modelVisible){
    			vm.md.modelToLoad = null;
    			vm.md.modelVisible = false;
    		}else {
            	var memberPass = AuthService.GetPassword();
            	var userName = AuthService.GetUser();
            	var member = AuthService.GetMember();
            	
 //   			vm.md.modelToLoad = FileService.GetFileAProjectFile_link(userName,projectName,member,memberPass);
    			vm.md.modelVisible = true;
    		}
    	}
    	
    	function getCompanyName (){
    		return AuthService.GetUser();
    	}
    	
    	function GetResource(projectName, resource){
        	var userName = AuthService.GetUser();
        	var member = AuthService.GetMember();
        	var memberPass = AuthService.GetPassword();
    		
        	console.log(resource);
    		var url = $rootScope.CONSTANTS_Member_AccountController.Member_AccountController.IMAGE_BASE_URL + "?" +
    		"userName=" + userName + 
    		"&projectName=" + projectName +
    		"&member=" + member + 
    		"&memberP=" + memberPass + 
    		"&fileName=" + resource;

    		return url;
    	}
    	
    	function GetProjectsMemberIsAPartOf(){
          var ret;
        	var memberPass = AuthService.GetPassword();
        	var userName = AuthService.GetUser();
        	var member = AuthService.GetMember();
            ProjectMemberService.GetProjectsMemberIsAPartOf(userName,member,memberPass)
                .then(function (response) {
                	if(response.success){
                		var data = response.message;
	                    if (data !== null) {	                    	
	                    	//vm.projectsMemberIsAPartOf = data;	                    	
	                    	data.forEach(function (projectName) {
	                    		GetAllFileMetaDataForAProject(projectName);
	                    	})
	                    	
	                    	
	                    } else {
	                    	ret = response;
	                    }
                	} else {
                		ret = response;
                	}
                });   		  		
    	}

    	function GetAllFileMetaDataForAProject(projectName){
        	var userName = AuthService.GetUser();
        	var member = AuthService.GetMember();
        	var memberPass = AuthService.GetPassword();
    		FileService.GetAllFileMetaData(userName,projectName,null,member,memberPass)
    		.then(function (response) {
                if (response.success) {
                	vm.projectsMemberIsAPartOf[projectName] = response.message;
                	initSlides(vm.projectsMemberIsAPartOf);
                } else {			                	
                	vm.error = response.message;
                	onError(response.message);
                }
			});	  		  		
    	}
    	
    
    	
    	
    	return vm;
    	
    }
 
 
})();