package com.syntrontech.pmo.service;

import com.syntrontech.pmo.redis.model.RedisSubject;

import java.util.List;
import java.util.Optional;


public interface RedisSubjectService {

	Optional<RedisSubject> findRedisSubjectByIdAndUserIdAndTenantId(String id, String userId, String tenantId);
}
