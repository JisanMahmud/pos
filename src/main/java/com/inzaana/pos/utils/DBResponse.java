package com.inzaana.pos.utils;

public enum DBResponse
{
	SUCCESS("SUCCESS"), //
	DB_NOT_FOUND("DB_NOT_FOUND"), //
	TABLE_NOT_FOUND("TABLE_NOT_FOUND"), //
	DUPLICATE_ENTRY("DUPLICATE_ENTRY"), //
	FAILURE("FAILURE"), //
	REGISTRATION_FAILURE("NEW USER REGISTRATION FAILED"), //
	USER_ID_NOT_VALID("USER ID NOT VALID"), //
	USER_NAME_NOT_VALID("USER NAME NOT VALID"), //
	USER_CAN_NOT_ACCESS_DB("USER CAN NOT ACCESS DB"), //
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
