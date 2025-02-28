package com.pn.service.impls.user;

import com.pn.common.reqParams.user.UserRolePermissionSettingParam;

/**
 * 用户操作模板类
 */
public abstract class AbstractUserService{

    public abstract Long insertRole(UserRolePermissionSettingParam param);
}
