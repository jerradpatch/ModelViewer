(function () {
    'use strict';
 
    angular
        .module('app.member')
        .constant("CONSTANTS_Member_ProjectController", {
        	"Member_ProjectController" : {
	        		"MEMBER_LOGIN_FORM" : {
		        		"COMPANY_NAME" : {
		        			"MAXIMUM_LENGTH": 30,
		        			"MINIMUM_LENGTH": 3,
		        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"},
		        		"MEMBER_NAME" : {
		        			"MAXIMUM_LENGTH": 30,
		        			"MINIMUM_LENGTH": 3,
		        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"},
		        		"PASSWORD": {
		        			"MAXIMUM_LENGTH": 20,
		        			"MINIMUM_LENGTH": 6,
		        			"VALID_PATTERN":"/^([0-9a-zA-Z@._-])+$/"}     		
	        		}
        	}
        
         })
//        .filter('getFirstImageFromList_retUrl', ['$rootScope','AuthService',function($rootScope,AuthService) {
//        	return function(inputList_inModels,projectName) {
//        		var supportedImageTypes = [".jpg",".jpeg",".png"];
//        		var returnedList = null;
//        		if(inputList_inModels){
//        			inputList_inModels.forEach(function(model){
//        				model.list.forEach(function(itemInModelFolder){
//	        				if("type" in itemInModelFolder){
//	        					supportedImageTypes.forEach(function(supportedType){
//	        						if(itemInModelFolder["type"] == supportedType){
//	        				        	var pass = AuthService.GetPassword();
//	        				        	var userName = AuthService.GetUser();
//	        				        	var member = AuthService.GetMember();
//	        				        	
//	        							return "/FileService/GetFileAProjectFile"+"?"+
//	        							"userName="+userName+"&"+
//	        							"userP="+null+"&"+
//	        							"projectName="+projectName+"&"+
//	        							"member="+member+"&"+
//	        							"memberP="+pass+"&"+
//	        							"fileName="+itemInModelFolder.name;																
//	        						}
//	        					});
//	        				}
//        				});
//    				});
//        		}
//        	}
//        }])    	
        .controller('Member_ProjectController', Member_ProjectController)
        .directive('jpNgUnity', jpNgUnity)
        .directive('jpNgBlink', jpNgBlink)
        .directive('jpNgRightFanImages', jpNgRightFanImages)
       // .directive('jpWebGlGlobe', jpWebGlGlobe)
        .run(function ($rootScope, CONSTANTS_Member_ProjectController) {
        	$rootScope.CONSTANTS_Member_ProjectController = CONSTANTS_Member_ProjectController;
        });
 
    Member_ProjectController.$inject = ['$location','$cookieStore','AuthService','ProjectInfoService','MemberService','FileService'];
    function Member_ProjectController($location,$cookieStore,AuthService, ProjectInfoService, MemberService, FileService) {
        var vm = this;

//        vm.GetAllFileMetaDataForAProject = GetAllFileMetaDataForAProject;	
        vm.modelImages = null;
        vm.projectMeta = null;
//        vm.GetProject = GetProject;
        vm.projectStory = null;
//        vm.projectTitle = GetProject();
        
//        GetAllFileMetaDataForAProject();	
//        GetStory();
        
//        function GetProject(){
//        	return UserProfileInfo.get("currentProject");
//        }
        
//        function GetStory(){
//        	var pass = AuthService.GetPassword();
//        	var userName = AuthService.GetUser();
//        	var projectName = GetProject();
//        	var member = AuthService.GetMember();
//        	
//        	ProjectInfoService.ReadProjectInfo(userName,null,projectName,member,pass)
//			.then(function (response) {
//                if (response.success) {
//                	vm.projectStory = response.message;
//                } else {			                	
//                	vm.error = response.message;
//                }
//			});	        	
//        }
        
//        function GetImagesFromModelsFolders(ProjectMetaData){
//        	var models = ProjectMetaData.Models;      	
//    		var supportedImageTypes = [".jpg",".jpeg",".png",".unity"];
//    		var projectName = GetProject();
//    		
//    		if(models){
//    			var retVal = [];
//    			models.forEach(function(model){
//					var ret = {};
//					ret["dataType"] = "";
//    				model.list.forEach(function(itemInModelFolder){
//        				if("type" in itemInModelFolder){
//				        	var pass = AuthService.GetPassword();
//				        	var userName = AuthService.GetUser();
//				        	var member = AuthService.GetMember();
//				        	var projectName = GetProject();
//				        	
//        					supportedImageTypes.forEach(function(supportedType){
//        						
//        						if(itemInModelFolder["type"] == supportedType){
//        				        	var urlJpg =  "FileService/GetFileAProjectFile"+"?"+
//			        							"userName="+userName+"&"+
//			        							"projectName="+projectName+"&"+
//			        							"member="+member+"&"+
//			        							"memberP="+pass+"&"+
//			        							"fileName="+model.name+"/"+itemInModelFolder.name;
//        				        	ret["urlImage"] = urlJpg;
//        				        	ret["dataType"] = ret["dataType"] + " image ";
//        						}        	
//        					});
//    						if(itemInModelFolder["type"] == "folder" && "name" in itemInModelFolder && itemInModelFolder["type"].indexOf('.unity')){
//    							ret["urlUnity"] = 
//    								"FileService/GetFileAProjectFile"+"?"+
//        							"userName="+userName+"&"+
//        							"projectName="+projectName+"&"+
//        							"member="+member+"&"+
//        							"memberP="+pass+"&"+
//        							"fileName="+model.name+"/"+itemInModelFolder.name;
//    							ret["dataType"] = ret["dataType"] + " unity ";
//    						}
//        				}
//    				});
//					retVal.push(ret);
//				});
//    			vm.modelImages = retVal;
//    		}
      	
//        }
//        
//    	function GetAllFileMetaDataForAProject(){
//        	var userName = AuthService.GetUser();
//        	var projectName = GetProject();
//        	if(projectName == null){
//        		return;
//        	}
//        	var member = AuthService.GetMember();
//        	var memberPass = AuthService.GetPassword();
//    		FileService.GetAllFileMetaData(userName,projectName,null,member,memberPass)
//    		.then(function (response) {
//                if (response.success) {
//                	GetImagesFromModelsFolders(response.message);
//                	vm.projectMeta = response.message;
//                } else {			                	
//                	vm.error = response.message;
//                	//onError(response.message);
//                }
//			});	  		  		
//    	}
    	
    	
        return vm;
    }
    
    jpNgUnity.$inject = ['$document','AuthService','$window'];
    function jpNgUnity($document,AuthService,$window){
    	
		return {
			link: function(scope, element, attr) {
				//from element get information about the unity location
				var urlUnity = attr['url-unity'];
				//to the dom add the unity element taken from index.html
	    		
				element.on('mousedown', function(event) {
			        // Prevent default dragging of selected content
					urlUnity = element[0].attributes['url-unity'].nodeValue;
			        event.preventDefault();
			        element.on('mouseup', function(event){
			        	jQuery('<div id="jp-ng-unity-all" style="position: absolute;z-index: 100;"><div id="jp-ng-unity-overlay" class="jp-ng-unity-overlay" style="background:black;opacity:0.8;"></div><div class="template-wrap clear"> <canvas class="emscripten" id="canvas" oncontextmenu="event.preventDefault()" height="600px" width="960px"></canvas> <br><div class="logo"></div><div class="fullscreen"><img src="static-files/images/fullscreen.png" width="38" height="38" alt="Fullscreen" title="Fullscreen" onclick="SetFullscreen(1);"/></div><div class="title">unity</div></div><p class="footer">&laquo; created with <a href="http://unity3d.com/" title="Go to unity3d.com">Unity</a> &raquo;</p></div>').prependTo($('body'));
						//add background overlay styling
						setOverlaySize();
						//add resize event for overlay
						jQuery(window).on('resize', setOverlaySize);
						//$("#jp-ng-unity-overlay").style.cssText = 'float:left;margin-top:75px;';

						//add click out of box event handeler to close the window
						jQuery("#jp-ng-unity-overlay").on('mousedown', function(event){
							jQuery("#jp-ng-unity-overlay").on('mouseup', function(event){
								jQuery("#jp-ng-unity-all").fadeOut(400);
								jQuery.when(jQuery("#jp-ng-unity-all").fadeOut(400)).done(function(){
									location.reload(false); //until unity makes it javascript api more friendly to single page apps, I have to free resources by refreshing the page
								})
							});
						});
						

			        	
						callUnity();
			        });
			    });
				
				function setOverlaySize(){
					var overlay = $("#jp-ng-unity-overlay");
					var docWidth = $(document).width();
					var docHeight = $(document).height();
					overlay.width(docWidth).height(docHeight);
				}
//				function addCloseButtonHook(){
//					element.find('.jp-ng-close').on('click', function() {
//						$("#jp-ng-unity-canvas").remove();
//						element.hidden = true; 
//						//removeCloseButtonHook();
//					});
//				}
//				function removeCloseButtonHook(){
//					element.find('.jp-ng-close').off('click');
//				}
				
				function callUnity (){
					//add background overlay
					

					//create unity varibles
					$window.Module = {
						TOTAL_MEMORY: 536870912,
						errorhandler: null,			// arguments: err, url, line. This function must return 'true' if the error is handled, otherwise 'false'
						compatibilitycheck: null,
						dataUrl: urlUnity+"/Release/unity.data",
						codeUrl: urlUnity+"/Release/unity.js",
						memUrl: urlUnity+"/Release/unity.mem"
					};
					jQuery.getScript(urlUnity+"/Release/UnityLoader.js");		   
				}
				
	    	}
	    }
    };
    
    
    
//    jpWebGlGlobe.$inject = ['$document','AuthService','$window'];
//    function jpWebGlGlobe($document,AuthService,$window){
//    			<div style="background-color:white;width:80%;height:80%;display:flex;justify-content:center;align-items:center;border-style:solid;border-radius:25px;padding:10px;">

//		return {
//			link: function(scope, element, attr) {
//				$('<div id="jpWebGlGlobe" style="z-index:-1;position:absolute;"><div id="container" style="color: rgb(255, 255, 255); font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 13px; line-height: 20px; font-family: Arial, sans-serif; cursor: move;"></div></div>').prependTo($('body'));
//				
//				var data = [ 10, 10, 20, 10, 20, 10, 20, 10, 20, 10, 20, 20];
//				
//				var container = document.getElementById( 'container' );
//				var globe = new DAT.Globe( container );
//				
//				for ( var i = 0; i < data.length; i ++ ) {
//		            globe.addData( data, {format: 'magnitude', name: 'test'} );
//		        }
//				
//				globe.createPoints();
//				
//				globe.animate();
//			}
//		}
//    }
    
    jpNgBlink.$inject = [];
    function jpNgBlink(){
    	
		return {
			link: function(scope, element, attr) {

				//parent attributes //jp-ng-blink active='true' options='{'duration':2000,'itteration':2}'
				var blinkTrue = attr['active'];
				if(blinkTrue == "true"){
					element.css( "position","relative");
					jQuery("<div jp-ng-blink-child class='jpSwipeLeftRightInform' style='top:0px;font-size: 13vw;display:flex;justify-content:center;align-items:center;position: absolute;width: 100%;height: 100%;'><span style='line-height: 1.25;'>&#171;</span><span>SWIPE</span><span style='line-height: 1.25;'>&#187;</span></div>").appendTo(element);

					var perpendedChild = jQuery(element).children("[jp-ng-blink-child]").first();
					
					var options = JSON.parse(attr['options']);

					for(var i = 0; i < options.itteration; i++){
						jQuery(perpendedChild).fadeIn().fadeOut(options.duration);
					};
					
					jQuery( perpendedChild ).promise().done(function() {
						jQuery(perpendedChild).remove();
					});
				}

			

			}
		}
    }
    
    jpNgRightFanImages.$inject = [];
    function jpNgRightFanImages(){
    	
		return {
			link: function(scope, element, attr) {

				//parent attributes //jp-ng-blink active='true' options='{'duration':2000,'itteration':2}'
				var blinkTrue = attr['active'];
				if(blinkTrue == "true"){
					element.css( "position","relative");
					jQuery("<div jp-ng-blink-child class='jpSwipeLeftRightInform' style='top:0px;font-size: 13vw;display:flex;justify-content:center;align-items:center;position: absolute;width: 100%;height: 100%;'><span style='line-height: 1.25;'>&#171;</span><span>SWIPE</span><span style='line-height: 1.25;'>&#187;</span></div>").appendTo(element);

					var perpendedChild = $(element).children("[jp-ng-blink-child]").first();
					
					var options = JSON.parse(attr['options']);

					for(var i = 0; i < options.itteration; i++){
						jQuery(perpendedChild).fadeIn().fadeOut(options.duration);
					};
					
					jQuery( perpendedChild ).promise().done(function() {
						jQuery(perpendedChild).remove();
					});
				}

			

			}
		}
    }
 

})();











