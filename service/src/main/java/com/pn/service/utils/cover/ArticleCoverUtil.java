package com.pn.service.utils.cover;

import com.pn.common.enums.PushStatusEnum;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.common.reqParams.article.ColumnParam;
import com.pn.common.reqParams.article.TagParam;
import com.pn.dao.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

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
        article.setArticleTypeId(params.getArticleType());
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

    public static PnColumnInfo paramCoverToColumnInfo(PnColumnInfo columnInfo, ColumnParam param, Long userId) {
        if (Objects.isNull(columnInfo)) {
            columnInfo = new PnColumnInfo();
        }
        columnInfo.setColumnName(columnInfo.getColumnName());
        columnInfo.setUserId(columnInfo.getUserId());
        columnInfo.setIntroduction(columnInfo.getIntroduction());
        columnInfo.setCover(columnInfo.getCover());
        columnInfo.setState(columnInfo.getState());
        columnInfo.setSection(columnInfo.getSection());
        columnInfo.setNums(columnInfo.getNums());
        columnInfo.setType(columnInfo.getType());
        columnInfo.setFreeStartTime(columnInfo.getFreeStartTime());
        columnInfo.setFreeEndTime(columnInfo.getFreeEndTime());
        columnInfo.setCreateBy(userId);
        columnInfo.setUpdateBy(userId);
        columnInfo.setIsDeleted(columnInfo.getIsDeleted());
        columnInfo.setId(columnInfo.getId());
        return columnInfo;
    }

    public static PnColumnArticle paramCoverToCA(Long articleId, Long columnId) {
        PnColumnArticle pnColumnArticle = new PnColumnArticle();
        pnColumnArticle.setColumnId(columnId);
        pnColumnArticle.setArticleId(articleId);
        return pnColumnArticle;
    }

    public static PnTag paramCoverToPNTag(PnTag pnTag, TagParam param, Long userId) {
        if (Objects.isNull(param)) {
            pnTag = new PnTag();
        }
        pnTag.setTagName(param.getTagName());
        pnTag.setStatus(PushStatusEnum.OFFLINE.getCode());
        pnTag.setTagType(2);
        return pnTag;
    }

}
