package com.dhm.repository.master;

import com.dhm.bean.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2018/9/24 10:13
 */
public interface MasterOrganizationRepository extends JpaRepository<Organization,Long> {
    Organization findOrganizationByOuCode(String ouCode);
}