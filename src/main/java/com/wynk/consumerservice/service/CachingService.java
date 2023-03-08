package com.wynk.consumerservice.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.lang.reflect.Type;

@Service
@Slf4j
public class CachingService {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    private Gson gson = new Gson();

    public String get(String key) {
        try (ShardedJedis shard = shardedJedisPool.getResource()) {
            String value = shard.get(key);
            //AnalyticService.update("redis_host", host);
            return value;
        } catch (Exception e) {
            log.error("Error in getting value from redis, key {}", key);
        }
        return null;
    }

    public <T> T get(String key, Type type) {
        String cachedValue = get(key);
        if(StringUtils.isNotEmpty((cachedValue))) {
            return convertToValue(cachedValue, type);
        }
        return null ;
    }

    public <T> T convertToValue(String cachedValue, Type type) {
        return gson.fromJson(cachedValue, type);
    }
}
