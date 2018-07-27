package com.syntrontech.pmo.repository;

import com.syntrontech.pmo.model.RedisUser;
import com.syntrontech.redis.RedisHashRepository;

public interface RedisUserRepository extends RedisHashRepository<String, RedisUser> {

}
