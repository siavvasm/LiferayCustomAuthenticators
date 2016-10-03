package com.mvc.portlets;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

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
//TODO: Create a separate Class for GitHub Authentication


public class GitHubAuthenticator implements Authenticator{

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
		//Useless - Liferay checks automatically if there is such a profile in its Database
		User user = null;
		
		try{
			if(!emailAddress.equals(StringPool.BLANK)){
				// Not supported feature...
			}else if (!screenName.equals(StringPool.BLANK)){
				System.out.println("The user is identified by his sceen name : " + screenName);
				user = UserUtil.findByC_SN(companyId, screenName);
			}else if (userId > 0){
				// Not supported feature...
			}else{
				System.out.println("The user doesn't exist locally!");
				return Authenticator.DNE;
			}
		}catch(NoSuchUserException nsue){
			System.out.println("The user doesn't exist!");
			return Authenticator.DNE;
		}
		
		//Provided that the user exists, check if the password is correct
		//String encPsw = PwdEncryptor.encrypt(password, user.getPassword());
		
		System.out.println("Authentication via GitHub...");
		System.out.println("Given Username : " + screenName);
		System.out.println("Given Password : " + password);

		//Invoke GitHub for Authentication
		boolean valid = authenticateViaGitHub(screenName, password);
		
		System.out.println("The credentials have been sent");
		System.out.println("Please wait!!");

	
		//Check if the user is valid or not
		if(!valid){
			System.out.println("The user is not valid!");
			return FAILURE;
		}else{
			System.out.println("The user is valid!");
			return SUCCESS;
		}
	}

	private boolean authenticateViaGitHub(String username, String password) {
				
		boolean valid = true;
		
		//Create a client in order to communicate with GitHub - Basic Authentication will be used
		RepositoryService service = new RepositoryService();
		service.getClient().setCredentials(username, password);
		//Send a simple request to the API
		try {
			List<Repository> repositories = service.getRepositories();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			valid = false;
		}
			
		return valid;
	}

}
