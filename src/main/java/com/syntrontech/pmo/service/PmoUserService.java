package com.syntrontech.pmo.service;

import com.syntrontech.pmo.pmo.model.UserData;
import com.syntrontech.pmo.redis.model.RedisSubject;

public interface PmoUserService {

    UserData saveUser(String id);
}
