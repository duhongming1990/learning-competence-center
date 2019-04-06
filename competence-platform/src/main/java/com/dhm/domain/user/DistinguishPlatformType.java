package com.dhm.domain.user;

import lombok.Getter;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/6 11:12
 */
public interface DistinguishPlatformType {
    @Getter
    enum SysPlatformType{
        UNKNOW_PLATFORM(0,"未知平台",SysPlatformType.class),
        MANAGEMENT_PLATFORM(1,"管理平台",ManagementPlatformOperator.class),
        BUSINESS_WEBSITE(2,"企业网站",CorporationUser.class),
        MERCHANT_PLATFORM(3,"商户平台",MerchantPlatformOperator.class)
        ;

        private int platformCode;
        private String platformName;
        private Class clazz;

        SysPlatformType(int platformCode,String platformName,Class clazz){
            this.platformCode = platformCode;
            this.platformName = platformName;
            this.clazz = clazz;
        }

        public String platformCode(){
            return String.valueOf(platformCode);
        }
    }

    SysPlatformType distinguishSysPlatformType();
}
