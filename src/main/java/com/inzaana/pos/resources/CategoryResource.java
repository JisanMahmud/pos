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
import com.inzaana.pos.models.Category;

@Path("/categories")
public class CategoryResource
{
	final private String	SUCCESS_MSG		= "SUCCESS";
	final private String	FAILURE_MSG		= "FAILED";

	CategoryManager			categoryManager	= new CategoryManager();

	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS", "USER" })
	public List<Category> getCategories(@PathParam("userName") String userName)
	{
		return categoryManager.getCategoryItems(userName);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN" })
	public List<Category> getAllCategories()
	{
		return categoryManager.getAllCategoryItems();
	}

	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String addCategory(@PathParam("userName") String userName, Category newCategory)
	{
		if (!categoryManager.addCategory(userName, newCategory))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}

	@POST
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String updateCategory(@PathParam("userName") String userName,
			Category updatedCategory)
	{
		if (!categoryManager.updateCategory(userName, updatedCategory))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}

	@DELETE
	@Path("/{userName}/{categoryId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String deleteCategory(@PathParam("userName") String userName, @PathParam("categoryId") String categoryId)
	{
		if (!categoryManager.deleteCategory(userName, categoryId))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}
}