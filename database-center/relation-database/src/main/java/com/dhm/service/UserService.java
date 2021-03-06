package com.dhm.service;


import com.dhm.bean.User;
import com.dhm.repository.master.MasterUserRepository;
import com.dhm.repository.slave.SlaveUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/15 14:29
 */
@Service
public class UserService {

    @Autowired
    @Qualifier("masterJdbcTemplate")
    protected JdbcTemplate masterJdbcTemplate;

    @Autowired
    @Qualifier("slaveJdbcTemplate")
    protected JdbcTemplate slaveJdbcTemplate;

    @Autowired
    private MasterUserRepository masterUserRepository;

    @Autowired
    private SlaveUserRepository slaveUserRepository;

    public void masterSave() {
        masterUserRepository.save(new User("master_aaa", 10));
        masterUserRepository.save(new User("master_bbb", 20));
        masterUserRepository.save(new User("master_ccc", 30));
        masterUserRepository.save(new User("master_ddd", 40));
        masterUserRepository.save(new User("master_eee", 50));
    }

    public void slaveSave() {
        slaveUserRepository.save(new User("slave_aaa", 10));
        slaveUserRepository.save(new User("slave_bbb", 20));
        slaveUserRepository.save(new User("slave_ccc", 30));
        slaveUserRepository.save(new User("slave_ddd", 40));
        slaveUserRepository.save(new User("slave_eee", 50));
    }

    public void masterUserCount() {
        Integer count = masterJdbcTemplate.queryForObject("select count(1) from user", Integer.class);
        System.out.println("masterJdbcTemplate data count:" + count);
    }

    public void slaveUserCount() {
        Integer count = slaveJdbcTemplate.queryForObject("select count(1) from user", Integer.class);
        System.out.println("slaveJdbcTemplate data count:" + count);
    }

}