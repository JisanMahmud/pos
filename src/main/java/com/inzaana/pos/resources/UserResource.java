package com.inzaana.pos.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.inzaana.pos.managers.CategoryManager;
import com.inzaana.pos.managers.UserManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.models.User;

/*
 * For Getting/Setting User info
 */
@Path("/users")
public class UserResource {
	
	final private String	SUCCESS_MSG		= "SUCCESS";
	final private String	FAILURE_MSG		= "FAILED";

	UserManager			userManager	= new UserManager();

	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public List<User> getUser(@PathParam("userName") String userName)
	{
		return userManager.getUser(userName);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN" })
	public List<User> getAllUsers()
	{
		return userManager.getAllUsers();
	}
	
	@GET
	@Path("/new/register")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String registerNewUser()
	{
		return userManager.registerNewUser();
	}

	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN" })
	public String addUser(@PathParam("userName") String userName, User newUser)
	{
		if (!userManager.addNewUser(newUser))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}

	@POST
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN" })
	public String updateUser(@PathParam("userName") String userName,
			User updatedUser)
	{
		if (!userManager.updateUser(userName, updatedUser))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}

	@DELETE
	@Path("/{userName}}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN" })
	public String deleteUser(@PathParam("userName") String userName)
	{
		if (!userManager.deleteUser(userName))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}

}