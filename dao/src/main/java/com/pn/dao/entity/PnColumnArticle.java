package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 专栏文章表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_column_article")
public class PnColumnArticle extends BaseModel {
    /**
     * 专栏ID
     */
    @TableField(value = "column_id")
    private Long columnId;

    /**
     * 文章ID
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 章节顺序，越小越靠前
     */
    @TableField(value = "`section`")
    private Integer section;
}