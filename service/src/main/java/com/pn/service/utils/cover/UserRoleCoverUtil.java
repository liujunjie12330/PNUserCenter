//package com.pn.service.utils.cover;
//
//import com.pn.common.reqParams.user.RoleParam;
//import com.pn.dao.entity.PnRole;
//import com.pn.dao.entity.PnRolePermission;
//
//import java.util.Objects;
//
///**
// * 用户相关转换
// */
//public class UserRoleCoverUtil {
//
//    public static PnRole paramCoverToPnRole(PnRole pnRole, RoleParam param, Long userId) {
//        if(Objects.isNull(pnRole)){
//            pnRole = new PnRole();
//        }
//        pnRole.setRoleName(param.getRoleName());
//        pnRole.setRoleType(param.getRoleType());
//        pnRole.setRoleDesc(param.getRoleDesc());
//        pnRole.setCreateBy(userId);
//        return pnRole;
//    }
//
//    public static PnRolePermission paramCoverTooPnRolePermission(PnRolePermission rolePermission, RoleParam param, Long userId) {
//        if(Objects.isNull(pnRole)){
//            pnRole = new PnRole();
//        }
//        pnRole.setRoleName(param.getRoleName());
//        pnRole.setRoleType(param.getRoleType());
//        pnRole.setRoleDesc(param.getRoleDesc());
//        pnRole.setCreateBy(userId);
//        return pnRole;
//    }
//}
