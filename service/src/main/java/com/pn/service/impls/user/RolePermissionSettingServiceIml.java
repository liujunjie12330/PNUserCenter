package com.pn.service.impls.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnRolePermission;
import com.pn.dao.mapper.PnRolePermissionMapper;
import com.pn.service.RolePermissionSettingService;
import com.pn.service.utils.cover.UserRoleCoverUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class RolePermissionSettingServiceIml extends ServiceImpl<PnRolePermissionMapper, PnRolePermission> implements RolePermissionSettingService {

    @Override
    public Long save(UserRolePermissionSettingParam param){
        if(Objects.isNull(param.getRolePermissionId())){
           return insert(param);
        }
        return update(param);

    }

    private Long insert(UserRolePermissionSettingParam param){
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        PnRolePermission rolePermission = UserRoleCoverUtil.paramCoverToPnRolePermission(null, param, currentUser.getId());
        this.baseMapper.insert(rolePermission);
        return rolePermission.getId();
    }

    private Long update(UserRolePermissionSettingParam param){
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        PnRolePermission rolePermission = getById(param.getRolePermissionId());
        if(Objects.isNull(rolePermission)){
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        UserRoleCoverUtil.paramCoverToPnRolePermission(rolePermission, param, currentUser.getId());
        updateById(rolePermission);
        return rolePermission.getId() ;
    }
}
