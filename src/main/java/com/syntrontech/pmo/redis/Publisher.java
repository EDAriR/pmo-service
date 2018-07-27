package com.syntrontech.pmo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class Publisher {
	
	@Autowired
	private JedisPool pool;
	
	private static final Logger logger = LoggerFactory.getLogger(Publisher.class);
	
	public void publish(String topic, Object object){
		ObjectMapper objMapper = new ObjectMapper();
		String jsonString;
		try (Jedis jedis = pool.getResource();) {
			
			jsonString = objMapper.writeValueAsString(object);
			jedis.publish(topic, jsonString);
			logger.trace("publish message[{}] to topic[{}]", jsonString, topic);
			
		} catch (JsonProcessingException e) {
			logger.error("Json object convert error: {}", e.getMessage());
		}
	}
}
