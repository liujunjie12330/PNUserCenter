package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.params.login.UserLoginParams;
import com.pn.common.utils.ResultUtils;
import com.pn.service.pnservice.login.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: javadadi
 * @Time: 19:12
 * @ClassName: LoginController
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL)
public class LoginController {
    @Resource
    private UserLoginService loginService;

    @PostMapping("/user/login")
    public BaseResponse<String> login(@RequestBody UserLoginParams params, HttpServletResponse response){
     String username = params.getUsername();
     String password = params.getPassword();
     String code = params.getCode();
     if (StringUtils.isAnyBlank(username,password,code)){
         throw  new BizException(StatusCode.PASSWORD_ERROR);
     }
        String token = loginService.doLogin(username, password, code);
        response.setHeader("token",token);
        return ResultUtils.success("ok");
    }
}
