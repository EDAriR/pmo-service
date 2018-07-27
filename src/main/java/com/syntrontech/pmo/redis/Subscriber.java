package com.syntrontech.pmo.redis;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Subscriber extends BinaryJedisPubSub implements Runnable {
	
	private SubscribeMessageHandlerController controller = new SubscribeMessageHandlerController();
	
	private JedisPool pool;
	
	public Subscriber(JedisPool pool){
		this.pool = pool;
	}
	
	public Subscriber(){
	}
	
	@Override
	public void onMessage(byte[] channel, byte[] message) {
		String channelName = new String(channel);
		ObjectMapper mapper = new ObjectMapper();
		try {
			ObjectMessage objectMessage = mapper.readValue(message, ObjectMessage.class);
			controller.handleMessage(channelName, objectMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Jedis jedis = pool.getResource();
		jedis.subscribe(this, "unit".getBytes(),"user".getBytes(), "ownership".getBytes());
	}
}
