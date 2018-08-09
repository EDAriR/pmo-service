package com.syntrontech.pmo.service.imp;

import com.syntrontech.pmo.service.RedisPmoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class RedisPmoUserServiceImp implements RedisPmoUserService {

    @Autowired
    private JedisPool pool;

    Jedis getJedis(){
        return pool.getResource();
    }

    void subSubject(){
        Jedis jedis = getJedis();
        jedis.pubsubChannels("");
    }

    List<String> brPop(){
        return getJedis().brpop(0,"pmoUser");
    }

}
