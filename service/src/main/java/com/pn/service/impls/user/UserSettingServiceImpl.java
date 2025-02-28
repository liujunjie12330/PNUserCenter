package com.pn.service.impls.user;

import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.entity.PnRole;
import com.pn.dao.mapper.PnRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
*
**/
@Service
public class UserSettingServiceImpl {

    @Resource
    private PnRoleMapper roleMapper;

    AbstractUserService userTemplate = new AbstractUserService() {

        @Override
        public Long insertRole(UserRolePermissionSettingParam param) {
            PnRole pnRole = new PnRole();
            roleMapper.insert(pnRole);
            return pnRole.getId();
        }
    };
}
