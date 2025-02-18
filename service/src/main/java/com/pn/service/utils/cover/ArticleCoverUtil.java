package com.pn.service.utils.cover;

import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.dao.entity.PnArticle;
import com.pn.dao.entity.PnArticleDetail;
import com.pn.dao.entity.PnArticleTag;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 文章转换工具
 */
public class ArticleCoverUtil {
    /**
     * 构建保存对象 params==>bean
     *
     * @param params
     * @return
     */
    public static PnArticle paramCoverToPnArticle(PnArticle article, ArticleSaveParams params, Long userId) {
        if (Objects.isNull(article)) {
            article = new PnArticle();
        }
        article.setId(params.getArticleId());
        article.setUserId(userId);
        article.setTitle(params.getTitle());
        article.setShortTitle(StringUtils.isEmpty(params.getShortTitle()) ? params.getTitle() : params.getShortTitle());
        article.setPicture(params.getCover());
        article.setSummary(params.getSummary());
        article.setCatalogId(params.getCategoryId());
        article.setArticleTypeId(params.getArticleTypeId());
        article.setSource(params.getSource());
        article.setSourceUrl(params.getSourceUrl());
        article.setStatus(params.getStatus());
        article.setPayWay(params.getPayWay());
        article.setPayAmount(params.getPayAmount());
        article.setPayImageUrl(params.getPayImageUrl());
        return article;
    }

    public static PnArticleDetail paramCoverToArticleDetail(PnArticleDetail articleDetail, ArticleSaveParams params, Long articleId) {
        if (Objects.isNull(articleDetail)) {
            articleDetail = new PnArticleDetail();
        }
        articleDetail.setArticleId(articleId);
        articleDetail.setVersion(0);
        articleDetail.setContent(params.getContent());
        return articleDetail;
    }

}
