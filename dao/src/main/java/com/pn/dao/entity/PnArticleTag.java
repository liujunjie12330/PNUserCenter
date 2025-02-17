package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章标签映射
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_article_tag")
public class PnArticleTag extends BaseModel {
    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 标签
     */
    @TableField(value = "tag_id")
    private Long tagId;
}