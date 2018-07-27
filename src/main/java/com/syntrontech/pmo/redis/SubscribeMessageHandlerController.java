package com.syntrontech.pmo.redis;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscribeMessageHandlerController {
	
	private static final Logger logger = LoggerFactory.getLogger(SubscribeMessageHandlerController.class);
	
	private static Map<String, SubscribeMessageHandler> handlers = new ConcurrentHashMap<>();
	
	private SubscribeMessageHandler createHandler(String channel){
		SubscribeMessageHandler handler = null;
		if("user".equals(channel)){
//			handler = new SubscribeUserMessage();
		}
		if("ownership".equals(channel)){
			handler = new SubscribeOwnershipMessage();
		}
		return handler;
	}
	
	private Optional<SubscribeMessageHandler> getHandler(String channel){
		if(!handlers.containsKey(channel)){
			SubscribeMessageHandler handler = createHandler(channel);
			
			if(Objects.isNull(handler)){
				logger.warn("No channel[{}]'s handler to handle message", channel);
			}else {
				logger.info("create channel[{}]'s handler[{}] successfully", channel, handler.getClass().getSimpleName());
			}
			handlers.put(channel, handler);
		}
		
		return Optional.ofNullable(handlers.get(channel));
	}
	
	public void handleMessage(String channel, ObjectMessage objMessage) {
		Optional<SubscribeMessageHandler> handler = getHandler(channel);
		if(!handler.isPresent()){
			logger.warn("No handler to handle channel[{}] message", channel, objMessage);
			return;
		}
		
		if(Objects.isNull(objMessage) || Objects.isNull(objMessage.getId()) 
				|| Objects.isNull(objMessage.getAction())){
			logger.warn("The message is null or empty");
			return;
		}
		
		if(ObjectMessageAction.UPDATED.equals(objMessage.getAction())){
			handler.get().handleUpdateAction(objMessage.getId());
		}else if(ObjectMessageAction.DELETED.equals(objMessage.getAction())){
			handler.get().handleDeleteAction(objMessage.getId());
		}
	}
}
