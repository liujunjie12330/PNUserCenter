package com.pn.service.impls.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 消息消费者
 * 用于消费directQueue队列中的消息
 */
@Component
@Slf4j
@RabbitListener(queues = "directQueue")
public class MessageConsumer {

    /**
     * 处理接收到的消息
     * @param message 接收到的消息内容
     */
    @RabbitHandler
    public void process(String msg, Channel channel, Message message) throws IOException {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            processMessage(msg);
            channel.basicAck(tag, false);
        }catch (Exception e){
            log.error("处理消息失败，准备重新入队", e);
            channel.basicNack(tag, false, true);
        }
    }

    /**
     * 处理消息的具体业务逻辑
     * @param message 消息内容
     */
    private void processMessage(String message) {
        // 根据消息内容进行业务处理
        // 1. 可以解析消息内容，提取关键信息
        // 2. 根据消息类型进行不同的处理
        // 3. 调用相应的服务完成业务逻辑

        // 这里只是示例，实际业务逻辑需要根据项目需求实现
        if (message.contains("article")) {
            // 处理文章相关消息
            log.info("处理文章相关消息");
        } else if (message.contains("user")) {
            // 处理用户相关消息
            log.info("处理用户相关消息");
        } else {
            // 处理其他类型消息
            log.info("处理其他类型消息");
        }
    }
}