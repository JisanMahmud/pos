package com.inzaana.pos.managers;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import com.inzaana.pos.models.EmailContent;
import com.inzaana.pos.utils.ResponseMessage;
import com.inzaana.pos.utils.Utility;

public class SmsManager {
	
	public SmsManager()
	{
		
	}
	
	public static boolean sendSms(EmailContent smsContent, ResponseMessage response)
	{	
		if (smsContent == null)
		{
			response.setMessage("Sms Content is Empty.");
			return false;
		}
		
		String userNumber = smsContent.getUserPhoneNumber();

		if (!isValidPhoneNumber(userNumber)) {
			response.setMessage(userNumber + " is not a valid email address");
			return false;
		}
	    
		ClientConfig config = new ClientConfig();
		Client client  = ClientBuilder.newClient(config);
		client.register(new LoggingFilter());
	    WebTarget target = client.target(getUrlForSmsProvider(smsContent));
	    Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_XML);
	    
	    boolean result = false;
	    Response webResponse = null;
	    try {
	    	webResponse = invocationBuilder.get();
	    	response.setMessage(webResponse.readEntity(String.class));
            response.setStatusCode(Status.fromStatusCode(webResponse.getStatus()));
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatusCode(Status.INTERNAL_SERVER_ERROR);    
        }
	    
		return result;
	}
	
	private static boolean isValidPhoneNumber(String number)
	{
		if (number.isEmpty()) {
			return false;
		}
		
		// TODO: Validate User Number according to the Indian rules.
		return true;
	}
	
	private static String getUrlForSmsProvider(EmailContent smsContent)
	{
		String userName = "gaurav";
		String password = "12345678";
		String clientPhoneNumber = smsContent.getUserPhoneNumber();
		String smsBody = getSmsBody(smsContent);
		smsBody = smsBody.replace(" ", "%20");
		
		String url = "http://sms.pandyasoftwares.com/sendsms.jsp?user="
				+ userName + "&password=" + password + "&mobiles=" 
				+ clientPhoneNumber + "&sms=" + smsBody;
		
		return url;
	}
	
	private static String getSmsBody(EmailContent smsContent) {
		String smsBody = smsContent.getCompanyName() + ". Payment Received. ";
		smsBody += "Total: Rs. " + Utility.round(smsContent.getTotalPrice(), 2)
				+ ". ";
		smsBody += "Paid: Rs. " + Utility.round(smsContent.getTotalPaid(), 2)
				+ ". ";
		smsBody += "Returned: Rs. "
				+ Math.abs(Utility.round(smsContent.getTotalPaid() - smsContent.getTotalPrice(),2))
				+ ".";

		return smsBody;
	}
	
	public static void main(String[] args) {
		System.out.println("Sending Sms");
		
		EmailContent content = new EmailContent("SixtyNine", "Jisan Mahmud",
				"Farmgate", "jisanmahmud69@gmail.com", "9829929797", 500, 100);
		ResponseMessage response = new ResponseMessage();

		System.out.println(getUrlForSmsProvider(content));
		//sendSms(content, response);
		//System.out.println(response.getMessage());
	}

}


