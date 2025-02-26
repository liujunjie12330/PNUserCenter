package com.pn.service.impls.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.user.RoleSaveParam;
import com.pn.dao.entity.PnRolePermission;
import com.pn.dao.mapper.PnRolePermissionMapper;
import com.pn.service.RolePermissonSettingService;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class RolePermissonSettingServiceIml extends ServiceImpl<PnRolePermissionMapper, PnRolePermission> implements RolePermissonSettingService {

    @Override
    public Long save(RoleSaveParam param){
        if(Objects.isNull(param.getId())){
            insert(param);
        }
        return update(param);

    }

    private Long insert(RoleSaveParam param){
        PnRolePermission rolePermission = new PnRolePermission();
        rolePermission.setPermissionId(param.getPermissionId());
        rolePermission.setRoleId(param.getRoleId());
        this.baseMapper.insert(rolePermission);
        return param.getId();
    }

    private Long update(RoleSaveParam param){
        PnRolePermission rolePermission = this.getById(param.getId());
        if(Objects.isNull(rolePermission)){
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        rolePermission.setRoleId(param.getRoleId());
        rolePermission.setRoleId(param.getRoleId());
        updateById(rolePermission);
        return param.getId();
    }


}
