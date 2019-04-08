package com.dhm.spring.aop;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2019/1/31 16:55
 * 真实主题类
 */
public class RealSubject implements Subject{
    @Override
    public void doSomething() {
        System.out.println("RealSubject do something");
    }
}