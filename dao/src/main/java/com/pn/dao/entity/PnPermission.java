package com.pn.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 权限表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pn_permission")
public class PnPermission extends BaseModel {
    private static final long serialVersionUID = -984244843203302668L;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否删除1--删除
     */
    @TableField(value = "is_deleted")
    private Boolean isDeleted;

    /**
     * 权限CODE
     */
    @TableField(value = "code")
    private String code;

    /**
     * 权限名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 展示名称
     */
    @TableField(value = "display")
    private String display;

    /**
     * 权限状态 0--可授权,1--禁止授权
     */
    @TableField(value = "`status`")
    private Integer status;
}