package com.inzaana.pos.models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.utils.DBResponse;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.ResponseMessage;

@XmlRootElement
public class StockDiary
{

	final static public String	ID						= "ID";
	final static public String	USER_ID					= "USER_ID";
	final static public String	DATENEW					= "DATENEW";
	final static public String	REASON					= "REASON";
	final static public String	LOCATION				= "LOCATION";
	final static public String	PRODUCT					= "PRODUCT";
	final static public String	ATTRIBUTESETINSTANCE_ID	= "ATTRIBUTESETINSTANCE_ID";
	final static public String	UNITS					= "UNITS";
	final static public String	PRICE					= "PRICE";
	final static public String	APPUSER					= "APPUSER";

	private String				id;
	private int					userId;
	private String				dateNew;
	private int					reason;
	private String				location;
	private String				product;
	private String				attributeSetInstance_id;
	private double				units;
	private double				price;
	private String				appUser;

	public StockDiary()
	{

	}

	/**
	 * @param id
	 * @param dateNew
	 * @param reason
	 * @param location
	 * @param product
	 * @param attributeSetInstance_id
	 * @param units
	 * @param price
	 * @param appUser
	 */
	public StockDiary(String id, String dateNew, int reason, String location, String product,
			String attributeSetInstance_id, double units, double price, String appUser)
	{
		this.id = id;
		this.dateNew = dateNew;
		this.reason = reason;
		this.location = location;
		this.product = product;
		this.attributeSetInstance_id = attributeSetInstance_id;
		this.units = units;
		this.price = price;
		this.appUser = appUser;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getDateNew()
	{
		return dateNew;
	}

	public void setDateNew(String dateNew)
	{
		this.dateNew = dateNew;
	}

	public int getReason()
	{
		return reason;
	}

	public void setReason(int reason)
	{
		this.reason = reason;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getProduct()
	{
		return product;
	}

	public void setProduct(String product)
	{
		this.product = product;
	}

	public String getAttributeSetInstance_id()
	{
		return attributeSetInstance_id;
	}

	public void setAttributeSetInstance_id(String attributeSetInstance_id)
	{
		this.attributeSetInstance_id = attributeSetInstance_id;
	}

	public double getUnits()
	{
		return units;
	}

	public void setUnits(double units)
	{
		this.units = units;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getAppUser()
	{
		return appUser;
	}

	public void setAppUser(String appUser)
	{
		this.appUser = appUser;
	}

	// ------------------------------------------------------------

	public String insertRecordIntoDB(String userName)
	{
		String sqlQuery = "INSERT INTO " + DBTables.STOCKDIARY.toString() + "(`ID`, `USER_ID`, `DATENEW`, `REASON`, `LOCATION`, `PRODUCT`, "
				+ "`ATTRIBUTESETINSTANCE_ID`, `UNITS`, `PRICE`, `APPUSER`) VALUES (?,?,?,?,?,?,?,?,?,?)";

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
		String sqlQuery = "UPDATE " + DBTables.STOCKDIARY.toString() + " SET `ID`=?,`USER_ID`=?,`DATENEW`=?,`REASON`=?,`LOCATION`=?,`PRODUCT`=?"
				+ ",`ATTRIBUTESETINSTANCE_ID`=?,`UNITS`=?,`PRICE`=?,`APPUSER`=? WHERE (`USER_ID`=? AND `ID`=?)";

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
		paramList.add(dateNew);
		paramList.add(reason);
		paramList.add(location);
		paramList.add(product);
		paramList.add(attributeSetInstance_id);
		paramList.add(units);
		paramList.add(price);
		paramList.add(appUser);

		return paramList;
	}

}
