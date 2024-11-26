package com.pn.web.controller;

import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.params.login.UserLoginParams;
import com.pn.common.utils.ResultUtils;
import com.pn.service.pnservice.login.Login3rdTarget;
import com.pn.service.pnservice.login.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: javadadi
 * @Time: 19:12
 * @ClassName: LoginController
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"user")
public class LoginController {
    @Resource
    private UserLoginService loginService;

    @Resource
    private Login3rdTarget login3rdTarget;

    @PostMapping("/login")
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

    @PostMapping("/user/login/third")
    public BaseResponse<String> loginByOAuth(@RequestParam String state, @RequestParam String code, HttpServletResponse response){
        if(StringUtils.isBlank(state) ){
            throw new BizException(StatusCode.PASSWORD_ERROR);
        }
        String token ="";
        if("GITEE".equals(state)){
            token = login3rdTarget.loginByGitee(code, state);
        }
        if( "GITHUB".equals(state)){
            token = login3rdTarget.loginByGitHub(code,state);
        }
        if("qq".equals(state)){
            token = login3rdTarget.loginByQQ("params");
        }
        if("wechat".equals(state)){
            token = login3rdTarget.loginByWechat("params");
        }
        response.setHeader("token",token);
        return ResultUtils.success("ok");
    }
}
