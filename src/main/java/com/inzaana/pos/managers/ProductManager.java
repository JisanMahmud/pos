package com.inzaana.pos.managers;

import java.util.ArrayList;
import java.util.List;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.models.Product;
import com.inzaana.pos.utils.DBTables;

public class ProductManager
{

	DBManager	dbManager;

	public ProductManager()
	{
		dbManager = new DBManager();
	}

	public List<Product> getProducts(String userName)
	{
		if (!dbManager.validateUserName(userName))
		{
			return new ArrayList<Product>();
		}

		return dbManager.getProducts(userName);
	}

	public List<Product> getAllProducts()
	{
		return dbManager.getProducts("");
	}

	public boolean addNewProduct(String userName, Product newProduct)
	{
		if (!dbManager.validateUserName(userName))
		{
			return false;
		}

		return newProduct.insertRecordIntoDB(userName);
	}

	public boolean updateProduct(String userName, String productName, Product updatedProduct)
	{
		if (!dbManager.validateUserName(userName))
		{
			return false;
		}

		return updatedProduct.updateRecordInDB(userName);
	}

	public boolean deleteProduct(String userName, String productName)
	{
		if (!dbManager.validateUserName(userName))
		{
			return false;
		}

		String sqlQuery = "DELETE FROM " + DBTables.PRODUCTS.toString() + " WHERE " + Product.USER_ID + "=? AND "
				+ Product.NAME + "=?;";

		ArrayList<String> paramList = new ArrayList<>();
		paramList.add(userName);
		paramList.add(productName);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}

}
