package com.pn.common.reqParams.user;


import com.pn.common.base.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置的相关参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRolePermissionSettingParam extends PageParam {
    private static final long serialVersionUID = 2206182507074232628L;

    /*用户相关*/
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户账户名称
     */
    private String username;

    /**
     * 创建人
     */
    private Long creatBy;

    /**
     * 创建人
     */
    private Long updateBy;


    /*角色相关*/
    /**
     * 角色id
     */
    private long roleId;
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

    /*权限相关*/
    /**
     * 权限id
     */
    private Long permissionId;
    /**
     * 权限名称
     */
    private String permissionName;

    /**
     *权限code
     */
    private String permissionCode;

    /**
     *权限展示名称
     */
    private String permissionDisplay;
    /**
     * 权限授权状态
     */
    private String permissionStatus;

    /*角色关联权限表，用户关联角色表相关*/
    /**
     * role_user表id
     */
    private Long roleUserId;

    /**
     * role_permission表id
     */
    private Long rolePermissionId;
}
