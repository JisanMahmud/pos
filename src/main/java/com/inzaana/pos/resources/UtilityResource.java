package com.inzaana.pos.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.inzaana.pos.managers.EmailManager;
import com.inzaana.pos.models.Category;
import com.inzaana.pos.models.EmailContent;

/*
 * For Getting/Setting Utility services
 */
@Path("/util")
public class UtilityResource {
	
	final private String	SUCCESS_MSG		= "SUCCESS";
	final private String	FAILURE_MSG		= "FAILED";

	EmailManager			userManager	= new EmailManager();

	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "ADMIN", "POS" })
	public String sendEmail(EmailContent emailContent)
	{
		return SUCCESS_MSG;
	}
}
