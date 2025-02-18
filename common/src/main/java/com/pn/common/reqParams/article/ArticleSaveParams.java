package com.pn.common.reqParams.article;

import com.pn.common.enums.ArticleTypeEnum;
import com.pn.common.enums.PushStatusEnum;
import com.pn.common.enums.SourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author: javadadi
 * @Time: 14:04
 * @ClassName: ArticleSaveParams
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveParams implements Serializable {
    private static final long serialVersionUID = -8075769541490100482L;
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章短标题
     */
    private String shortTitle;

    /**
     * 分类
     */
    private Long categoryId;

    /**
     * 标签
     */
    private Set<Long> tagIds;

    /**
     * 简介
     */
    private String summary;

    /**
     * 正文内容
     */
    private String content;

    /**
     * 封面
     */
    private String cover;

    /**
     * 文本类型
     *
     * @see ArticleTypeEnum
     */
    private Integer articleTypeId;


    /**
     * 来源：1-转载，2-原创，3-翻译
     *
     * @see SourceTypeEnum
     */
    private Integer source;

    /**
     * 状态：状态：0-未发布，1-待审核,2-待发布
     *
     * @see PushStatusEnum
     */
    private Integer status;

    /**
     * 原文地址
     */
    private String sourceUrl;

    /**
     * POST 发表, SAVE 暂存 DELETE 删除
     */
    private String actionType;

    /**
     * 专栏序号
     */
    private Long columnId;

    /**
     * 文章阅读类型
     *
     * @see com.pn.common.enums.ArticleReadTypeEnum#getType()
     */
    private Integer readType;
    /**
     * 当 ArticleReadTypeEnum 为 付费阅读时，这里记录具体的收款方式
     */
    private Integer payWay;
    /**
     * 付费解锁价格
     */
    private String payAmount;
    /**
     * 打赏的收款码截图
     */
    private String payImageUrl;
}
