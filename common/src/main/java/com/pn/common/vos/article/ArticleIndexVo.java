package com.pn.common.vos.article;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 首页文章列表查询实体类，映射到 Elasticsearch 索引
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "article_index_vo")  // Elasticsearch 索引名称
public class ArticleIndexVo implements Serializable {

    private static final long serialVersionUID = -3971925590748824678L;
    /**
     * 文章 ID
     */
    @Id
    private Long articleId;

    /**
     * 阅读类型
     */
    @Field(type = FieldType.Integer)
    private Integer readType;

    /**
     * 作者 ID
     */
    @Field(type = FieldType.Long)
    private Long authorId;

    /**
     * 文章摘要
     * 使用 IK 分词器进行全文索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String summary;

    /**
     * 作者姓名
     * 使用 IK 分词器进行全文索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String authorName;

    /**
     * 作者头像
     */
    @Field(type = FieldType.Text)
    private String authorAvatar;

    /**
     * 文章标题
     * 使用 IK 分词器进行全文索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String title;

    /**
     * 文章短标题
     * 使用 IK 分词器进行全文索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String shortTitle;

    /**
     * 封面图片
     */
    @Field(type = FieldType.Text)
    private String cover;

    /**
     * 官方状态
     * 0 - 非官方，1 - 官方
     */
    @Field(type = FieldType.Integer)
    private Integer officalStat;

    /**
     * 是否置顶
     * 0 - 不推荐，1 - 推荐
     */
    @Field(type = FieldType.Integer)
    private Integer toppingStat;

    /**
     * 推荐指数
     */
    @Field(type = FieldType.Integer)
    private Integer recommend;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date)
    private Date updateTime;

    /**
     * 标签列表
     * 使用嵌套类型，以支持多标签查询
     */
    @Field(type = FieldType.Nested)
    private List<TagVo> tagVos;

    /**
     * 栏目 ID
     */
    @Field(type = FieldType.Long)
    private Long columnId;

    /**
     * 栏目名称
     * 使用 IK 分词器进行全文索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String columnName;

    /**
     * 目录名称
     * 使用 IK 分词器进行全文索引
     */
    @Field(type = FieldType.Text, analyzer = "ik_analyzer")
    private String catalogName;

    /**
     * 目录 ID
     */
    @Field(type = FieldType.Long)
    private Long catalogId;
    /**
     * 用户信息
     */
    @Field(index = false)
    private ArticleFootCountVo articleFootCountVo;
}