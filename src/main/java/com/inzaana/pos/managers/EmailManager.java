package com.inzaana.pos.managers;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.inzaana.pos.models.EmailContent;
import com.inzaana.pos.utils.ResponseMessage;
import com.inzaana.pos.utils.Utility;

public class EmailManager {

	private static String MAIL_HOST = "p3plcpnl0171.prod.phx3.secureserver.net";
	private static String MAIL_PORT = "465";
	private static String MAIL_PROTOCOL = "smtp";
	private static String MAIL_SSL_ENABLED = "true";
	private static String MAIL_SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static String MAIL_AUTH_ENABLED = "true";
	private static String MAIL_TLS_ENABLED = "true";
	private static String MAIL_USER_NAME = "admin.noreply@inzaana.com";
	private static String MAIL_USER_PASS = "!Inzaana&Email#";
	private static String MAIL_FORMAT = "text/html";

	public static boolean sendEmail(EmailContent emailContent,
			ResponseMessage response) {
		
		if (emailContent == null)
		{
			response.setMessage("Email Content is Empty.");
			return false;
		}
		
		String userEmail = emailContent.getUserEmail();

		if (!isValidEmailAddress(userEmail)) {
			response.setMessage(userEmail + " is not a valid email address");
			return false;
		}

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", MAIL_HOST);
		properties.put("mail.smtp.port", MAIL_PORT);
		properties.put("mail.transport.protocol", MAIL_PROTOCOL);
		properties.put("mail.smtp.ssl.enable", MAIL_SSL_ENABLED);
		properties.put("mail.smtp.ssl.trust", MAIL_HOST);
		properties.put("mail.smtp.socketFactory.class", MAIL_SOCKET_FACTORY);
		properties.put("mail.smtp.socketFactory.port", MAIL_PORT);
		properties.put("mail.smtp.auth", MAIL_AUTH_ENABLED);
		properties.put("mail.smtp.starttls.enable", MAIL_TLS_ENABLED);

		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MAIL_USER_NAME,
						MAIL_USER_PASS);
			}
		};

		Session session = Session.getDefaultInstance(properties, auth);
		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(MAIL_USER_NAME));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					userEmail));
			message.setSubject(getEmailSubject());
			message.setContent(getEmailBody(emailContent), MAIL_FORMAT);
			;
			Transport.send(message);
			response.setMessage("Email sent successfully");
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
			response.setMessage("Email sending failed.\n" + mex.getMessage());
			return false;
		}

		return true;
	}

	private static String getEmailSubject() {
		return "Payment Confirmation";
	}

	private static String getEmailBody(EmailContent emailContent) {
		String emailbody = "";
		emailbody += "<h4>Onlyne The Most Advanced POS</h4>";
		emailbody += "</br></br>";
		emailbody += "<h1>" + emailContent.getCompanyName() + "</h1>";
		emailbody += "</br></br>";
		emailbody += "<h4>Name    : " + emailContent.getUserName()
				+ "</h4> </br>";
		emailbody += "<h4>Address : " + emailContent.getUserAddress()
				+ "</h4> </br>";
		emailbody += "<h4>Phone   : " + emailContent.getUserPhoneNumber()
				+ "</h4> </br>";
		emailbody += "</br></br>";
		emailbody += "<h3>Total Price : Rs. " + Utility.round(emailContent.getTotalPrice(), 2)
				+ "</h4> </br>";
		emailbody += "<h3>Total Paid  : Rs. " + Utility.round(emailContent.getTotalPaid(), 2)
				+ "</h4> </br>";
		emailbody += "<h3>Returned    : Rs. "
				+ Math.abs(Utility.round((emailContent.getTotalPaid() - emailContent.getTotalPrice()), 2))
				+ "</h4> </br>";
		emailbody += "</br></br>";
		emailbody += "<h4>Thank you for shopping with us</h4>";

		return emailbody;
	}

	private static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static void main(String[] args) {

		String to = "jisanmahmud69@gmail.com";
		EmailContent content = new EmailContent("SixtyNine", "Jisan Mahmud",
				"Farmgate", to, "01674388964", 50, 100);
		ResponseMessage response = new ResponseMessage();

		sendEmail(content, response);
		System.out.println(response.getMessage());
	}

}
