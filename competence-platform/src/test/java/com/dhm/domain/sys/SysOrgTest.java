package com.dhm.domain.sys;

public class SysOrgTest {
    public static void main(String[] args) {
        SysOrg sysOrg = new ElectricCarCompany();
//        sysOrg.setOuCode("10001");
        sysOrg.setOuCode("00001");
        sysOrg.setOuName("电动汽车公司");
        sysOrg.setParentOuCode("1");
        ((ElectricCarCompany) sysOrg).setMarketingSystemOuCode("10001");
        System.out.println(sysOrg instanceof ElectricCarCompany);
        System.out.println(sysOrg.distinguishSysOrgType().toString());
    }

}