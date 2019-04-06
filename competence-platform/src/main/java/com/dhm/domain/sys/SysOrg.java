package com.dhm.domain.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/6 09:12
 *
 * 组织机构
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysOrg implements DistinguishSysOrgType{

    private String ouCode;
    private String ouName;
    private String parentOuCode;

    @Override
    public SysOrgType distinguishSysOrgType() {
        if(StringUtils.isNotBlank(this.ouCode)){
            for (SysOrgType type : SysOrgType.values()) {
                if(this.ouCode.startsWith(type.typeId())){
                   return type;
                }
            }
        }
        return SysOrgType.UNKNOW_ORG;
    }
}
