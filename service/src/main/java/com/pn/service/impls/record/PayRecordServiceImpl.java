package com.pn.service.impls.record;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.PayTypeEnum;
import com.pn.dao.entity.*;
import com.pn.dao.mapper.*;
import com.pn.service.PayRecordService;
import com.pn.service.bean.config.PoolConfig;
import com.pn.service.utils.RedisCache;
import com.pn.service.utils.cover.RecordCoverUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

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

    /**
     * 记录所有的交易信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveRecord(Map<String, String[]> parameterMap) {
        Long reArticleId = 0L;
        String buyerId = parameterMap.get("buyer_id")[0];
        PnAlipayUserInfo userInfo = infoMapper.getByUuId(buyerId);
        PnTransactions transaction = RecordCoverUtil.parmaCoverToPT(parameterMap, userInfo.getPnUserId());
        //保存订单数据
        save(transaction);
        //查看支付的类型
        String paytype = (String) redisCache.getHashCache(transaction.getOrderId(), "paytype");
        //用户支付宝的回调信息，判断是否支付成功
        if (StringUtils.equalsIgnoreCase(transaction.getTradeStatus(), "1")) {
            //文章支付
            if (StringUtils.equalsIgnoreCase(paytype, PayTypeEnum.ARTICLE.getType())) {
                Long articleId = reArticleId = (Long) redisCache.getHashCache(transaction.getOrderId(), "articleId");
                redisCache.set(PNUserCenterConstant.ARTICLE_PAID + userInfo.getPnUserId() + "_" + articleId, articleId);
                //把支付的相关信息存入到文章支付信息表,异步通知mq发起转账
                PoolConfig.RUN_SYNC_JOB_POOL.submit(() -> {
                    try {
                        PnArticle article = articleMapper.selectById(articleId);
                        PnAlipayUserInfo receiveInfo = infoMapper.getByUserId(article.getUserId());
                        PnArticlePayRecord payRecord = RecordCoverUtil.paramCoverToPA(parameterMap, buyerId, userInfo.getPnUserId(), receiveInfo.getAlipayUuid(), receiveInfo.getPnUserId(), articleId);
                        payRecordMapper.insert(payRecord);
                        //todo发出异步消息，通知平台打款给作者，并且进行积分奖励
                    } catch (Exception e) {
                        log.error("文章支付出现错误==>{}",e.getMessage());
                    }
                });
            }
            //第三方转账
            if (StringUtils.equalsIgnoreCase(paytype, PayTypeEnum.PAY_TO_THIRD.getType())) {
                //todo 发出消息，邮件通知
            }
        }
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            //完成事务之后的回调函数，在
            @Override
            public void afterCompletion(int status) {
                //代表事务顺利完成,把用户的支付信息删除，释放空间
                if (Objects.equals(status, STATUS_COMMITTED)) {
                    redisCache.delHashCache(transaction.getOrderId(),"payinfo","paystatus","paytypes");
                }
            }
        });
        return reArticleId;
    }

}
