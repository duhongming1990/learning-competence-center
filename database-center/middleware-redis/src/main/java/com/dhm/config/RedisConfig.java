package com.dhm.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/16 18:44
 */
@Configuration
public class RedisConfig {
    @Bean(name = "jedis.pool")
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
                               @Value("${spring.redis.host}") String host,
                               @Value("${spring.redis.port}") int port,
                               @Value("${spring.redis.timeout}") int timeout,
                               @Value("${spring.redis.password}") String password) {
        return new JedisPool(config, host, port, timeout, password);
    }

    @Bean(name = "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig(
            @Value("${jedis.pool.config.maxTotal}") int maxTotal,
            @Value("${jedis.pool.config.maxIdle}") int maxIdle,
            @Value("${jedis.pool.config.maxWaitMillis}") int maxWaitMillis,
            @Value("${jedis.pool.config.testOnBorrow}") boolean testOnBorrow) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }
}