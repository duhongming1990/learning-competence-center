package com.dhm.repository.slave;


import com.dhm.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/15 14:28
 */
public interface SlaveUserRepository extends JpaRepository<User, Long> {

}