package com.mvc.portlets;
import java.util.List;
import java.util.Map;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;

import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.PasswordTrackerLocalServiceUtil;

/**
 * This class receives the user's credentials and authenticates him/her against
 * the Liferay Database. It imitates the normal internal authentication mechanism
 * of the Liferay platform that is executed when the property 
 * auth.pipeline.enable.liferay.check is set to true.
 */
//TODO: LOG FILES!!!


public class LiferayAuth implements Authenticator{

	@Override
	public int authenticateByEmailAddress(long companyId, String emailAddress, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
			throws AuthException {
		
		try {
			return authenticate(companyId, emailAddress, StringPool.BLANK, 0, password);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new AuthException(e);
		}
	}

	@Override
	public int authenticateByScreenName(long companyId, String screenName, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
			throws AuthException {
		
		try {
			return authenticate(companyId, StringPool.BLANK, screenName, 0, password);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new AuthException(e);
		}
	}

	@Override
	public int authenticateByUserId(long companyId, long userId, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
			throws AuthException {
		
		try {
			return authenticate(companyId, StringPool.BLANK, StringPool.BLANK, userId, password);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new AuthException(e);
		}
		
	}
	
	protected int authenticate(long companyId, String emailAddress, String screenName, long userId, String password) throws Exception {
		
		//Search for the user (Check if the user exists)
		User user = null;
		
		try{
			if(!emailAddress.equals(StringPool.BLANK)){
				System.out.println("The user is identified by his email address : " + emailAddress);
				user = UserUtil.findByC_EA(companyId, emailAddress);
			}else if (!screenName.equals(StringPool.BLANK)){
				System.out.println("The user is identified by his sceen name : " + screenName);
				user = UserUtil.findByC_SN(companyId, screenName);
			}else if (userId > 0){
				System.out.println("The user is identified by his ID : " + userId);
				user = UserUtil.findByC_U(companyId, userId);
			}else{
				System.out.println("The user doesn't exist!");
				return Authenticator.DNE;
			}
		}catch(NoSuchUserException nsue){
			System.out.println("The user doesn't exist!");
			return Authenticator.DNE;
		}
		
		//Provided that the user exists, check if the password is correct
		//String encPsw = PwdEncryptor.encrypt(password, user.getPassword());
		
		String psw = user.getPassword();
		String upsw = user.getPasswordUnencrypted();
		
		System.out.println("Given Password : " + password);
		System.out.println("Stored Password Encrypted : " + psw);
		System.out.println("Stored Password Unencrypted : " + upsw);
		
		
		// Check if the password is valid ...
		boolean valid = PasswordTrackerLocalServiceUtil.isSameAsCurrentPassword(user.getUserId(), password);
		System.out.println("VALID = " + valid);
		
		
		if(!valid){
			System.out.println("Passwords did not match!");
			return FAILURE;
		}
		
		//If I reach this point, the user is successfully authenticated
		System.out.println("The passwords match!");
		System.out.println("The user successfully authenticated!");
		return SUCCESS;
	}

}
