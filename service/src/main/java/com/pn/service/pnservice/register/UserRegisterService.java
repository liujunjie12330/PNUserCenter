package com.pn.service.pnservice.register;

import com.pn.dao.dto.register.UserRegisterDTO;
import com.pn.dao.dto.register.UserRegisterRespDTO;
import org.springframework.stereotype.Service;

public interface UserRegisterService {

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    UserRegisterRespDTO register(UserRegisterDTO userRegisterDTO);
    /**
     * 用户名是否存在
     *
     * @param username 用户名
     * @return 用户名是否存在返回结果
     */
    Boolean hasUsername(String username);
}
