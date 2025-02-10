package com.pn;


import com.pn.web.config.UserRegisterBloomFilterProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: javadadi
 * @Time: 16:32
 * @ClassName: PNUserCenterApp
 */
@SpringBootApplication
@EnableConfigurationProperties(UserRegisterBloomFilterProperties.class)  // Register your properties class
public class PNUserCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(PNUserCenterApp.class,args);
    }
}
