package com.dhm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/15 17:02
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

//    @Test
    public void test(){
        userService.masterSave();
        userService.slaveSave();
        userService.masterUserCount();
        userService.slaveUserCount();
    }

}