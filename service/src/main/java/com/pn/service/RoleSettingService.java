package com.pn.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.user.UserRolePermissionSettingParam;
import com.pn.dao.entity.PnRole;
import org.springframework.transaction.annotation.Transactional;

public interface RoleSettingService extends IService<PnRole>{

    @Transactional(rollbackFor = Exception.class)
    Long save(UserRolePermissionSettingParam param);
}
