package com.pn.service.utils.cover;

import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.entity.PnRole;
import com.pn.dao.entity.PnRolePermission;

import java.util.Objects;

/**
 * 用户相关转换
 */
public class UserRoleCoverUtil {

    public static PnRole paramCoverToPnRole(PnRole pnRole, UserRolePermissionSettingParam param, Long userId) {
        if(Objects.isNull(pnRole)){
            pnRole = new PnRole();
        }
        pnRole.setRoleName(param.getRoleName());
        pnRole.setRoleType(param.getRoleType());
        pnRole.setRoleDesc(param.getRoleDesc());
        pnRole.setCreateBy(userId);
        return pnRole;
    }

    public static PnRolePermission paramCoverToPnRolePermission(PnRolePermission rolePermission, UserRolePermissionSettingParam param, Long userId) {
        if(Objects.isNull(rolePermission)){
           rolePermission = new PnRolePermission();
        }
        rolePermission.setPermissionId(param.getPermissionId());
        rolePermission.setRoleId(param.getRoleId());
        rolePermission.setCreateBy(userId);
        return rolePermission;
    }


}
