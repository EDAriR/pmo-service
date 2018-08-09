package com.syntrontech.pmo.service.imp;

import com.syntrontech.pmo.service.RedisPmoResultService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class RedisPmoResultServiceImp implements RedisPmoResultService {


    @Autowired
    private JedisPool pool;

    Jedis getJedis(){
        return pool.getResource();
    }

    List<String> brPop(){
        return getJedis().brpop(0,"pmoResult");
    }
}
