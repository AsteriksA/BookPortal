package com.gold.security.jwt;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public enum RedisUtil {
    INSTANCE;

    private final JedisPool pool;

    RedisUtil() {
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
    }

    public void sadd(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sadd(key, value);
        }
    }

    public void srem(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.srem(key, value);
        }
    }

    public boolean sismember(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.sismember(key, value);
        }
    }

    public Set<String> smembers(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.smembers(key);
        }
    }
}
