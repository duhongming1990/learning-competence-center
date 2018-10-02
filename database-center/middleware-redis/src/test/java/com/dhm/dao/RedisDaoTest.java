package com.dhm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/10/2 18:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDaoTest {
    @Autowired
    private RedisDao redisDao;
    @Test
    public void contextLoads() {
        //String
        String string_result = redisDao.set("string_key","string_value",0);
        String string_value = redisDao.get("string_key");

        //Map
        Map<String,String> map = new HashMap<>();
        map.put("map_key1","map_value1");
        map.put("map_key2","map_value2");
        map.put("map_key3","map_value3");
        String map_result = redisDao.hmset("map_key",map,0);
        Map<String,String> map_value = redisDao.hgetAll("map_key");

        //List
        List<String> list = new ArrayList<>();
        list.add("list_value1");
        list.add("list_value2");
        list.add("list_value3");
        list.add("list_value4");
        long list_result = redisDao.rpush("list_key",list,0);
        List<String> list_value = redisDao.lrange("list_key");

        //Set
        Set<String> set = new HashSet<>();
        set.add("set_value1");
        set.add("set_value2");
        set.add("set_value3");
        set.add("set_value4");
        long set_result = redisDao.sadd("set_key",set,0);
        Set<String> set_value = redisDao.smembers("set_key");

        //Zset
        Map<String,Double> zset = new HashMap<>();
        zset.put("map_key1",10.1);
        zset.put("map_key2",9.8);
        zset.put("map_key3",10.5);
        long zset_result = redisDao.zset("zset_key",zset,0);
        Set<String> zset_value = redisDao.zrange("zset_key",0,2);

        //pipeline
        Map<String,String> pipeline = new HashMap<>();
        pipeline.put("pipeline_key1","pipeline_value1");
        pipeline.put("pipeline_key2","pipeline_value2");
        pipeline.put("pipeline_key3","pipeline_value3");
        pipeline.put("pipeline_key4","pipeline_value4");
        redisDao.pipeline(pipeline);

    }

}