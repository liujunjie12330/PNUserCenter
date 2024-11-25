package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.utils.ResultUtils;
import com.pn.dao.dto.register.UserDeletionReqDTO;
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

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping(PNUserCenterConstant.BASE_URL+"/register")
    public BaseResponse<UserRegisterRespDTO> register(@RequestBody UserRegisterDTO userRegisterDTO){
        return ResultUtils.success(userRegisterService.register(userRegisterDTO));
    }

    /**
     * 用户注销
     * @param userDeletionReqDTO
     * @return
     */
    @PostMapping(PNUserCenterConstant.BASE_URL+"/deletion")
    public BaseResponse<Void> deletion(@RequestBody UserDeletionReqDTO userDeletionReqDTO){
        userRegisterService.deletion(userDeletionReqDTO);
        return ResultUtils.success(null);
    }
}
