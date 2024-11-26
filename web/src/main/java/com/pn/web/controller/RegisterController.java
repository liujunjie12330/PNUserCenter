package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.params.register.UserDeletionReqParam;
import com.pn.common.utils.ResultUtils;
import com.pn.common.params.register.UserRegisterParam;
import com.pn.common.vos.register.UserRegisterRespVo;
import com.pn.service.pnservice.register.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(PNUserCenterConstant.BASE_URL + "user")
public class RegisterController {

    @Resource
    private UserRegisterService userRegisterService;

    /**
     * 用户注册
     *
     * @param param
     * @return
     */
    @PostMapping(PNUserCenterConstant.BASE_URL + "/register")
    public BaseResponse<UserRegisterRespVo> register(@RequestBody UserRegisterParam param) {
        String username = param.getUsername();
        String password = param.getPassword();
        String checkPassword = param.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BizException(StatusCode.PARAMS_ERROR);
        }
        if (!checkPassword.equals(password)) {
            throw new BizException(StatusCode.PASSWORD_NOT_EQUALS);
        }
        return ResultUtils.success(userRegisterService.register(param));
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
