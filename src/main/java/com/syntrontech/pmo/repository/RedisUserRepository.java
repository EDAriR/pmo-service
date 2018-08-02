package com.syntrontech.pmo.repository;

import com.syntrontech.pmo.redis.model.RedisUser;
import com.syntrontech.redis.RedisHashRepository;

public interface RedisUserRepository extends RedisHashRepository<String, RedisUser> {

}
