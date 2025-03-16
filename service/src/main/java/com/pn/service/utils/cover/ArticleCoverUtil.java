package com.pn.service.utils.cover;

import com.google.common.collect.Lists;
import com.pn.common.enums.PayTypeEnum;
import com.pn.common.enums.PushStatusEnum;
import com.pn.common.enums.ThirdPayWayEnum;
import com.pn.common.reqParams.article.ArticleSaveParams;
import com.pn.common.reqParams.article.ColumnParam;
import com.pn.common.reqParams.article.TagParam;
import com.pn.common.vos.article.*;
import com.pn.dao.entity.*;
import com.pn.service.impls.pay.dto.AlipayByQrCodeDto;
import com.pn.service.utils.id.IdUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章转换工具
 */
public class ArticleCoverUtil {
    /**
     * 构建保存对象 params==>bean
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
        article.setReadType(params.getReadType());
        article.setSourceUrl(params.getSourceUrl());
        article.setStatus(params.getStatus());
        article.setPayWay(params.getPayWay());
        article.setPayAmount(params.getPayAmount());
        article.setPayImageUrl(params.getPayImageUrl());
        article.setCreateBy(userId);
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


    public static AlipayByQrCodeDto articleCoverToDto(PnArticle article) {
        AlipayByQrCodeDto codeDto = new AlipayByQrCodeDto();
        codeDto.setOutBizNo(IdUtil.genPayCode(ThirdPayWayEnum.ALI_QR, article.getId()));
        codeDto.setTransAmount(StringUtils.isEmpty(article.getPayAmount()) ? "0.88" : article.getPayAmount());
        codeDto.setTitle(String.format("尊敬的用户,您正在支付文章:%s", article.getTitle()));
        codeDto.setRemark("本次支付的结果会以邮件或者平台消息通知您");
        codeDto.setPayType(PayTypeEnum.ARTICLE.getType());
        return codeDto;
    }

    public static ArticleIndexVo coverTpIndexVo(ArticleIndexVo indexVo,
                                         PnArticle article,
                                         List<PnTag> tags,
                                         PnColumnInfo columnInfo,
                                         PnCatalog catalog,
                                         PnUser user) {
        if (Objects.isNull(indexVo)) {
            indexVo = new ArticleIndexVo();
        }
        indexVo.setArticleId(article.getId());
        indexVo.setReadType(article.getReadType());
        indexVo.setAuthorId(article.getUserId());
        indexVo.setSummary(article.getSummary());
        indexVo.setAuthorName(user.getFullName());
        indexVo.setAuthorAvatar(user.getAvatar());
        indexVo.setTitle(article.getTitle());
        indexVo.setShortTitle(article.getShortTitle());
        indexVo.setCover(article.getPicture());
        indexVo.setOfficalStat(article.getOfficalStat());
        indexVo.setToppingStat(article.getToppingStat());
        indexVo.setRecommend(article.getRecommend());
        indexVo.setUpdateTime(article.getUpdateAt());
        List<TagVo> tagVos;
        if (CollectionUtils.isNotEmpty(tags)) {
            tagVos = ListUtil.coverToListVo(tags, tag -> {
                return TagVo.builder()
                        .tagId(tag.getId())
                        .status(tag.getStatus())
                        .tagName(tag.getTagName())
                        .tagType(tag.getTagType())
                        .build();
            });
        } else {
            tagVos = Lists.newArrayList();
        }
        indexVo.setTagVos(tagVos);
        if (Objects.nonNull(columnInfo)){
            indexVo.setColumnId(columnInfo.getId());
            indexVo.setColumnName(columnInfo.getColumnName());
        }
        if (Objects.nonNull(catalog)){
            indexVo.setCatalogId(catalog.getId());
            indexVo.setCatalogName(catalog.getCategoryName());
        }

        return indexVo;
    }

    public static ArticleVO coverToArticleVo(ArticleVO articleVO,
                                             PnArticle article,
                                             PnArticleDetail articleDetail,
                                             List<PnTag> tags,
                                             PnColumnInfo columnInfo,
                                             PnCatalog catalog,
                                             PnUser user) {
        // 如果 articleVO 为 null，则初始化
        if (Objects.isNull(articleVO)) {
            articleVO = new ArticleVO();
        }

        // 设置文章基本信息
        articleVO.setArticleId(article.getId());
        articleVO.setTitle(article.getTitle());
        articleVO.setShortTitle(article.getShortTitle());
        articleVO.setCover(article.getPicture());
        articleVO.setSummary(article.getSummary());
        articleVO.setArticleType(article.getArticleType());
        articleVO.setSource(article.getSource());
        articleVO.setSourceUrl(article.getSourceUrl());
        articleVO.setOfficalStat(article.getOfficalStat());
        articleVO.setRecommend(article.getRecommend());
        articleVO.setContext(articleDetail.getContent());

        // 设置标签信息
        List<TagVo> tagVos;
        if (CollectionUtils.isNotEmpty(tags)) {
            tagVos = ListUtil.coverToListVo(tags, tag -> {
                return TagVo.builder()
                        .tagId(tag.getId())
                        .status(tag.getStatus())
                        .tagName(tag.getTagName())
                        .tagType(tag.getTagType())
                        .build();
            });
        } else {
            tagVos = Lists.newArrayList();
        }
        articleVO.setTags(tagVos);

        // 设置分类信息
        CatalogPaveVo catalogPaveVo;
        if (columnInfo != null) {
            catalogPaveVo = CatalogPaveVo.builder()
                    .categoryName(catalog.getCategoryName())
                    .categoryId(catalog.getId())
                    .rank(catalog.getRank())
                    .status(catalog.getStatus())
                    .build();

        }else {
            catalogPaveVo = null;
        }
        articleVO.setCatalog(catalogPaveVo);
        return articleVO;
    }




}
