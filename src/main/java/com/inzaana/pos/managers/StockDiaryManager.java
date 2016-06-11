package com.inzaana.pos.managers;

import java.util.ArrayList;
import java.util.List;

import com.inzaana.pos.db.DBManager;
import com.inzaana.pos.models.StockDiary;
import com.inzaana.pos.utils.DBResponse;
import com.inzaana.pos.utils.DBTables;
import com.inzaana.pos.utils.ResponseMessage;

public class StockDiaryManager
{
	DBManager	dbManager;

	public StockDiaryManager()
	{
		dbManager = new DBManager();
	}

	public List<StockDiary> getStockDiaryItems(String userName)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return new ArrayList<StockDiary>();
		}

		return dbManager.getStockDiaryItems(userName);
	}

	public List<StockDiary> getAllStockDiaryItems()
	{
		return dbManager.getStockDiaryItems("");
	}

	public String addStockDiaryItem(String userName, StockDiary newStockDiaryItem)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return DBResponse.USER_CAN_NOT_ACCESS_DB.toString();
		}

		return newStockDiaryItem.insertRecordIntoDB(userName);
	}

	public String updateStockDiaryItem(String userName, StockDiary updatedStockDiaryItem)
	{
		if (!dbManager.canUserDoDBTransaction(userName))
		{
			return DBResponse.USER_CAN_NOT_ACCESS_DB.toString();
		}

		return updatedStockDiaryItem.updateRecordInDB(userName);
	}

	public String deleteStockDiaryItem(String userName, String StockDiaryId)
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

		String sqlQuery = "DELETE FROM " + DBTables.STOCKDIARY.toString() + " WHERE USER_ID=? AND ID=?;";

		ArrayList<Object> paramList = new ArrayList<>();
		paramList.add(userNameId);
		paramList.add(StockDiaryId);

		ResponseMessage response = new ResponseMessage();
		dbManager.executeUpdate(sqlQuery, paramList, response);

		return response.getMessage();
	}
}
