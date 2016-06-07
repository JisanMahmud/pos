package com.inzaana.pos.models;

import java.security.Principal;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.Pair;

@XmlRootElement
public class User implements Principal
{

	final static public String				NAME		= "NAME";
	final static public String				USER_ID		= "USER_ID";
	final static public String				PASSWORD	= "PASSWORD";
	final static public String				ROLE		= "ROLE";

	private String							userName;
	private String							userId;
	private String							userPassword;
	private String							userRole;

	private ArrayList<Pair<String, String>>	userTableStringList;

	public User(String userName, String userId, String userPassword, String userRole)
	{
		userTableStringList = new ArrayList<>();
		this.SetUserName(userName);
		this.SetUserId(userId);
		this.SetUserPassword(userPassword);
		this.SetUserRole(userRole);
		
		System.out.println("USER NAME: " + userName);
		System.out.println("USER ID: " + userId);
		System.out.println("USER PASSWORD: " + userPassword);
		System.out.println("USER ROLE: " + userRole);
	}

	public User()
	{
		userTableStringList = new ArrayList<>();
	}

	@Override
	public String getName()
	{
		return userName;
	}

	public String GetUserName()
	{
		return userName;
	}

	public void SetUserName(String userName)
	{
		this.userName = userName;
		userTableStringList.add(new Pair<String, String>(NAME, userName));
	}

	public String GetUserId()
	{
		return userId;
	}

	public void SetUserId(String userId)
	{
		this.userId = userId;
		userTableStringList.add(new Pair<String, String>(USER_ID, userId));
	}

	public String GetUserPassword()
	{
		return userPassword;
	}

	public void SetUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
		userTableStringList.add(new Pair<String, String>(PASSWORD, userPassword));
	}

	public String GetUserRole()
	{
		return userRole;
	}

	public void SetUserRole(String userRole)
	{
		this.userRole = userRole;
		userTableStringList.add(new Pair<String, String>(ROLE, userRole));
	}

	// ------------------------------------------------------------

	public boolean insertRecordIntoDB()
	{

		String sqlQuery = "INSERT INTO " + DBTables.USERS.toString() + " (";
		String colNameList = "";
		String colValueList = "";

		for (int i = 0; i < userTableStringList.size(); i++)
		{
			colNameList += userTableStringList.get(i).getFirst();
			colValueList += "'" + userTableStringList.get(i).getSecond() + "'";

			if (i < (userTableStringList.size() - 1))
			{
				colNameList += ", ";
				colValueList += ", ";
			}
		}

		sqlQuery += colNameList + ") " + "VALUES (" + colValueList + ");";

		DBManager dbManager = new DBManager();

		if (!dbManager.canUserDoDBTransaction(getName()))
		{
			return false;
		}

		return dbManager.executeUpdate(sqlQuery, new ArrayList<Object>());
	}

	public boolean updateRecordInDB(String userID)
	{
		return false;
	}
}
