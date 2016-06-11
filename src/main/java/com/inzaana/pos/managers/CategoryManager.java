package com.inzaana.pos.managers;

import java.util.ArrayList;
import java.util.List;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.utils.DBResponse;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.ResponseMessage;

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

	public String addCategory(String userName, Category newCategoryItem)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return DBResponse.USER_CAN_NOT_ACCESS_DB.toString();
		}

		return newCategoryItem.insertRecordIntoDB(userName);
	}

	public String updateCategory(String userName, Category updatedCategoryItem)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return DBResponse.USER_CAN_NOT_ACCESS_DB.toString();
		}

		return updatedCategoryItem.updateRecordInDB(userName);
	}

	public String deleteCategory(String userName, String categoryId)
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

		String sqlQuery = "DELETE FROM " + DBTables.CATEGORIES.toString() + " WHERE USER_ID=? AND ID=?;";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userNameId);
		paramList.add(categoryId);

		ResponseMessage response = new ResponseMessage();
		dbManager.executeUpdate(sqlQuery, paramList, response);

		return response.getMessage();
	}
}
