package com.service.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;


/**
 * @author Effort
 * @description Redis配置
 * @date 2020/9/29 8:33 下午
 */
@Service
public class RedisService {

    private static Logger log = LoggerFactory.getLogger(RedisService.class);
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.database:2}")
    private int database;

    public JedisPool getPool() {
        return pool;
    }

    private JedisPool pool = null;

    @PostConstruct
    private void init() {
        if (this.host==null) {
            log.info("RedisService : no init.");
        } else {
            JedisPoolConfig config = new JedisPoolConfig();
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(500);
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100); // 100秒
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            this.pool = new JedisPool(config, this.host, this.port, this.timeout, this.password, database);
            log.info("redis start.");
        }
    }

    public Jedis getResource() {
        return this.pool.getResource();
    }

    public void set(String key, String value, Long seconds) {
        try (Jedis jedis = this.getResource()) {
            jedis.set(key, value, "NX", "EX", seconds);
        }
    }

    public void set(String key, String value) {
        try (Jedis jedis = this.getResource()) {
            jedis.set(key, value);
        }
    }

    public void expire(String key,Long seconds) {
        try (Jedis jedis = this.getResource()) {
            jedis.expire(key,seconds.intValue());
        }
    }

    public void del(String key) {
        try (Jedis jedis = this.getResource()) {
            jedis.del(key);
        }
    }

    public String get(String key) {
        try (Jedis jedis = this.getResource()) {
            return jedis.get(key);
        }
    }

    public void publish(String channel, String message) {
        try (Jedis jedis = this.getResource()) {
            jedis.publish(channel, message);
        }
    }

    public Set<String> keys(String pattern) {
        try (Jedis jedis = this.getResource()) {
            return  jedis.keys(pattern);
        }
    }

    public void setOrUpdate(String key, String value,Long seconds) {
        try (Jedis jedis = this.getResource()) {
            jedis.del(key);
            this.set(key, value, seconds);
        }
    }

    public Set<String> getKeys(String key) {
        try (Jedis jedis = this.getResource()) {
            return jedis.keys(key);
        }
    }


    public long setnx(String key, String value, int expireSeconds) {
        try (Jedis jedis = this.getResource()) {
            return this.setnx(jedis, key, value, expireSeconds);
        }
    }

    public long setnx(Jedis jedis, String key, String value, int expireSeconds) {
        long acquired = jedis.setnx(key, value);
        if (acquired == 1) // SETNX成功，则设置过期时间
            jedis.expire(key, expireSeconds);

        return acquired;
    }

    public void updateForTTL(String key,String value){
        try (Jedis jedis = this.getResource()) {
            Long seconds = jedis.ttl(key);  //获取剩余时间
            jedis.del(key);
            this.set(key, value,seconds);
        }
    }

    public long lpush(String key,String value){
        try (Jedis jedis = this.getResource()) {
            long result = jedis.lpush(key, value);
            return result;
        }
    }


    public List<String> brpop(int timeout, String key) {
        try (Jedis jedis = this.getResource()) {
            return jedis.brpop(timeout, key);
        }catch (Exception e){
            return null;
        }
    }
}

