package com.pn.common.reqParams.user;

import com.pn.common.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleParam extends PageParam {
    private static final long serialVersionUID = 1L;

    private long roleId;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 权限id
     */
    private Long permissionId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 角色类型
     */
    private Integer roleType;
}
