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
 * 角色权限关联表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pn_role_permission")
public class PnRolePermission extends BaseModel {
    private static final long serialVersionUID = 787952365387990714L;
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
     * 关联角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 关联权限id
     */
    @TableField(value = "permission_id")
    private Long permissionId;
}