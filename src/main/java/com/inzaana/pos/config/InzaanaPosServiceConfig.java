package com.inzaana.pos.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.WebApplicationException;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.inzaana.pos.filters.AuthenticationRequestFilter;
import com.inzaana.pos.utils.POSConfig;
import com.inzaana.pos.utils.ResponseMessage;

@ApplicationPath("/")
public class InzaanaPosServiceConfig extends ResourceConfig {

	public InzaanaPosServiceConfig() {
		packages("com.inzaana.pos");
		register(LoggingFilter.class);
		register(RolesAllowedDynamicFeature.class);
		register(AuthenticationRequestFilter.class);
		
		ResponseMessage response =  new ResponseMessage();
		if (!POSConfig.initializePosConfig(response))
		{
			throw new WebApplicationException(response.getMessage(), response.getStatusCode());
		}
	}
}