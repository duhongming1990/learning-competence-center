package com.dhm.lock.handler;


import com.dhm.lock.DistributionLock;
import com.dhm.lock.annotion.SharedLock;
import com.dhm.lock.exception.LockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/10/2 19:02
 */
@Aspect
@Component
@Slf4j
public class LockHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockHandler.class);

    private static ThreadLocal<Integer> retryCountHolder = new ThreadLocal<>();


    @Autowired
    private DistributionLock distributionLock;

    @Pointcut("@annotation(com.dhm.lock.annotion.SharedLock)")
    public void lockPoint(){

    }


    @Around("lockPoint()")
    public Object aroundLock(ProceedingJoinPoint jp)throws Throwable{

        Object[] args = jp.getArgs();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String[] parameterNames = signature.getParameterNames();

        Map<String,Object> params = new HashMap<>();

        if(args != null && parameterNames != null && args.length > 0 && parameterNames.length > 0){
            int length = args.length;
            for(int i = 0;i<length;i++){
                params.put(parameterNames[i],args[i]);
            }
        }
        SharedLock lock = signature.getMethod().getAnnotation(SharedLock.class);
        String key = lock.key();
        if(key == null || "".equals(key.trim())){
            key = lock.value();
        }

        if(key == null || "".equals(key.trim())){
            throw new LockException("key值不能为空");
        }

        Pattern compile = Pattern.compile("#\\{([^\\}]*)\\}");

        Matcher m = compile.matcher(key);

        while(m.find()){
            String old = m.group();
            String paramName = m.group(1);
            Object param = params.get(paramName);
            if(param == null){
                key = key.replace(old,"");
            }else{
                key = key.replace(old,param.toString());
            }
        }

        try{
            boolean l = distributionLock.tryGetNonBlockLock(key,lock.maxLockTime());
            if(l){
                try {
                    Object proceed = jp.proceed();
                    retryCountHolder.remove();
                    return proceed;
                } catch (Throwable throwable) {
                    LOGGER.error("共享锁锁定方法报错" + throwable.getMessage());
                    throw throwable;
                }
            }
            switch (lock.policy()){
                case throw_exception:
                    throw new LockException("该key值已经被锁定,key="+key);
                case do_nothing:
                    break;
                case wait_and_retry:
                    Integer retryTimes = retryCountHolder.get();
                    if(retryTimes != null && retryTimes >= lock.maxRetryTimes()){
                        retryCountHolder.remove();
                        throw new LockException("尝试获取锁失败超过了最大重试次数,key="+key);
                    }

                    if(retryTimes == null)
                        retryTimes = 1;
                    else{
                        retryTimes ++;
                    }
                    log.info("尝试获取锁:{},失败次数:{}",key,retryTimes);
                    retryCountHolder.set(retryTimes);
                    try{
                        Thread.sleep(lock.waitTime());
                    }catch (Exception e){
                        LOGGER.error(e.getMessage(),e);
                    }
                    aroundLock(jp);
                    break;
                default:break;
            }
            return null;

        }finally {
            distributionLock.releaseLock(key);
        }

    }

}
