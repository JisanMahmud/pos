package com.inzaana.pos.managers;

import java.util.ArrayList;
import java.util.List;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.models.User;
import com.inzaana.pos.utils.DBResponse;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.UserRole;

public class UserManager
{
	public static String	USER_NAME			= "***@@@***DEFAULT_USER_NAME***@@@***";
	public static String	USER_ID				= "***@@@***DEFAULT_USER_ID***@@@***";
	public static int		USER_NAME_ID		= -555;
	public static String	NEW_USER_PASSWORD	= "***@@@***NEW_USER_PASSWORD***@@@***";
	public static String	USER_ROLE			= UserRole.GUEST.toString();

	DBManager				dbManager;

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

	public boolean addUserName(String userName)
	{
		String sqlQuery = "INSERT INTO " + DBTables.USERNAME.toString() + " (ID, NAME) VALUES (null, ?);";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userName);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}

	public boolean verifyPassword(String password)
	{
		System.out.println("Verifying Password for " + USER_ID);
		
		boolean success = true;
		String savedPassword = dbManager.getUserPassword(USER_ID);

		System.out.println("Password: " + savedPassword);
		
		if (savedPassword.equals(NEW_USER_PASSWORD))
		{
			success = addPasswordForNewUser(password);
		}
		else if (!password.equals(savedPassword))
		{
			success = false;
		}

		if (success)
		{
			USER_NAME = dbManager.getUserName(USER_ID);
			USER_ROLE = getUserRole(USER_ID);
			USER_NAME_ID = dbManager.getUserNameId(USER_ID);
		}

		return false;
	}

	public boolean addPasswordForNewUser(String password)
	{
		String sqlQuery = "UPDATE " + DBTables.USERS.toString() + " SET PASSWORD=? WHERE USER_ID=?;";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(password);
		paramList.add(USER_ID);

		System.out.println("Adding Password for new user.");
		System.out.println(sqlQuery);
		System.out.println(paramList.toString());
		
		return dbManager.executeUpdate(sqlQuery, paramList);
	}

	public int getUserNameId(String userId)
	{
		return dbManager.getUserNameId(userId);
	}

	public String getUserName(String userId)
	{
		return dbManager.getUserName(userId);
	}

	public boolean addNewUser(User newUser)
	{
		return newUser.insertRecordIntoDB();
	}

	public boolean updateUser(String userId, User updatedUser)
	{
		return false;
	}

	public boolean deleteUserId(String userId)
	{
		if (!dbManager.canUserDoDBTransaction(userId))
		{
			return false;
		}

		String sqlQuery = "DELETE FROM " + DBTables.USERS.toString() + " WHERE USER_ID=?;";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userId);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}
	
	public boolean deleteUser(String userName)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return false;
		}

		String sqlQuery = "DELETE FROM " + DBTables.USERNAME.toString() + " WHERE NAME=?;";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userName);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}

	public String getUserPassword(String userName)
	{
		return dbManager.getUserPassword(userName);
	}

	public String getUserRole(String userId)
	{
		return dbManager.getUserRole(userId);
	}

	public String registerNewUser()
	{
		if (dbManager.getUserPassword(USER_ID).equals(NEW_USER_PASSWORD))
		{
			return DBResponse.REGISTRATION_FAILURE.toString();
		}
		
		return DBResponse.SUCCESS.toString();
	}
}
