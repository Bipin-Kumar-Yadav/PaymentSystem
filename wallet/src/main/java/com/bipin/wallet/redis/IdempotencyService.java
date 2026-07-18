package com.bipin.wallet.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdempotencyService {
     private final StringRedisTemplate redisTemplate;
    
     public boolean acquire(String key){
        return Boolean.TRUE.equals(
            redisTemplate.opsForValue().setIfAbsent(key, "PROCESSING",Duration.ofHours(24))
        );
     }

     public void markSuccess(String key){
        redisTemplate.opsForValue().set(key,"SUCCESS",Duration.ofHours(24));
     }

     public void release(String key){
        redisTemplate.delete(key);
     }
}
