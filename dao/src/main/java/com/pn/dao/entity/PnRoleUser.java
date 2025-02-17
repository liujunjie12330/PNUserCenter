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
 * 角色用户关联表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pn_role_user")
public class PnRoleUser extends BaseModel {
    private static final long serialVersionUID = -4400303255479572977L;
    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;
}