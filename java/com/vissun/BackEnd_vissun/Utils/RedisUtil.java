package com.vissun.BackEnd_vissun.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date： Create in 15:19 2018/10/8
 */
@Service
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


    public String get(final String key) {
        Object result = null;
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        if(result==null){
            return null;
        }
        return result.toString();
    }

/**
 * 写入缓存
 *
 * @param key
 * @param value
 * @return
 */

	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			redisTemplate.setValueSerializer(new StringRedisSerializer());
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);

		}
	}
}
