package com.inzaana.pos.utils;

public enum DBResponse
{
	SUCCESS("SUCCESS"), //
	DB_NOT_FOUND("DB_NOT_FOUND"), //
	TABLE_NOT_FOUND("TABLE_NOT_FOUND"), //
	DUPLICATE_ENTRY("DUPLICATE_ENTRY"), //
	FAILURE("FAILURE"), //
	REGISTRATION_FAILURE("NEW USER REGISTRATION FAILED"), //
	UNKNOWN("UNKNOWN ERROR"); //

	String	dbResponse;

	DBResponse(String dbResponse)
	{
		this.dbResponse = dbResponse;
	}

	@Override
	public String toString()
	{
		return this.dbResponse;
	}
}
