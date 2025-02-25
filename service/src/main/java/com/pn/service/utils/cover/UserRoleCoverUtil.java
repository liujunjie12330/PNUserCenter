package com.pn.service.utils.cover;

import com.pn.common.reqParams.user.RoleSaveParam;
import com.pn.dao.entity.PnRole;

import java.util.Objects;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/25 16:04
 * @Descirption xxx
 */
public class UserRoleCoverUtil {
    public static PnRole paramConvertoPnRole(PnRole pnRole, RoleSaveParam param,Long userId) {
        if(Objects.isNull(param)){
            pnRole = new PnRole();
        }
        pnRole.setRoleName(param.getRoleName());
        pnRole.setRoleType(param.getRoleType());
        pnRole.setRoleDesc(param.getRoleDesc());
        pnRole.setCreateBy(userId);
        return pnRole;
    }
}
