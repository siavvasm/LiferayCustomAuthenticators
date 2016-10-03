package com.mvc.portlets;
import java.util.List;
import java.util.Map;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;

/**
 * This class is a dummy Authenticator class that always returns FAILURE (i.e. -1 value).
 * Attention: If you add this class to your Custom Authentication System the access will
 * be denied to everyone, even to those providing their valid credentials.
 */

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
