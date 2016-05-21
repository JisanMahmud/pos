package com.inzaana.pos.managers;

import java.util.ArrayList;
import java.util.List;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.models.User;
import com.inzaana.pos.utils.DBTables;

public class UserManager
{
	DBManager	dbManager;

	public UserManager()
	{
		dbManager = new DBManager();
	}

	public List<User> getUser(String userName)
	{
		return dbManager.getUsers(userName);
	}

	public List<User> getAllUsers()
	{
		return dbManager.getUsers("");
	}

	public boolean addNewUser(User newUser)
	{
		return newUser.insertRecordIntoDB();
	}

	public boolean updateUser(String userId, User updatedUser)
	{
		return false;
	}

	public boolean deleteUser(String userName)
	{
		if (!dbManager.validateUserName(userName))
		{
			return false;
		}

		String sqlQuery = "DELETE FROM " + DBTables.USERS.toString() + " WHERE NAME=?;";

		ArrayList<String> paramList = new ArrayList<>();
		paramList.add(userName);

		return dbManager.ExecuteUpdate(sqlQuery, paramList);
	}

	public String getUserPassword(String userName)
	{
		return dbManager.getUserPassword(userName);
	}

	public String getUserRole(String userName)
	{
		return dbManager.getUserRole(userName);
	}
}
