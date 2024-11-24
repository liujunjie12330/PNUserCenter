package com.pn.service.pnservice.register;

import com.pn.dao.dto.register.UserRegisterDTO;
import com.pn.dao.dto.register.UserRegisterRespDTO;
import org.springframework.stereotype.Service;

public interface UserRegisterService {
    UserRegisterRespDTO register(UserRegisterDTO userRegisterDTO);
}
