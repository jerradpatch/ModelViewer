(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('AuthService', AuthService);
 
    AuthService.$inject = ['$cookieStore'];
    function AuthService($cookieStore) {    	
    	
    	var service = {};
    	service.memberModel = null;
    	service.userModel = null;
    	service.readUserModel = readUserModel;
    	service.updateUserModel = updateUserModel;
    	service.readMemberModel = readMemberModel;
    	service.updateMemberModel = updateMemberModel;
    	
    	return service;
    	
    	function readUserModel(){
    		return service.userModel;
    	}
    	function updateUserModel(userModel){
    		service.userModel = userModel;
    	}
    	function readMemberModel(){
    		return service.memberModel;
    	}
    	function updateMemberModel(memberModel){
    		service.memberModel = memberModel;
    	}   	   	
//    	var service = {};
//    	var auth = {};
//    	
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
    
    
    // Base64 encoding service used by AuthenticationService
//    var Base64 = {
// 
//        keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
// 
//        encode: function (input) {
//            var output = "";
//            var chr1, chr2, chr3 = "";
//            var enc1, enc2, enc3, enc4 = "";
//            var i = 0;
// 
//            do {
//                chr1 = input.charCodeAt(i++);
//                chr2 = input.charCodeAt(i++);
//                chr3 = input.charCodeAt(i++);
// 
//                enc1 = chr1 >> 2;
//                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
//                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
//                enc4 = chr3 & 63;
// 
//                if (isNaN(chr2)) {
//                    enc3 = enc4 = 64;
//                } else if (isNaN(chr3)) {
//                    enc4 = 64;
//                }
// 
//                output = output +
//                    this.keyStr.charAt(enc1) +
//                    this.keyStr.charAt(enc2) +
//                    this.keyStr.charAt(enc3) +
//                    this.keyStr.charAt(enc4);
//                chr1 = chr2 = chr3 = "";
//                enc1 = enc2 = enc3 = enc4 = "";
//            } while (i < input.length);
// 
//            return output;
//        },
// 
//        decode: function (input) {
//            var output = "";
//            var chr1, chr2, chr3 = "";
//            var enc1, enc2, enc3, enc4 = "";
//            var i = 0;
// 
//            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
//            var base64test = /[^A-Za-z0-9\+\/\=]/g;
//            if (base64test.exec(input)) {
//                window.alert("There were invalid base64 characters in the input text.\n" +
//                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
//                    "Expect errors in decoding.");
//            }
//            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
// 
//            do {
//                enc1 = this.keyStr.indexOf(input.charAt(i++));
//                enc2 = this.keyStr.indexOf(input.charAt(i++));
//                enc3 = this.keyStr.indexOf(input.charAt(i++));
//                enc4 = this.keyStr.indexOf(input.charAt(i++));
// 
//                chr1 = (enc1 << 2) | (enc2 >> 4);
//                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
//                chr3 = ((enc3 & 3) << 6) | enc4;
// 
//                output = output + String.fromCharCode(chr1);
// 
//                if (enc3 != 64) {
//                    output = output + String.fromCharCode(chr2);
//                }
//                if (enc4 != 64) {
//                    output = output + String.fromCharCode(chr3);
//                }
// 
//                chr1 = chr2 = chr3 = "";
//                enc1 = enc2 = enc3 = enc4 = "";
// 
//            } while (i < input.length);
// 
//            return output;
//        }
//    }
    
})();