package com.dhm.lock;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.UUID;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/26 11:38
 */
@Component
public class DistributionLock {
    private final ThreadLocal<Lock> lockThreadLocal = new ThreadLocal<>();
    private final String LOCK_PREFIX = "distribution-lock:redis:";
    private final String SET_IF_NOT_EXIST = "NX";
    private final String SET_WITH_EXPIRE_TIME = "PX";
    private final String LOCK_SUCCESS = "OK";
    private final long INTERVAL_TIMES = 200; //下一次重试等待，单位毫秒
    @Autowired
    private JedisPool jedisPool;

    /**
     * 尝试获取阻塞分布式锁
     * <p>
     * 首先，为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
     * 互斥性。在任意时刻，只有一个客户端能持有锁。
     * 不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
     * 具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
     * 解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
     *
     * @param lockKey 锁
     * @return 是否获取成功
     */
    public Boolean tryGetBlockLock(String lockKey,Long maxLockTime) {

        /**
         * Set the string value as value of the key. The string can't be longer than
         * 1073741824 bytes (1 GB).
         *
         * @param key
         * @param value
         * @param nxxx
         *            NX|XX, NX -- Only set the key if it does not already exist. XX --
         *            Only set the key if it already exist.
         * @param expx
         *            EX|PX, expire time units: EX = seconds; PX = milliseconds
         * @param time
         *            expire time in the units of <code>expx</code>
         * @return Status(OK | null) code reply
         */
        Jedis jedis = null;
        final Lock nLock = new Lock(nextUid());
        try {
            jedis = jedisPool.getResource();
            for (; ; ) {
                String result = jedis.set(LOCK_PREFIX + lockKey, nLock.toString(),
                        SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, maxLockTime);

                if (LOCK_SUCCESS.equals(result)) {
                    lockThreadLocal.set(nLock);
                    return true;
                } else {
                    //防止一直消耗 CPU
                    try {
                        Thread.sleep(INTERVAL_TIMES);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } finally {
            jedis.close();
        }
    }

    /**
     * 尝试获取非阻塞分布式锁
     * <p>
     * 首先，为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
     * 互斥性。在任意时刻，只有一个客户端能持有锁。
     * 不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
     * 具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
     * 解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
     *
     * @param lockKey 锁
     * @return 是否获取成功
     */
    public Boolean tryGetNonBlockLock(String lockKey,Long maxLockTime) {
        /**
         * Set the string value as value of the key. The string can't be longer than
         * 1073741824 bytes (1 GB).
         *
         * @param key
         * @param value
         * @param nxxx
         *            NX|XX, NX -- Only set the key if it does not already exist. XX --
         *            Only set the key if it already exist.
         * @param expx
         *            EX|PX, expire time units: EX = seconds; PX = milliseconds
         * @param time
         *            expire time in the units of <code>expx</code>
         * @return Status(OK | null) code reply
         */
        String result;
        Jedis jedis = null;
        final Lock nLock = new Lock(nextUid());
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(LOCK_PREFIX + lockKey, nLock.toString(),
                    SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, maxLockTime);
        } finally {
            jedis.close();
        }
        if (LOCK_SUCCESS.equals(result)) {
            lockThreadLocal.set(nLock);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 释放分布式锁
     *
     * @param lockKey 锁
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey) {
        Lock cLock = lockThreadLocal.get();
        Object result;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            result = jedis.eval(script, Collections.singletonList(LOCK_PREFIX + lockKey), Collections.singletonList(cLock.toString()));
        } finally {
            jedis.close();
        }
        if (((Long) result) == 1L) {
            lockThreadLocal.remove();
            return true;
        }
        return false;
    }

    protected static class Lock {
        private String uid; // lock 唯一标识

        Lock(String uid) {
            this.uid = uid;
        }

        public String getUid() {
            return uid;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this, false);
        }
    }

    private String nextUid() {
        // 可以考虑雪花算法..
        return UUID.randomUUID().toString();
    }
}