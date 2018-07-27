package com.syntrontech.pmo.service;

import java.util.Optional;

import com.syntrontech.pmo.model.RedisUser;

public interface RedisUserService {
	Optional<RedisUser> findRedisUserByUserId(String userId);
}
