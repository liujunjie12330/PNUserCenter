package com.pn.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.common.reqParams.user.RoleParam;
import com.pn.dao.entity.PnRole;

public interface RoleSettingService extends IService<PnRole>{


    Long save(RoleParam param);
}
