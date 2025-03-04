package com.pn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pn.dao.entity.PnUser;

/**
 * 用户操作
 */
public interface UserSettingService  {
    void bindUserAlipay(String authCode, Long userId);
}
