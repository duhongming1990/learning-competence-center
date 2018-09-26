package com.dhm.redis;

import com.dhm.dao.RedisUtil;
import com.dhm.locks.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/16 19:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DistributionLockTest {

    private static int count = 0;

    @Autowired
    private DistributedLock distributedLock;

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void testBlockDistributionLock() throws InterruptedException {
        int availProcessors = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {

                distributedLock.tryGetBlockLock("lockKey");
                log.info("Thread.currentThread().getName()开始 = {}", Thread.currentThread().getName());
                redisUtil.incr("already_coount");
                log.info("Thread.currentThread().getName()结束 = {},已执行 = {}", Thread.currentThread().getName());
                countDownLatch.countDown();

                if(distributedLock.releaseLock("lockKey")){
                    log.info("剩余执行数量：{}",countDownLatch.getCount());
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count = {}", redisUtil.get("already_coount"));
    }

}