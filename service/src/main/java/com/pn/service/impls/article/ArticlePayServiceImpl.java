package com.pn.service.impls.article;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.PayTypeEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnArticle;
import com.pn.dao.entity.PnArticlePayRecord;
import com.pn.dao.mapper.PnArticleMapper;
import com.pn.dao.mapper.PnArticlePayRecordMapper;
import com.pn.service.ArticlePayService;
import com.pn.service.PayService;
import com.pn.service.impls.pay.AliPayService;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.utils.RedisCache;
import com.pn.service.utils.cover.ArticleCoverUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 文章支付
 */
@Service
@Slf4j
public class ArticlePayServiceImpl extends ServiceImpl<PnArticlePayRecordMapper, PnArticlePayRecord> implements ArticlePayService {

    @Resource
    private PnArticlePayRecordMapper recordMapper;

    @Resource
    private PnArticleMapper articleMapper;

    @Resource(type = AliPayService.class)
    private PayService payService;
    @Autowired
    private RedisCache redisCache;

    @Override
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
        redisCache.setHashCache(codeDto.getOutBizNo(), "articleId", articleId);
        String url = payService.payByQrCode(codeDto, PayTypeEnum.ARTICLE);
        if (StringUtils.isEmpty(url)) {
            throw new BizException(StatusCode.SYSTEM_ERROR);
        }
        return url;
    }


    @Override
    public boolean isPaid(Long articleId, Long userId) {
        //现在redis里面查一次
        boolean key = redisCache.hasKey(PNUserCenterConstant.ARTICLE_PAID + userId + "_" + articleId);
        if (BooleanUtils.isTrue(key)) {
            return key;
        }
        boolean isPaid = recordMapper.isPaid(articleId, userId);
        if (BooleanUtils.isTrue(isPaid)) {
            redisCache.set(PNUserCenterConstant.ARTICLE_PAID + userId + "_" + articleId, articleId);
        }
        return isPaid;
    }
}
