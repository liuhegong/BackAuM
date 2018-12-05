package com.chankin.system.security.shiro;

import com.chankin.util.SystemConstant;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

public class RedisCacheManager implements CacheManager, Serializable {
    private transient static Logger log = LoggerFactory.getLogger(RedisCacheManager.class);
    private transient RedisTemplate<Object, Object> redisTemplate;

    public RedisCacheManager() {
    }
    public RedisCacheManager(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty");
        }
        log.debug("redis cache manage get cache name is: {}", name);
        Cache cache = (Cache) redisTemplate.opsForValue().get(name);
        if (cache == null) {
            cache = new RedisCache<>(redisTemplate);
            //name为shiro-cache-com.chankin.system.security.shiro.ShiroRealm.authorizationCache
            redisTemplate.opsForValue().set(SystemConstant.shiro_cache_prefix + name, cache);
        }
        return cache;
    }

    public RedisTemplate<Object, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
