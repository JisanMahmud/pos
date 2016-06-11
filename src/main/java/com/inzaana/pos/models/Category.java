package com.inzaana.pos.models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.managers.UserManager;
import com.inzaana.pos.utils.DBResponse;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.Pair;
import com.inzaana.pos.utils.ResponseMessage;

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

	public String insertRecordIntoDB(String userName)
	{
		System.out.println("[TEST] id: " + id);
		System.out.println("[TEST] user id: " + userId);
		System.out.println("[TEST] name: " + name);
		System.out.println("[TEST] tip: " + textTip);
		
		
		String sqlQuery = "INSERT INTO `categories`(`ID`, `USER_ID`, `NAME`, `PARENTID`, `IMAGE`, `TEXTTIP`, `CATSHOWNAME`) "
				+ "VALUES (?,?,?,?,?,?,?)";

		DBManager dbManager = new DBManager();

		if (!dbManager.validateUserName(userName))
		{
			return DBResponse.USER_NAME_NOT_VALID.toString();
		}

		Integer userNameId = dbManager.getUserNameIdFromName(userName);
		ArrayList<Object> paramList = getColumnValueList(userNameId);

		ResponseMessage response = new ResponseMessage();
		dbManager.executeUpdate(sqlQuery, paramList, response);

		return response.getMessage();

	}

	public String updateRecordInDB(String userName)
	{
		String sqlQuery = "UPDATE `categories` SET `ID`=?,`USER_ID`=?,`NAME`=?,`PARENTID`=?,`IMAGE`=?,"
				+ "`TEXTTIP`=?,`CATSHOWNAME`=? WHERE (`USER_ID`=? AND `ID`=?)";

		DBManager dbManager = new DBManager();

		if (!dbManager.validateUserName(userName))
		{
			return DBResponse.USER_ID_NOT_VALID.toString();
		}

		Integer userNameId = dbManager.getUserNameIdFromName(userName);

		ArrayList<Object> paramList = getColumnValueList(userNameId);
		paramList.add(userNameId);
		paramList.add(id);

		ResponseMessage response = new ResponseMessage();
		dbManager.executeUpdate(sqlQuery, paramList, response);

		return response.getMessage();

	}

	private ArrayList<Object> getColumnValueList(int userNameId)
	{
		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(id);
		paramList.add(userNameId);
		paramList.add(name);
		paramList.add(parentId);
		paramList.add(image);
		paramList.add(textTip);
		paramList.add(catShowName);

		return paramList;
	}

}
