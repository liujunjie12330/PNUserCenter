//package com.pn.service.impls.user;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.pn.common.base.UserTokenThreadHolder;
//import com.pn.common.enums.StatusCode;
//import com.pn.common.exception.BizException;
//import com.pn.common.reqParams.user.RoleParam;
//import com.pn.common.vos.login.UserVo;
//import com.pn.dao.entity.PnRole;
//import com.pn.dao.entity.PnRolePermission;
//import com.pn.dao.mapper.PnRolePermissionMapper;
//import com.pn.service.RolePermissionSettingService;
//import com.pn.service.utils.cover.UserRoleCoverUtil;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//
//
//@Service
//public class RolePermissionSettingServiceIml extends ServiceImpl<PnRolePermissionMapper, PnRolePermission> implements RolePermissionSettingService {
//
//    @Override
//    public Long save(RoleParam param){
//        if(Objects.isNull(param.getRoleDesc())){
//           return insert(param);
//        }
//        return update(param);
//
//    }
//
//    private Long insert(RoleParam param){
//        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
//        PnRolePermission rolePermission = UserRoleCoverUtil.paramCoverTooPnRolePermission(null, param, currentUser.getId());
//        return rolePermission.getId();
//    }
//
//    private Long update(RoleParam param){
//        PnRolePermission rolePermission = getById(param.getRoleId());
//        if(Objects.isNull(rolePermission)){
//            throw new BizException(StatusCode.PARAMS_ERROR);
//        }
//        rolePermission.setRoleId(param.getRoleId());
//        rolePermission.setRoleId(param.getRoleId());
//        updateById(rolePermission);
//        return param.getId();
//    }
//
//
//}
