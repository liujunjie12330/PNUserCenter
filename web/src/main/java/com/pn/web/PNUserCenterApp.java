package com.pn.web;



import com.pn.web.config.UserRegisterBloomFilterProperties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: javadadi
 * @Time: 16:32
 * @ClassName: PNUserCenterApp
 */
@EnableElasticsearchRepositories(basePackages = "com.pn.service")
@SpringBootApplication(scanBasePackages = "com.pn")
@MapperScan(basePackages = "com.pn.dao")
@EnableScheduling
@EnableConfigurationProperties(UserRegisterBloomFilterProperties.class)  // Register your properties class
public class PNUserCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(PNUserCenterApp.class,args);
    }
}
