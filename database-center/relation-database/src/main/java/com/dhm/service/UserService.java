package com.dhm.service;


import com.dhm.master.bean.MasterUser;
import com.dhm.master.repository.MasterUserRepository;
import com.dhm.slave.bean.SlaveUser;
import com.dhm.slave.repository.SlaveUserRepository;
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

    public void masterSave(){
        masterUserRepository.save(new MasterUser("master_aaa", 10));
        masterUserRepository.save(new MasterUser("master_bbb", 20));
        masterUserRepository.save(new MasterUser("master_ccc", 30));
        masterUserRepository.save(new MasterUser("master_ddd", 40));
        masterUserRepository.save(new MasterUser("master_eee", 50));
    }

    public void slaveSave(){
        slaveUserRepository.save(new SlaveUser("slave_aaa", 10));
        slaveUserRepository.save(new SlaveUser("slave_bbb", 20));
        slaveUserRepository.save(new SlaveUser("slave_ccc", 30));
        slaveUserRepository.save(new SlaveUser("slave_ddd", 40));
        slaveUserRepository.save(new SlaveUser("slave_eee", 50));
    }

    public void masterUserCount(){
        Integer count = masterJdbcTemplate.queryForObject("select count(1) from master_user", Integer.class);
        System.out.println("masterJdbcTemplate data count:"+count);
    }

    public void slaveUserCount(){
        Integer count = slaveJdbcTemplate.queryForObject("select count(1) from slave_user", Integer.class);
        System.out.println("slaveJdbcTemplate data count:"+count);
    }

}