package com.inzaana.pos.models;

import java.security.Principal;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.Pair;
import com.inzaana.pos.utils.ResponseMessage;

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


	public User(String userName, String userId, String userPassword, String userRole)
	{
		this.setUserName(userName);
		this.setUserId(userId);
		this.setUserPassword(userPassword);
		this.setUserRole(userRole);
		
		System.out.println("USER NAME: " + userName);
		System.out.println("USER ID: " + userId);
		System.out.println("USER PASSWORD: " + userPassword);
		System.out.println("USER ROLE: " + userRole);
	}

	public User()
	{
	}

	@Override
	public String getName()
	{
		return userName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserPassword()
	{
		return userPassword;
	}

	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}

	public String getUserRole()
	{
		return userRole;
	}

	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}

	public boolean updateRecordInDB(String userID)
	{
		return false;
	}
}
