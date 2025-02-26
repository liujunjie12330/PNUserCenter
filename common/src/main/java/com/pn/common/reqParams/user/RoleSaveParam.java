package com.pn.common.reqParams.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleSaveParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id ;

    //角色id
    Long roleId;

    //用户id
    Long userId;

    //权限id
    Long permissionId;

    //角色名
    String roleName;
    //角色描述
    String roleDesc;
    //角色类型
    Boolean roleType;
}
