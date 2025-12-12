package com.mycompany.myapp.service.impl;
import com.mycompany.myapp.service.dto.SesionCacheDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class SesionRedisManager {

    private static final long TTL_SECONDS = 1800L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String key(String token) {
        return "sesion:" + token;
    }

    public void guardarEnCache(SesionCacheDTO dto) {
        redisTemplate.opsForValue().set(key(dto.getToken()), dto, TTL_SECONDS, TimeUnit.SECONDS);
    }

    public SesionCacheDTO obtenerDeCache(String token) {
        String redisKey = key(token);
        Long ttl = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        if (ttl == null || ttl <= 0) {
            return null;
        }
        SesionCacheDTO dto = (SesionCacheDTO) redisTemplate.opsForValue().get(redisKey);
        if (dto != null) {
            redisTemplate.expire(redisKey, TTL_SECONDS, TimeUnit.SECONDS);
        }
        return dto;
    }

    public void borrar(String token) {
        redisTemplate.delete(key(token));
    }

    public boolean existe(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key(token)));
    }
}
