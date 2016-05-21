package com.inzaana.pos.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.inzaana.pos.filters.AuthenticationRequestFilter;

@ApplicationPath("/")
public class InzaanaPosServiceConfig extends ResourceConfig {

	public InzaanaPosServiceConfig() {
		packages("com.inzaana.pos");
		register(LoggingFilter.class);
		register(RolesAllowedDynamicFeature.class);
		register(AuthenticationRequestFilter.class);
	}
}