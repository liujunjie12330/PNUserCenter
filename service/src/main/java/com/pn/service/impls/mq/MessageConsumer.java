package com.pn.service.impls.mq;

import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.PayTypeEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.enums.ThirdPayWayEnum;
import com.pn.common.exception.BizException;
import com.pn.dao.entity.PnArticlePayRecord;
import com.pn.dao.entity.PnTransactions;
import com.pn.dao.mapper.PnArticlePayRecordMapper;
import com.pn.dao.mapper.PnTransactionsMapper;
import com.pn.service.PayService;
import com.pn.service.impls.pay.dto.AlipayToThirdUserDto;
import com.pn.service.utils.id.IdUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;


/**
 * 消息消费者
 * 用于消费directQueue队列中的消息
 */
@Component
@Slf4j
public class MessageConsumer {

    @Resource
    private PayService payService;



    @Resource
    private PnArticlePayRecordMapper payRecordMapper;
    /**
     * 处理接收到的消息
     *
     * @param message 接收到的消息内容
     */
    @RabbitHandler
    @RabbitListener(queues = PNUserCenterConstant.THIRD_PAY_QUEUE)
    public void process(String outBizNo, Channel channel, Message message) throws IOException {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            processMessage(outBizNo);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("处理消息失败，准备重新入队", e);
            channel.basicNack(tag, false, true);
        }
    }

    /**
     * 处理消息的具体业务逻辑
     *
     * @param outBizNo 文章id
     */
    private void processMessage(String outBizNo) {
        if (StringUtils.isBlank(outBizNo)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        Long id = IdUtil.parseIdFromPayCode(outBizNo);
        PnArticlePayRecord payRecord = payRecordMapper.getByOutBizNo(outBizNo);
        if(Objects.isNull(payRecord)) {
            throw new BizException(StatusCode.TRANSACTION_NOT_EXIST);
        }
        AlipayToThirdUserDto dto = initDto(payRecord);
        payService.payToThirdUser(dto, PayTypeEnum.PAY_TO_THIRD);
    }

    private AlipayToThirdUserDto initDto(PnArticlePayRecord payRecord){
        AlipayToThirdUserDto dto = new AlipayToThirdUserDto();
        dto.setArticleId(payRecord.getArticleId());
        dto.setAuthorId(payRecord.getReceivePnUserId());
        dto.setOutBizNo(IdUtil.genPayCode(ThirdPayWayEnum.ALI_THIRD_PAY,payRecord.getReceivePnUserId()));
        dto.setTransAmount(payRecord.getPayAmount());
        dto.setTitle("尊敬的用户,您的文章收到一笔支付");
        dto.setRemark("");
        return dto;
    }
}