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


public class FailureAuth implements Authenticator {

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
           System.out.println("FAILURE");
            return FAILURE;
    }

}
