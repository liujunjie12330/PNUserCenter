package com.pn.dao.bo.user;


import lombok.Data;

import java.util.Date;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/26 16:55
 * @Descirption xxx
 */
@Data
public class SearchUserPermissionBo {
    private static final long serialVersionUID = -8305370453007829239L;

    private int id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户全名
     */
    private String fullName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否管理员
     */
    private Integer isAdmin;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginDate;

    /**
     * 最后一次修改时间
     */
    private Date updateLastTime;


    private String avatar;


    /**
     * 角色id
     */
    private Long roleId;


    private Long permissionId;

    /**
     * 权限CODE
     */
    private String code;

    /**
     * 权限名
     */
    private String name;

    /**
     * 展示名称
     */
    private String display;

    /**
     * 权限状态 0--可授权,1--禁止授权
     */
    private Integer status;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型 0--系统内置，1--自定义
     */
    private Boolean roleType;

    /**
     * role_value-->admin-->000
     */
    private String roleValue;

    /**
     * 角色描述
     */
    private String roleDesc;
}
