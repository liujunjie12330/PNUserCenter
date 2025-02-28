package com.pn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
public interface RoleUserSettingService {

    @Transactional(rollbackFor = Exception.class)
    Long save(UserRolePermissionSettingParam param);


    Page<SearchUserPermissionBo> searchRoleOfUser(UserRolePermissionSettingParam param);
}
