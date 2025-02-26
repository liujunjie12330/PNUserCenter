package com.pn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pn.common.reqParams.user.RoleSaveParam;
import com.pn.dao.bo.user.SearchUserPermissionBo;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/26 15:03
 * @Descirption xxx
 */
public interface RoleUserSettingService {

    Long save(RoleSaveParam param);

    Page<SearchUserPermissionBo> searchRoleUser(RoleSaveParam param);
}
