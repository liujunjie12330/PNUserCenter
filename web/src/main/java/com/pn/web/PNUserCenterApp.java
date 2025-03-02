package com.pn.web;


import com.pn.common.enums.NotifyEnum;
import com.pn.common.vos.notify.NotifyMsgEvent;
import com.pn.dao.entity.PnUserFoot;
import com.pn.web.config.UserRegisterBloomFilterProperties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author: javadadi
 * @Time: 16:32
 * @ClassName: PNUserCenterApp
 */
@SpringBootApplication(scanBasePackages = "com.pn")
@MapperScan(basePackages = "com.pn.dao")
@EnableConfigurationProperties(UserRegisterBloomFilterProperties.class)  // Register your properties class

public class PNUserCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(PNUserCenterApp.class,args);
    }
}
