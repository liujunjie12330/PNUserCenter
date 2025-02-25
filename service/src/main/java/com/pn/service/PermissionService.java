package com.pn.service;

import com.pn.common.reqParams.user.PermissionSaveParam;

/**
 * @version 1.0
 * @Author：alex
 * @Date：2025/2/25 17:11
 * @Descirption xxx
 */
public interface PermissionService {
    Long save(PermissionSaveParam param);

    Long insert(PermissionSaveParam param);

    Long update(PermissionSaveParam param);
}
