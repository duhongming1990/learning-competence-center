package com.dhm.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/24 9:50
 */
@Slf4j
@Component
//@RabbitListener(queues = "org.delete")
public class OrgDeleteReceiver {

//    @Autowired
//    private SlaveOrganizationRepository slaveOrganizationRepository;
//
//    @RabbitHandler
//    public void process(String id) {
//        log.info("delete:{}",id);
//        slaveOrganizationRepository.delete(Long.parseLong(id));
//        log.info("从库删除成功！");
//    }

}