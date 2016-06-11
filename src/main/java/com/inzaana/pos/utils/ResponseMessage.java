package com.inzaana.pos.utils;

public class ResponseMessage
{
	private String	errorMessage	= "";

	public ResponseMessage(String message)
	{
		this.errorMessage = message;
	}

	public ResponseMessage()
	{

	}

	public void setMessage(String message)
	{
		this.errorMessage = message;
	}

	public String getMessage()
	{
		return this.errorMessage;
	}

	public void clear()
	{
		this.errorMessage = "";
	}

}
