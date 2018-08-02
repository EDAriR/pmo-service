package com.syntrontech.pmo.service;

import com.syntrontech.pmo.redis.model.RedisUser;

import java.util.Optional;

public interface RedisUserService {
	Optional<RedisUser> findRedisUserByUserId(String userId);
}
