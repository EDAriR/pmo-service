package com.syntrontech.pmo.service.imp;

import java.util.List;
import java.util.Optional;

import com.syntrontech.pmo.redis.model.RedisSubject;
import com.syntrontech.pmo.repository.RedisSubjectRepository;
import com.syntrontech.pmo.service.RedisSubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.syntrontech.redis.RedisHashRepositoryProxy;

import redis.clients.jedis.JedisPool;

@Component
public class RedisSubjectServiceImp implements RedisSubjectService {

	private static final Logger logger = LoggerFactory.getLogger(RedisSubjectServiceImp.class);
	
	@Autowired
	private JedisPool pool;

	private RedisSubjectRepository getRepository() {
		return RedisHashRepositoryProxy.getRepository(pool, RedisSubjectRepository.class);
	}
	
	private String createRedisId(String id, String userId, String tenantId){
		return tenantId + ":" + userId + ":" + id;
	}
	

	@Override
	public Optional<RedisSubject> findRedisSubjectByIdAndUserIdAndTenantId(String id, String userId, String tenantId) {
		return getRepository().get(createRedisId(id, userId, tenantId));
	}


}
