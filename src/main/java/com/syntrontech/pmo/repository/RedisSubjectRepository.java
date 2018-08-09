package com.syntrontech.pmo.repository;

import com.syntrontech.pmo.redis.model.RedisSubject;
import com.syntrontech.redis.RedisHashRepository;

public interface RedisSubjectRepository extends RedisHashRepository<String, RedisSubject> {

}
