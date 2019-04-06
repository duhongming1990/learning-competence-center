package com.dhm.domain.user;

import com.dhm.domain.sys.ElectricCarCompany;
import com.dhm.domain.sys.SysOrg;

public class SysOperatorTest {
    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {

            SysOperator sysOperator = new ManagementPlatformOperator();
            sysOperator.setAdminCode("admin");
            sysOperator.setAdminName("admin");
            sysOperator.setAdminPwd("admin");

            SysOrg sysOrg = new ElectricCarCompany();
            sysOrg.setOuCode(i+"0001");
            sysOrg.setOuName("电动汽车公司");
            sysOrg.setParentOuCode("1");
            sysOperator.setSysOrg(sysOrg);

            System.out.println(sysOperator instanceof ManagementPlatformOperator);
            System.out.println(sysOperator.distinguishSysPlatformType());
        }

    }

}