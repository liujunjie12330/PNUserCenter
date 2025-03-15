package com.pn.service.mq;

import com.pn.service.impls.mq.MessageProducer;
import com.pn.web.PNUserCenterApp;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
@SpringBootTest(classes = PNUserCenterApp.class)
@RunWith(SpringRunner.class)
class MessageProducerTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MessageProducer messageProducer;


    @Test
    void testSendMessage() {
        String message = "Test Message";
        messageProducer.sendMessage(message);
    }
}