package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.utils.ResultUtils;
import com.pn.common.reqParams.register.UserRegisterParam;
import com.pn.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: javadadi
 * @Time: 19:12
 * @ClassName: RegisterController
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL + "/user")
public class RegisterController {

    @Resource
    private UserRegisterService userRegisterService;

    /**
     * 用户注册
     *
     * @param param
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<String> register(@RequestBody UserRegisterParam param) {
        String username = param.getUsername();
        String password = param.getPassword();
        String checkPassword = param.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        if (!checkPassword.equals(password)) {
            throw new BizException(StatusCode.PASSWORD_NOT_EQUALS);
        }
        userRegisterService.register(param);
        return ResultUtils.success("ok");
    }

    /**
     * 用户注销
     *
     * @param userRegisterParam
     * @return
     */
    @PostMapping("/deletion")
    public BaseResponse<String> deletion(@RequestBody UserRegisterParam userRegisterParam) {
        userRegisterService.deletion(userRegisterParam);
        return ResultUtils.success("ok");
    }
}
