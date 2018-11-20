package com.chankin.system.security.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisSessionDao extends AbstractSessionDAO {
    private static final String sessionIdPrefix = "shiro-session-";
    private static final String sessionIdPrefix_keys = "shiro-session-*";
    private static final long timeout = 2593000;
    private transient static Logger log = LoggerFactory.getLogger(RedisSessionDao.class);
    @Autowired
    private transient RedisTemplate<Serializable, Session> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = sessionIdPrefix + UUID.randomUUID().toString();
        assignSessionId(session, sessionId);
        //redis储存session及过期时间
        redisTemplate.opsForValue().set(sessionId, session, timeout, TimeUnit.SECONDS);
        log.info("create shiro session,sessionId is:{}", sessionId.toString());
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("read shiro session,sessionId is:{}", sessionId.toString());
        return redisTemplate.opsForValue().get(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.info("update shiro session ,sessionId is :{}", session.getId().toString());
        redisTemplate.opsForValue().set(session.getId(), session, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void delete(Session session) {
        log.info("delete shiro session ,sessionId is :{}", session.getId().toString());
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Serializable> keys = redisTemplate.keys(sessionIdPrefix_keys);
        if (keys.size() == 0) {
            return Collections.emptySet();
        }
        List<Session> sessions = redisTemplate.opsForValue().multiGet(keys);
        return Collections.unmodifiableCollection(sessions);
    }
}
