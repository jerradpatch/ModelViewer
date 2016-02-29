(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('ErrorModel', ErrorModel);
 
    ErrorModel.$inject = ['$cookieStore'];

    function ErrorModel($cookieStore) {  
    	
    	var vm = this;
    	//varibles
  //  	vm.errorModelFirst = null;//{};
    	vm.errorModels = [];
    	
    	init(); //set default model for binding
    	function init(){
    		vm.errorModelFirst = getNewModel(true,"",false);
    	}
    	
    	vm.pushModel = pushModel
    	function pushModel(model){
    		var newModel = {};
    		newModel["meta"] = {"notSeen":true};
    		newModel["model"] = model;   		
 //   		angular.copy(newModel, vm.errorModelFirst);
    		vm.errorModels.push(newModel);
    	}
    	
    	vm.getNewModel = getNewModel;
    	function getNewModel(success, message, notSeen){
    		return {  
    				"meta":{
    					"notSeen": notSeen || "false"
    			 	},
    				"model":{  
	    	    	   "data":{  
	    	    	      "success":success,
	    	    	      "message":message
	    	    	   },
	    	    	   "status":"",
	    	    	   "config":{  
	    	    	      "method":"",
	    	    	      "transformRequest":[  
	    	    	         null
	    	    	      ],
	    	    	      "transformResponse":[  
	    	    	         null
	    	    	      ],
	    	    	      "params":{  
	    	    	         "userName":"",
	    	    	         "password":""
	    	    	      },
	    	    	      "url":"",
	    	    	      "headers":{  
	    	    	         "Accept":"application/json, text/plain, "
	    	    	      }
	    	    	   },
	    	    	   "statusText":""
	    	    	}
    			}
    	}
    	
    	return vm;

    }

})();