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

import com.inzaana.pos.managers.StockDiaryManager;
import com.inzaana.pos.models.StockDiary;

@Path("/stockdiary")
public class StockResource
{
	StockDiaryManager	stockManager	= new StockDiaryManager();

	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS", "USER" })
	public List<StockDiary> getStockDiaryItems(@PathParam("userName") String userName)
	{
		return stockManager.getStockDiaryItems(userName);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN" })
	public List<StockDiary> getAllStockDiaryItems()
	{
		return stockManager.getAllStockDiaryItems();
	}

	@PUT
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String addStockDiaryItem(@PathParam("userName") String userName, StockDiary newStockDiary)
	{
		return stockManager.addStockDiaryItem(userName, newStockDiary);
	}

	@POST
	@Path("/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String updateStockDiary(@PathParam("userName") String userName, StockDiary updatedStockDiary)
	{
		return stockManager.updateStockDiaryItem(userName, updatedStockDiary);
	}

	@DELETE
	@Path("/{userName}/{categoryId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String deleteStockDiary(@PathParam("userName") String userName, @PathParam("categoryId") String categoryId)
	{
		return stockManager.deleteStockDiaryItem(userName, categoryId);
	}
}
