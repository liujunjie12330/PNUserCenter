package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_article")
public class PnArticle extends BaseModel {
    private static final long serialVersionUID = 3974221729194735357L;
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 文章标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 短标题
     */
    @TableField(value = "short_title")
    private String shortTitle;

    /**
     * 文章头图链接
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 文章摘要
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 类目ID
     */
    @TableField(value = "catalog_id")
    private Long catalogId;

    /**
     * 文章类型
     */
    @TableField(value = "article_type_id")
    private Long articleTypeId;

    /**
     * 来源：1-转载，2-原创，3-翻译
     */
    @TableField(value = "`source`")
    private Byte source;

    /**
     * 原文链接
     */
    @TableField(value = "source_url")
    private String sourceUrl;

    /**
     * 官方状态：0-非官方，1-官方
     */
    @TableField(value = "offical_stat")
    private Byte officalStat;

    /**
     * 置顶状态：0-不置顶，1-置顶
     */
    @TableField(value = "topping_stat")
    private Byte toppingStat;

    /**
     * 状态：0-未发布，1-待审核,2-待发布
     */
    @TableField(value = "`status`")
    private Byte status;
}