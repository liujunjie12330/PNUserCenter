package com.pn.service.impls.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;
import com.pn.dao.entity.PnRoleUser;
import com.pn.dao.mapper.PnRoleUserMapper;
import com.pn.service.RoleUserSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long save(UserRolePermissionSettingParam param){
        if(Objects.isNull(param.getRoleUserId())){
            insert(param);
        }
        return update(param);
    }



    private Long insert(UserRolePermissionSettingParam param){
        PnRoleUser roleUser = new PnRoleUser();
        roleUser.setUserId(param.getUserId());
        roleUser.setRoleId(param.getRoleId());
        this.baseMapper.insert(roleUser);
        return roleUser.getId();
    }

    private Long update(UserRolePermissionSettingParam param){
       PnRoleUser pnRoleUser = this.getById(param.getRoleUserId());
        if(Objects.isNull(pnRoleUser)){
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        pnRoleUser.setRoleId(param.getRoleId());
        pnRoleUser.setUserId(param.getUserId());
        updateById(pnRoleUser);
        return pnRoleUser.getId();
    }

    @Override
    public Page<SearchUserPermissionBo> searchRoleOfUser(UserRolePermissionSettingParam param){
        Page<SearchUserPermissionBo> page = new Page<>(param.getCurrent(), param.getSize());
        Page<SearchUserPermissionBo> permissionBoPage = pnRoleUserMapper.searchUserPermission(page,param);
        return permissionBoPage;
    }
}
