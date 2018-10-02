package com.dhm.lock;

import com.dhm.dao.RedisDao;
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
    private DistributionLock distributionLock;

    @Autowired
    RedisDao redisDao;

    @Test
    public void testBlockDistributionLock() throws InterruptedException {
        int availProcessors = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {

                //获取锁-阻塞
                distributionLock.tryGetBlockLock("lockKey",2000L);
                log.info("Thread.currentThread().getName()开始 = {}", Thread.currentThread().getName());
                redisDao.incr("already_coount");
                log.info("Thread.currentThread().getName()结束 = {},已执行 = {}", Thread.currentThread().getName());
                countDownLatch.countDown();

                //释放锁
                if(distributionLock.releaseLock("lockKey")){
                    log.info("剩余执行数量：{}",countDownLatch.getCount());
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count = {}", redisDao.get("already_coount"));
    }

}