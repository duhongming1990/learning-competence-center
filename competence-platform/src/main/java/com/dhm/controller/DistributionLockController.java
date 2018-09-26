package com.dhm.controller;

import com.dhm.dao.RedisUtil;
import com.dhm.locks.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.LongAdder;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/26 14:17
 */
@RestController
@RequestMapping("/lock")
public class DistributionLockController {

    private static LongAdder longAdder = new LongAdder();
    private static Long stock = 100000L;

    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private RedisUtil redisUtil;

    static {
        longAdder.add(stock);
    }

    @GetMapping("/v2/seckill")
    public String seckillV2() throws InterruptedException {

        boolean acquireResult = false;

        acquireResult = distributedLock.tryGetBlockLock("seckillV2");

        if (!acquireResult) {
            return "人太多了，换个姿势操作一下!";
        }

        if (longAdder.longValue() == 0L) {
            return "已抢光!";
        }

        redisUtil.incr("already_bought");

        longAdder.decrement();

        System.out.println("已抢: " + (stock - longAdder.longValue()) + ", 还剩下: " + longAdder.longValue());

        distributedLock.releaseLock("seckillV2");

        return "OK";
    }
}