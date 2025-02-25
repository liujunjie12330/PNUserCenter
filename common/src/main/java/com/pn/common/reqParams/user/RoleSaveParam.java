package com.pn.common.reqParams.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleSaveParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long roleId;
    //角色名
    String roleName;
    //角色描述
    String roleDesc;
    //角色类型
    Boolean roleType;
}
