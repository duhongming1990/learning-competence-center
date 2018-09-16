package com.dhm.master.repository;


import com.dhm.master.bean.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/15 14:28
 */
public interface MasterUserRepository extends JpaRepository<MasterUser, Long> {

}