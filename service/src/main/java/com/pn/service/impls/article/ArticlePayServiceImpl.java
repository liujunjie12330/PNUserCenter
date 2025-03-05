package com.pn.service.impls.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
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
import com.pn.service.utils.cover.ArticleCoverUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public String payArticle(Long articleId) {
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
        String url = payService.payByQrCode(codeDto);
        if (StringUtils.isEmpty(url)) {
            throw new BizException(StatusCode.SYSTEM_ERROR);
        }
        return url;
    }


    @Override
    public boolean isPaid(Long articleId, Long userId) {
        return recordMapper.isPaid(articleId, userId);
    }
}
