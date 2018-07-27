package com.syntrontech.pmo.redis;

public interface SubscribeMessageHandler {
	void handleUpdateAction(String id);
	void handleDeleteAction(String id);
}
