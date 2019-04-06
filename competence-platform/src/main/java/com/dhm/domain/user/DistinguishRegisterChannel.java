package com.dhm.domain.user;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2019/4/6 11:12
 */
public interface DistinguishRegisterChannel {

    enum SysRegisterChannel{
        ZQ_XT(1,"系统"),
        ZQ_YYT(2,"营业厅"),
        ZQ_WZ(3,"网站"),
        ZQ_SJAPP(4,"手机app"),
        ZQ_WX(5,"微信");
        ;
        private int channelCode;
        private String channelName;
        SysRegisterChannel(int channelCode,String channelName){
            this.channelCode = channelCode;
            this.channelName = channelName;
        }

        @Override
        public String toString() {
            return "SysRegisterChannel{" +
                    "channelCode=" + channelCode +
                    ", channelName='" + channelName + '\'' +
                    '}';
        }
    }
}
