package com.pn.service.impls.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.entity.PnUser;
import com.pn.dao.mapper.PnUserMapper;
import com.pn.service.UserSettingService;

/**
 * 用户操作模板类
 */
public abstract class AbstractUserService{

    public final UserVo getUserVo(){
        return UserTokenThreadHolder.getCurrentUser();
    }
}
