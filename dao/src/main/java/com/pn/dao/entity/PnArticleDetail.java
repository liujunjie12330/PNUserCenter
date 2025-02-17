package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章详细表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_article_detail")
public class PnArticleDetail extends BaseModel {
    private static final long serialVersionUID = -387499284706829184L;
    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 版本号
     */
    @TableField(value = "version")
    private Integer version;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;
}