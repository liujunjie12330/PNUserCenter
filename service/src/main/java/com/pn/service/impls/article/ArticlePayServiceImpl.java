package com.pn.service.impls.article;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.PayTypeEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnArticle;
import com.pn.dao.entity.PnArticlePayRecord;
import com.pn.dao.entity.PnOrder;
import com.pn.dao.mapper.PnArticleMapper;
import com.pn.dao.mapper.PnArticlePayRecordMapper;
import com.pn.dao.mapper.PnOrderMapper;
import com.pn.service.ArticlePayService;
import com.pn.service.PayService;
import com.pn.service.impls.pay.AliPayService;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.utils.RedisCache;
import com.pn.service.utils.cover.ArticleCoverUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 文章支付
 */
@Service
@Slf4j
public class ArticlePayServiceImpl extends ServiceImpl<PnArticlePayRecordMapper,PnArticlePayRecord> implements ArticlePayService {

    @Resource
    private PnArticlePayRecordMapper recordMapper;

    @Resource
    private PnArticleMapper articleMapper;

    @Resource(type = AliPayService.class)
    private PayService payService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private PnOrderMapper orderMapper;

    @Override
    @Transactional
    public String payArticle(Long articleId) throws AlipayApiException {
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        //查询文章是否被当前用户支付过
        if (isPaid(articleId, currentUser.getId())) {
            return null;
        }
        //没有支付过，查询出文章
        PnArticle article = articleMapper.selectById(articleId);
        if (Objects.isNull(article)) {
            throw new BizException(StatusCode.NO_SUCH_ARTICLE);
        }
        AlipayByQrCodeDto codeDto = ArticleCoverUtil.articleCoverToDto(article);
        String url = payService.payByQrCode(codeDto, PayTypeEnum.ARTICLE);
        if (StringUtils.isEmpty(url)) {
            throw new BizException(StatusCode.SYSTEM_ERROR);
        }
        return url;
    }


    @Override
    public boolean isPaid(Long articleId, Long userId) {
        //支付成功并且成功回调
        if (redisCache.hasKey(String.format(PNUserCenterConstant.ARTICLE_PAID, articleId, userId))) {
            return true;
        }
        //支付成功并且成功回调，但是redis里面没有
        boolean paid = recordMapper.isPaid(articleId, userId);
        if (BooleanUtils.isTrue(paid)) {
            redisCache.set(String.format(PNUserCenterConstant.ARTICLE_PAID, articleId, userId), "true");
            return true;
        }
        //没有成功回调，并且没有成功调起支付界面  redis 和 mysql都没有存在数据
        if (!redisCache.hasKey(String.format(PNUserCenterConstant.ORDER_PREFIX, articleId, userId))
                && !orderMapper.exist(articleId,userId,PayTypeEnum.ARTICLE.getType())) {
            return false;
        }
        //成功发起支付界面，但是没有回调,要去第三方平台进行查询
        if (redisCache.hasKey(String.format(PNUserCenterConstant.ORDER_PREFIX, articleId, userId))) {
            String outBizNo = (String) redisCache.get(String.format(PNUserCenterConstant.ORDER_PREFIX, articleId, userId));
            AlipayTradeQueryResponse response = payService.queryPay(outBizNo, null);
            return StringUtils.equalsIgnoreCase("TRADE_SUCCESS",response.getTradeStatus());
        }
        //如果redis里面没有，就在订单表里面进行查询
        PnOrder pnOrder = orderMapper.selectArticleIdAndUserId(articleId, userId);
        if (Objects.isNull(pnOrder)){
            return false;
        }
        String outBizNo = pnOrder.getOutBizNo();
        AlipayTradeQueryResponse response = payService.queryPay(outBizNo, null);
        return StringUtils.equalsIgnoreCase("TRADE_SUCCESS",response.getTradeStatus());
    }
}
