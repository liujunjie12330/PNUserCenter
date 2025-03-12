package com.pn.service.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 邮件配置类，可以application.properties文件中查看
 */
@Configuration
@PropertySource("classpath:config/email.properties")
public class EmailConfig {
    @Value("${qq.host}")
    private String host;
    @Value("${aa.default-encoding:utf-8}")
    private String defaultEncoding;
    @Value("${qq.password}")
    private String password;
    @Value("${qq.username}")
    private String username;
    @Value("${qq.protocol}")
    private String protocol;
    @Bean
    public JavaMailSenderImpl JavaMailSender() {
        JavaMailSenderImpl qqEmailSender = new JavaMailSenderImpl();
        qqEmailSender.setHost(host);
        qqEmailSender.setDefaultEncoding(defaultEncoding);
        qqEmailSender.setPassword(password);
        qqEmailSender.setUsername(username);
        qqEmailSender.setProtocol(protocol);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.setProperty("mail.smtp.starttls.required","true");
        qqEmailSender.setJavaMailProperties(properties);
        return qqEmailSender;
    }

}
