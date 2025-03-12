package com.pn.common.vos.article;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

/**
 * 文章信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "article_vo")
public class ArticleVO implements Serializable {
    private static final long serialVersionUID = -793906904770296838L;

    /*作者信息*/
    /**
     * 作者信息
     */
    @Field(index = false)
    private SimpleUserInfoDTO authorInfo;

    /*文章信息*/
    /**
     * 文章ID
     */
    @Id
    private Long articleId;

    /**
     * 文章标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 短标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String shortTitle;

    /**
     * 文章头图链接
     */
    @Field(type = FieldType.Text)
    private String cover;

    /**
     * 文章摘要
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;

    /**
     * 文章类型
     */
    @Field(type = FieldType.Integer)
    private Integer articleType;

    /**
     * 来源：1-转载，2-原创，3-翻译
     */
    @Field(type = FieldType.Integer)
    private Integer source;

    /**
     * 原文链接
     */
    @Field(type = FieldType.Text)
    private String sourceUrl;

    /**
     * 官方状态：0-非官方，1-官方
     */
    @Field(type = FieldType.Integer)
    private Integer officalStat;

    /**
     * 打赏的收款码截图
     */
    @Field(type = FieldType.Text)
    private String payImageUrl;

    /**
     * 是否推荐1--推荐
     */
    @Field(type = FieldType.Integer)
    private Integer recommend;

    /*article detail*/
    /**
     * 正文
     */
    @Field(type = FieldType.Text)
    private String context;

    /*文章统计信息*/
    /**
     * 文章统计信息
     */
    @Field(index = false)
    private ArticleFootCountVo countVo;

    /*标签信息*/
    /**
     * 标签
     */
    @Field(type = FieldType.Nested)
    private List<TagVo> tags;

    /*分类信息*/
    /**
     * 分类信息
     */
    @Field(type = FieldType.Nested)
    private CatalogPaveVo catalog;

    /*支付相关的信息*/
    @Field(index = false)
    private boolean isNeedTpPay = false;

    @Field(index = false)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIsNeedTpPay(boolean b) {
        this.isNeedTpPay=b;
    }

    public boolean isNeedTpPay() {
        return isNeedTpPay;
    }
}
