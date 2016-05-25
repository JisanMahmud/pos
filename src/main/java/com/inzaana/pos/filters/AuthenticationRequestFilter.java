package com.inzaana.pos.filters;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.inzaana.pos.managers.UserManager;
import com.inzaana.pos.models.User;
import com.inzaana.pos.utils.InzaanaSecurityContext;
import com.inzaana.pos.utils.UserRole;

@Priority(Priorities.AUTHENTICATION)
@Provider
@PreMatching
public class AuthenticationRequestFilter implements ContainerRequestFilter
{

	@Context
	private ResourceInfo		resourceInfo;

	public static String		USER_NAME					= "***@@@***DEFAULT_USER_NAME***@@@***";
	public static String		USER_ROLE					= UserRole.GUEST.toString();

	private static final String	AUTHORIZATION_PROPERTY		= "Authorization";
	private static final String	AUTHENTICATION_SCHEME		= "Basic";
	private static final String	ACCESS_UNAUTHORIZED			= "ACCESS UNAUTHORIZED : ";
	private static final String	AUTH_NOT_FOUND				= "AUTHENTICATION INFORMATION NOT FOUND";
	private static final String	BLANK_USER_NAME_PASSWORD	= "USER NAME OR PASSWORD CANN'T BE BLANK";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{

		final List<String> authorization = requestContext.getHeaders().get(AUTHORIZATION_PROPERTY);

		// If no authorization information present; block access
		if (authorization == null || authorization.isEmpty())
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity(ACCESS_UNAUTHORIZED + AUTH_NOT_FOUND).build());
			return;
		}

		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		String userNameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

		if (userNameAndPassword == null || userNameAndPassword.replace(":", "").trim().isEmpty())
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity(ACCESS_UNAUTHORIZED + AUTH_NOT_FOUND).build());
			return;
		}

		final String[] credentials = userNameAndPassword.split(":");

		if (credentials.length != 2)
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity(ACCESS_UNAUTHORIZED + BLANK_USER_NAME_PASSWORD).build());
			return;
		}
		else if (credentials[0].trim().isEmpty() || credentials[1].trim().isEmpty())
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity(ACCESS_UNAUTHORIZED + BLANK_USER_NAME_PASSWORD).build());
			return;
		}

		String userName = credentials[0];
		USER_NAME = userName;
		String userPassword = credentials[1];

		UserManager userManager = new UserManager();
		String savedPassword = userManager.getUserPassword(userName);
		if (!userPassword.equals(savedPassword))
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity("Password Missmatch : " + savedPassword + " : " + userPassword).build());
			return;
		}

		String userRole = userManager.getUserRole(userName);
		USER_ROLE = userRole;
		if (userRole.equals(UserRole.GUEST.toString()))
		{
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("GUEST USER ROLE NOT ALLOWED")
					.build());
			return;
		}

		String scheme = requestContext.getUriInfo().getRequestUri().getScheme();
		User user = new User(userName, savedPassword, userRole);
		requestContext.setSecurityContext(new InzaanaSecurityContext(user, scheme));
	}
}
