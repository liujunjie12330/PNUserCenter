package com.pn.web.controller;

import com.google.gson.Gson;
import com.pn.common.base.BaseResponse;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.enums.AuthEnum;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.params.login.UserLoginParams;
import com.pn.common.utils.ResultUtils;
import com.pn.service.authBean.AuthListBean;
import com.pn.service.pnservice.login.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: javadadi
 * @Time: 19:12
 * @ClassName: LoginController
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL + "user")
@Slf4j
public class LoginController {
    @Resource
    private UserLoginService loginService;

    @Resource
    private AuthListBean auth;

    @PostMapping("/loginByUsername")
    public BaseResponse<String> login(@RequestBody UserLoginParams params, HttpServletResponse response) {
        String username = params.getUsername();
        String password = params.getPassword();
        String code = params.getCode();
        if (StringUtils.isAnyBlank(username, password, code)) {
            throw new BizException(StatusCode.PASSWORD_ERROR);
        }
        String token = loginService.doLogin(username, password, code);
        response.setHeader("token", token);
        return ResultUtils.success("ok");
    }

    @GetMapping("loginByGitee")
    public void loginByGitee(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = auth.getAuthRequest(AuthEnum.GITEE.getName());
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorizeUrl);
    }

    @GetMapping("loginByGithub")
    public void loginByGithub(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = auth.getAuthRequest(AuthEnum.GITHUB.getName());
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        response.sendRedirect(authorizeUrl);
    }


    @GetMapping("/login/{resource}")
    public BaseResponse<String> login(@PathVariable("resource") String resource, AuthCallback callback, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = auth.getAuthRequest(resource);
        AuthResponse<AuthUser> authResponse = authRequest.login(callback);
        if (!authResponse.ok()) {
            throw new BizException(StatusCode.SYSTEM_ERROR);
        }
        AuthUser data = authResponse.getData();
        String token = loginService.doLogin(data);
        response.setHeader("token", token);
        return ResultUtils.success("ok");
    }


}
