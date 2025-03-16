package com.pn.service.impls.record;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.PayTypeEnum;
import com.pn.dao.entity.*;
import com.pn.dao.mapper.*;
import com.pn.service.EmailService;
import com.pn.service.PayRecordService;
import com.pn.service.impls.mq.ArticlePayMessageProducer;
import com.pn.service.utils.RedisCache;
import com.pn.service.utils.cover.RecordCoverUtil;
import com.pn.service.utils.id.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付记录
 */
@Service
@Slf4j
public class PayRecordServiceImpl extends ServiceImpl<PnTransactionsMapper, PnTransactions> implements PayRecordService {
    @Resource
    private PnAlipayUserInfoMapper infoMapper;

    @Resource
    private RedisCache redisCache;

    @Resource
    private PnArticleMapper articleMapper;

    @Resource
    private PnArticlePayRecordMapper payRecordMapper;

    @Resource
    private ArticlePayMessageProducer producer;

    @Resource
    private EmailService emailService;

    @Resource
    private PnUserMapper userMapper;

    /**
     * 记录所有的交易信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveRecord(Map<String, String[]> parameterMap) {
        log.info("call back param==>{}", JSONUtil.toJsonStr(parameterMap));
        Long reArticleId = 0L;
        String buyerId = parameterMap.get("buyer_id")[0];
        PnAlipayUserInfo userInfo = infoMapper.getByUuId(buyerId);
        PnTransactions transaction = RecordCoverUtil.parmaCoverToPT(parameterMap, userInfo.getPnUserId());
        //查看支付的类型
        String paytype = (String) redisCache.getHashCache(transaction.getOrderId(), "pay_type");
        transaction.setPayType(paytype);
        //保存订单数据
        save(transaction);
        //文章支付
        if (StringUtils.equalsIgnoreCase(paytype, PayTypeEnum.ARTICLE.getType())) {
            Long articleId = IdUtil.parseIdFromPayCode(transaction.getOrderId());
            reArticleId = articleId;
            redisCache.set(PNUserCenterConstant.ARTICLE_PAID + userInfo.getPnUserId() + "_" + articleId, articleId);
            //把支付的相关信息存入到文章支付信息表,异步通知mq发起转账
            Map<String, String[]> localParameterMap = new HashMap<>(parameterMap);
            try {
                PnArticle article = articleMapper.selectById(articleId);
                PnAlipayUserInfo receiveInfo = infoMapper.getByUserId(article.getUserId());
                PnArticlePayRecord payRecord = RecordCoverUtil.paramCoverToPA(localParameterMap, buyerId, userInfo.getPnUserId(), receiveInfo.getAlipayUuid(), receiveInfo.getPnUserId(), articleId);
                payRecordMapper.insert(payRecord);
                producer.sendMessage(String.valueOf(payRecord.getOutBizNo()));
                PnUser pnUser = userMapper.selectById(receiveInfo.getPnUserId());
                emailService.sendArticlePaid(pnUser.getFullName(), article.getTitle(), article.getUserId());
            } catch (Exception e) {
                log.error("call back sync error==>{}", e.getMessage());
            }
        }
        //第三方转账
        if (StringUtils.equalsIgnoreCase(paytype, PayTypeEnum.PAY_TO_THIRD.getType())) {
            //todo 发出消息，邮件通知
        }
        return reArticleId;
    }

}
