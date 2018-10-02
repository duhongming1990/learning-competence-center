package com.dhm.service;


import com.dhm.lock.annotion.SharedLock;
import com.dhm.lock.constant.LockPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/10/2 19:10
 */
@Service
@Slf4j
public class SharedLockService {

    private static int count0 = 0;
    private static int count1 = 0;
    private static int count2 = 0;

    @SharedLock(key = "test_#{lockParams0}")
    public void lockWithThrowExceptionPolicy(String lockParams0) {

        log.info("lock方法开始,lockparams0={},count0={}", lockParams0,count0);
        count0++;
        log.info("lock方法结束,lockparams0={},count0={}", lockParams0,count0);
    }


    @SharedLock(key = "test_#{lockParams1}", policy = LockPolicy.do_nothing)
    public void lockWithDoNothingPolicy(String lockParams1) {

        log.info("lock方法开始,lockParams1={},count0={}", lockParams1,count1);
//        try {
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        count1++;
        log.info("lock方法结束,lockParams1={},count0={}", lockParams1,count1);
    }

    @SharedLock(key = "test_#{lockParams2}", policy = LockPolicy.wait_and_retry, waitTime = 1000L, maxRetryTimes = 5)
    public void lockWaitAndRetryPolicy(String lockParams2) {

        log.info("lock方法开始,lockParams2={},count0={}", lockParams2,count2);
//        try {
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        count2++;
        log.info("lock方法结束,lockParams2={},count0={}", lockParams2,count2);
    }

}
