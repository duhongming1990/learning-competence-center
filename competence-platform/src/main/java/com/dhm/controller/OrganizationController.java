package com.dhm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2018/9/24 11:19
 */
@RestController
@RequestMapping("/org")
public class OrganizationController {

//    @Autowired
//    private MasterOrganizationRepository orgRepository;
//
//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    @RequestMapping("/add")
//    public String add(Organization organization) {
//        String orgStr = JSON.toJSONString(organization);
//        //主库保存
//        orgRepository.save(organization);
//        //发送到MQ中
//        rabbitTemplate.convertAndSend("org.add", orgStr);
//        return "ADD SUCCESS";
//    }
//
//    @RequestMapping("update")
//    public String update(Organization organization) {
//        String orgStr = JSON.toJSONString(organization);
//        //主库更新
//        orgRepository.save(organization);
//        //发送到MQ中
//        rabbitTemplate.convertAndSend("org.update", orgStr);
//        return "UPDATE SUCCESS";
//    }
//
//    @RequestMapping("/delete")
//    public String delete(Long id) {
//        //主库删除
//        orgRepository.delete(id);
//        //发送到MQ中
//        rabbitTemplate.convertAndSend("org.delete", String.valueOf(id));
//        return "DELETE SUCCESS";
//    }
}
