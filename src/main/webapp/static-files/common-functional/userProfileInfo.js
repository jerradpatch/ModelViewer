(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('UserProfileInfo', UserProfileInfo);
 
    UserProfileInfo.$inject = ['$cookieStore'];
    function UserProfileInfo($cookieStore) {    	
    	
    	var service = this;
    	
    	service.isFirstVisit = isFirstVisit;
    	
    	
    	function setCookieValue(key,value){	
    		var scookie = $cookieStore.get('AuthService');
    		scookie[key] = value;  		
    		$cookieStore.put('AuthService', scookie);
    	}
    	function getCookieValue(key){	
    		var scookie = $cookieStore.get('AuthService');
    		var value = scookie[key];  		
    		return value;
    	}
    	
    	function isFirstVisit(where){
    		var first = getCookieValue(where);
    		if(first){
    			return true;
    		}
    		setCookieValue(where,true);
    		return false;
    	}
    	
//    	if($cookieStore.get('AuthService') != null){
//    		GetCredentialsFromCookie();
//    	} else {
//	    	var user = "";
//	    	var member = "";
//	    	var password = "";	
//	    	
//	    	service.auth = auth;
//	    	service.auth.password = password;
//	    	service.auth.user = user;
//	    	service.auth.member = member;
//    	}
//    	service.GetCredentialsFromCookie = GetCredentialsFromCookie;
//    	service.GetPassword = GetPassword;
//    	service.GetUser = GetUser;
//    	service.GetMember = GetMember;
//    	service.SetCredentials = SetCredentials;
//    	service.ClearCredentials = ClearCredentials;
//
//    	
//        function GetCredentialsFromCookie() {
//        	var auth = $cookieStore.get('AuthService');
//        	if(auth != null){
//        		service.auth = auth;
//        	}
//        }        
//        function GetPassword(){
//        	return service.auth.password;
//        }
//        
//        function GetUser(){
//        	return service.auth.password;
//        }    
//        
//        function GetMember(){
//        	return service.auth.member;
//        } 
//        function SetCredentials(username, password, member) {
//        	if(typeof member !== "undefined"){
//        		service.auth.member = member;
//        	}
//        	service.auth.user = username;
//        	service.auth.password = password;
//            $cookieStore.put('AuthService', service.auth);
//        }
//        function ClearCredentials() {
//        	service.auth.password = "";
//        	service.auth.user = "";
//            $cookieStore.remove('AuthService');
//        }
    	return service;
    };
 
})();