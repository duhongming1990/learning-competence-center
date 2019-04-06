package com.dhm.domain.user;

import com.dhm.domain.sys.DistinguishSysOrgType;
import com.dhm.domain.sys.SysOrg;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/6 10:28
 */
@Getter
@Setter
public class SysOperator implements DistinguishPlatformType {
    private String adminCode;
    private String adminName;
    private String adminPwd;

    private SysOrg sysOrg;

    @Override
    public SysPlatformType distinguishSysPlatformType() {
        String ouCode = sysOrg.getOuCode();
        if(StringUtils.isNotBlank(ouCode)){
            if(ouCode.startsWith(DistinguishSysOrgType.SysOrgType.SOCIAL_MERCHANT.typeId())){
                return SysPlatformType.MERCHANT_PLATFORM;
            }else if(ouCode.startsWith(DistinguishSysOrgType.SysOrgType.INDIVIDUAL_MERCHANT.typeId())){
                return SysPlatformType.MERCHANT_PLATFORM;
            }
            for (SysPlatformType type : SysPlatformType.values()) {
                if(ouCode.startsWith(type.platformCode())){
                    return type;
                }
            }
        }
        return SysPlatformType.UNKNOW_PLATFORM;
    }
}
