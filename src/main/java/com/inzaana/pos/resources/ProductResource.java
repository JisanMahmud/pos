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

import com.inzaana.pos.managers.ProductManager;
import com.inzaana.pos.models.Product;

@Path("/products")
public class ProductResource
{

	final private String	SUCCESS_MSG		= "SUCCESS";
	final private String	FAILURE_MSG		= "FAILED";

	ProductManager			productManager	= new ProductManager();

	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS", "USER" })
	public List<Product> getProducts(@PathParam("userName") String userName)
	{
		return productManager.getProducts(userName);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN" })
	public List<Product> getAllProducts()
	{
		return productManager.getAllProducts();
	}

	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String addNewProduct(@PathParam("userName") String userName, Product newProduct)
	{
		if (!productManager.addNewProduct(userName, newProduct))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}

	@POST
	@Path("/{userName}/{productName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String updateProduct(@PathParam("userName") String userName, @PathParam("productName") String productName,
			Product updatedProduct)
	{
		if (!productManager.updateProduct(userName, productName, updatedProduct))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}

	@DELETE
	@Path("/{userName}/{productName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String deleteProduct(@PathParam("userName") String userName, @PathParam("productName") String productName)
	{
		if (!productManager.deleteProduct(userName, productName))
		{
			return FAILURE_MSG;
		}

		return SUCCESS_MSG;
	}
}
