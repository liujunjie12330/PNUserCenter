package com.pn.service.utils.cover;

import com.pn.common.enums.PushStatusEnum;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.common.reqParams.article.ColumnParam;
import com.pn.common.reqParams.article.TagParam;
import com.pn.common.vos.article.*;
import com.pn.dao.entity.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
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
        article.setArticleType(params.getArticleType());
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

    public static ArticleVO coverToArticleVo(PnArticle article,
                                              PnArticleDetail articleDetail,
                                              List<PnTag> tagList,
                                              PnColumnInfo columnInfo,
                                              PnCatalog catalog,
                                              PnUser user) {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setAuthorInfo(SimpleUserInfoDTO
                .builder()
                .userId(user.getId()).avatar(user.getAvatar())
                .name(user.getFullName())
                .build());
        articleVO.setArticleId(article.getId());
        articleVO.setTitle(article.getTitle());
        articleVO.setShortTitle(article.getShortTitle());
        articleVO.setCover(article.getPicture());
        articleVO.setSummary(article.getSummary());
        articleVO.setArticleType(article.getArticleType());
        articleVO.setSource(article.getSource());
        articleVO.setSourceUrl(article.getSourceUrl());
        articleVO.setOfficalStat(article.getOfficalStat());
        articleVO.setPayImageUrl(article.getPayImageUrl());
        articleVO.setRecommend(article.getRecommend());
        articleVO.setContext(articleDetail.getContent());
        articleVO.setCountVo(null);
        articleVO.setTags(ListUtil.coverToListVo(tagList, tag -> TagVo
                .builder()
                .tagId(tag.getId())
                .tagName(tag.getTagName())
                .build()));
        articleVO.setCatalog(CatalogPaveVo
                .builder()
                .categoryId(catalog.getId())
                .categoryName(catalog.getCategoryName())
                .build());
        return articleVO;
    }

}
