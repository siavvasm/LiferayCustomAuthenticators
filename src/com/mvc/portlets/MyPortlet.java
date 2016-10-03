package com.mvc.portlets;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;


import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class MyPortlet extends MVCPortlet{
	
	// It is equivalent to the doPost() method and the parameters to the HTTPRequest and HTTPResponse
	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		System.out.println("Portlet Controller Executing");
		PortletPreferences prefs = (PortletPreferences) actionRequest.getPreferences();
		String greeting = actionRequest.getParameter("greeting");
		if(greeting != null){
			prefs.setValue("greeting", greeting);
			prefs.store();
		}
		SessionMessages.add(actionRequest, "success");
		super.processAction(actionRequest, actionResponse);
	}

}
