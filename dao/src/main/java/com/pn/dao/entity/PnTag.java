package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签管理表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "pn_tag")
public class PnTag extends BaseModel {
    /**
     * 标签名称
     */
    @TableField(value = "tag_name")
    private String tagName;

    /**
     * 标签类型：1-系统标签，2-自定义标签
     */
    @TableField(value = "tag_type")
    private Byte tagType;

    /**
     * 状态：0-未发布，1-已发布
     */
    @TableField(value = "`status`")
    private Byte status;
}