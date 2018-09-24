package com.dhm.mq;

import com.alibaba.fastjson.JSON;
import com.dhm.bean.Organization;
import com.dhm.repository.slave.SlaveOrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/24 9:50
 */
@Slf4j
@Component
@RabbitListener(queues = {"org.add","org.update"})
public class OrgUpdateReceiver {

    @Autowired
    private SlaveOrganizationRepository slaveOrganizationRepository;

    @RabbitHandler
    public void process(String orgStr) {
        log.info("add and update:{}",orgStr);
        slaveOrganizationRepository.save(JSON.parseObject(orgStr, Organization.class));
        log.info("从库更新成功！");
    }

}