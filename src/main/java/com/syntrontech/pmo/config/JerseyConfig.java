package com.syntrontech.pmo.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;
import org.springframework.stereotype.Component;

import com.syntrontech.autoTool.exception.handler.ClientExceptionMapper;
import com.syntrontech.autoTool.exception.handler.ForbiddenExceptionMapper;
import com.syntrontech.autoTool.exception.handler.NotFoundExceptionMapper;
import com.syntrontech.autoTool.exception.handler.ServerExceptionMapper;
import com.syntrontech.autoTool.exception.handler.UnauthorizedExceptionMapper;
import com.syntrontech.pmo.provider.JsonObjectHandlerProvider;

/*
 * register the Endpoints in Jersey
 */

@Component
public class JerseyConfig extends ResourceConfig{
	
	public JerseyConfig(){
		registerResource();
		registerProvider();
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(JsonObjectHandlerProvider.class)				
				.to(ResourceMethodInvocationHandlerProvider.class);
			}
		});
	}
	
	private void registerResource(){
//		register(OwnershipResource.class);
	}
	
	private void registerProvider(){
		register(new ClientExceptionMapper());
		register(new ServerExceptionMapper());
		register(new ForbiddenExceptionMapper());
		register(new NotFoundExceptionMapper());
		register(new UnauthorizedExceptionMapper());
	}
}
