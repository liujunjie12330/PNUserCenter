package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 类目管理表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pn_catalog")
public class PnCatalog extends BaseModel {
    private static final long serialVersionUID = -7901244407632945451L;
    /**
     * 类目名称
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 状态：0-未发布，1-已发布
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 排序
     */
    @TableField(value = "`rank`")
    private Integer rank;
}