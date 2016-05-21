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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import com.inzaana.pos.db.DBConnector;
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

	private static final String	AUTHORIZATION_PROPERTY	= "Authorization";
	private static final String	AUTHENTICATION_SCHEME	= "Basic";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{

		final List<String> authorization = requestContext.getHeaders().get(AUTHORIZATION_PROPERTY);

		// If no authorization information present; block access
		if (authorization == null || authorization.isEmpty())
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Access Unauthorized")
					.build());
			return;
		}

		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String userName = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		if (userName.isEmpty())
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User name can not be Empty")
					.build());
			return;
		}

		UserManager userManager = new UserManager();
		String origPassword = userManager.getUserPassword(userName);
		if (!password.equals(origPassword))
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity("Password Missmatch : " + origPassword + " : " + password).build());
			return;
		}

		String userRole = userManager.getUserRole(userName);
		if (userRole.equals(UserRole.GUEST.toString()))
		{
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("GUEST User role not allowed.")
					.build());
			return;
		}

		String scheme = requestContext.getUriInfo().getRequestUri().getScheme();
		User user = new User(userName, origPassword, userRole);
		requestContext.setSecurityContext(new InzaanaSecurityContext(user, scheme));
	}
}
