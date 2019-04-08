package com.dhm.spring.aop;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2019/1/31 16:58
 */
public class JDKDynamicProxyTest {
    public static void main(String[] args) {
        // 保存生成的代理类的字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        // jdk动态代理测试
        Subject subject = new JDKDynamicProxy(new RealSubject()).getProxy();

        subject.doSomething();
    }
}