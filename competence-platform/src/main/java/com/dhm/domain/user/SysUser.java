package com.dhm.domain.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/6 10:30
 */
@Getter
@Setter
public class SysUser implements DistinguishRegisterChannel{
    private String userCode;
    private String userName;
    private String userPwd;

    private SysRegisterChannel sysRegisterChannel;
}
