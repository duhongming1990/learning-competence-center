package com.dhm.domain.sys;

import lombok.Getter;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/6 09:28
 */
public interface DistinguishSysOrgType {

    @Getter
    enum SysOrgType{
        UNKNOW_ORG(0,"未知组织机构",SysOrg.class),
        ELECTRIC_CAR_COMPANY(1,"电动汽车公司",ElectricCarCompany.class),
        BUSINESS_USER_COMPANY(2,"企业用户公司",BusinessUserCompany.class),
        STATE_GRID_MERCHANT(3,"国网商户",StateGridMerchant.class),
        SOCIAL_MERCHANT(4,"社会商户",SocialMerchant.class),
        INDIVIDUAL_MERCHANT(5,"个人商户",IndividualMerchant.class),
        ;

        private int typeId;
        private String typeName;
        private Class clazz;

        SysOrgType(int typeId,String typeName,Class clazz) {
            this.typeId = typeId;
            this.typeName = typeName;
            this.clazz = clazz;
        }

        public String typeId(){
            return String.valueOf(typeId);
        }

        @Override
        public String toString() {
            return "SysOrgType{" +
                    "typeId=" + typeId +
                    ", typeName='" + typeName + '\'' +
                    ", clazz=" + clazz +
                    '}';
        }
    }

    SysOrgType distinguishSysOrgType();
}
