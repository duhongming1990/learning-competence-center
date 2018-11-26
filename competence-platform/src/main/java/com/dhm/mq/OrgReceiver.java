package com.dhm.mq;

import com.dhm.bean.Organization;
import com.dhm.repository.master.MasterOrganizationRepository;
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
public class OrgReceiver {

    @Autowired
    private MasterOrganizationRepository masterOrganizationRepository;
    @Autowired
    private SlaveOrganizationRepository slaveOrganizationRepository;

    @RabbitListener(queues = "org.delete")
    @RabbitHandler
    public void delete(String ouCode) {
        log.info("MQ接收消息-org.delete：{}",ouCode);
        Organization organizationByOuCode = slaveOrganizationRepository.findOrganizationByOuCode(ouCode);
        slaveOrganizationRepository.delete(organizationByOuCode);
        log.info("从库org.delete删除成功！");
    }

    @RabbitListener(queues = "org.add")
    @RabbitHandler
    public void add(String ouCode) {
        log.info("MQ接收消息-org.add：{}",ouCode);
        Organization organization = masterOrganizationRepository.findOrganizationByOuCode(ouCode);
        organization.setId(null);
        slaveOrganizationRepository.save(organization);
        log.info("从库org.add添加成功！");
    }

    @RabbitListener(queues = "org.update")
    @RabbitHandler
    public void update(String ouCode) {
        log.info("MQ接收消息-org.update：{}",ouCode);
        try {
            Organization organization = masterOrganizationRepository.findOrganizationByOuCode(ouCode);

            Organization organizationByOuCode = slaveOrganizationRepository.findOrganizationByOuCode(ouCode);
            organization.setId(organizationByOuCode.getId());

            slaveOrganizationRepository.save(organization);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            log.info("从库org.update添加失败！");
            return;
        }
        log.info("从库org.update添加成功！");
    }

}