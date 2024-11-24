package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.utils.ResultUtils;
import com.pn.dao.dto.register.UserRegisterDTO;
import com.pn.dao.dto.register.UserRegisterRespDTO;
import com.pn.service.pnservice.register.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: javadadi
 * @Time: 19:12
 * @ClassName: RegisterController
 */
@RestController
public class RegisterController {

    @Autowired
    private  UserRegisterService userRegisterService;

    @PostMapping(PNUserCenterConstant.BASE_URL+"/register")
    public BaseResponse<UserRegisterRespDTO> register(@RequestBody UserRegisterDTO userRegisterDTO){
        return ResultUtils.success(userRegisterService.register(userRegisterDTO));
    }
}
