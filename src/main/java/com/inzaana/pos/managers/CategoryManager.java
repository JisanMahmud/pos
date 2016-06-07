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
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return new ArrayList<Category>();
		}

		return dbManager.getCategoryItems(userName);
	}

	public List<Category> getAllCategoryItems()
	{
		return dbManager.getCategoryItems("");
	}

	public boolean addCategory(String userName, Category newCategoryItem)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return false;
		}

		return newCategoryItem.insertRecordIntoDB(userName);
	}

	public boolean updateCategory(String userName, Category updatedCategoryItem)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return false;
		}

		return updatedCategoryItem.updateRecordInDB(userName);
	}

	public boolean deleteCategory(String userName, String categoryId)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return false;
		}

		Integer userNameId = dbManager.getUserNameIdFromName(userName);
		if (userNameId < 0)
		{
			return false;
		}

		String sqlQuery = "DELETE FROM " + DBTables.CATEGORIES.toString() + " WHERE USER_ID=? AND ID=?;";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userNameId);
		paramList.add(categoryId);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}
}
