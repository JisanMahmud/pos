package com.inzaana.pos.utils;

public enum DBTables
{
	CATEGORIES("CATEGORIES"), //
	PRODUCTS("PRODUCTS"), //
	PAYMENTS("Payment"), //
	STOCKDIARY("STOCKDIARY"), //
	USERS("USERS"); //

	String	tableName;

	DBTables(String tableName)
	{
		this.tableName = tableName;
	}

	String ToString()
	{
		return this.tableName;
	}

}
