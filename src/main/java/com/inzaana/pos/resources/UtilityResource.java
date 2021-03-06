package com.inzaana.pos.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.inzaana.pos.managers.EmailManager;
import com.inzaana.pos.managers.SmsManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.models.EmailContent;
import com.inzaana.pos.utils.ResponseMessage;

/*
 * For Getting/Setting Utility services
 */
@Path("/util")
public class UtilityResource {
	
	final private String	SUCCESS_MSG		= "SUCCESS";
	final private String	FAILURE_MSG		= "FAILED";

	ResponseMessage response = new ResponseMessage();
	
	@PUT
	@Path("/email")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String sendEmail(EmailContent emailContent)
	{
		//System.out.println("[Jisan_TEST]Total Price: " + String.valueOf(emailContent.getTotalPrice()));
		//System.out.println("[Jisan_TEST]Total Paid: " + String.valueOf(emailContent.getTotalPaid()));
		
		EmailManager emailManager = new EmailManager();
		if (!emailManager.sendEmail(emailContent, response))
		{
			return FAILURE_MSG + "_" + response.getMessage();
		}
		
		return SUCCESS_MSG + "_" + response.getMessage();
	}
	
	@PUT
	@Path("/sms")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String sendSms(EmailContent smsContent)
	{
		SmsManager smsManager = new SmsManager();
		if (!smsManager.sendSms(smsContent, response))
		{
			return FAILURE_MSG + "_" + response.getMessage();
		}
		
		return SUCCESS_MSG + "_" + response.getMessage();
	}
}
