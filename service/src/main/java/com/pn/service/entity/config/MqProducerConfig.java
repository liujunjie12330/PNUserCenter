package com.pn.service.entity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 消息队列配置类
 */
@Configuration
@Slf4j
public class MqProducerConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = null;
        if (correlationData != null) {
            id = correlationData.getId();
        }
        if (!ack) {
            log.info("id为: {}的消息,未能抵达broker, 原因: {}", id, cause);
        } else {
            log.info("broker已接收id为: {}的消息", id);
        }
    }


    @Override
    public void returnedMessage(ReturnedMessage returned) {
        // 该消息抵达的交换机
        String exchange = returned.getExchange();
        // 消息体的详细信息
        Message message = returned.getMessage();
        // 回复的状态码
        int replyCode = returned.getReplyCode();
        // 该消息使用的路由键
        String routingKey = returned.getRoutingKey();
        // 回复的文本内容
        String replyText = returned.getReplyText();
        log.info(
                "exchange: {}, message: {}, replyCode: {}, routingKey: {}, replyText: {}",
                exchange, message, replyCode, routingKey, replyText
        );
    }

    @Resource
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
}
