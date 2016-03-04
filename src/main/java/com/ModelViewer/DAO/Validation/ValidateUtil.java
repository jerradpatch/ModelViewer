package com.ModelViewer.DAO.Validation;

import com.ModelViewer.LoginApp.Service.ReturnedObject;

public class ValidateUtil {

	final static String EMPTY_STRING = "";

	final static String USERNULL = "\"User name was null\"";
	final static int USERNAME_LENGTH_MAX = 30;
	final static int USERNAME_LENGTH_MIN = 3;
	final static String[] USERNAME_RESTRICTED_CHARACTERS = {"/"};
	
	final static String PROJECTNULL = "\"Project name was null\"";
	final static int PROJECTNAME_LENGTH_MAX = 30;
	final static int PROJECTNAME_LENGTH_MIN = 3;
	final static String[] PROJECTNAME_RESTRICTED_CHARACTERS = {"/"};
	
	final static String MEMBERNULL = "\"Member name was null\"";
	final static int MEMBERNAME_LENGTH_MAX = 30;
	final static int MEMBERNAME_LENGTH_MIN = 3;
	final static String[] MEMBERNAME_RESTRICTED_CHARACTERS = {"/"};
	
	final static String PASSWORDNULL = "\"Password name was null\"";
	final static int PASSWORD_LENGTH_MAX = 20;
	final static int PASSWORD_LENGTH_MIN = 6;
	final static String[] PASSWORD_RESTRICTED_CHARACTERS = {"/"};
	
	final static String EMAILNULL = "\"Email was null\"";
	final static int EMAIL_LEFT_LENGTH_MAX = 30;
	final static int EMAIL_LEFT_LENGTH_MIN = 3;
	final static int EMAIL_RIGHT_LENGTH_MAX = 19;
	final static int EMAIL_RIGHT_LENGTH_MIN = 5;
	final static String[] EMAIL_RESTRICTED_CHARACTERS = {"/","\\"};
	
	
	final static String TEXTFIELDNULL = "\"Text field was null\"";
	final static int TEXTFIELD_LENGTH_MAX = 350;
	final static int TEXTFIELD_LENGTH_MIN = 0;
	final static String[] TEXTFIELD_RESTRICTED_CHARACTERS = {"/","\\"};	
	
	final static String FILENAMENULL = "\"File name was null\"";
	final static int FILENAME_LEFT_LENGTH_MAX = 50;
	final static int FILENAME_LEFT_LENGTH_MIN = 1;
	final static int FILENAME_RIGHT_LENGTH_MAX = 6;
	final static int FILENAME_RIGHT_LENGTH_MIN = 2;
	final static String[] FILENAME_RESTRICTED_CHARACTERS = {"\\"};	
	
	final static String TEXTFIELD_FAIL1 = "\"Text field is too long, required maximum length of "+USERNAME_LENGTH_MAX+"\"";
	final static String TEXTFIELD_FAIL2 = "\"Text field is too short, required minimum length of "+USERNAME_LENGTH_MIN+"\"";
	final static String TEXTFIELD_FAIL3 = "\"Text field contains restricted characters, \""+USERNAME_RESTRICTED_CHARACTERS+"\" not allowed\"";	
	public static void validateTextField(String textFeild) throws ReturnedObject{
		if(textFeild == null){
			ReturnedObject.sThrowException(USERNULL);
		} else if(textFeild.length() > TEXTFIELD_LENGTH_MAX){
			ReturnedObject.sThrowException(TEXTFIELD_FAIL1);
		} else if (textFeild.length() < TEXTFIELD_LENGTH_MIN) {
			ReturnedObject.sThrowException(TEXTFIELD_FAIL2);
		} else if (containsAny(textFeild,TEXTFIELD_RESTRICTED_CHARACTERS)){
			ReturnedObject.sThrowException(TEXTFIELD_FAIL3);
			return;	
		} 
	}
	

	
	final static String USERNAME_FAIL1 = "\"User name is too long, required maximum length of "+USERNAME_LENGTH_MAX+"\"";
	final static String USERNAME_FAIL2 = "\"User name is too short, required minimum length of "+USERNAME_LENGTH_MIN+"\"";
	final static String USERNAME_FAIL3 = "\"User name contains restricted characters, \""+USERNAME_RESTRICTED_CHARACTERS+"\" not allowed\"";	
	public static void validateUserName(String userName) throws ReturnedObject{
		if(userName == null){
			ReturnedObject.sThrowException(USERNULL);
		} else if(userName.length() > USERNAME_LENGTH_MAX){
			ReturnedObject.sThrowException(USERNAME_FAIL1);
		} else if (userName.length() < USERNAME_LENGTH_MIN) {
			ReturnedObject.sThrowException(USERNAME_FAIL2);
		} else if (containsAny(userName,USERNAME_RESTRICTED_CHARACTERS)){
			ReturnedObject.sThrowException(USERNAME_FAIL3);
		} 
	}
	
	final static String MEMBERNAME_FAIL1 = "\"Member name is too long, required maximum length of "+MEMBERNAME_LENGTH_MAX+"\"";
	final static String MEMBERNAME_FAIL2 = "\"Member name is too short, required minimum length of "+MEMBERNAME_LENGTH_MIN+"\"";
	final static String MEMBERNAME_FAIL3 = "\"Member name contains restricted characters, \""+MEMBERNAME_RESTRICTED_CHARACTERS+"\" not allowed\"";	
	public static void validateMemberName(String memberName) throws ReturnedObject{		
		if(memberName == null){
			ReturnedObject.sThrowException(MEMBERNULL);
		} else if(memberName.length() > MEMBERNAME_LENGTH_MAX){
			ReturnedObject.sThrowException(MEMBERNAME_FAIL1);
		} else if (memberName.length() < MEMBERNAME_LENGTH_MIN) {
			ReturnedObject.sThrowException(MEMBERNAME_FAIL2);
		} else if (containsAny(memberName,MEMBERNAME_RESTRICTED_CHARACTERS)){
			ReturnedObject.sThrowException(MEMBERNAME_FAIL3);
		}		
	}
	
	final static String PROJECTNAME_FAIL1 = "\"Project name is too long, required maximum length of "+PROJECTNAME_LENGTH_MAX+"\"";
	final static String PROJECTNAME_FAIL2 = "\"Project name is too short, required minimum length of "+PROJECTNAME_LENGTH_MIN+"\"";
	final static String PROJECTNAME_FAIL3 = "\"Project name contains restricted characters, \""+PROJECTNAME_RESTRICTED_CHARACTERS+"\" not allowed\"";	
	public static void validateProjectName(String projectName) throws ReturnedObject{		
		if(projectName == null){
			ReturnedObject.sThrowException(PROJECTNULL);
		} else if(projectName.length() > PROJECTNAME_LENGTH_MAX){
			ReturnedObject.sThrowException(PROJECTNAME_FAIL1);
		} else if (projectName.length() < PROJECTNAME_LENGTH_MIN) {
			ReturnedObject.sThrowException(PROJECTNAME_FAIL2);
		} else if (containsAny(projectName,PROJECTNAME_RESTRICTED_CHARACTERS)){
			ReturnedObject.sThrowException(PROJECTNAME_FAIL3);
		}		
	}
	
	
	final static String PASSWORD_FAIL1 = "\"Password is too long, required maximum length of "+PASSWORD_LENGTH_MAX+"\"";
	final static String PASSWORD_FAIL2 = "\"Password is too short, required minimum length of "+PASSWORD_LENGTH_MIN+"\"";
	final static String PASSWORD_FAIL3 = "\"Password contains restricted characters, \""+PASSWORD_RESTRICTED_CHARACTERS+"\" not allowed";
	public static void validatePassword(String password) throws ReturnedObject{
		
		if(password == null){
			ReturnedObject.sThrowException(PASSWORDNULL);
		} else if(password.length() > PASSWORD_LENGTH_MAX){
			ReturnedObject.sThrowException(PASSWORD_FAIL1);
		} else if (password.length() < PASSWORD_LENGTH_MIN) {
			ReturnedObject.sThrowException(PASSWORD_FAIL2);
		} else if (containsAny(password,PASSWORD_RESTRICTED_CHARACTERS)){
			ReturnedObject.sThrowException(PASSWORD_FAIL3);	
		} 	
	}
	
	final static String EMAIL_FAIL1 = "\"Email (left of @) is too long, required maximum length of "+EMAIL_LEFT_LENGTH_MAX+"\"";
	final static String EMAIL_FAIL2 = "\"Email (left of @) is too short, required minimum length of "+EMAIL_LEFT_LENGTH_MIN+"\"";
	final static String EMAIL_FAIL3 = "\"Email (right of @) is too long, required maximum length of "+EMAIL_RIGHT_LENGTH_MAX+"\"";
	final static String EMAIL_FAIL4 = "\"Email (right of @) is too short, required minimum length of "+EMAIL_RIGHT_LENGTH_MIN+"\"";
	final static String EMAIL_FAIL5 = "\"Email contains restricted characters, \""+PASSWORD_RESTRICTED_CHARACTERS+"\" not allowed\"";
	final static String EMAIL_FAIL6 = "\"Email did not contain a '@' character.\"";
	final static String EMAIL_FAIL7 = "\"Email contained too many '@' characters.\"";
	public static void validateEmail(String email) throws ReturnedObject{
		
		if(email == null){
			ReturnedObject.sThrowException(EMAILNULL);
		} 
		String[] temp = email.split("@");
		if(temp.length > 2){
			ReturnedObject.sThrowException(EMAIL_FAIL7);	
		} else if (temp.length < 2){
			ReturnedObject.sThrowException(EMAIL_FAIL6);	
		}
		
		String emailLeft = temp[0];
		String emailRight = temp[1];
		
		if(emailLeft.length() > EMAIL_LEFT_LENGTH_MAX){
			ReturnedObject.sThrowException(EMAIL_FAIL1);	
		} else if (emailLeft.length() < EMAIL_LEFT_LENGTH_MIN) {
			ReturnedObject.sThrowException(EMAIL_FAIL2);	
		} else if(emailRight.length() > EMAIL_RIGHT_LENGTH_MAX){
			ReturnedObject.sThrowException(EMAIL_FAIL3);	
		} else if (emailRight.length() < EMAIL_RIGHT_LENGTH_MIN) {
			ReturnedObject.sThrowException(EMAIL_FAIL4);	
		} else if (containsAny(email,EMAIL_RESTRICTED_CHARACTERS)){
			ReturnedObject.sThrowException(EMAIL_FAIL5);	
		}
	}
	
	final static String FILENAME_FAIL1 = "\"File Name (left of .) is too long, required maximum length of "+FILENAME_LEFT_LENGTH_MAX+"\"";
	final static String FILENAME_FAIL2 = "\"File Name (left of .) is too short, required minimum length of "+FILENAME_LEFT_LENGTH_MIN+"\"";
	final static String FILENAME_FAIL3 = "\"File Name (right of .) is too long, required maximum length of "+FILENAME_RIGHT_LENGTH_MAX+"\"";
	final static String FILENAME_FAIL4 = "\"File Name (right of .) is too short, required minimum length of "+FILENAME_RIGHT_LENGTH_MIN+"\"";
	final static String FILENAME_FAIL5 = "\"File Name contains restricted characters, \""+PASSWORD_RESTRICTED_CHARACTERS+"\" not allowed\"";
	final static String FILENAME_FAIL6 = "\"File Name did not contain a '.' character.\"";
	final static String FILENAME_FAIL7 = "\"File Name contained too many '.' characters.\"";
	public static void validateFileName(String fileName) throws ReturnedObject{
		
		if(fileName == null){
			ReturnedObject.sThrowException(FILENAMENULL);
		} 
//		String[] temp = fileName.split("[.]");
//		logger.debug("validateFileName: fileName"+fileName);
//		if(temp.length > 2){
//			ro.setSuccess(false);
//			ro.setMessage(FILENAME_FAIL7);
//			return;
//		} else if (temp.length < 2){
//			ro.setSuccess(false);
//			ro.setMessage(FILENAME_FAIL6);	
//			return;
//		}
		
//		String fileNameLeft = temp[0];
//		String fileNameRight = temp[1];
		
//		if(fileNameLeft.length() > FILENAME_LEFT_LENGTH_MAX){
//			ro.setSuccess(false);
//			ro.setMessage(FILENAME_FAIL1);
//		} else if (fileNameLeft.length() < FILENAME_LEFT_LENGTH_MIN) {
//			ro.setSuccess(false);
//			ro.setMessage(FILENAME_FAIL2);
//		} else if(fileNameRight.length() > FILENAME_RIGHT_LENGTH_MAX){
//			ro.setSuccess(false);
//			ro.setMessage(FILENAME_FAIL3);
//		} else if (fileNameRight.length() < FILENAME_RIGHT_LENGTH_MIN) {
//			ro.setSuccess(false);
//			ro.setMessage(FILENAME_FAIL4);
//		} else 
		if (containsAny(fileName,FILENAME_RESTRICTED_CHARACTERS)){
			ReturnedObject.sThrowException(FILENAME_FAIL5);
		}
	}
	
	
	public static boolean containsAny(String str, String[] searchString) {
		  
	    if (str == null || str.length() == 0 || searchString == null || searchString.length == 0) {
	        return false;
	    }
	    for (int i = 0; i < str.length(); i++) {
	         char ch = str.charAt(i);
	        for (int j = 0; j < searchString.length; j++) {
	            if (searchString[j].charAt(0) == ch) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
}
