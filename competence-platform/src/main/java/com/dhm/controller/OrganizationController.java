package com.dhm.controller;

import com.alibaba.fastjson.JSON;
import com.dhm.bean.Organization;
import com.dhm.repository.master.MasterOrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2018/9/24 11:19
 */
@Slf4j
@RestController
@RequestMapping("/org")
public class OrganizationController {

    @Autowired
    private MasterOrganizationRepository orgRepository;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/add")
    public String add(Organization organization) {

        //主库保存
        orgRepository.save(organization);
        log.info("主库保存数据：{}", JSON.toJSONString(organization));

        //发送到MQ中
        String ouCode = String.valueOf(organization.getOuCode());
        rabbitTemplate.convertAndSend("org.add", ouCode);
        log.info("MQ发送数据-org.add：{}", ouCode);

        return "ADD SUCCESS";
    }

    @RequestMapping("update")
    public String update(Organization organization) {

        //主库更新
        Organization organizationByOuCode = orgRepository.findOrganizationByOuCode(organization.getOuCode());
        organization.setId(organizationByOuCode.getId());
        orgRepository.save(organization);
        log.info("主库更新数据：{}", JSON.toJSONString(organization));

        //发送到MQ中
        String ouCode = String.valueOf(organization.getOuCode());
        rabbitTemplate.convertAndSend("org.update", ouCode);
        log.info("MQ发送数据-org.update：{}", ouCode);

        return "UPDATE SUCCESS";
    }

    @RequestMapping("/delete")
    public String delete(String ouCode) {

        //主库删除
        Organization organizationByOuCode = orgRepository.findOrganizationByOuCode(ouCode);
        orgRepository.delete(organizationByOuCode);
        log.info("主库删除数据：{}", ouCode);

        //发送到MQ中
        rabbitTemplate.convertAndSend("org.delete", ouCode);
        log.info("MQ发送数据-org.delete：{}", ouCode);

        return "DELETE SUCCESS";
    }
}
