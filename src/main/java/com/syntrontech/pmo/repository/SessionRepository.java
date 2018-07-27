package com.syntrontech.pmo.repository;

import com.syntrontech.pmo.model.Session;
import com.syntrontech.redis.RedisHashRepository;

public interface SessionRepository extends RedisHashRepository<String, Session>{

}
