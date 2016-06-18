package com.inzaana.pos.utils;

import javax.ws.rs.core.Response.Status;

public class ResponseMessage
{
	private String	errorMessage	= "";
	private Status statusCode	= Status.ACCEPTED;

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
	
	public void setStatusCode(Status statusCode)
	{
		this.statusCode = statusCode;
	}
	
	public Status getStatusCode()
	{
		return statusCode;
	}

	public void clear()
	{
		this.errorMessage = "";
		this.statusCode = Status.ACCEPTED;
	}

}
