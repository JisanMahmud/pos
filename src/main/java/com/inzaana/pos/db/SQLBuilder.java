package com.inzaana.pos.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SQLBuilder
{
	public SQLBuilder()
	{

	}

	public String getSQLForPassword(String userName)
	{
		//String sql = "SELECT pass FROM Users WHERE name=\"" + userName + "\"";
		String tableName = "Users";
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("pass");
		
		return getSelectSqlString(tableName, columnNames, new HashMap<String, String>());
	}

	public String getSQLForUserRole(String userName)
	{
		//String sql = "SELECT role FROM Users WHERE name=\"" + userName + "\"";
		String tableName = "Users";
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("pass");
		
		return getSelectSqlString(tableName, columnNames, new HashMap<String, String>());
	}

	public String getSelectSqlString(String tableName, ArrayList<String> columnNames, HashMap<String, String> queries)
	{
		String selectSql = "SELECT";
		selectSql.concat(" ");

		if (columnNames.isEmpty())
		{
			return "Table column name list can not be empty";
		}

		for (int i = 0; i < columnNames.size(); i++)
		{
			selectSql.concat(columnNames.get(i));
			if (i < (columnNames.size() - 1))
			{
				selectSql.concat(",");
			}
		}

		selectSql.concat(" ");
		selectSql.concat("FROM");
		selectSql.concat(" ");
		selectSql.concat(tableName);

		if (queries.size() > 0)
		{
			selectSql.concat(" ");
			selectSql.concat("WHERE");
			selectSql.concat(" ");
			Iterator it = queries.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry pair = (Map.Entry) it.next();
				selectSql.concat(pair.getKey() + "=" + pair.getValue());
				if (it.hasNext())
				{
					selectSql.concat(",");
				}
			}
		}

		selectSql.concat(";");

		return null;
	}
}
