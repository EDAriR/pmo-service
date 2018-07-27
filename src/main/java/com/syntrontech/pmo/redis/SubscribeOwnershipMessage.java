package com.syntrontech.pmo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscribeOwnershipMessage  implements SubscribeMessageHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(SubscribeOwnershipMessage.class);
	
	@Override
	public void handleUpdateAction(String id) {
		
		logger.debug("Subscribe Ownership Message UpdateAction successful");
		
	}

	@Override
	public void handleDeleteAction(String id) {
		logger.debug("Subscribe Ownership Message DeleteAction successful");
	}

}
