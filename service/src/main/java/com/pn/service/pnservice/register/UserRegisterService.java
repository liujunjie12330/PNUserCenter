package com.pn.service.pnservice.register;

import com.pn.common.params.register.UserDeletionReqParam;
import com.pn.common.params.register.UserRegisterParam;
import com.pn.common.vos.register.UserRegisterRespVo;

public interface UserRegisterService {

    /**
     * 用户注册
     * @param userRegisterParam
     * @return
     */
    UserRegisterRespVo register(UserRegisterParam userRegisterParam);
    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @return 用户名是否存在返回结果
     */
    Boolean hasUsername(String username);

    void deletion(UserRegisterParam param);
}
