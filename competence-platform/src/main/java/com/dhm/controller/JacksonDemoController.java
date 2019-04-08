package com.dhm.controller;

import com.alibaba.fastjson.JSON;
import com.dhm.domain.JacksonDemoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/5 09:51
 */
@Slf4j
@RestController
@RequestMapping("/jackson")
public class JacksonDemoController {

    @RequestMapping("/out")
    public JacksonDemoBean out(){
        JacksonDemoBean jacksonDemoBean = new JacksonDemoBean();
        jacksonDemoBean.setDept("fxss");
        jacksonDemoBean.setName("duhongming");
        jacksonDemoBean.setPassword("duhongming");
//        jacksonDemoBean.setAge(30);
        return jacksonDemoBean;
    }

    @RequestMapping("/in")
    public String in(@RequestBody JacksonDemoBean jacksonDemoBean){
        String json = JSON.toJSONString(jacksonDemoBean);
        log.info(json);
        return json;
    }


}
