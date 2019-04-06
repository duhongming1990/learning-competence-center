package com.dhm.domain.user;
import com.dhm.domain.user.DistinguishRegisterChannel.SysRegisterChannel;

import static org.junit.Assert.*;

public class SysUserTest {
    public static void main(String[] args) {
        SysUser sysUser = new CorporationUser();
        sysUser.setUserCode("user");
        sysUser.setUserName("user");
        sysUser.setUserPwd("user");
        sysUser.setSysRegisterChannel(SysRegisterChannel.ZQ_SJAPP);

        System.out.println(sysUser instanceof CorporationUser);
        System.out.println(sysUser.getSysRegisterChannel().toString());
    }

}