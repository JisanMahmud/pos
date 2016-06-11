package com.inzaana.pos.managers;

import java.util.ArrayList;
import java.util.List;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.models.Product;
import com.inzaana.pos.utils.DBResponse;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.ResponseMessage;

public class ProductManager
{

	DBManager	dbManager;

	public ProductManager()
	{
		dbManager = new DBManager();
	}

	public List<Product> getProducts(String userName)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return new ArrayList<Product>();
		}

		return dbManager.getProducts(userName);
	}

	public List<Product> getAllProducts()
	{
		return dbManager.getProducts("");
	}

	public String addNewProduct(String userName, Product newProduct)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return DBResponse.USER_CAN_NOT_ACCESS_DB.toString();
		}

		return newProduct.insertRecordIntoDB(userName);
	}

	public String updateProduct(String userName, Product updatedProduct)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return DBResponse.USER_CAN_NOT_ACCESS_DB.toString();
		}

		return updatedProduct.updateRecordInDB(userName);
	}

	public String deleteProduct(String userName, String productId)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return DBResponse.USER_CAN_NOT_ACCESS_DB.toString();
		}

		Integer userNameId = dbManager.getUserNameIdFromName(userName);
		if (userNameId < 0)
		{
			return DBResponse.USER_NAME_NOT_VALID.toString();
		}

		String sqlQuery = "DELETE FROM " + DBTables.PRODUCTS.toString() + " WHERE " + Product.USER_ID + "=? AND "
				+ Product.ID + "=?;";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userNameId);
		paramList.add(productId);

		ResponseMessage response = new ResponseMessage();
		dbManager.executeUpdate(sqlQuery, paramList, response);

		return response.getMessage();
	}

}
