package com.inzaana.pos.managers;

import java.util.ArrayList;
import java.util.List;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.utils.DBTables;

public class CategoryManager
{
	DBManager	dbManager;

	public CategoryManager()
	{
		dbManager = new DBManager();
	}

	public List<Category> getCategoryItems(String userName)
	{

		// Category cat_1 = new Category("dsfsdfasdfasfdsdf", "Food", "sdfasf",
		// "sdfsaf", "dfsdf", 10);
		// Category cat_2 = new Category("dsfsdfasdfasfdsdf", "Drink", "sdfasf",
		// "sdfsaf", "dfsdf", 10);
		//
		// List<Category> categoryList = new ArrayList<Category>();
		// categoryList.add(cat_1);
		// categoryList.add(cat_2);
		
		if (!dbManager.validateUserName(userName))
		{
			return new ArrayList<Category>();
		}

		return dbManager.getCategoryItems(userName);
	}

	public List<Category> getAllCategoryItems()
	{

		// Category cat_1 = new Category("dsfsdfasdfasfdsdf", "Food", "sdfasf",
		// "sdfsaf", "dfsdf", 10);
		// Category cat_2 = new Category("dsfsdfasdfasfdsdf", "Drink", "sdfasf",
		// "sdfsaf", "dfsdf", 10);
		// Category cat_3 = new Category("dsfsdfasdfasfdsdf", "Furniture",
		// "sdfasf", "sdfsaf", "dfsdf", 10);
		//
		// List<Category> categoryList = new ArrayList<Category>();
		// categoryList.add(cat_1);
		// categoryList.add(cat_2);
		// categoryList.add(cat_3);

		return dbManager.getCategoryItems("");
	}

	public boolean addCategory(String userName, Category newCategoryItem)
	{
		if (!dbManager.validateUserName(userName))
		{
			return false;
		}

		return newCategoryItem.insertRecordIntoDB(userName);
	}

	public boolean updateCategory(String userName, String categoryId, Category updatedCategoryItem)
	{
		if (!dbManager.validateUserName(userName))
		{
			return false;
		}

		return updatedCategoryItem.updateRecordInDB(userName);
	}

	public boolean deleteCategory(String userName, String categoryName)
	{
		if (!dbManager.validateUserName(userName))
		{
			return false;
		}

		String sqlQuery = "DELETE FROM " + DBTables.CATEGORIES.toString() + " WHERE USER_ID=? AND NAME=?;";

		ArrayList<String> paramList = new ArrayList<>();
		paramList.add(userName);
		paramList.add(categoryName);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}
}
