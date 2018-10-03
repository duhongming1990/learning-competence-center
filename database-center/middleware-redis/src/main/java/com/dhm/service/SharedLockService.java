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

    @SharedLock(key = "test_#{lockParams0}")
    public void lockWithThrowExceptionPolicy(String lockParams0) {

        log.info("lock方法开始,lockparams0={},线程ID={}", lockParams0,Thread.currentThread().getId());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("lock方法结束,lockparams0={},线程ID={}", lockParams0,Thread.currentThread().getId());

    }


    @SharedLock(key = "test_#{lockParams1}", policy = LockPolicy.do_nothing)
    public void lockWithDoNothingPolicy(String lockParams1) {

        log.info("lock方法开始,lockParams1={},线程ID={}", lockParams1,Thread.currentThread().getId());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("lock方法结束,lockParams1={},线程ID={}", lockParams1,Thread.currentThread().getId());
    }

    @SharedLock(key = "test_#{lockParams2}", policy = LockPolicy.wait_and_retry, waitTime = 1000L, maxRetryTimes = 5)
    public void lockWaitAndRetryPolicy(String lockParams2) {

        log.info("lock方法开始,lockParams2={},线程ID={}", lockParams2,Thread.currentThread().getId());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("lock方法结束,lockParams2={},线程ID={}", lockParams2,Thread.currentThread().getId());
    }

}
