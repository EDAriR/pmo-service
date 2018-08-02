package com.syntrontech.pmo.service.imp;

import java.util.Optional;

import com.syntrontech.pmo.redis.model.RedisUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syntrontech.pmo.repository.RedisUserRepository;
import com.syntrontech.pmo.service.RedisUserService;
import com.syntrontech.redis.RedisHashRepositoryProxy;

import redis.clients.jedis.JedisPool;

@Service
public class RedisUserServiceImp implements RedisUserService {
	
	@Autowired
	private JedisPool pool;

	private RedisUserRepository getRepository() {
		return RedisHashRepositoryProxy.getRepository(pool, RedisUserRepository.class);
	}


	@Override
	public Optional<RedisUser> findRedisUserByUserId(String userId) {
		return getRepository().get(userId);
	}
	
}
