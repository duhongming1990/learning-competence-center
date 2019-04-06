package com.dhm.domain.sys;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/6 09:08
 *
 * 组织ouCode start 2
 */
//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class BusinessUserCompany extends SysOrg{
    /**
     * 清算单位的ouCode,只能从电动汽车公司中选取
     */
    private String liquidationUnitOuCode;
    /**
     * 清算单位的ouName,只能从电动汽车公司中选取
     */
    private String liquidationUnitOuName;
}
