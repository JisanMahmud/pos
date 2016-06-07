package com.inzaana.pos.utils;

public class ErrorMessage
{
	private String	errorMessage	= "";

	public ErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public ErrorMessage()
	{

	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage()
	{
		return this.errorMessage;
	}

	public void clear()
	{
		this.errorMessage = "";
	}

}
