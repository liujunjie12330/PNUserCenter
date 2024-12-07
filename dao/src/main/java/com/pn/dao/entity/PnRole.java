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
 * 角色表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pn_role")
public class PnRole extends BaseModel {
    private static final long serialVersionUID = 1222430275650395208L;
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
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色类型 0--系统内置，1--自定义
     */
    @TableField(value = "role_type")
    private Boolean roleType;

    /**
     * role_value-->admin-->000
     */
    @TableField(value = "role_value")
    private String roleValue;

    /**
     * 角色描述
     */
    @TableField(value = "role_desc")
    private String roleDesc;
}