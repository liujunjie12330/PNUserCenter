package com.pn.web;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: javadadi
 * @Time: 16:32
 * @ClassName: PNUserCenterApp
 */
@SpringBootApplication
@MapperScan("com.pn.dao.mapper")
@ComponentScan("com.pn")
public class PNUserCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(PNUserCenterApp.class,args);
    }
}
