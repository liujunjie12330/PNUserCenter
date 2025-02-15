package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@TableName("integral_weight")
public class IntegralWeight {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 积分类型
     */
    private String key;

    /**
     * 积分值
     */
    private Long value;

    /**
     * 加分名称
     */
    private String title;

    /**
     * 描述
     */
    @TableField(value = "\"desc\"")
    private String desc;

    /**
     * 上限，-1为无上限
     */
    private Long upperLimit;

    /**
     * 是否启用
     */
    private Boolean isUse;

}
