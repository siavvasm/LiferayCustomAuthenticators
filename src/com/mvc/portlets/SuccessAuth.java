package com.mvc.portlets;
import java.util.List;
import java.util.Map;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;

/**
 * This class receives the user's credentials and authenticates him/her against
 * the Liferay Database. It imitates the normal internal authentication mechanism
 * of the Liferay platform that is executed when the property 
 * auth.pipeline.enable.liferay.check is set to true.
 */
//TODO: LOG FILES!!!

import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.service.persistence.UserUtil;


public class SuccessAuth implements Authenticator {

    public int authenticateByEmailAddress( long companyId, String emailAddress, String password, Map headerMap, Map parameterMap) {
            return authenticate();
    }

    public int authenticateByScreenName( long companyId, String screenName, String password, Map headerMap, Map parameterMap) {
            return authenticate();
    }

    public int authenticateByUserId( long companyId, long userId, String password, Map headerMap, Map parameterMap) {
            return authenticate();
    }

    protected int authenticate( ) {
           System.out.println("SUCCESS");
            return SUCCESS;
    }

    

//end
	
	/**
	 * A main method for testing purposes ...
	 * @param args
	 */
	public static void main(String[] args){
		
		//Create a LiferayAuth authenticator ...
		SuccessAuth auth = new SuccessAuth();
		
		//Get the user's credentials
		String username = "siavvasm@iti.gr";
		String password = "0287PAOK6613!!";
		
		System.out.println("I am going to search for the user: " + username + " with password: " + password);
		
		int access = auth.authenticateByEmailAddress((long) 0.0, username, password, null, null);
		
		System.out.println("The result is : " + access);
		
		List<User> users = null;
		try {
			//users = UserUtil.findAll();
			java.util.List<com.liferay.portal.model.User> userList =    com.liferay.portal.service.UserLocalServiceUtil.getUsers(1, 2);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Number of users : " + users.size());
		
	}

}
