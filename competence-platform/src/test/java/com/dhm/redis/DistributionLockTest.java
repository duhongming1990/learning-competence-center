package com.dhm.redis;

import com.dhm.dao.RedisUtil;
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
public class DistributionLockTest {

    private static int count = 0;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testBlockDistributionLock() throws InterruptedException {
        int availProcessors = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(()->{
                redisUtil.tryGetNonBlockLock("lockKey","0000-0000");
                System.out.println("Thread.currentThread().getName()开始 = " + Thread.currentThread().getName());
                count++;
                System.out.println("Thread.currentThread().getName()结束 = " + Thread.currentThread().getName());
                redisUtil.releaseLock("lockKey","0000-0000");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count = " + count);
    }

}