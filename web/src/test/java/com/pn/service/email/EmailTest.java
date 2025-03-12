package com.pn.service.email;

import com.pn.service.EmailService;
import com.pn.web.PNUserCenterApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = PNUserCenterApp.class)
@RunWith(SpringRunner.class)
public class EmailTest {
    @Resource
    private EmailService emailService;


    @Test
    public void test(){
        emailService.send("liujunjie","1647415022@qq.com","2830486579@qq.com","ss","cccc",false,null,null,null);
    }
}
