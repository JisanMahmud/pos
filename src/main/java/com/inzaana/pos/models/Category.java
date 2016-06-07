package com.inzaana.pos.models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.Pair;

@XmlRootElement
public class Category
{

	final static public String					ID			= "ID";
	final static public String					USER_ID		= "USER_ID";
	final static public String					NAME		= "NAME";
	final static public String					PARENTID	= "PARENTID";
	final static public String					IMAGE		= "IMAGE";
	final static public String					TEXTTIP		= "TEXTTIP";
	final static public String					CATSHOWNAME	= "CATSHOWNAME";

	private String								id;
	private int									userId;
	private String								name;
	private String								parentId;
	private String								image;
	private String								textTip;
	private boolean								catShowName;

	private ArrayList<Pair<String, String>>		categoryTableStringList;
	private ArrayList<Pair<String, Integer>>	categoryTableIntegerList;

	public Category()
	{
		categoryTableStringList = new ArrayList<>();
		categoryTableIntegerList = new ArrayList<>();
	}

	/**
	 * @param id
	 * @param name
	 * @param parentId
	 * @param image
	 * @param textTip
	 * @param catShowName
	 */
	public Category(String id, String name, String parentId, String image, String textTip, boolean catShowName)
	{
		categoryTableStringList = new ArrayList<>();
		categoryTableIntegerList = new ArrayList<>();

		this.setId(id);
		this.setName(name);
		this.setParentId(parentId);
		this.setImage(image);
		this.setTextTip(textTip);
		this.setCatShowName(catShowName);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
		categoryTableStringList.add(new Pair<String, String>(ID, id));
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
		categoryTableStringList.add(new Pair<String, String>(NAME, name));
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
		categoryTableStringList.add(new Pair<String, String>(PARENTID, parentId));
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
		categoryTableStringList.add(new Pair<String, String>(IMAGE, image));
	}

	public String getTextTip()
	{
		return textTip;
	}

	public void setTextTip(String textTip)
	{
		this.textTip = textTip;
		categoryTableStringList.add(new Pair<String, String>(TEXTTIP, textTip));
	}

	public boolean getCatShowName()
	{
		return catShowName;
	}

	public void setCatShowName(boolean catShowName)
	{
		this.catShowName = catShowName;
		int intValue = catShowName ? 1 : 0;
		categoryTableIntegerList.add(new Pair<String, Integer>(CATSHOWNAME, intValue));
	}

	// ------------------------------------------------------------

	public boolean insertRecordIntoDB(String userName)
	{

		String sqlQuery = "INSERT INTO " + DBTables.CATEGORIES.toString() + " (";
		String colNameList = USER_ID;
		String colValueList = "?";

		for (int i = 0; i < categoryTableStringList.size(); i++)
		{
			colNameList += ", " + categoryTableStringList.get(i).getFirst();
			colValueList += ", '" + categoryTableStringList.get(i).getSecond() + "'";
		}

		for (int i = 0; i < categoryTableIntegerList.size(); i++)
		{
			colNameList += ", " + categoryTableIntegerList.get(i).getFirst();
			colValueList += ", " + categoryTableIntegerList.get(i).getSecond().toString();
		}

		sqlQuery += colNameList + ") " + "VALUES (" + colValueList + ");";

		DBManager dbManager = new DBManager();

		if (!dbManager.validateUserId(userName))
		{
			return false;
		}

		Integer userNameId = dbManager.getUserNameIdFromName(userName);
		if (userNameId < 0)
		{
			return false;
		}

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userNameId);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}

	public boolean updateRecordInDB(String userName)
	{

		String sqlQuery = "UPDATE " + DBTables.CATEGORIES.toString() + " SET ";

		for (int i = 0; i < categoryTableStringList.size(); i++)
		{
			sqlQuery += categoryTableStringList.get(i).getFirst() + "=";
			sqlQuery += "'" + categoryTableStringList.get(i).getSecond() + "'";

			if (i < (categoryTableStringList.size() - 1))
			{
				sqlQuery += ", ";
			}
		}

		for (int i = 0; i < categoryTableIntegerList.size(); i++)
		{
			sqlQuery += ", " + categoryTableIntegerList.get(i).getFirst() + "=";
			sqlQuery += categoryTableIntegerList.get(i).getSecond().toString();
		}

		sqlQuery += " WHERE " + USER_ID + "=? AND " + ID + "=?;";

		DBManager dbManager = new DBManager();

		if (!dbManager.validateUserId(userName))
		{
			return false;
		}
		
		Integer userNameId = dbManager.getUserNameIdFromName(userName);
		if (userNameId < 0)
		{
			return false;
		}

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userNameId);
		paramList.add(id);

		return dbManager.executeUpdate(sqlQuery, paramList);
	}
}
