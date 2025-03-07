package com.pn.web.controller.user;


import com.pn.common.base.BaseResponse;
import com.pn.common.base.UserTokenThreadHolder;
import com.pn.common.constant.PNUserCenterConstant;
import com.pn.common.utils.ResultUtils;
import com.pn.common.vos.login.UserVo;
import com.pn.dao.mapper.PnAlipayUserInfoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author javadadi
 */
@RestController
@RequestMapping(PNUserCenterConstant.BASE_URL+"/userSetting")
public class UserSettingController {

    @Resource
    PnAlipayUserInfoMapper pnAlipayUserInfoMapper;

    @Value("${alipay.appid}")
    private String appid;

    @Value("${alipay.notifyUrl}")
    private String callBack;

    @GetMapping("/bindUserAlipay")
    public void bindAlipayAccount(HttpServletResponse response){
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        try {
            response.sendRedirect("https://openauth-sandbox.dl.alipaydev.com/oauth2/publicAppAuthorize.htm?"
                    +"app_id="+appid
                    +"&scope=auth_user,auth_base"
                    +"&userId="+currentUser.getId()
                    +"&redirect_uri="+callBack);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/existAlipayUser")
    public BaseResponse<Boolean> existAlipayUserInfo(HttpServletRequest request){
        UserVo currentUser = UserTokenThreadHolder.getCurrentUser();
        Boolean res = pnAlipayUserInfoMapper.existUser(currentUser.getId());
        return ResultUtils.success(res);
    }
}
