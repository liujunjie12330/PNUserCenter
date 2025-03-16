package com.pn.service.impls.mq;

import com.pn.service.utils.id.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class ArticlePayMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    
    public  void sendMessage(String articleId) {
        // 发送消息到directExchange交换机，使用路由键directRouting
        Long id = IdUtil.genId();
        rabbitTemplate.convertAndSend("directExchange", "directRouting", articleId, msg->{
            msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            msg.getMessageProperties().setDeliveryTag(id);
            msg.getMessageProperties().setMessageId(id.toString());
            return msg;
        },new CorrelationData(id.toString()));
    }

}