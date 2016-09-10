package com.inzaana.pos.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.util.Tokenizer;

public class POSConfig {

	public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static String DB_URL = "jdbc:mysql://localhost:3306/pos";
	public static String DB_USER_NAME = "root";
	public static String DB_PASS = "";
	private final static String CONFIG_FILE = "/onlyne.config";

	public static boolean initializePosConfig(ResponseMessage response) {
		System.out.println("Initializing Config File.");
		
		try {
			for (String line : Files.readAllLines(Paths.get(CONFIG_FILE))) {
				System.out.println("[CONFIG_FILE] " + line);
				
				String token[] = line.split("=");
				if (token.length > 2)
				{
					response.setMessage("Invalid token in config file.");
					response.setStatusCode(Status.INTERNAL_SERVER_ERROR);
					return false;
				}
				
				String key = token[0].trim();
				String value = "";
				if (token.length == 2)
				{
					value = token[1].trim();
				}
				
				if (key.equals("DB_URL"))
				{
					DB_URL = value.trim();
				}
				else if (key.equals("DB_USER_NAME"))
				{
					DB_USER_NAME = value;
				}
				else if (key.equals("DB_PASS"))
				{
					DB_PASS = value;
				}
				else
				{
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (e.getMessage().contains("NoSuchFileException"))
			{
				response.setMessage("onlyne.config file not found.");
			}
			else{
				response.setMessage(e.getMessage());
			}
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR);
			return false;
		}

		return true;
	}
}
