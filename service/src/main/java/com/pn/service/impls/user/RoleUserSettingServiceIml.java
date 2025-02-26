package com.pn.service.impls.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.user.RoleSaveParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;
import com.pn.dao.entity.PnRoleUser;
import com.pn.dao.mapper.PnRoleUserMapper;
import com.pn.service.RoleUserSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/26 14:39
 * @Descirption 用户角色关联
 */
@Service
public class RoleUserSettingServiceIml extends ServiceImpl<PnRoleUserMapper, PnRoleUser> implements RoleUserSettingService {

    @Resource
    private  PnRoleUserMapper pnRoleUserMapper;

    @Override
    public Long save(RoleSaveParam param){
        if(Objects.isNull(param.getId())){
            insert(param);
        }
        return update(param);

    }

    private Long insert(RoleSaveParam param){
        PnRoleUser roleUser = new PnRoleUser();
        roleUser.setUserId(param.getUserId());
        roleUser.setRoleId(param.getRoleId());
        this.baseMapper.insert(roleUser);
        return param.getId();
    }

    private Long update(RoleSaveParam param){
        PnRoleUser roleUser = this.getById(param.getId());
        if(Objects.isNull(roleUser)){
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        roleUser.setRoleId(param.getRoleId());
        roleUser.setRoleId(param.getRoleId());
        updateById(roleUser);
        return param.getId();
    }



    @Override
    public Page<SearchUserPermissionBo> searchRoleUser(RoleSaveParam param){
        Page<SearchUserPermissionBo> page = new Page<>();
        page.setCurrent(1L);
        page.setSize(10L);
        Page<SearchUserPermissionBo> permissionBoPage = pnRoleUserMapper.searchUserPermission(page,param);
        return permissionBoPage;
    }
}
