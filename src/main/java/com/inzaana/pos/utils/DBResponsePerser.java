package com.inzaana.pos.utils;

public class DBResponsePerser
{
	private String		dbError		= "";
	private DBResponse	dbResponse	= DBResponse.UNKNOWN;

	public DBResponsePerser(String dbError)
	{
		this.dbError = dbError;
		perseError();
	}

	private void perseError()
	{

		if (dbError.contains("DB Not Found"))
		{
			dbResponse = DBResponse.DB_NOT_FOUND;
		}
		else if (dbError.contains("Table Not Found"))
		{
			dbResponse = DBResponse.TABLE_NOT_FOUND;
		}
		else if (dbError.contains("Duplicate entry"))
		{
			dbResponse = DBResponse.DUPLICATE_ENTRY;
		}
		else
		{
			dbResponse = DBResponse.FAILURE;
		}
	}

	public DBResponse getResponse()
	{
		return this.dbResponse;
	}
}
