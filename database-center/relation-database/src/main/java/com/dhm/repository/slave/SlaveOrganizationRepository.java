package com.dhm.repository.slave;

import com.dhm.bean.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/24 10:15
 */
public interface SlaveOrganizationRepository extends JpaRepository<Organization,Long> {
}