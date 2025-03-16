//package com.pn.service.entity.config;
//
//
//import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//
///**
// * xxl-job 配置
// */
//@Configuration
//@PropertySource("classpath:config/xxl_job.properties")
//@Getter
//public class XxlJobConfig {
//    @Value("${xxl-job.admin.address}")
//    private String adminAddress;
//
//    @Value("${xxl-job.executor.accesstoken}")
//    private String accessToken;
//
//    @Value("${xxl-job.executor.appname}")
//    private String appName;
//
//    @Value("${xxl-job.executor.port}")
//    private String port;
//
//    @Bean
//    public XxlJobSpringExecutor xxlJobExecutor() {
//        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
//        executor.setAdminAddresses(adminAddress);
//        executor.setAppname(appName);
//        executor.setPort(Integer.parseInt(port));
//        executor.setAccessToken(accessToken);
//        return executor;
//    }
//}
