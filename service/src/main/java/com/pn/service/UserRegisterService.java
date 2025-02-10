package com.pn.service;

import com.pn.common.reqParams.register.UserRegisterParam;


public interface UserRegisterService {

    /**
     * 用户注册
     * @param userRegisterParam
     * @return
     */
    void register(UserRegisterParam userRegisterParam);
    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @return 用户名是否存在返回结果
     */
    Boolean hasUsername(String username);

    /**
     * 用户注销
     * @param param
     */
    void deletion(UserRegisterParam param);
}
