package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;

@Data
@TableName("integral")
public class Integral {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分值
     */
    private Long value;

    /**
     * 加分类型
     */
    private String type;

    /**
     * 加分描述
     */
    @TableField(value = "\"desc\"")
    private String desc;

}
