package com.example.Proxy.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServices {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        System.out.println("DATA REDIS = " + key + " : " + value);
    }

    public String get(String key) {
        System.out.println("DATA REDIS = " + key + " : " + key);
        return redisTemplate.opsForValue().get(key).toString();
    }

}
