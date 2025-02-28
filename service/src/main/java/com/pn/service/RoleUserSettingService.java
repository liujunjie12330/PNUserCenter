package com.pn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.user.RoleParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;

/**
 *
 */
public interface RoleUserSettingService {

    Long save(RoleParam param);

    Page<SearchUserPermissionBo> searchRoleUser(RoleParam param);
}
