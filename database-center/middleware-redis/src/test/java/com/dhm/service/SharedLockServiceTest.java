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
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for(int i=0;i<2;i++){
            executorService.submit(()->{
                try {
                    sharedLockService.lockWithThrowExceptionPolicy("lockWithThrowExceptionPolicy");
                    log.info("线程ID：{}，获取锁：lockWithThrowExceptionPolicy成功！",Thread.currentThread().getId());
                }catch(Exception e){
                    log.error("线程ID：{}，获取锁失败：{}",Thread.currentThread().getId(),e);
                }finally {
                    countDownLatch.countDown();
                    log.info("剩余执行数量：{}",countDownLatch.getCount());
                }
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
    public void testLockWithDoNothingPolicy(){
        int availProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for(int i=0;i<2;i++){
            executorService.submit(()->{
                try {
                    sharedLockService.lockWithDoNothingPolicy("lockWithThrowExceptionPolicy");
                    log.info("线程ID：{}，获取锁：lockWithThrowExceptionPolicy！",Thread.currentThread().getId());
                }finally {
                    countDownLatch.countDown();
                    log.info("剩余执行数量：{}",countDownLatch.getCount());
                }
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
    public void testLockWaitAndRetryPolicy() {
        int availProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availProcessors);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for(int i=0;i<2;i++){
            executorService.submit(()->{
                try {
                    sharedLockService.lockWaitAndRetryPolicy("lockWithThrowExceptionPolicy");
                    log.info("线程ID：{}，获取锁：lockWithThrowExceptionPolicy成功！",Thread.currentThread().getId());
                }catch(Exception e){
                    log.error("线程ID：{}，获取锁失败：{}",Thread.currentThread().getId(),e);
                }finally {
                    countDownLatch.countDown();
                    log.info("剩余执行数量：{}",countDownLatch.getCount());
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}