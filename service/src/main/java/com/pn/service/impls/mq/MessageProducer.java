package com.pn.service.impls.mq;

import com.google.gson.Gson;
import com.pn.service.utils.id.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class MessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    
    public  void sendMessage(String message) {
        // 发送消息到directExchange交换机，使用路由键directRouting
        Long id = IdUtil.genId();
        rabbitTemplate.convertAndSend("directExchange", "directRouting",message,msg->{
            msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            msg.getMessageProperties().setDeliveryTag(id);
            msg.getMessageProperties().setMessageId(id.toString());
            return msg;
        },new CorrelationData(id.toString()));
    }

}