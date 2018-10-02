package com.dhm.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SharedLockServiceTest {

    @Autowired
    private SharedLockService sharedLockService;

    @Test
    public void testLockWithThrowExceptionPolicy(){
        int availProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0;i<10;i++){
            executorService.submit(()->{
                sharedLockService.lockWithThrowExceptionPolicy("lockWithThrowExceptionPolicy");
                countDownLatch.countDown();
                log.info("剩余执行数量：{}",countDownLatch.getCount());
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    @Test
    public void testLockWithDoNothingPolicy() throws InterruptedException {
        int availProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for(int i=0;i<1000;i++){
            executorService.submit(()->{
                sharedLockService.lockWithDoNothingPolicy("lockWithDoNothingPolicy");
                countDownLatch.countDown();
                log.info("剩余执行数量：{}",countDownLatch.getCount());
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    @Test
    public void testLockWaitAndRetryPolicy() throws InterruptedException {
        int availProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for(int i=0;i<1000;i++){
            executorService.submit(()->{
                sharedLockService.lockWaitAndRetryPolicy("lockWaitAndRetryPolicy");
                countDownLatch.countDown();
                log.info("剩余执行数量：{}",countDownLatch.getCount());
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

}