Liferay Custom Authentication System
=======================================================

This repository contains the source code of Java classes that
act as custom authenticators for the Liferay Portal (in particular
Liferay Portal 6.1.1 CE GA1 and above).

More specifically, it contains:

 1. **FailureAuth.java**: A dummy Authenticator class that always returns -1, and therefore prevents users from connecting to the Liferay Portal instance.
 
 2. **SuccessAuth.java**: A dummy Authenticator class that always returns 1, and therefore allows the users to connect to the Liferay Portal instance.
 
 3. **LiferayAuth.java**: A custom Authenticator class that validates the users by searching their credentials locally.
 
 4. **GithubAuthenticator.java**: A custom Authenticator class that allows users to connect to the Liferay instance with their Github account. It leverages Basic Authentication for this purpose.
 
The purpose of these source code files is to present how a custom Authenticator class for Liferay Portal can be 
implemented and used. The first two classes are dummy authenticators, while the last two are actual working authenticators 
that may be directly deployed and used.

<h2> Liferay CAS Overview </h2>

A detailed documentation of the Liferay *Authentication Pipeline* and the *Custom Authentication System (CAS)* can be found [here](https://web.liferay.com/community/wiki/-/wiki/Main/Liferay+Authentation+Process) and [here](https://web.liferay.com/community/wiki/-/wiki/Main/Developing+a+Custom+Authentication+System).

In brief the general *Authentication Pipeline* of Liferay Portal is depicted in the figure below:

 <p align="center" width="624" height="661">
	<img align="center" src="/authentication-pipeline.png" />
 </p>

The users in order to log in to a Liferay Portal instance have to type their credentials to the appropriate fields of the *Sign In* portlet found on the 
portal's *Welcome Page* and click on the *Sign In* button. Subsequently, the *Authentication Pipeline*
is executed. The overall process comprises three steps as shown in the figure above. These are:

 1. **pre-authentication step**: Custom Authenticator classes are executed.
 
 2. **internal authentication**: Liferay takes the user's credentials from the *Sign In* portet and validates the user in the local database.
 
 3. **post-authentication step**: Custom Authenticator classes are executed.

At each step a set of custom Authenticator classes, like those found in this repository, are executed. In order for the user to 
successfully connect to the Liferay Portal instance, each one of these classes should return 1. If at least one class returns a 
value other than 1 (i.e. 0 or -1), access is denied to the user.

By default, no custom Authenticator class is executed during the pre- and post-authentication steps. Only the second step is executed
which corresponds to the Liferay internal authentication. However, you can easily modify the *Authentication Pipeline* and create your own 
*Custom Authentication System* by following the steps presented below:

 1. Create your custom Authenticator classes (use those found in this repository as templates).
 
 2. Export them as a standalone .jar file and place it to the appropriate folder (**liferay home directory -> tomcat -> lib -> ext**).
 
 3. Define when they should be executed (i.e. at the pre- or post-authentication step) and with which order.
 
 4. Bypass the internal authentication mechanism (second step of the *Authentication Pipeline*).
 
**Attention:** The last two steps are performed by defining the appropriate properties of the *"portal-ext.properties"* file. For more information on 
how to edit this file see the next section.
 
A useful tutorial on how to create custom Authenticator classes can be found [here](https://web.liferay.com/community/wiki/-/wiki/Main/Developing+a+Custom+Authentication+System).
 
A more detailed description of how to create your own *CAS* is provided in the next section, where the deployment process 
of the custom Authenticator classes offered by this repository is described.

<h2 id="setup"> Setup the Custom Authenticators </h2>

In order to setup the custom Authenticator classes found in this repository to your Liferay Portal instance follow the steps presented below:

 1. Import the Java sources to your preferable Java IDE. Compile them and export them as a single .jar file. Alternatively, download the .jar file 
found inside the target folder of this repository.

 2. Navigate to your Liferay Portal instance *Home directory*. Open the *tomcat* folder, then the *lib* folder and finally the *ext* folder. The exact path 
 should look like the following:
 
 ```
 path/to/liferay-portal-6.2.0-ce-ga1/tomcat-7.0.42/lib/ext
 ```
 
 3. Paste the .jar file containing the custom Authenticator classes inside this folder (along with the other jar files which are necessary for the GithubAuthenticator.java and can be found inside the *target* folder of this repository).
 
 4. Go back to the tomcat directory and navigate to webapps/ROOT/WEB-INF/classes. In particular, the exact path should look like the following:
 
 ```
  path/to/liferay-portal-6.2.0-ce-ga1/tomcat-7.0.42/webapps/ROOT/WEB-INF/classes
 ```
 
 5. Create a file named *"portal-ext.properties"* inside this folder.
 
 6. Open this file with your preferable editor and add the following properties:
 
 ```
	auth.pipeline.pre = com.mvc.portlets.SuccessAuth
	auth.pipeline.post = com.mvc.portlets.GitHubAuthenticator
	auth.pipeline.enable.liferay.check=false
 ```
 
 7. Start your Liferay Portal instance.
 
In the example presented above, we defined the following:
 
  - During the pre-authentication step the dummy SuccessAuth authenticator is executed, which always returns 1.
  
  - During the post-authentication step the GithubAuthenticator class is executed, and therefore the user is validated via Github.
  
  - We disabled the internal authentication mechanism. Thus, we bypassed the second step of the *Authentication Pipeline*.
  
As a result, the users are validated exclusively via Github API.
 

 
<h2> Github Custom Authenticator </h2>
 
 If you are interested in using my custom Github Authenticator class, please read this section. 
 
<h3> A. Information </h3>
 
This class is a custom Authenticator class for the Liferay Portal.
 
It is a simple way for authenticating your users via Github account (username and password), and therefore avoiding storing their credentials locally.
 
Basic Authentication is used for the users' authentication. In other words:
 
 - The users should provide their Github credentials to the *Sign In* portlet found on your portal's *Welcome Page*. 
 
 - Their credentials are sent to the Github API over HTTPS in order to ensure secure transmition (i.e. username and password are encrypted). 
 
 - The Github API returns a response to the custom Authenticator.
 
 - Based on this response, the custom authenticator allows or denies access to the user.
 
**Attention:** No password is kept locally into the Liferay database.
 
<h3> B. Setup </h3>
 
In order to setup the GithubAuthenticator class, follow the general setup instruction provided in the [previous section](#setup).
 
In brief:
 
  - Add the .jar files to the ext folder of your tomcat directory.
  
  - Create a *'portal-ext.properties'* file containing the appropriate properties.
  
  - Start your Liferay Portal instance.
  
<h3> C. Usage </h3>
 
The users should first create an account in your Liferay Portal instance in order to be authenticated via Github. This is necessary because
only the password user validation is performed via Github. No user information, apart from username and password, is exchanged between Github and Liferay. (It is not an SSO application after all :P).
As a result, the users should define their name and their email address in order to be identified internally.
 
The steps are simple:
 
  1. Select "Create New Account" option from the portal's *Sign In* portlet.
  
  2. Provide all the required fields (i.e. First Name, Last Name, email address etc.).

  3. In the *Screen Name* field type your *Github username*.

  4. Create your account.

Now you can sign in to the Liferay Portal instance by providing your Github credentials.

**Attention:** 

  - The key point here is that the *Screen Name* of the Liferay account should correspond to the user's *Github username*. 
  
  - Obviously, you should allow your users connect to your Liferay instance with their *Screen Name* instead of their email address.
  
  
 



